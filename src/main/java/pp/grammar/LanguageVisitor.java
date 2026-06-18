// Generated from pp/grammar/Language.g4 by ANTLR 4.13.1
package pp.grammar;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link LanguageParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface LanguageVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link LanguageParser#program}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProgram(LanguageParser.ProgramContext ctx);
	/**
	 * Visit a parse tree produced by {@link LanguageParser#block}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlock(LanguageParser.BlockContext ctx);
	/**
	 * Visit a parse tree produced by the {@code explicitDeclaration}
	 * labeled alternative in {@link LanguageParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExplicitDeclaration(LanguageParser.ExplicitDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by the {@code print}
	 * labeled alternative in {@link LanguageParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrint(LanguageParser.PrintContext ctx);
	/**
	 * Visit a parse tree produced by the {@code cond}
	 * labeled alternative in {@link LanguageParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCond(LanguageParser.CondContext ctx);
	/**
	 * Visit a parse tree produced by the {@code expr}
	 * labeled alternative in {@link LanguageParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpr(LanguageParser.ExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code while}
	 * labeled alternative in {@link LanguageParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhile(LanguageParser.WhileContext ctx);
	/**
	 * Visit a parse tree produced by the {@code threadStart}
	 * labeled alternative in {@link LanguageParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitThreadStart(LanguageParser.ThreadStartContext ctx);
	/**
	 * Visit a parse tree produced by the {@code threadJoin}
	 * labeled alternative in {@link LanguageParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitThreadJoin(LanguageParser.ThreadJoinContext ctx);
	/**
	 * Visit a parse tree produced by {@link LanguageParser#conditional}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConditional(LanguageParser.ConditionalContext ctx);
	/**
	 * Visit a parse tree produced by {@link LanguageParser#whileLoop}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhileLoop(LanguageParser.WhileLoopContext ctx);
	/**
	 * Visit a parse tree produced by the {@code par}
	 * labeled alternative in {@link LanguageParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPar(LanguageParser.ParContext ctx);
	/**
	 * Visit a parse tree produced by the {@code or}
	 * labeled alternative in {@link LanguageParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOr(LanguageParser.OrContext ctx);
	/**
	 * Visit a parse tree produced by the {@code assignment}
	 * labeled alternative in {@link LanguageParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignment(LanguageParser.AssignmentContext ctx);
	/**
	 * Visit a parse tree produced by the {@code lt}
	 * labeled alternative in {@link LanguageParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLt(LanguageParser.LtContext ctx);
	/**
	 * Visit a parse tree produced by the {@code subtraction}
	 * labeled alternative in {@link LanguageParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSubtraction(LanguageParser.SubtractionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code notEqual}
	 * labeled alternative in {@link LanguageParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNotEqual(LanguageParser.NotEqualContext ctx);
	/**
	 * Visit a parse tree produced by the {@code gt}
	 * labeled alternative in {@link LanguageParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGt(LanguageParser.GtContext ctx);
	/**
	 * Visit a parse tree produced by the {@code implicitDeclaration}
	 * labeled alternative in {@link LanguageParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitImplicitDeclaration(LanguageParser.ImplicitDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by the {@code equal}
	 * labeled alternative in {@link LanguageParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEqual(LanguageParser.EqualContext ctx);
	/**
	 * Visit a parse tree produced by the {@code and}
	 * labeled alternative in {@link LanguageParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAnd(LanguageParser.AndContext ctx);
	/**
	 * Visit a parse tree produced by the {@code exprLit}
	 * labeled alternative in {@link LanguageParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExprLit(LanguageParser.ExprLitContext ctx);
	/**
	 * Visit a parse tree produced by the {@code exprId}
	 * labeled alternative in {@link LanguageParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExprId(LanguageParser.ExprIdContext ctx);
	/**
	 * Visit a parse tree produced by the {@code le}
	 * labeled alternative in {@link LanguageParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLe(LanguageParser.LeContext ctx);
	/**
	 * Visit a parse tree produced by the {@code multiplication}
	 * labeled alternative in {@link LanguageParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMultiplication(LanguageParser.MultiplicationContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ge}
	 * labeled alternative in {@link LanguageParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGe(LanguageParser.GeContext ctx);
	/**
	 * Visit a parse tree produced by the {@code addition}
	 * labeled alternative in {@link LanguageParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAddition(LanguageParser.AdditionContext ctx);
	/**
	 * Visit a parse tree produced by {@link LanguageParser#literal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLiteral(LanguageParser.LiteralContext ctx);
	/**
	 * Visit a parse tree produced by {@link LanguageParser#bool}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBool(LanguageParser.BoolContext ctx);
	/**
	 * Visit a parse tree produced by {@link LanguageParser#int}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInt(LanguageParser.IntContext ctx);
}