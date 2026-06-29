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

public class LanguageElaborator extends LanguageBaseVisitor<Type> {
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
        builder.append("{\"program\":[");
        visit(ctx.block());
        builder.append("]}");

        return null;
    }

    @Override
    public Type visitExplicitDeclaration(LanguageParser.ExplicitDeclarationContext ctx) {
        String varName = ctx.ID().getText();
        String varType = ctx.TYPE().getText();
        builder.append(String.format("{\"decl\":{\"name\":\"%s\",\"type\":\"%s\",\"expr\":", varName, varType), false);

        Type value = null;
        if (ctx.expression() != null)
            value = visit(ctx.expression());
        else
            builder.append("null", false);

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
            builder.append(String.format(",\"coordinate\":{\"level\":%s,\"offset\":%s}}},",
                    newCoordinate.level(), newCoordinate.offset()));
        }

        return null;
    }

    @Override
    public Type visitImplicitDeclaration(LanguageParser.ImplicitDeclarationContext ctx) {
        String varName = ctx.ID().getText();
        builder.append(String.format("{\"decl\":{\"name\":\"%s\",\"expr\":", varName), false);

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
            builder.append(String.format(",\"type\":\"%s\",\"coordinate\":{\"level\":%s,\"offset\":%s}}},",
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

        if (currentValue == null) {
            errorListener.syntaxError(
                    ctx.ID().getSymbol(),
                    String.format("assignment of undeclared variable `%s`", varName)
            );
            return null;
        }

        builder.append(String.format("{\"set\":{\"name\":\"%s\",\"expr\":", varName), false);

        Type newValue = visit(ctx.expression());
        if (newValue == null || newValue.empty())
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
            builder.append(String.format(",\"coordinate\":{\"level\":%s,\"offset\":%s}}},",
                    coordinate.level(), coordinate.offset()));
            return newLiteral;
        }

        return null;
    }

    @Override
    public Type visitExprId(LanguageParser.ExprIdContext ctx) {
        String varName = ctx.ID().getText();
        Coordinate coordinate = symbolTable.get(varName);
        Type value;

        if (coordinate == null || (value = coordinate.type()) == null)
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
        builder.append(String.format("{\"%s\":[", opName.getOpName()), false);

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
            builder.append("]},");
            return new Type(result, true);
        }

        return null;
    }

    @Override
    public Type visitPrint(LanguageParser.PrintContext ctx) {
        builder.append("{\"print\":", false);
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
        builder.append("},");

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
        builder.append("],\"else\":");
        if (ctx.else_() != null)
            visit(ctx.else_());
        else
            builder.append("null", false);
        builder.append("},");
        return null;
    }

    @Override
    public Type visitIf (LanguageParser.IfContext ctx) {
        builder.append("\"if\":{\"cond\":", false);
        visit(ctx.expression());
        builder.append(",\"children\":[");
        visitBlock(ctx.statement(), ctx.block());
        builder.append("]},");
        return null;
    }

    @Override
    public Type visitElseif (LanguageParser.ElseifContext ctx) {
        builder.append("{\"cond\":", false);
        visit(ctx.expression());
        builder.append(",\"children\":[");
        visitBlock(ctx.statement(), ctx.block());
        builder.append("]},");
        return null;
    }

    @Override
    public Type visitElse(LanguageParser.ElseContext ctx) {
        builder.append("{\"children\":[", false);
        visitBlock(ctx.statement(), ctx.block());
        builder.append("]},");
        return null;
    }

    @Override
    public Type visitWhileLoop(LanguageParser.WhileLoopContext ctx) {
        builder.append("{\"while\":{\"cond\":",false);
        visit(ctx.expression());
        builder.append(",\"children\":[");
        visitBlock(ctx.statement(), ctx.block());
        builder.append("]}},");
        return null;
    }

    private void visitBlock(LanguageParser.StatementContext statement, LanguageParser.BlockContext block) {
        symbolTable.addScope();
        if (statement != null) {
            visit(statement);
        } else if (block != null)
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
        builder.append(String.format("{\"function\":{\"name\":\"%s\",\"type\":\"%s\",\"args\":[",
                funcName, returnType), false);

        List<TypeName> argTypes = new ArrayList<>();
        for (int i=1; i<ctx.ID().size(); i++) {
            TypeName argType = TypeName.fromTypeName(ctx.TYPE(i-1).getText());
            argTypes.add(argType);
        } // separate loop for the sake of creating function signature before processing the block - recursive function

        Type funcSign = new Type(FUNC, returnType, argTypes, true);
        Coordinate newCoordinate = symbolTable.put(funcName, funcSign);

        symbolTable.addLevel();
        for (int i=1; i<ctx.ID().size(); i++) {
            String argName = ctx.ID(i).getText();
            TypeName argType = argTypes.get(i-1);
            Coordinate newArgCoordinate = symbolTable.put(argName, new Type(argType, true)); // TODO: add support for passing function as argument

            assert argType != null;
            builder.append(String.format("{\"name\":\"%s\",\"type\":\"%s\",\"coordinate\":{\"level\":%s,\"offset\":%s}},",
                    argName, argType, newArgCoordinate.level(), newArgCoordinate.offset()), false);
        }

        builder.append("],\"children\":[");
        visitFunctionBlock(ctx.block(), returnType);
        symbolTable.removeLevel();

        builder.append(String.format(
                "],\"coordinate\":{\"level\":%s,\"offset\":%s}}},", newCoordinate.level(), newCoordinate.offset()));

        return null;
    }

    public void visitFunctionBlock(LanguageParser.BlockContext ctx, TypeName expectedType) {
        int returnCount = 0;
        isFunctionBlock = true;

        for (LanguageParser.StatementContext statement : ctx.statement()) {
            if (statement instanceof LanguageParser.ReturnContext) {
                returnCount++;
                Type value = visit(statement);
                if (value == null || value.empty()) // TODO: add support for void return
                    errorListener.syntaxError(
                            ((LanguageParser.ReturnContext) statement).expression().getStart(),
                            "empty return not yet supported"
                    );
                else if (value.typeName() != expectedType)
                    errorListener.syntaxError(
                            ((LanguageParser.ReturnContext) statement).expression().getStart(),
                            String.format("return type mismatch (declared `%s`, actual `%s)",
                                    expectedType.toString(), value.typeName().toString())
                    );
            } else
                visit(statement);
        }

        if (returnCount == 0 && expectedType != null && expectedType != TypeName.VOID)
            errorListener.syntaxError(
                    ctx.getStart(),
                    "No return statement in not void function"
            );

        isFunctionBlock = false;
    }

    @Override
    public Type visitReturn(LanguageParser.ReturnContext ctx) {
        if (!isFunctionBlock)
            errorListener.syntaxError(
                    ctx.RETURN().getSymbol(),
                    "return outside of function context"
            );

        builder.append("{\"return\":", false);
        Type value = visit(ctx.expression());
        builder.append("},");
        return value;
    }

    @Override
    public Type visitFuncCall(LanguageParser.FuncCallContext ctx) {
        String funcName = ctx.ID().getText();
        Coordinate coordinate = symbolTable.get(funcName);

        if (coordinate == null) {
            errorListener.syntaxError(
                    ctx.ID().getSymbol(),
                    String.format("Undefined function call `%s`", funcName)
            );
            return null;
        } else if (coordinate.type().typeName() != FUNC) {
            errorListener.syntaxError(
                    ctx.ID().getSymbol(),
                    String.format("Type %s is not callable", coordinate.type().typeName().toString())
            );
            return null;
        }

        Type funcSign = coordinate.type();

        builder.append(String.format("{\"call\":{\"name\":\"%s\",\"type\":\"%s\",\"args\":[",
                funcName, funcSign.returnType()), false);
        if (coordinate.type().getArgs().size() != ctx.expression().size())
            errorListener.syntaxError(
                    ctx.LPAR().getSymbol(),
                    String.format(
                            "Argument count mismatch (expected %s, actual %s)",
                            coordinate.type().getArgs().size(),
                            ctx.expression().size()
                    )
            );
        else {
            for (int i=0; i<ctx.expression().size(); i++) {
                Type argType = visit(ctx.expression(i));
                if (argType == null)
                    errorListener.syntaxError(
                            ctx.expression(i).getStart(),
                            String.format("argument type mismatch (declared `%s`, actual `undefined`)",
                                    funcSign.getArgs().get(i))
                    );
                else if (argType.typeName() != funcSign.getArgs().get(i))
                    errorListener.syntaxError(
                            ctx.expression(i).getStart(),
                            String.format("argument type mismatch (declared `%s`, actual `%s`)",
                                    funcSign.getArgs().get(i), argType.typeName())
                    );
            }
        }
        builder.append(String.format(
                "],\"coordinate\":{\"level\":%s,\"offset\":%s}}},", coordinate.level(), coordinate.offset()));

        return new Type(funcSign.returnType(), true);
    }

    @Override
    public Type visitThreadStart(LanguageParser.ThreadStartContext ctx) {
        String targetName = ctx.ID().getText();
        Coordinate coordinate = symbolTable.get(targetName);

        if (coordinate == null) {
            errorListener.syntaxError(
                    ctx.ID().getSymbol(),
                    String.format("undefined target function `%s`", targetName)
            );
            return null;
        }

        Type funcSign = coordinate.type();
        TypeName resultType = FORK.getResultType(funcSign.typeName());
        if (resultType == null) {
            errorListener.syntaxError(
                    ctx.ID().getSymbol(),
                    String.format("invalid argument type (expected `function`, actual `%s`)", funcSign.typeName())
            );
            return null;
        }

        builder.append(String.format("{\"fork\":{\"target\":\"%s\",\"args\":[",
                targetName), false);

        for (int i=0; i<ctx.expression().size(); i++) {
            Type argType = visit(ctx.expression(i));
            TypeName expectedTypeName = funcSign.getArgs().get(i);

            if (argType == null)
                errorListener.syntaxError(
                        ctx.expression(i).getStart(),
                        "undefined value used as an argument"
                );
            else if (argType.empty() || argType.typeName() != expectedTypeName) {
                errorListener.syntaxError(
                        ctx.expression(i).getStart(),
                        String.format("type mismatch (declared `%s`, actual `%s`)", expectedTypeName, argType.typeName())
                );
            }
        }

        builder.append("]}},");
        return new Type(resultType, true);
    }

    @Override
    public Type visitThreadJoin(LanguageParser.ThreadJoinContext ctx) {
        builder.append("{\"join\":", false);
        String threadId = ctx.ID().getText();
        Coordinate coordinate = symbolTable.get(threadId);

        if (coordinate == null) {
            errorListener.syntaxError(
                    ctx.ID().getSymbol(),
                    String.format("undefined thread id `%s`", threadId)
            );
            return null;
        }

        TypeName resultType = JOIN.getResultType(coordinate.type().typeName());
        if (resultType == null)
            errorListener.syntaxError(
                    ctx.ID().getSymbol(),
                    String.format("type mismatch (expected `int`, actual `%s`)", coordinate.type().typeName())
            );

        builder.append(String.format(
                "{\"get\":{\"name\":\"%s\",\"type\":\"%s\",\"coordinate\":{\"level\":%s,\"offset\":%s}}}},",
                threadId, coordinate.type().typeName(), coordinate.level(), coordinate.offset()));
        return null;
    }

    @Override
    public Type visitLock(LanguageParser.LockContext ctx) {
        visitLock("lock", ctx.ID());
        return null;
    }

    @Override
    public Type visitUnlock(LanguageParser.UnlockContext ctx) {
        visitLock("unlock", ctx.ID());
        return null;
    }

    private void visitLock(String type, TerminalNode id) {
        String targetName = id.getText();
        Coordinate coordinate = symbolTable.get(targetName);

        if (coordinate == null) {
            errorListener.syntaxError(
                    id.getSymbol(),
                    String.format("undeclared variable `%s`", targetName)
            );
            return;
        }

        Operation op = Operation.valueOfOpName(type);
        assert op != null;
        TypeName resultType = op.getResultType(coordinate.type().typeName());
        if (resultType == null)
            errorListener.syntaxError(
                    id.getSymbol(),
                    String.format("type mismatch (expected `int`, actual `%s`)", coordinate.type().typeName())
            );

        builder.append(String.format(
                "{\"%s\":{\"name\":\"%s\",\"type\":\"%s\",\"coordinate\":{\"level\":%s,\"offset\":%s}}},",
                type, targetName, coordinate.type().typeName(), coordinate.level(), coordinate.offset()), false);
    }
}
