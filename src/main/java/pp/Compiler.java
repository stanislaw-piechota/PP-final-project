package pp;

import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;
import pp.errors.ErrorListener;
import pp.grammar.LanguageBaseVisitor;
import pp.grammar.LanguageParser;
import pp.helpers.CustomStringBuilder;
import pp.types.OpName;
import pp.types.Operations;
import pp.types.Type;
import pp.types.TypeName;

import static pp.types.OpName.*;

public class Compiler extends LanguageBaseVisitor<Type> {
    private final ErrorListener errorListener = new ErrorListener();
    private final SymbolTable symbolTable = new SymbolTable();

    private final CustomStringBuilder builder = new CustomStringBuilder();

    public String compile(ParseTree tree) {
        visit(tree);

        if (errorListener.hasErrors()) {
            System.err.println(errorListener.getErrors());
            errorListener.throwException();
        }

        return builder.toString();
    }

    @Override
    public Type visitProgram(LanguageParser.ProgramContext ctx) {
        builder.append("{\"program\":{\"children\":[");
        visit(ctx.block());
        builder.append("]}}");

        return null;
    }

    @Override
    public Type visitExplicitDeclaration(LanguageParser.ExplicitDeclarationContext ctx) {
        String varName = ctx.ID().getText();
        String varType = ctx.TYPE().getText();
        builder.append(String.format("{\"decl\":{\"name\":\"%s\",\"type\":\"%s\",\"children\":[", varName, varType), false);

        Type value = null;
        if (ctx.expression() != null)
            value = visit(ctx.expression());

        if (value == null || !value.valuePresent())
            value = new Type(TypeName.fromTypeName(varType), false);
        else if (!value.type().getTypeName().equals(varType))
            errorListener.syntaxError(
                    ctx.expression().getStart(),
                    String.format("type mismatch (expected `%s`, actual `%s`)", varType, value.type().getTypeName())
            );

        if (symbolTable.getLatest(varName) != null)
            errorListener.syntaxError(
                    ctx.ID().getSymbol(),
                    String.format("invalid redeclaration of variable `%s`", varName)
            );
        else
            symbolTable.put(varName, value);

        builder.append("]}},");
        return null;
    }

    @Override
    public Type visitImplicitDeclaration(LanguageParser.ImplicitDeclarationContext ctx) {
        String varName = ctx.ID().getText();
        builder.append(String.format("{\"decl\":{\"name\":\"%s\",\"children\":[", varName), false);

        Type value = visit(ctx.expression());
        if (value == null || !value.valuePresent())
            errorListener.syntaxError(
                    ctx.expression().getStart(),
                    String.format("attempted type inference of variable `%s` with undefined literal", varName)
            );
        else if (symbolTable.getLatest(varName) != null)
            errorListener.syntaxError(
                    ctx.ID().getSymbol(),
                    String.format("invalid redeclaration of variable `%s`", varName)
            );
        else {
            builder.append(String.format("],\"type\":\"%s\"}},", value.type().getTypeName()));
            symbolTable.put(varName, value);
        }

        return value;
    }

    @Override
    public Type visitInt(LanguageParser.IntContext ctx) {
        builder.append(ctx.getText(), false);
        return new Type(TypeName.INT, true);
    }

    @Override
    public Type visitBool(LanguageParser.BoolContext ctx) {
        builder.append(ctx.getText(), false);
        return new Type(TypeName.BOOL, true);
    }

    @Override
    public Type visitLiteral(LanguageParser.LiteralContext ctx) {
        if (ctx.int_() != null) {
            return visit(ctx.int_());
        } else if (ctx.bool() != null) {
            return visit(ctx.bool());
        }
        return null;
    }

    @Override
    public Type visitExprLit(LanguageParser.ExprLitContext ctx) {
        Type value = visit(ctx.literal());
        builder.append(',');
        return value;
    }

    @Override
    public Type visitAssignment(LanguageParser.AssignmentContext ctx) {
        String varName = ctx.ID().getText();

        if (symbolTable.get(varName) == null)
            errorListener.syntaxError(
                    ctx.ID().getSymbol(),
                    String.format("assignment of undeclared variable `%s`", varName)
            );

        builder.append(String.format("{\"set\":{\"name\":\"%s\",\"children\":[", varName), false);

        Type currentValue = symbolTable.get(varName);
        Type newValue = visit(ctx.expression());
        if (currentValue == null)
            errorListener.syntaxError(
                    ctx.ID().getSymbol(),
                    String.format("attempted reference of undefined variable `%s`", varName)
            );
        else if (newValue == null || !newValue.valuePresent())
            errorListener.syntaxError(
                    ctx.expression().getStart(),
                    String.format("attempted assignment of undefined literal to variable `%s`", varName)
            );
        else if (!newValue.type().equals(currentValue.type()))
            errorListener.syntaxError(
                    ctx.expression().getStart(),
                    String.format("type mismatch (expected `%s`, actual `%s`)",
                            currentValue.type().getTypeName(),
                            newValue.type().getTypeName())
            );
        else {
            Type newLiteral = new Type(newValue.type(), true);
            symbolTable.put(varName, newLiteral, false);
            builder.append("]}},");
            return newLiteral;
        }

        return null;
    }

    @Override
    public Type visitExprId(LanguageParser.ExprIdContext ctx) {
        String varName = ctx.ID().getText();
        Type value = symbolTable.get(varName);

        if (value == null)
            errorListener.syntaxError(
                    ctx.ID().getSymbol(),
                    String.format("usage of undeclared variable `%s`", varName)
            );
        else if (!value.valuePresent())
            errorListener.syntaxError(
                    ctx.ID().getSymbol(),
                    String.format("usage of uninitialised variable `%s`", varName)
            );
        else {
            builder.append(String.format("{\"get\":{\"children\":[\"%s\"]}},", varName), false);
            return value;
        }

        return null;
    }

    @Override
    public Type visitPar(LanguageParser.ParContext ctx) {
        return visit(ctx.expression());
    }

    @Override
    public Type visitAddition(LanguageParser.AdditionContext ctx) {
        return evaluateExprVisit(ADD, ctx.expression(0), ctx.expression(1), ctx.ADD());
    }

    @Override
    public Type visitSubtraction(LanguageParser.SubtractionContext ctx) {
        return evaluateExprVisit(SUB, ctx.expression(0), ctx.expression(1), ctx.SUB());
    }

    @Override
    public Type visitMultiplication(LanguageParser.MultiplicationContext ctx) {
        return evaluateExprVisit(MUL, ctx.expression(0), ctx.expression(1), ctx.TIMES());
    }

    @Override
    public Type visitAnd(LanguageParser.AndContext ctx) {
        return evaluateExprVisit(AND, ctx.expression(0), ctx.expression(1), ctx.AND());
    }

    @Override
    public Type visitOr(LanguageParser.OrContext ctx) {
        return evaluateExprVisit(OR, ctx.expression(0), ctx.expression(1), ctx.OR());
    }

    @Override
    public Type visitEqual(LanguageParser.EqualContext ctx) {
        return evaluateExprVisit(EQ, ctx.expression(0), ctx.expression(1), ctx.EQUAL());
    }

    @Override
    public Type visitNotEqual(LanguageParser.NotEqualContext ctx) {
        return evaluateExprVisit(NEQ, ctx.expression(0), ctx.expression(1), ctx.NOT_EQUAL());
    }

    @Override
    public Type visitLt(LanguageParser.LtContext ctx) {
        return evaluateExprVisit(LT, ctx.expression(0), ctx.expression(1), ctx.LT());
    }

    @Override
    public Type visitGt(LanguageParser.GtContext ctx) {
        return evaluateExprVisit(GT, ctx.expression(0), ctx.expression(1), ctx.GT());
    }
    @Override
    public Type visitGe(LanguageParser.GeContext ctx) {
        return evaluateExprVisit(GE, ctx.expression(0), ctx.expression(1), ctx.GE());
    }

    @Override
    public Type visitLe(LanguageParser.LeContext ctx) {
        return evaluateExprVisit(LE, ctx.expression(0), ctx.expression(1), ctx.LE());
    }


    private Type evaluateExprVisit(
            OpName opName,
            LanguageParser.ExpressionContext leftCtx,
            LanguageParser.ExpressionContext rightCtx,
            TerminalNode opCtx
    ) {
        builder.append(String.format("{\"%s\":{\"children\":[", opName.getOpName()), false);

        Type left = visit(leftCtx);
        Type right = visit(rightCtx);
        TypeName result;

        if (left == null || !left.valuePresent())
            errorListener.syntaxError(
                    leftCtx.getStart(),
                    "attempted operation with undefined value"
            );
        else if (right == null || !right.valuePresent())
            errorListener.syntaxError(
                    rightCtx.getStart(),
                    "attempted operation with undefined value"
            );
        else if ((result = Operations.getResultType(opName, left.type(), right.type())) == null)
            errorListener.syntaxError(
                    opCtx.getSymbol(),
                    "type mismatch in operation"
            );
        else {
            builder.append("]}},");
            return new Type(result, true);
        }

        return null;
    }

    @Override
    public Type visitPrint(LanguageParser.PrintContext ctx) {
        builder.append("{\"print\":{\"children\":[", false);
        visit(ctx.expression());
        builder.append("]}}");

        return null;
    }

    @Override
    public Type visitConditional(LanguageParser.ConditionalContext ctx) {
        builder.append('{');
        visit(ctx.if_());

        builder.append("\"elifs\":[", false);
        for (LanguageParser.ElseifContext elseif : ctx.elseif()) {
            visit(elseif);
        }
        builder.append("],\"else\":{");
        if (ctx.else_() != null)
            visit(ctx.else_());
        builder.append("}},");
        return null;
    }

    @Override
    public Type visitIf (LanguageParser.IfContext ctx) {
        builder.append("\"if\":{\"cond\":{\"children\":[", false);
        visit(ctx.expression());
        builder.append("]},\"children\":[");
        visitBlock(ctx.statement(), ctx.block());
        builder.append("]},");
        return null;
    }

    @Override
    public Type visitElseif (LanguageParser.ElseifContext ctx) {
        builder.append("{\"elif\":{\"cond\":{\"children\":[", false);
        visit(ctx.expression());
        builder.append("]},\"children\":[");
        visitBlock(ctx.statement(), ctx.block());
        builder.append("]}},");
        return null;
    }

    @Override
    public Type visitElse(LanguageParser.ElseContext ctx) {
        builder.append("\"children\":[", false);
        visitBlock(ctx.statement(), ctx.block());
        builder.append("],");
        return null;
    }

    @Override
    public Type visitWhileLoop(LanguageParser.WhileLoopContext ctx) {
        builder.append("{\"while\":{\"cond\":{\"children\":[",false);
        visit(ctx.expression());
        builder.append("]},\"children\":[");
        visitBlock(ctx.statement(), ctx.block());
        builder.append("]}},");
        return null;
    }

    private void visitBlock(LanguageParser.StatementContext stmnt, LanguageParser.BlockContext block) {
        symbolTable.addLevel();
        if (stmnt != null) {
            visit(stmnt);
        } else
            visit(block);
        symbolTable.removeLevel();
    }
}
