// Generated from pp/grammar/Language.g4 by ANTLR 4.13.1
package pp.grammar;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue"})
public class LanguageParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.13.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, EQUAL=5, NOT_EQUAL=6, GT=7, LT=8, GE=9, 
		LE=10, AND=11, OR=12, ADD=13, SUB=14, TIMES=15, LPAR=16, RPAR=17, LBRACK=18, 
		RBRACK=19, PRINT=20, IF=21, ELSEIF=22, ELSE=23, TYPE=24, BOOL=25, INTEGER=26, 
		ID=27, COMMENT=28, WS=29;
	public static final int
		RULE_block = 0, RULE_statement = 1, RULE_conditional = 2, RULE_expression = 3, 
		RULE_binaryOp = 4, RULE_literal = 5, RULE_bool = 6, RULE_int = 7;
	private static String[] makeRuleNames() {
		return new String[] {
			"block", "statement", "conditional", "expression", "binaryOp", "literal", 
			"bool", "int"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "':'", "'='", "';'", "':='", "'=='", "'!='", "'>'", "'<'", "'>='", 
			"'<='", "'&&'", "'||'", "'+'", "'-'", "'*'", "'('", "')'", "'{'", "'}'", 
			"'print'", "'if'", "'elif'", "'else'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, "EQUAL", "NOT_EQUAL", "GT", "LT", "GE", 
			"LE", "AND", "OR", "ADD", "SUB", "TIMES", "LPAR", "RPAR", "LBRACK", "RBRACK", 
			"PRINT", "IF", "ELSEIF", "ELSE", "TYPE", "BOOL", "INTEGER", "ID", "COMMENT", 
			"WS"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "Language.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public LanguageParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@SuppressWarnings("CheckReturnValue")
	public static class BlockContext extends ParserRuleContext {
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public BlockContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_block; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LanguageListener ) ((LanguageListener)listener).enterBlock(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LanguageListener ) ((LanguageListener)listener).exitBlock(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LanguageVisitor ) return ((LanguageVisitor<? extends T>)visitor).visitBlock(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BlockContext block() throws RecognitionException {
		BlockContext _localctx = new BlockContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_block);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(17); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(16);
				statement();
				}
				}
				setState(19); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & 238108672L) != 0) );
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class StatementContext extends ParserRuleContext {
		public StatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_statement; }
	 
		public StatementContext() { }
		public void copyFrom(StatementContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class Implicit_declarationContext extends StatementContext {
		public TerminalNode ID() { return getToken(LanguageParser.ID, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public Implicit_declarationContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LanguageListener ) ((LanguageListener)listener).enterImplicit_declaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LanguageListener ) ((LanguageListener)listener).exitImplicit_declaration(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LanguageVisitor ) return ((LanguageVisitor<? extends T>)visitor).visitImplicit_declaration(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class PrintContext extends StatementContext {
		public TerminalNode PRINT() { return getToken(LanguageParser.PRINT, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public PrintContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LanguageListener ) ((LanguageListener)listener).enterPrint(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LanguageListener ) ((LanguageListener)listener).exitPrint(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LanguageVisitor ) return ((LanguageVisitor<? extends T>)visitor).visitPrint(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class Explicit_declarationContext extends StatementContext {
		public TerminalNode ID() { return getToken(LanguageParser.ID, 0); }
		public TerminalNode TYPE() { return getToken(LanguageParser.TYPE, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public Explicit_declarationContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LanguageListener ) ((LanguageListener)listener).enterExplicit_declaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LanguageListener ) ((LanguageListener)listener).exitExplicit_declaration(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LanguageVisitor ) return ((LanguageVisitor<? extends T>)visitor).visitExplicit_declaration(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ExprContext extends StatementContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public ExprContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LanguageListener ) ((LanguageListener)listener).enterExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LanguageListener ) ((LanguageListener)listener).exitExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LanguageVisitor ) return ((LanguageVisitor<? extends T>)visitor).visitExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class CondContext extends StatementContext {
		public ConditionalContext conditional() {
			return getRuleContext(ConditionalContext.class,0);
		}
		public CondContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LanguageListener ) ((LanguageListener)listener).enterCond(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LanguageListener ) ((LanguageListener)listener).exitCond(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LanguageVisitor ) return ((LanguageVisitor<? extends T>)visitor).visitCond(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StatementContext statement() throws RecognitionException {
		StatementContext _localctx = new StatementContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_statement);
		int _la;
		try {
			setState(42);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,2,_ctx) ) {
			case 1:
				_localctx = new Explicit_declarationContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(21);
				match(ID);
				setState(22);
				match(T__0);
				setState(23);
				match(TYPE);
				setState(26);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==T__1) {
					{
					setState(24);
					match(T__1);
					setState(25);
					expression();
					}
				}

				setState(28);
				match(T__2);
				}
				break;
			case 2:
				_localctx = new Implicit_declarationContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(29);
				match(ID);
				setState(30);
				match(T__3);
				setState(31);
				expression();
				setState(32);
				match(T__2);
				}
				break;
			case 3:
				_localctx = new PrintContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(34);
				match(PRINT);
				setState(35);
				expression();
				setState(36);
				match(T__2);
				}
				break;
			case 4:
				_localctx = new CondContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(38);
				conditional();
				}
				break;
			case 5:
				_localctx = new ExprContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(39);
				expression();
				setState(40);
				match(T__2);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ConditionalContext extends ParserRuleContext {
		public TerminalNode IF() { return getToken(LanguageParser.IF, 0); }
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public List<TerminalNode> ELSEIF() { return getTokens(LanguageParser.ELSEIF); }
		public TerminalNode ELSEIF(int i) {
			return getToken(LanguageParser.ELSEIF, i);
		}
		public TerminalNode ELSE() { return getToken(LanguageParser.ELSE, 0); }
		public List<TerminalNode> LBRACK() { return getTokens(LanguageParser.LBRACK); }
		public TerminalNode LBRACK(int i) {
			return getToken(LanguageParser.LBRACK, i);
		}
		public List<BlockContext> block() {
			return getRuleContexts(BlockContext.class);
		}
		public BlockContext block(int i) {
			return getRuleContext(BlockContext.class,i);
		}
		public List<TerminalNode> RBRACK() { return getTokens(LanguageParser.RBRACK); }
		public TerminalNode RBRACK(int i) {
			return getToken(LanguageParser.RBRACK, i);
		}
		public ConditionalContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_conditional; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LanguageListener ) ((LanguageListener)listener).enterConditional(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LanguageListener ) ((LanguageListener)listener).exitConditional(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LanguageVisitor ) return ((LanguageVisitor<? extends T>)visitor).visitConditional(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ConditionalContext conditional() throws RecognitionException {
		ConditionalContext _localctx = new ConditionalContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_conditional);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(44);
			match(IF);
			setState(45);
			expression();
			setState(51);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case LBRACK:
				{
				{
				setState(46);
				match(LBRACK);
				setState(47);
				block();
				setState(48);
				match(RBRACK);
				}
				}
				break;
			case SUB:
			case LPAR:
			case PRINT:
			case IF:
			case BOOL:
			case INTEGER:
			case ID:
				{
				setState(50);
				statement();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			setState(64);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,5,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(53);
					match(ELSEIF);
					setState(54);
					expression();
					setState(60);
					_errHandler.sync(this);
					switch (_input.LA(1)) {
					case LBRACK:
						{
						{
						setState(55);
						match(LBRACK);
						setState(56);
						block();
						setState(57);
						match(RBRACK);
						}
						}
						break;
					case SUB:
					case LPAR:
					case PRINT:
					case IF:
					case BOOL:
					case INTEGER:
					case ID:
						{
						setState(59);
						statement();
						}
						break;
					default:
						throw new NoViableAltException(this);
					}
					}
					} 
				}
				setState(66);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,5,_ctx);
			}
			setState(76);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,7,_ctx) ) {
			case 1:
				{
				setState(67);
				match(ELSE);
				setState(68);
				expression();
				setState(74);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case LBRACK:
					{
					{
					setState(69);
					match(LBRACK);
					setState(70);
					block();
					setState(71);
					match(RBRACK);
					}
					}
					break;
				case SUB:
				case LPAR:
				case PRINT:
				case IF:
				case BOOL:
				case INTEGER:
				case ID:
					{
					setState(73);
					statement();
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ExpressionContext extends ParserRuleContext {
		public ExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expression; }
	 
		public ExpressionContext() { }
		public void copyFrom(ExpressionContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ExprBinOpContext extends ExpressionContext {
		public BinaryOpContext binaryOp() {
			return getRuleContext(BinaryOpContext.class,0);
		}
		public ExprBinOpContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LanguageListener ) ((LanguageListener)listener).enterExprBinOp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LanguageListener ) ((LanguageListener)listener).exitExprBinOp(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LanguageVisitor ) return ((LanguageVisitor<? extends T>)visitor).visitExprBinOp(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class AssignmentContext extends ExpressionContext {
		public TerminalNode ID() { return getToken(LanguageParser.ID, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public AssignmentContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LanguageListener ) ((LanguageListener)listener).enterAssignment(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LanguageListener ) ((LanguageListener)listener).exitAssignment(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LanguageVisitor ) return ((LanguageVisitor<? extends T>)visitor).visitAssignment(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ExprLitContext extends ExpressionContext {
		public LiteralContext literal() {
			return getRuleContext(LiteralContext.class,0);
		}
		public ExprLitContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LanguageListener ) ((LanguageListener)listener).enterExprLit(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LanguageListener ) ((LanguageListener)listener).exitExprLit(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LanguageVisitor ) return ((LanguageVisitor<? extends T>)visitor).visitExprLit(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ExprIdContext extends ExpressionContext {
		public TerminalNode ID() { return getToken(LanguageParser.ID, 0); }
		public ExprIdContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LanguageListener ) ((LanguageListener)listener).enterExprId(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LanguageListener ) ((LanguageListener)listener).exitExprId(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LanguageVisitor ) return ((LanguageVisitor<? extends T>)visitor).visitExprId(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExpressionContext expression() throws RecognitionException {
		ExpressionContext _localctx = new ExpressionContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_expression);
		try {
			setState(84);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,8,_ctx) ) {
			case 1:
				_localctx = new ExprLitContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(78);
				literal();
				}
				break;
			case 2:
				_localctx = new ExprIdContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(79);
				match(ID);
				}
				break;
			case 3:
				_localctx = new ExprBinOpContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(80);
				binaryOp(0);
				}
				break;
			case 4:
				_localctx = new AssignmentContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(81);
				match(ID);
				setState(82);
				match(T__1);
				setState(83);
				expression();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class BinaryOpContext extends ParserRuleContext {
		public BinaryOpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_binaryOp; }
	 
		public BinaryOpContext() { }
		public void copyFrom(BinaryOpContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ParContext extends BinaryOpContext {
		public TerminalNode LPAR() { return getToken(LanguageParser.LPAR, 0); }
		public BinaryOpContext binaryOp() {
			return getRuleContext(BinaryOpContext.class,0);
		}
		public TerminalNode RPAR() { return getToken(LanguageParser.RPAR, 0); }
		public ParContext(BinaryOpContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LanguageListener ) ((LanguageListener)listener).enterPar(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LanguageListener ) ((LanguageListener)listener).exitPar(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LanguageVisitor ) return ((LanguageVisitor<? extends T>)visitor).visitPar(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class BinOpIdContext extends BinaryOpContext {
		public TerminalNode ID() { return getToken(LanguageParser.ID, 0); }
		public BinOpIdContext(BinaryOpContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LanguageListener ) ((LanguageListener)listener).enterBinOpId(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LanguageListener ) ((LanguageListener)listener).exitBinOpId(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LanguageVisitor ) return ((LanguageVisitor<? extends T>)visitor).visitBinOpId(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class OrContext extends BinaryOpContext {
		public List<BinaryOpContext> binaryOp() {
			return getRuleContexts(BinaryOpContext.class);
		}
		public BinaryOpContext binaryOp(int i) {
			return getRuleContext(BinaryOpContext.class,i);
		}
		public TerminalNode OR() { return getToken(LanguageParser.OR, 0); }
		public OrContext(BinaryOpContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LanguageListener ) ((LanguageListener)listener).enterOr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LanguageListener ) ((LanguageListener)listener).exitOr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LanguageVisitor ) return ((LanguageVisitor<? extends T>)visitor).visitOr(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class LtContext extends BinaryOpContext {
		public List<BinaryOpContext> binaryOp() {
			return getRuleContexts(BinaryOpContext.class);
		}
		public BinaryOpContext binaryOp(int i) {
			return getRuleContext(BinaryOpContext.class,i);
		}
		public TerminalNode LT() { return getToken(LanguageParser.LT, 0); }
		public LtContext(BinaryOpContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LanguageListener ) ((LanguageListener)listener).enterLt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LanguageListener ) ((LanguageListener)listener).exitLt(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LanguageVisitor ) return ((LanguageVisitor<? extends T>)visitor).visitLt(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class NotEqualContext extends BinaryOpContext {
		public List<BinaryOpContext> binaryOp() {
			return getRuleContexts(BinaryOpContext.class);
		}
		public BinaryOpContext binaryOp(int i) {
			return getRuleContext(BinaryOpContext.class,i);
		}
		public TerminalNode NOT_EQUAL() { return getToken(LanguageParser.NOT_EQUAL, 0); }
		public NotEqualContext(BinaryOpContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LanguageListener ) ((LanguageListener)listener).enterNotEqual(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LanguageListener ) ((LanguageListener)listener).exitNotEqual(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LanguageVisitor ) return ((LanguageVisitor<? extends T>)visitor).visitNotEqual(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class SubstractionContext extends BinaryOpContext {
		public List<BinaryOpContext> binaryOp() {
			return getRuleContexts(BinaryOpContext.class);
		}
		public BinaryOpContext binaryOp(int i) {
			return getRuleContext(BinaryOpContext.class,i);
		}
		public TerminalNode SUB() { return getToken(LanguageParser.SUB, 0); }
		public SubstractionContext(BinaryOpContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LanguageListener ) ((LanguageListener)listener).enterSubstraction(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LanguageListener ) ((LanguageListener)listener).exitSubstraction(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LanguageVisitor ) return ((LanguageVisitor<? extends T>)visitor).visitSubstraction(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class BinOpLitContext extends BinaryOpContext {
		public LiteralContext literal() {
			return getRuleContext(LiteralContext.class,0);
		}
		public BinOpLitContext(BinaryOpContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LanguageListener ) ((LanguageListener)listener).enterBinOpLit(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LanguageListener ) ((LanguageListener)listener).exitBinOpLit(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LanguageVisitor ) return ((LanguageVisitor<? extends T>)visitor).visitBinOpLit(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class GtContext extends BinaryOpContext {
		public List<BinaryOpContext> binaryOp() {
			return getRuleContexts(BinaryOpContext.class);
		}
		public BinaryOpContext binaryOp(int i) {
			return getRuleContext(BinaryOpContext.class,i);
		}
		public TerminalNode GT() { return getToken(LanguageParser.GT, 0); }
		public GtContext(BinaryOpContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LanguageListener ) ((LanguageListener)listener).enterGt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LanguageListener ) ((LanguageListener)listener).exitGt(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LanguageVisitor ) return ((LanguageVisitor<? extends T>)visitor).visitGt(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class EqualContext extends BinaryOpContext {
		public List<BinaryOpContext> binaryOp() {
			return getRuleContexts(BinaryOpContext.class);
		}
		public BinaryOpContext binaryOp(int i) {
			return getRuleContext(BinaryOpContext.class,i);
		}
		public TerminalNode EQUAL() { return getToken(LanguageParser.EQUAL, 0); }
		public EqualContext(BinaryOpContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LanguageListener ) ((LanguageListener)listener).enterEqual(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LanguageListener ) ((LanguageListener)listener).exitEqual(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LanguageVisitor ) return ((LanguageVisitor<? extends T>)visitor).visitEqual(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class AndContext extends BinaryOpContext {
		public List<BinaryOpContext> binaryOp() {
			return getRuleContexts(BinaryOpContext.class);
		}
		public BinaryOpContext binaryOp(int i) {
			return getRuleContext(BinaryOpContext.class,i);
		}
		public TerminalNode AND() { return getToken(LanguageParser.AND, 0); }
		public AndContext(BinaryOpContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LanguageListener ) ((LanguageListener)listener).enterAnd(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LanguageListener ) ((LanguageListener)listener).exitAnd(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LanguageVisitor ) return ((LanguageVisitor<? extends T>)visitor).visitAnd(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class LeContext extends BinaryOpContext {
		public List<BinaryOpContext> binaryOp() {
			return getRuleContexts(BinaryOpContext.class);
		}
		public BinaryOpContext binaryOp(int i) {
			return getRuleContext(BinaryOpContext.class,i);
		}
		public TerminalNode LE() { return getToken(LanguageParser.LE, 0); }
		public LeContext(BinaryOpContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LanguageListener ) ((LanguageListener)listener).enterLe(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LanguageListener ) ((LanguageListener)listener).exitLe(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LanguageVisitor ) return ((LanguageVisitor<? extends T>)visitor).visitLe(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class MultiplicationContext extends BinaryOpContext {
		public List<BinaryOpContext> binaryOp() {
			return getRuleContexts(BinaryOpContext.class);
		}
		public BinaryOpContext binaryOp(int i) {
			return getRuleContext(BinaryOpContext.class,i);
		}
		public TerminalNode TIMES() { return getToken(LanguageParser.TIMES, 0); }
		public MultiplicationContext(BinaryOpContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LanguageListener ) ((LanguageListener)listener).enterMultiplication(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LanguageListener ) ((LanguageListener)listener).exitMultiplication(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LanguageVisitor ) return ((LanguageVisitor<? extends T>)visitor).visitMultiplication(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class GeContext extends BinaryOpContext {
		public List<BinaryOpContext> binaryOp() {
			return getRuleContexts(BinaryOpContext.class);
		}
		public BinaryOpContext binaryOp(int i) {
			return getRuleContext(BinaryOpContext.class,i);
		}
		public TerminalNode GE() { return getToken(LanguageParser.GE, 0); }
		public GeContext(BinaryOpContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LanguageListener ) ((LanguageListener)listener).enterGe(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LanguageListener ) ((LanguageListener)listener).exitGe(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LanguageVisitor ) return ((LanguageVisitor<? extends T>)visitor).visitGe(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class AdditionContext extends BinaryOpContext {
		public List<BinaryOpContext> binaryOp() {
			return getRuleContexts(BinaryOpContext.class);
		}
		public BinaryOpContext binaryOp(int i) {
			return getRuleContext(BinaryOpContext.class,i);
		}
		public TerminalNode ADD() { return getToken(LanguageParser.ADD, 0); }
		public AdditionContext(BinaryOpContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LanguageListener ) ((LanguageListener)listener).enterAddition(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LanguageListener ) ((LanguageListener)listener).exitAddition(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LanguageVisitor ) return ((LanguageVisitor<? extends T>)visitor).visitAddition(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BinaryOpContext binaryOp() throws RecognitionException {
		return binaryOp(0);
	}

	private BinaryOpContext binaryOp(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		BinaryOpContext _localctx = new BinaryOpContext(_ctx, _parentState);
		BinaryOpContext _prevctx = _localctx;
		int _startState = 8;
		enterRecursionRule(_localctx, 8, RULE_binaryOp, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(93);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case LPAR:
				{
				_localctx = new ParContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(87);
				match(LPAR);
				setState(88);
				binaryOp(0);
				setState(89);
				match(RPAR);
				}
				break;
			case SUB:
			case BOOL:
			case INTEGER:
				{
				_localctx = new BinOpLitContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(91);
				literal();
				}
				break;
			case ID:
				{
				_localctx = new BinOpIdContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(92);
				match(ID);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			_ctx.stop = _input.LT(-1);
			setState(130);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,11,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(128);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,10,_ctx) ) {
					case 1:
						{
						_localctx = new MultiplicationContext(new BinaryOpContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_binaryOp);
						setState(95);
						if (!(precpred(_ctx, 14))) throw new FailedPredicateException(this, "precpred(_ctx, 14)");
						setState(96);
						match(TIMES);
						setState(97);
						binaryOp(15);
						}
						break;
					case 2:
						{
						_localctx = new AdditionContext(new BinaryOpContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_binaryOp);
						setState(98);
						if (!(precpred(_ctx, 13))) throw new FailedPredicateException(this, "precpred(_ctx, 13)");
						setState(99);
						match(ADD);
						setState(100);
						binaryOp(14);
						}
						break;
					case 3:
						{
						_localctx = new SubstractionContext(new BinaryOpContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_binaryOp);
						setState(101);
						if (!(precpred(_ctx, 12))) throw new FailedPredicateException(this, "precpred(_ctx, 12)");
						setState(102);
						match(SUB);
						setState(103);
						binaryOp(13);
						}
						break;
					case 4:
						{
						_localctx = new AndContext(new BinaryOpContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_binaryOp);
						setState(104);
						if (!(precpred(_ctx, 11))) throw new FailedPredicateException(this, "precpred(_ctx, 11)");
						setState(105);
						match(AND);
						setState(106);
						binaryOp(12);
						}
						break;
					case 5:
						{
						_localctx = new OrContext(new BinaryOpContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_binaryOp);
						setState(107);
						if (!(precpred(_ctx, 10))) throw new FailedPredicateException(this, "precpred(_ctx, 10)");
						setState(108);
						match(OR);
						setState(109);
						binaryOp(11);
						}
						break;
					case 6:
						{
						_localctx = new EqualContext(new BinaryOpContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_binaryOp);
						setState(110);
						if (!(precpred(_ctx, 9))) throw new FailedPredicateException(this, "precpred(_ctx, 9)");
						setState(111);
						match(EQUAL);
						setState(112);
						binaryOp(10);
						}
						break;
					case 7:
						{
						_localctx = new NotEqualContext(new BinaryOpContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_binaryOp);
						setState(113);
						if (!(precpred(_ctx, 8))) throw new FailedPredicateException(this, "precpred(_ctx, 8)");
						setState(114);
						match(NOT_EQUAL);
						setState(115);
						binaryOp(9);
						}
						break;
					case 8:
						{
						_localctx = new LtContext(new BinaryOpContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_binaryOp);
						setState(116);
						if (!(precpred(_ctx, 7))) throw new FailedPredicateException(this, "precpred(_ctx, 7)");
						setState(117);
						match(LT);
						setState(118);
						binaryOp(8);
						}
						break;
					case 9:
						{
						_localctx = new GtContext(new BinaryOpContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_binaryOp);
						setState(119);
						if (!(precpred(_ctx, 6))) throw new FailedPredicateException(this, "precpred(_ctx, 6)");
						setState(120);
						match(GT);
						setState(121);
						binaryOp(7);
						}
						break;
					case 10:
						{
						_localctx = new LeContext(new BinaryOpContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_binaryOp);
						setState(122);
						if (!(precpred(_ctx, 5))) throw new FailedPredicateException(this, "precpred(_ctx, 5)");
						setState(123);
						match(LE);
						setState(124);
						binaryOp(6);
						}
						break;
					case 11:
						{
						_localctx = new GeContext(new BinaryOpContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_binaryOp);
						setState(125);
						if (!(precpred(_ctx, 4))) throw new FailedPredicateException(this, "precpred(_ctx, 4)");
						setState(126);
						match(GE);
						setState(127);
						binaryOp(5);
						}
						break;
					}
					} 
				}
				setState(132);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,11,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class LiteralContext extends ParserRuleContext {
		public IntContext int_() {
			return getRuleContext(IntContext.class,0);
		}
		public BoolContext bool() {
			return getRuleContext(BoolContext.class,0);
		}
		public LiteralContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_literal; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LanguageListener ) ((LanguageListener)listener).enterLiteral(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LanguageListener ) ((LanguageListener)listener).exitLiteral(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LanguageVisitor ) return ((LanguageVisitor<? extends T>)visitor).visitLiteral(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LiteralContext literal() throws RecognitionException {
		LiteralContext _localctx = new LiteralContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_literal);
		try {
			setState(135);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case SUB:
			case INTEGER:
				enterOuterAlt(_localctx, 1);
				{
				setState(133);
				int_();
				}
				break;
			case BOOL:
				enterOuterAlt(_localctx, 2);
				{
				setState(134);
				bool();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class BoolContext extends ParserRuleContext {
		public TerminalNode BOOL() { return getToken(LanguageParser.BOOL, 0); }
		public BoolContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_bool; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LanguageListener ) ((LanguageListener)listener).enterBool(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LanguageListener ) ((LanguageListener)listener).exitBool(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LanguageVisitor ) return ((LanguageVisitor<? extends T>)visitor).visitBool(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BoolContext bool() throws RecognitionException {
		BoolContext _localctx = new BoolContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_bool);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(137);
			match(BOOL);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class IntContext extends ParserRuleContext {
		public TerminalNode INTEGER() { return getToken(LanguageParser.INTEGER, 0); }
		public TerminalNode SUB() { return getToken(LanguageParser.SUB, 0); }
		public IntContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_int; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LanguageListener ) ((LanguageListener)listener).enterInt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LanguageListener ) ((LanguageListener)listener).exitInt(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LanguageVisitor ) return ((LanguageVisitor<? extends T>)visitor).visitInt(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IntContext int_() throws RecognitionException {
		IntContext _localctx = new IntContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_int);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(140);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==SUB) {
				{
				setState(139);
				match(SUB);
				}
			}

			setState(142);
			match(INTEGER);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 4:
			return binaryOp_sempred((BinaryOpContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean binaryOp_sempred(BinaryOpContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 14);
		case 1:
			return precpred(_ctx, 13);
		case 2:
			return precpred(_ctx, 12);
		case 3:
			return precpred(_ctx, 11);
		case 4:
			return precpred(_ctx, 10);
		case 5:
			return precpred(_ctx, 9);
		case 6:
			return precpred(_ctx, 8);
		case 7:
			return precpred(_ctx, 7);
		case 8:
			return precpred(_ctx, 6);
		case 9:
			return precpred(_ctx, 5);
		case 10:
			return precpred(_ctx, 4);
		}
		return true;
	}

	public static final String _serializedATN =
		"\u0004\u0001\u001d\u0091\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001"+
		"\u0002\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004"+
		"\u0002\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007\u0007\u0007"+
		"\u0001\u0000\u0004\u0000\u0012\b\u0000\u000b\u0000\f\u0000\u0013\u0001"+
		"\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0003\u0001\u001b"+
		"\b\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001"+
		"\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001"+
		"\u0001\u0001\u0001\u0001\u0001\u0003\u0001+\b\u0001\u0001\u0002\u0001"+
		"\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0003"+
		"\u00024\b\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001"+
		"\u0002\u0001\u0002\u0001\u0002\u0003\u0002=\b\u0002\u0005\u0002?\b\u0002"+
		"\n\u0002\f\u0002B\t\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002"+
		"\u0001\u0002\u0001\u0002\u0001\u0002\u0003\u0002K\b\u0002\u0003\u0002"+
		"M\b\u0002\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003"+
		"\u0001\u0003\u0003\u0003U\b\u0003\u0001\u0004\u0001\u0004\u0001\u0004"+
		"\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0003\u0004^\b\u0004"+
		"\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004"+
		"\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004"+
		"\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004"+
		"\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004"+
		"\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004"+
		"\u0001\u0004\u0001\u0004\u0001\u0004\u0005\u0004\u0081\b\u0004\n\u0004"+
		"\f\u0004\u0084\t\u0004\u0001\u0005\u0001\u0005\u0003\u0005\u0088\b\u0005"+
		"\u0001\u0006\u0001\u0006\u0001\u0007\u0003\u0007\u008d\b\u0007\u0001\u0007"+
		"\u0001\u0007\u0001\u0007\u0000\u0001\b\b\u0000\u0002\u0004\u0006\b\n\f"+
		"\u000e\u0000\u0000\u00a5\u0000\u0011\u0001\u0000\u0000\u0000\u0002*\u0001"+
		"\u0000\u0000\u0000\u0004,\u0001\u0000\u0000\u0000\u0006T\u0001\u0000\u0000"+
		"\u0000\b]\u0001\u0000\u0000\u0000\n\u0087\u0001\u0000\u0000\u0000\f\u0089"+
		"\u0001\u0000\u0000\u0000\u000e\u008c\u0001\u0000\u0000\u0000\u0010\u0012"+
		"\u0003\u0002\u0001\u0000\u0011\u0010\u0001\u0000\u0000\u0000\u0012\u0013"+
		"\u0001\u0000\u0000\u0000\u0013\u0011\u0001\u0000\u0000\u0000\u0013\u0014"+
		"\u0001\u0000\u0000\u0000\u0014\u0001\u0001\u0000\u0000\u0000\u0015\u0016"+
		"\u0005\u001b\u0000\u0000\u0016\u0017\u0005\u0001\u0000\u0000\u0017\u001a"+
		"\u0005\u0018\u0000\u0000\u0018\u0019\u0005\u0002\u0000\u0000\u0019\u001b"+
		"\u0003\u0006\u0003\u0000\u001a\u0018\u0001\u0000\u0000\u0000\u001a\u001b"+
		"\u0001\u0000\u0000\u0000\u001b\u001c\u0001\u0000\u0000\u0000\u001c+\u0005"+
		"\u0003\u0000\u0000\u001d\u001e\u0005\u001b\u0000\u0000\u001e\u001f\u0005"+
		"\u0004\u0000\u0000\u001f \u0003\u0006\u0003\u0000 !\u0005\u0003\u0000"+
		"\u0000!+\u0001\u0000\u0000\u0000\"#\u0005\u0014\u0000\u0000#$\u0003\u0006"+
		"\u0003\u0000$%\u0005\u0003\u0000\u0000%+\u0001\u0000\u0000\u0000&+\u0003"+
		"\u0004\u0002\u0000\'(\u0003\u0006\u0003\u0000()\u0005\u0003\u0000\u0000"+
		")+\u0001\u0000\u0000\u0000*\u0015\u0001\u0000\u0000\u0000*\u001d\u0001"+
		"\u0000\u0000\u0000*\"\u0001\u0000\u0000\u0000*&\u0001\u0000\u0000\u0000"+
		"*\'\u0001\u0000\u0000\u0000+\u0003\u0001\u0000\u0000\u0000,-\u0005\u0015"+
		"\u0000\u0000-3\u0003\u0006\u0003\u0000./\u0005\u0012\u0000\u0000/0\u0003"+
		"\u0000\u0000\u000001\u0005\u0013\u0000\u000014\u0001\u0000\u0000\u0000"+
		"24\u0003\u0002\u0001\u00003.\u0001\u0000\u0000\u000032\u0001\u0000\u0000"+
		"\u00004@\u0001\u0000\u0000\u000056\u0005\u0016\u0000\u00006<\u0003\u0006"+
		"\u0003\u000078\u0005\u0012\u0000\u000089\u0003\u0000\u0000\u00009:\u0005"+
		"\u0013\u0000\u0000:=\u0001\u0000\u0000\u0000;=\u0003\u0002\u0001\u0000"+
		"<7\u0001\u0000\u0000\u0000<;\u0001\u0000\u0000\u0000=?\u0001\u0000\u0000"+
		"\u0000>5\u0001\u0000\u0000\u0000?B\u0001\u0000\u0000\u0000@>\u0001\u0000"+
		"\u0000\u0000@A\u0001\u0000\u0000\u0000AL\u0001\u0000\u0000\u0000B@\u0001"+
		"\u0000\u0000\u0000CD\u0005\u0017\u0000\u0000DJ\u0003\u0006\u0003\u0000"+
		"EF\u0005\u0012\u0000\u0000FG\u0003\u0000\u0000\u0000GH\u0005\u0013\u0000"+
		"\u0000HK\u0001\u0000\u0000\u0000IK\u0003\u0002\u0001\u0000JE\u0001\u0000"+
		"\u0000\u0000JI\u0001\u0000\u0000\u0000KM\u0001\u0000\u0000\u0000LC\u0001"+
		"\u0000\u0000\u0000LM\u0001\u0000\u0000\u0000M\u0005\u0001\u0000\u0000"+
		"\u0000NU\u0003\n\u0005\u0000OU\u0005\u001b\u0000\u0000PU\u0003\b\u0004"+
		"\u0000QR\u0005\u001b\u0000\u0000RS\u0005\u0002\u0000\u0000SU\u0003\u0006"+
		"\u0003\u0000TN\u0001\u0000\u0000\u0000TO\u0001\u0000\u0000\u0000TP\u0001"+
		"\u0000\u0000\u0000TQ\u0001\u0000\u0000\u0000U\u0007\u0001\u0000\u0000"+
		"\u0000VW\u0006\u0004\uffff\uffff\u0000WX\u0005\u0010\u0000\u0000XY\u0003"+
		"\b\u0004\u0000YZ\u0005\u0011\u0000\u0000Z^\u0001\u0000\u0000\u0000[^\u0003"+
		"\n\u0005\u0000\\^\u0005\u001b\u0000\u0000]V\u0001\u0000\u0000\u0000]["+
		"\u0001\u0000\u0000\u0000]\\\u0001\u0000\u0000\u0000^\u0082\u0001\u0000"+
		"\u0000\u0000_`\n\u000e\u0000\u0000`a\u0005\u000f\u0000\u0000a\u0081\u0003"+
		"\b\u0004\u000fbc\n\r\u0000\u0000cd\u0005\r\u0000\u0000d\u0081\u0003\b"+
		"\u0004\u000eef\n\f\u0000\u0000fg\u0005\u000e\u0000\u0000g\u0081\u0003"+
		"\b\u0004\rhi\n\u000b\u0000\u0000ij\u0005\u000b\u0000\u0000j\u0081\u0003"+
		"\b\u0004\fkl\n\n\u0000\u0000lm\u0005\f\u0000\u0000m\u0081\u0003\b\u0004"+
		"\u000bno\n\t\u0000\u0000op\u0005\u0005\u0000\u0000p\u0081\u0003\b\u0004"+
		"\nqr\n\b\u0000\u0000rs\u0005\u0006\u0000\u0000s\u0081\u0003\b\u0004\t"+
		"tu\n\u0007\u0000\u0000uv\u0005\b\u0000\u0000v\u0081\u0003\b\u0004\bwx"+
		"\n\u0006\u0000\u0000xy\u0005\u0007\u0000\u0000y\u0081\u0003\b\u0004\u0007"+
		"z{\n\u0005\u0000\u0000{|\u0005\n\u0000\u0000|\u0081\u0003\b\u0004\u0006"+
		"}~\n\u0004\u0000\u0000~\u007f\u0005\t\u0000\u0000\u007f\u0081\u0003\b"+
		"\u0004\u0005\u0080_\u0001\u0000\u0000\u0000\u0080b\u0001\u0000\u0000\u0000"+
		"\u0080e\u0001\u0000\u0000\u0000\u0080h\u0001\u0000\u0000\u0000\u0080k"+
		"\u0001\u0000\u0000\u0000\u0080n\u0001\u0000\u0000\u0000\u0080q\u0001\u0000"+
		"\u0000\u0000\u0080t\u0001\u0000\u0000\u0000\u0080w\u0001\u0000\u0000\u0000"+
		"\u0080z\u0001\u0000\u0000\u0000\u0080}\u0001\u0000\u0000\u0000\u0081\u0084"+
		"\u0001\u0000\u0000\u0000\u0082\u0080\u0001\u0000\u0000\u0000\u0082\u0083"+
		"\u0001\u0000\u0000\u0000\u0083\t\u0001\u0000\u0000\u0000\u0084\u0082\u0001"+
		"\u0000\u0000\u0000\u0085\u0088\u0003\u000e\u0007\u0000\u0086\u0088\u0003"+
		"\f\u0006\u0000\u0087\u0085\u0001\u0000\u0000\u0000\u0087\u0086\u0001\u0000"+
		"\u0000\u0000\u0088\u000b\u0001\u0000\u0000\u0000\u0089\u008a\u0005\u0019"+
		"\u0000\u0000\u008a\r\u0001\u0000\u0000\u0000\u008b\u008d\u0005\u000e\u0000"+
		"\u0000\u008c\u008b\u0001\u0000\u0000\u0000\u008c\u008d\u0001\u0000\u0000"+
		"\u0000\u008d\u008e\u0001\u0000\u0000\u0000\u008e\u008f\u0005\u001a\u0000"+
		"\u0000\u008f\u000f\u0001\u0000\u0000\u0000\u000e\u0013\u001a*3<@JLT]\u0080"+
		"\u0082\u0087\u008c";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}