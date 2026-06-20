package pp;

import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;
import pp.errors.ErrorListener;
import pp.grammar.LanguageBaseVisitor;
import pp.grammar.LanguageParser;
import pp.helpers.CustomStringBuilder;
import pp.tables.Coordinate;
import pp.tables.SymbolTable;
import pp.types.Operation;
import pp.types.Type;
import pp.types.TypeName;

import java.util.ArrayList;
import java.util.List;

import static pp.types.Operation.*;
import static pp.types.TypeName.*;

public class Compiler extends LanguageBaseVisitor<Type> {
    private final ErrorListener errorListener = new ErrorListener();
    private final SymbolTable symbolTable = new SymbolTable();
    private final CustomStringBuilder builder = new CustomStringBuilder();
    private boolean isFunctionBlock = false;

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

        if (value == null || value.empty())
            value = new Type(TypeName.fromTypeName(varType), false);
        else if (!value.typeName().toString().equals(varType))
            errorListener.syntaxError(
                    ctx.expression().getStart(),
                    String.format("type mismatch (expected `%s`, actual `%s`)", varType, value.typeName().toString())
            );

        if (symbolTable.getLatest(varName) != null)
            errorListener.syntaxError(
                    ctx.ID().getSymbol(),
                    String.format("invalid redeclaration of variable `%s`", varName)
            );
        else {
            Coordinate newCoordinate = symbolTable.put(varName, value);
            builder.append(String.format("],\"coordinate\":{\"level\":%s,\"offset\":%s}}},",
                    newCoordinate.level(), newCoordinate.offset()));
        }

        return null;
    }

    @Override
    public Type visitImplicitDeclaration(LanguageParser.ImplicitDeclarationContext ctx) {
        String varName = ctx.ID().getText();
        builder.append(String.format("{\"decl\":{\"name\":\"%s\",\"children\":[", varName), false);

        Type value = visit(ctx.expression());
        if (value == null || value.empty())
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
            Coordinate newCoordinate = symbolTable.put(varName, value);
            builder.append(String.format("],\"type\":\"%s\",\"coordinate\":{\"level\":%s,\"offset\":%s}}},",
                    value.typeName().toString(), newCoordinate.level(), newCoordinate.offset()));
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
        Coordinate currentValue = symbolTable.get(varName);

        if (currentValue == null)
            errorListener.syntaxError(
                    ctx.ID().getSymbol(),
                    String.format("assignment of undeclared variable `%s`", varName)
            );

        builder.append(String.format("{\"set\":{\"name\":\"%s\",\"children\":[", varName), false);

        Type newValue = visit(ctx.expression());
        if (currentValue == null)
            errorListener.syntaxError(
                    ctx.ID().getSymbol(),
                    String.format("attempted reference of undefined variable `%s`", varName)
            );
        else if (newValue == null || newValue.empty())
            errorListener.syntaxError(
                    ctx.expression().getStart(),
                    String.format("attempted assignment of undefined literal to variable `%s`", varName)
            );
        else if (!newValue.typeName().equals(currentValue.type().typeName()))
            errorListener.syntaxError(
                    ctx.expression().getStart(),
                    String.format("type mismatch (expected `%s`, actual `%s`)",
                            currentValue.type().typeName().toString(),
                            newValue.typeName().toString())
            );
        else {
            Type newLiteral = new Type(newValue.typeName(), true);
            Coordinate coordinate = symbolTable.put(varName, newLiteral, false);
            builder.append(String.format("],\"coordinate\":{\"level\":%s,\"offset\":%s}}},",
                    coordinate.level(), coordinate.offset()));
            return newLiteral;
        }

        return null;
    }

    @Override
    public Type visitExprId(LanguageParser.ExprIdContext ctx) {
        String varName = ctx.ID().getText();
        Coordinate coordinate = symbolTable.get(varName);
        Type value = coordinate.type();

        if (value == null)
            errorListener.syntaxError(
                    ctx.ID().getSymbol(),
                    String.format("usage of undeclared variable `%s`", varName)
            );
        else if (value.empty())
            errorListener.syntaxError(
                    ctx.ID().getSymbol(),
                    String.format("usage of uninitialised variable `%s`", varName)
            );
        else {
            builder.append(String.format("{\"get\":{\"name\":\"%s\",\"coordinate\":{\"level\":%s,\"offset\":%s}}},",
                    varName, coordinate.level(), coordinate.offset()), false);
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
            Operation opName,
            LanguageParser.ExpressionContext leftCtx,
            LanguageParser.ExpressionContext rightCtx,
            TerminalNode opCtx
    ) {
        builder.append(String.format("{\"%s\":{\"children\":[", opName.getOpName()), false);

        Type left = visit(leftCtx);
        Type right = visit(rightCtx);
        TypeName result;

        if (left == null || left.empty())
            errorListener.syntaxError(
                    leftCtx.getStart(),
                    "attempted operation with undefined value"
            );
        else if (right == null || right.empty())
            errorListener.syntaxError(
                    rightCtx.getStart(),
                    "attempted operation with undefined value"
            );
        else if ((result = opName.getResultType(left.typeName(), right.typeName())) == null)
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
        Type value = visit(ctx.expression());
        if (value == null || value.empty())
            errorListener.syntaxError(
                    ctx.expression().getStart(),
                    "empty print not yet supported"
            );
        else if (PRINT.getResultType(value.typeName()) == null)
            errorListener.syntaxError(
                    ctx.expression().getStart(),
                    String.format("unable to print expression of type `%s`", value.typeName().toString())
            );
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
        symbolTable.addScope();
        if (stmnt != null) {
            visit(stmnt);
        } else
            visit(block);
        symbolTable.removeScope();
    }

    @Override
    public Type visitFuncDef(LanguageParser.FuncDefContext ctx) {
        String funcName = ctx.ID(0).getText();

        if (symbolTable.get(funcName) != null) {
            errorListener.syntaxError(
                    ctx.ID(0).getSymbol(),
                    String.format("function/variable with name `%s` already exists", funcName)
            );
            return null;
        }

        TypeName returnType = TypeName.fromTypeName(ctx.TYPE().getLast().getText());
        assert returnType != null;
        builder.append(String.format("{\"func\":{\"name\":\"%s\",\"type\":\"%s\",\"args\":[",
                funcName, returnType), false);

        symbolTable.addLevel();
        List<TypeName> argTypes = new ArrayList<>();
        for (int i=1; i<ctx.ID().size(); i++) {
            String argName = ctx.ID(i).getText();
            TypeName argType = TypeName.fromTypeName(ctx.TYPE(i-1).getText());
            argTypes.add(argType);
            Coordinate newCoordinate = symbolTable.put(argName, new Type(argType, true)); // TODO: add support for passing function as argument

            assert argType != null;
            builder.append(String.format("{\"arg\":{\"name\":\"%s\",\"type\":\"%s\",\"coordinate\":{\"level\":%s,\"offset\":%s}}},",
                    argName, argType, newCoordinate.level(), newCoordinate.offset()), false);
        }

        builder.append("],\"children\":[");
        visitFunctionBlock(ctx.block(), returnType);
        builder.append("]}},");
        symbolTable.removeLevel();

        Type funcSign = new Type(FUNC, returnType, argTypes, true);
        symbolTable.put(funcName, funcSign);

        return null;
    }

    public void visitFunctionBlock(LanguageParser.BlockContext ctx, TypeName expectedType) {
        isFunctionBlock = true;

        for (LanguageParser.StatementContext stmnt : ctx.statement()) {
            if (stmnt instanceof LanguageParser.ReturnContext) {
                Type value = visit(stmnt);
                if (value == null || value.empty()) // TODO: add support for void return
                    errorListener.syntaxError(
                            ((LanguageParser.ReturnContext) stmnt).expression().getStart(),
                            "empty return not yet supported"
                    );
                else if (value.typeName() != expectedType)
                    errorListener.syntaxError(
                            ((LanguageParser.ReturnContext) stmnt).expression().getStart(),
                            String.format("return type mismatch (declared `%s`, actual `%s)",
                                    expectedType.toString(), value.typeName().toString())
                    );
            } else
                visit(stmnt);
        }

        isFunctionBlock = false;
    }

    @Override
    public Type visitReturn(LanguageParser.ReturnContext ctx) {
        if (!isFunctionBlock)
            errorListener.syntaxError(
                    ctx.RETURN().getSymbol(),
                    "return outside of function context"
            );

        builder.append("{\"return\":{\"children\":[", false);
        Type value = visit(ctx.expression());
        builder.append("]}},");
        return value;
    }
}
