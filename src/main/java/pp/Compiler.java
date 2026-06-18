package pp;

import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeProperty;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.antlr.v4.runtime.tree.TerminalNode;
import pp.errors.ErrorListener;
import pp.grammar.LanguageBaseListener;
import pp.grammar.LanguageParser;
import pp.types.OpName;
import pp.types.Operations;
import pp.types.Type;
import pp.types.TypeName;

import java.util.HashMap;
import java.util.Map;

import static pp.types.OpName.*;

public class Compiler extends LanguageBaseListener {
    private final ErrorListener errorListener = new ErrorListener();
    private final ParseTreeProperty<Type> expressions = new ParseTreeProperty<>();
    private final Map<String, Type> variables = new HashMap<>();
    private final StringBuilder builder = new StringBuilder();

    public String compile(ParseTree tree) {
        new ParseTreeWalker().walk(this, tree);

        if (errorListener.hasErrors()) {
            System.err.println(errorListener.getErrors());
            errorListener.throwException();
        }

        return builder.toString();
    }

    @Override
    public void enterProgram(LanguageParser.ProgramContext ctx) {
        builder.append("{\"program\":{\"children\":[");
    }

    @Override
    public void exitProgram(LanguageParser.ProgramContext ctx) {
        if (builder.charAt(builder.length() - 1) == ',')
            builder.deleteCharAt(builder.length() - 1); // remove last comma
        builder.append("]}}");
    }

    @Override
    public void enterExplicitDeclaration(LanguageParser.ExplicitDeclarationContext ctx) {
        String varName = ctx.ID().getText();
        String varType = ctx.TYPE().getText();
        builder.append(String.format("{\"decl\":{\"name\":\"%s\",\"type\":\"%s\",\"children\":[", varName, varType));
    }

    @Override
    public void exitExplicitDeclaration(LanguageParser.ExplicitDeclarationContext ctx) {
        String varName = ctx.ID().getText();
        String varType = ctx.TYPE().getText();
        Type value = expressions.get(ctx.expression());

        if (value == null || !value.valuePresent())
            value = new Type(TypeName.fromTypeName(varType), false);
        else if (!value.type().getTypeName().equals(varType))
            errorListener.syntaxError(
                    ctx.expression().getStart(),
                    String.format("type mismatch (expected `%s`, actual `%s`)", varType, value.type().getTypeName())
            );

        if (variables.get(varName) != null)
            errorListener.syntaxError(
                    ctx.ID().getSymbol(),
                    String.format("invalid redeclaration of variable `%s`", varName)
            );

        variables.put(varName, value);

        if (builder.charAt(builder.length() - 1) == ',')
            builder.deleteCharAt(builder.length() - 1);
        builder.append("]}},");
    }

    @Override
    public void enterImplicitDeclaration(LanguageParser.ImplicitDeclarationContext ctx) {
        String varName = ctx.ID().getText();
        builder.append(String.format("{\"decl\":{\"name\":\"%s\",\"children\":[", varName));
    }

    @Override
    public void exitImplicitDeclaration(LanguageParser.ImplicitDeclarationContext ctx) {
        String varName = ctx.ID().getText();
        Type value = expressions.get(ctx.expression());

        if (value == null || !value.valuePresent())
            errorListener.syntaxError(
                    ctx.expression().getStart(),
                    String.format("attempted type inference of variable `%s` with undefined literal", varName)
            );
        else if (variables.get(varName) != null)
            errorListener.syntaxError(
                    ctx.ID().getSymbol(),
                    String.format("invalid redeclaration of variable `%s`", varName)
            );
        else {
            if (builder.charAt(builder.length() - 1) == ',')
                builder.deleteCharAt(builder.length() - 1);
            builder.append(String.format("],\"type\":\"%s\"}},", value.type().getTypeName()));
        }

        variables.put(varName, value);
        expressions.put(ctx, value);
    }

    @Override
    public void exitInt(LanguageParser.IntContext ctx) {
        expressions.put(ctx, new Type(TypeName.INT, true));
        builder.append(ctx.getText());
    }

    @Override
    public void exitBool(LanguageParser.BoolContext ctx) {
        expressions.put(ctx, new Type(TypeName.BOOL, true));
        builder.append(ctx.getText());
    }

    @Override
    public void exitLiteral(LanguageParser.LiteralContext ctx) {
        if (ctx.int_() != null) {
            expressions.put(ctx, expressions.get(ctx.int_()));
        } else if (ctx.bool() != null) {
            expressions.put(ctx, expressions.get(ctx.bool()));
        }
    }

    @Override
    public void exitExprLit(LanguageParser.ExprLitContext ctx) {
        expressions.put(ctx, expressions.get(ctx.literal()));
        builder.append(',');
    }

    @Override
    public void enterAssignment(LanguageParser.AssignmentContext ctx) {
        String varName = ctx.ID().getText();
        builder.append(String.format("{\"set\":{\"name\":\"%s\",\"children\":[", varName));
    }

    @Override
    public void exitAssignment(LanguageParser.AssignmentContext ctx) {
        String varName = ctx.ID().getText();

        if (variables.get(varName) == null)
            errorListener.syntaxError(
                    ctx.ID().getSymbol(),
                    String.format("assignment of undeclared variable `%s`", varName)
            );

        Type currentValue = variables.get(varName);
        Type newValue = expressions.get(ctx.expression());
        if (newValue == null || !newValue.valuePresent())
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
            variables.put(varName, newLiteral);
            expressions.put(ctx, newLiteral);

            if (builder.charAt(builder.length() - 1) == ',')
                builder.deleteCharAt(builder.length() - 1);
            builder.append("]}},");
        }
    }

    @Override
    public void exitExprId(LanguageParser.ExprIdContext ctx) {
        String varName = ctx.ID().getText();
        Type value = variables.get(varName);

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
            expressions.put(ctx, value);
            builder.append(String.format("{\"get\": {\"children\":[\"%s\"]}},", varName));
        }
    }

    @Override
    public void exitPar(LanguageParser.ParContext ctx) {
        expressions.put(ctx, expressions.get(ctx.expression()));
    }

    @Override
    public void enterAddition(LanguageParser.AdditionContext ctx) {
        evaluateExprEnter(ADD);
    }

    @Override
    public void exitAddition(LanguageParser.AdditionContext ctx) {
        evaluateExprExit(ADD, ctx, ctx.expression(0), ctx.expression(1), ctx.ADD());
    }

    @Override
    public void enterSubtraction(LanguageParser.SubtractionContext ctx) {
        evaluateExprEnter(SUB);
    }

    @Override
    public void exitSubtraction(LanguageParser.SubtractionContext ctx) {
        evaluateExprExit(SUB, ctx, ctx.expression(0), ctx.expression(1), ctx.SUB());
    }

    @Override
    public void enterMultiplication(LanguageParser.MultiplicationContext ctx) {
        evaluateExprEnter(MUL);
    }

    @Override
    public void exitMultiplication(LanguageParser.MultiplicationContext ctx) {
        evaluateExprExit(MUL, ctx, ctx.expression(0), ctx.expression(1), ctx.TIMES());
    }

    @Override
    public void enterAnd(LanguageParser.AndContext ctx) {
        evaluateExprEnter(AND);
    }

    @Override
    public void exitAnd(LanguageParser.AndContext ctx) {
        evaluateExprExit(AND, ctx, ctx.expression(0), ctx.expression(1), ctx.AND());
    }

    @Override
    public void enterOr(LanguageParser.OrContext ctx) {
        evaluateExprEnter(OR);
    }

    @Override
    public void exitOr(LanguageParser.OrContext ctx) {
        evaluateExprExit(OR, ctx, ctx.expression(0), ctx.expression(1), ctx.OR());
    }

    @Override
    public void enterEqual(LanguageParser.EqualContext ctx) {
        evaluateExprEnter(EQ);
    }

    @Override
    public void exitEqual(LanguageParser.EqualContext ctx) {
        evaluateExprExit(EQ, ctx, ctx.expression(0), ctx.expression(1), ctx.EQUAL());
    }

    @Override
    public void enterNotEqual(LanguageParser.NotEqualContext ctx) {
        evaluateExprEnter(NEQ);
    }

    @Override
    public void exitNotEqual(LanguageParser.NotEqualContext ctx) {
        evaluateExprExit(NEQ, ctx, ctx.expression(0), ctx.expression(1), ctx.NOT_EQUAL());
    }

    @Override
    public void enterLt(LanguageParser.LtContext ctx) {
        evaluateExprEnter(LT);
    }

    @Override
    public void exitLt(LanguageParser.LtContext ctx) {
        evaluateExprExit(LT, ctx, ctx.expression(0), ctx.expression(1), ctx.LT());
    }

    @Override
    public void enterGt(LanguageParser.GtContext ctx) {
        evaluateExprEnter(GT);
    }

    @Override
    public void exitGt(LanguageParser.GtContext ctx) {
        evaluateExprExit(GT, ctx, ctx.expression(0), ctx.expression(1), ctx.GT());
    }

    @Override
    public void enterGe(LanguageParser.GeContext ctx) {
        evaluateExprEnter(GE);
    }

    @Override
    public void exitGe(LanguageParser.GeContext ctx) {
        evaluateExprExit(GE, ctx, ctx.expression(0), ctx.expression(1), ctx.GE());
    }

    @Override
    public void enterLe(LanguageParser.LeContext ctx) {
        evaluateExprEnter(LE);
    }

    @Override
    public void exitLe(LanguageParser.LeContext ctx) {
        evaluateExprExit(LE, ctx, ctx.expression(0), ctx.expression(1), ctx.LE());
    }

    private void evaluateExprEnter(OpName opName) {
        builder.append(String.format("{\"%s\":{\"children\":[", opName.getOpName()));
    }

    private void evaluateExprExit(
            OpName opName,
            LanguageParser.ExpressionContext ctx,
            LanguageParser.ExpressionContext leftCtx,
            LanguageParser.ExpressionContext rightCtx,
            TerminalNode opCtx
    ) {
        Type left = expressions.get(leftCtx);
        Type right = expressions.get(rightCtx);
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
            expressions.put(ctx, new Type(result, true));
            if (builder.charAt(builder.length() - 1) == ',')
                builder.deleteCharAt(builder.length() - 1);
            builder.append("]}},");
        }
    }
}
