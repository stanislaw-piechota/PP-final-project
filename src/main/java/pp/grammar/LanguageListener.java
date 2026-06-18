// Generated from pp/grammar/Language.g4 by ANTLR 4.13.1
package pp.grammar;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link LanguageParser}.
 */
public interface LanguageListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link LanguageParser#program}.
	 * @param ctx the parse tree
	 */
	void enterProgram(LanguageParser.ProgramContext ctx);
	/**
	 * Exit a parse tree produced by {@link LanguageParser#program}.
	 * @param ctx the parse tree
	 */
	void exitProgram(LanguageParser.ProgramContext ctx);
	/**
	 * Enter a parse tree produced by {@link LanguageParser#block}.
	 * @param ctx the parse tree
	 */
	void enterBlock(LanguageParser.BlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link LanguageParser#block}.
	 * @param ctx the parse tree
	 */
	void exitBlock(LanguageParser.BlockContext ctx);
	/**
	 * Enter a parse tree produced by the {@code explicitDeclaration}
	 * labeled alternative in {@link LanguageParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterExplicitDeclaration(LanguageParser.ExplicitDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by the {@code explicitDeclaration}
	 * labeled alternative in {@link LanguageParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitExplicitDeclaration(LanguageParser.ExplicitDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by the {@code print}
	 * labeled alternative in {@link LanguageParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterPrint(LanguageParser.PrintContext ctx);
	/**
	 * Exit a parse tree produced by the {@code print}
	 * labeled alternative in {@link LanguageParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitPrint(LanguageParser.PrintContext ctx);
	/**
	 * Enter a parse tree produced by the {@code cond}
	 * labeled alternative in {@link LanguageParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterCond(LanguageParser.CondContext ctx);
	/**
	 * Exit a parse tree produced by the {@code cond}
	 * labeled alternative in {@link LanguageParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitCond(LanguageParser.CondContext ctx);
	/**
	 * Enter a parse tree produced by the {@code expr}
	 * labeled alternative in {@link LanguageParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterExpr(LanguageParser.ExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code expr}
	 * labeled alternative in {@link LanguageParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitExpr(LanguageParser.ExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code while}
	 * labeled alternative in {@link LanguageParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterWhile(LanguageParser.WhileContext ctx);
	/**
	 * Exit a parse tree produced by the {@code while}
	 * labeled alternative in {@link LanguageParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitWhile(LanguageParser.WhileContext ctx);
	/**
	 * Enter a parse tree produced by the {@code threadStart}
	 * labeled alternative in {@link LanguageParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterThreadStart(LanguageParser.ThreadStartContext ctx);
	/**
	 * Exit a parse tree produced by the {@code threadStart}
	 * labeled alternative in {@link LanguageParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitThreadStart(LanguageParser.ThreadStartContext ctx);
	/**
	 * Enter a parse tree produced by the {@code threadJoin}
	 * labeled alternative in {@link LanguageParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterThreadJoin(LanguageParser.ThreadJoinContext ctx);
	/**
	 * Exit a parse tree produced by the {@code threadJoin}
	 * labeled alternative in {@link LanguageParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitThreadJoin(LanguageParser.ThreadJoinContext ctx);
	/**
	 * Enter a parse tree produced by {@link LanguageParser#conditional}.
	 * @param ctx the parse tree
	 */
	void enterConditional(LanguageParser.ConditionalContext ctx);
	/**
	 * Exit a parse tree produced by {@link LanguageParser#conditional}.
	 * @param ctx the parse tree
	 */
	void exitConditional(LanguageParser.ConditionalContext ctx);
	/**
	 * Enter a parse tree produced by {@link LanguageParser#whileLoop}.
	 * @param ctx the parse tree
	 */
	void enterWhileLoop(LanguageParser.WhileLoopContext ctx);
	/**
	 * Exit a parse tree produced by {@link LanguageParser#whileLoop}.
	 * @param ctx the parse tree
	 */
	void exitWhileLoop(LanguageParser.WhileLoopContext ctx);
	/**
	 * Enter a parse tree produced by the {@code par}
	 * labeled alternative in {@link LanguageParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterPar(LanguageParser.ParContext ctx);
	/**
	 * Exit a parse tree produced by the {@code par}
	 * labeled alternative in {@link LanguageParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitPar(LanguageParser.ParContext ctx);
	/**
	 * Enter a parse tree produced by the {@code or}
	 * labeled alternative in {@link LanguageParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterOr(LanguageParser.OrContext ctx);
	/**
	 * Exit a parse tree produced by the {@code or}
	 * labeled alternative in {@link LanguageParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitOr(LanguageParser.OrContext ctx);
	/**
	 * Enter a parse tree produced by the {@code assignment}
	 * labeled alternative in {@link LanguageParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterAssignment(LanguageParser.AssignmentContext ctx);
	/**
	 * Exit a parse tree produced by the {@code assignment}
	 * labeled alternative in {@link LanguageParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitAssignment(LanguageParser.AssignmentContext ctx);
	/**
	 * Enter a parse tree produced by the {@code lt}
	 * labeled alternative in {@link LanguageParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterLt(LanguageParser.LtContext ctx);
	/**
	 * Exit a parse tree produced by the {@code lt}
	 * labeled alternative in {@link LanguageParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitLt(LanguageParser.LtContext ctx);
	/**
	 * Enter a parse tree produced by the {@code subtraction}
	 * labeled alternative in {@link LanguageParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterSubtraction(LanguageParser.SubtractionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code subtraction}
	 * labeled alternative in {@link LanguageParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitSubtraction(LanguageParser.SubtractionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code notEqual}
	 * labeled alternative in {@link LanguageParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterNotEqual(LanguageParser.NotEqualContext ctx);
	/**
	 * Exit a parse tree produced by the {@code notEqual}
	 * labeled alternative in {@link LanguageParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitNotEqual(LanguageParser.NotEqualContext ctx);
	/**
	 * Enter a parse tree produced by the {@code gt}
	 * labeled alternative in {@link LanguageParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterGt(LanguageParser.GtContext ctx);
	/**
	 * Exit a parse tree produced by the {@code gt}
	 * labeled alternative in {@link LanguageParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitGt(LanguageParser.GtContext ctx);
	/**
	 * Enter a parse tree produced by the {@code implicitDeclaration}
	 * labeled alternative in {@link LanguageParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterImplicitDeclaration(LanguageParser.ImplicitDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by the {@code implicitDeclaration}
	 * labeled alternative in {@link LanguageParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitImplicitDeclaration(LanguageParser.ImplicitDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by the {@code equal}
	 * labeled alternative in {@link LanguageParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterEqual(LanguageParser.EqualContext ctx);
	/**
	 * Exit a parse tree produced by the {@code equal}
	 * labeled alternative in {@link LanguageParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitEqual(LanguageParser.EqualContext ctx);
	/**
	 * Enter a parse tree produced by the {@code and}
	 * labeled alternative in {@link LanguageParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterAnd(LanguageParser.AndContext ctx);
	/**
	 * Exit a parse tree produced by the {@code and}
	 * labeled alternative in {@link LanguageParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitAnd(LanguageParser.AndContext ctx);
	/**
	 * Enter a parse tree produced by the {@code exprLit}
	 * labeled alternative in {@link LanguageParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExprLit(LanguageParser.ExprLitContext ctx);
	/**
	 * Exit a parse tree produced by the {@code exprLit}
	 * labeled alternative in {@link LanguageParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExprLit(LanguageParser.ExprLitContext ctx);
	/**
	 * Enter a parse tree produced by the {@code exprId}
	 * labeled alternative in {@link LanguageParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExprId(LanguageParser.ExprIdContext ctx);
	/**
	 * Exit a parse tree produced by the {@code exprId}
	 * labeled alternative in {@link LanguageParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExprId(LanguageParser.ExprIdContext ctx);
	/**
	 * Enter a parse tree produced by the {@code le}
	 * labeled alternative in {@link LanguageParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterLe(LanguageParser.LeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code le}
	 * labeled alternative in {@link LanguageParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitLe(LanguageParser.LeContext ctx);
	/**
	 * Enter a parse tree produced by the {@code multiplication}
	 * labeled alternative in {@link LanguageParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterMultiplication(LanguageParser.MultiplicationContext ctx);
	/**
	 * Exit a parse tree produced by the {@code multiplication}
	 * labeled alternative in {@link LanguageParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitMultiplication(LanguageParser.MultiplicationContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ge}
	 * labeled alternative in {@link LanguageParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterGe(LanguageParser.GeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ge}
	 * labeled alternative in {@link LanguageParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitGe(LanguageParser.GeContext ctx);
	/**
	 * Enter a parse tree produced by the {@code addition}
	 * labeled alternative in {@link LanguageParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterAddition(LanguageParser.AdditionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code addition}
	 * labeled alternative in {@link LanguageParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitAddition(LanguageParser.AdditionContext ctx);
	/**
	 * Enter a parse tree produced by {@link LanguageParser#literal}.
	 * @param ctx the parse tree
	 */
	void enterLiteral(LanguageParser.LiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link LanguageParser#literal}.
	 * @param ctx the parse tree
	 */
	void exitLiteral(LanguageParser.LiteralContext ctx);
	/**
	 * Enter a parse tree produced by {@link LanguageParser#bool}.
	 * @param ctx the parse tree
	 */
	void enterBool(LanguageParser.BoolContext ctx);
	/**
	 * Exit a parse tree produced by {@link LanguageParser#bool}.
	 * @param ctx the parse tree
	 */
	void exitBool(LanguageParser.BoolContext ctx);
	/**
	 * Enter a parse tree produced by {@link LanguageParser#int}.
	 * @param ctx the parse tree
	 */
	void enterInt(LanguageParser.IntContext ctx);
	/**
	 * Exit a parse tree produced by {@link LanguageParser#int}.
	 * @param ctx the parse tree
	 */
	void exitInt(LanguageParser.IntContext ctx);
}