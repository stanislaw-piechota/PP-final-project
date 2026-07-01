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
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, EQUAL=6, NOT_EQUAL=7, GT=8, LT=9, 
		GE=10, LE=11, AND=12, OR=13, NOT=14, ADD=15, SUB=16, TIMES=17, DIV=18, 
		LPAR=19, RPAR=20, LBRACK=21, RBRACK=22, FUNC=23, RETURN=24, LOCK=25, UNLOCK=26, 
		FORK=27, JOIN=28, WHILE=29, PRINT=30, IF=31, ELSEIF=32, ELSE=33, TYPE=34, 
		BOOL=35, INTEGER=36, ID=37, COMMENT=38, WS=39;
	public static final int
		RULE_program = 0, RULE_block = 1, RULE_statement = 2, RULE_conditional = 3, 
		RULE_if = 4, RULE_elseif = 5, RULE_else = 6, RULE_whileLoop = 7, RULE_expression = 8, 
		RULE_literal = 9, RULE_bool = 10, RULE_int = 11;
	private static String[] makeRuleNames() {
		return new String[] {
			"program", "block", "statement", "conditional", "if", "elseif", "else", 
			"whileLoop", "expression", "literal", "bool", "int"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "':'", "'='", "';'", "','", "':='", "'=='", "'!='", "'>'", "'<'", 
			"'>='", "'<='", "'&&'", "'||'", "'!'", "'+'", "'-'", "'*'", "'/'", "'('", 
			"')'", "'{'", "'}'", "'function'", "'return'", "'lock'", "'unlock'", 
			"'fork'", "'join'", "'while'", "'print'", "'if'", "'elif'", "'else'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, "EQUAL", "NOT_EQUAL", "GT", "LT", 
			"GE", "LE", "AND", "OR", "NOT", "ADD", "SUB", "TIMES", "DIV", "LPAR", 
			"RPAR", "LBRACK", "RBRACK", "FUNC", "RETURN", "LOCK", "UNLOCK", "FORK", 
			"JOIN", "WHILE", "PRINT", "IF", "ELSEIF", "ELSE", "TYPE", "BOOL", "INTEGER", 
			"ID", "COMMENT", "WS"
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
	public static class ProgramContext extends ParserRuleContext {
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public ProgramContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_program; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LanguageVisitor ) return ((LanguageVisitor<? extends T>)visitor).visitProgram(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ProgramContext program() throws RecognitionException {
		ProgramContext _localctx = new ProgramContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_program);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(24);
			block();
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
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LanguageVisitor ) return ((LanguageVisitor<? extends T>)visitor).visitBlock(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BlockContext block() throws RecognitionException {
		BlockContext _localctx = new BlockContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_block);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(27); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(26);
				statement();
				}
				}
				setState(29); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & 244805353472L) != 0) );
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
	public static class ExplicitDeclarationContext extends StatementContext {
		public TerminalNode ID() { return getToken(LanguageParser.ID, 0); }
		public TerminalNode TYPE() { return getToken(LanguageParser.TYPE, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public ExplicitDeclarationContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LanguageVisitor ) return ((LanguageVisitor<? extends T>)visitor).visitExplicitDeclaration(this);
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
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LanguageVisitor ) return ((LanguageVisitor<? extends T>)visitor).visitPrint(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class UnlockContext extends StatementContext {
		public TerminalNode UNLOCK() { return getToken(LanguageParser.UNLOCK, 0); }
		public TerminalNode ID() { return getToken(LanguageParser.ID, 0); }
		public UnlockContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LanguageVisitor ) return ((LanguageVisitor<? extends T>)visitor).visitUnlock(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class LockContext extends StatementContext {
		public TerminalNode LOCK() { return getToken(LanguageParser.LOCK, 0); }
		public TerminalNode ID() { return getToken(LanguageParser.ID, 0); }
		public LockContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LanguageVisitor ) return ((LanguageVisitor<? extends T>)visitor).visitLock(this);
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
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LanguageVisitor ) return ((LanguageVisitor<? extends T>)visitor).visitCond(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class WhileContext extends StatementContext {
		public WhileLoopContext whileLoop() {
			return getRuleContext(WhileLoopContext.class,0);
		}
		public WhileContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LanguageVisitor ) return ((LanguageVisitor<? extends T>)visitor).visitWhile(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ThreadJoinContext extends StatementContext {
		public TerminalNode JOIN() { return getToken(LanguageParser.JOIN, 0); }
		public TerminalNode ID() { return getToken(LanguageParser.ID, 0); }
		public ThreadJoinContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LanguageVisitor ) return ((LanguageVisitor<? extends T>)visitor).visitThreadJoin(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ReturnContext extends StatementContext {
		public TerminalNode RETURN() { return getToken(LanguageParser.RETURN, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public ReturnContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LanguageVisitor ) return ((LanguageVisitor<? extends T>)visitor).visitReturn(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class FuncDefContext extends StatementContext {
		public TerminalNode FUNC() { return getToken(LanguageParser.FUNC, 0); }
		public List<TerminalNode> ID() { return getTokens(LanguageParser.ID); }
		public TerminalNode ID(int i) {
			return getToken(LanguageParser.ID, i);
		}
		public TerminalNode LPAR() { return getToken(LanguageParser.LPAR, 0); }
		public TerminalNode RPAR() { return getToken(LanguageParser.RPAR, 0); }
		public List<TerminalNode> TYPE() { return getTokens(LanguageParser.TYPE); }
		public TerminalNode TYPE(int i) {
			return getToken(LanguageParser.TYPE, i);
		}
		public TerminalNode LBRACK() { return getToken(LanguageParser.LBRACK, 0); }
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public TerminalNode RBRACK() { return getToken(LanguageParser.RBRACK, 0); }
		public FuncDefContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LanguageVisitor ) return ((LanguageVisitor<? extends T>)visitor).visitFuncDef(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StatementContext statement() throws RecognitionException {
		StatementContext _localctx = new StatementContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_statement);
		int _la;
		try {
			setState(85);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,4,_ctx) ) {
			case 1:
				_localctx = new ExplicitDeclarationContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(31);
				match(ID);
				setState(32);
				match(T__0);
				setState(33);
				match(TYPE);
				setState(36);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==T__1) {
					{
					setState(34);
					match(T__1);
					setState(35);
					expression(0);
					}
				}

				setState(38);
				match(T__2);
				}
				break;
			case 2:
				_localctx = new PrintContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(39);
				match(PRINT);
				setState(40);
				expression(0);
				setState(41);
				match(T__2);
				}
				break;
			case 3:
				_localctx = new CondContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(43);
				conditional();
				}
				break;
			case 4:
				_localctx = new ExprContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(44);
				expression(0);
				setState(45);
				match(T__2);
				}
				break;
			case 5:
				_localctx = new WhileContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(47);
				whileLoop();
				}
				break;
			case 6:
				_localctx = new ThreadJoinContext(_localctx);
				enterOuterAlt(_localctx, 6);
				{
				setState(48);
				match(JOIN);
				setState(49);
				match(ID);
				setState(50);
				match(T__2);
				}
				break;
			case 7:
				_localctx = new LockContext(_localctx);
				enterOuterAlt(_localctx, 7);
				{
				setState(51);
				match(LOCK);
				setState(52);
				match(ID);
				setState(53);
				match(T__2);
				}
				break;
			case 8:
				_localctx = new UnlockContext(_localctx);
				enterOuterAlt(_localctx, 8);
				{
				setState(54);
				match(UNLOCK);
				setState(55);
				match(ID);
				setState(56);
				match(T__2);
				}
				break;
			case 9:
				_localctx = new FuncDefContext(_localctx);
				enterOuterAlt(_localctx, 9);
				{
				setState(57);
				match(FUNC);
				setState(58);
				match(ID);
				setState(59);
				match(LPAR);
				setState(72);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==ID) {
					{
					setState(60);
					match(ID);
					setState(61);
					match(T__0);
					setState(62);
					match(TYPE);
					setState(69);
					_errHandler.sync(this);
					_la = _input.LA(1);
					while (_la==T__3) {
						{
						{
						setState(63);
						match(T__3);
						setState(64);
						match(ID);
						setState(65);
						match(T__0);
						setState(66);
						match(TYPE);
						}
						}
						setState(71);
						_errHandler.sync(this);
						_la = _input.LA(1);
					}
					}
				}

				setState(74);
				match(RPAR);
				setState(75);
				match(T__0);
				setState(76);
				match(TYPE);
				setState(77);
				match(LBRACK);
				setState(78);
				block();
				setState(79);
				match(RBRACK);
				}
				break;
			case 10:
				_localctx = new ReturnContext(_localctx);
				enterOuterAlt(_localctx, 10);
				{
				setState(81);
				match(RETURN);
				setState(82);
				expression(0);
				setState(83);
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
		public IfContext if_() {
			return getRuleContext(IfContext.class,0);
		}
		public List<ElseifContext> elseif() {
			return getRuleContexts(ElseifContext.class);
		}
		public ElseifContext elseif(int i) {
			return getRuleContext(ElseifContext.class,i);
		}
		public ElseContext else_() {
			return getRuleContext(ElseContext.class,0);
		}
		public ConditionalContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_conditional; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LanguageVisitor ) return ((LanguageVisitor<? extends T>)visitor).visitConditional(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ConditionalContext conditional() throws RecognitionException {
		ConditionalContext _localctx = new ConditionalContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_conditional);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(87);
			if_();
			setState(91);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,5,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(88);
					elseif();
					}
					} 
				}
				setState(93);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,5,_ctx);
			}
			setState(95);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,6,_ctx) ) {
			case 1:
				{
				setState(94);
				else_();
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
	public static class IfContext extends ParserRuleContext {
		public TerminalNode IF() { return getToken(LanguageParser.IF, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public StatementContext statement() {
			return getRuleContext(StatementContext.class,0);
		}
		public TerminalNode LBRACK() { return getToken(LanguageParser.LBRACK, 0); }
		public TerminalNode RBRACK() { return getToken(LanguageParser.RBRACK, 0); }
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public IfContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_if; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LanguageVisitor ) return ((LanguageVisitor<? extends T>)visitor).visitIf(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IfContext if_() throws RecognitionException {
		IfContext _localctx = new IfContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_if);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(97);
			match(IF);
			setState(98);
			expression(0);
			setState(105);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case LBRACK:
				{
				{
				setState(99);
				match(LBRACK);
				setState(101);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 244805353472L) != 0)) {
					{
					setState(100);
					block();
					}
				}

				setState(103);
				match(RBRACK);
				}
				}
				break;
			case NOT:
			case SUB:
			case LPAR:
			case FUNC:
			case RETURN:
			case LOCK:
			case UNLOCK:
			case FORK:
			case JOIN:
			case WHILE:
			case PRINT:
			case IF:
			case BOOL:
			case INTEGER:
			case ID:
				{
				setState(104);
				statement();
				}
				break;
			default:
				throw new NoViableAltException(this);
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
	public static class ElseifContext extends ParserRuleContext {
		public TerminalNode ELSEIF() { return getToken(LanguageParser.ELSEIF, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public StatementContext statement() {
			return getRuleContext(StatementContext.class,0);
		}
		public TerminalNode LBRACK() { return getToken(LanguageParser.LBRACK, 0); }
		public TerminalNode RBRACK() { return getToken(LanguageParser.RBRACK, 0); }
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public ElseifContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_elseif; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LanguageVisitor ) return ((LanguageVisitor<? extends T>)visitor).visitElseif(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ElseifContext elseif() throws RecognitionException {
		ElseifContext _localctx = new ElseifContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_elseif);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(107);
			match(ELSEIF);
			setState(108);
			expression(0);
			setState(115);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case LBRACK:
				{
				{
				setState(109);
				match(LBRACK);
				setState(111);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 244805353472L) != 0)) {
					{
					setState(110);
					block();
					}
				}

				setState(113);
				match(RBRACK);
				}
				}
				break;
			case NOT:
			case SUB:
			case LPAR:
			case FUNC:
			case RETURN:
			case LOCK:
			case UNLOCK:
			case FORK:
			case JOIN:
			case WHILE:
			case PRINT:
			case IF:
			case BOOL:
			case INTEGER:
			case ID:
				{
				setState(114);
				statement();
				}
				break;
			default:
				throw new NoViableAltException(this);
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
	public static class ElseContext extends ParserRuleContext {
		public TerminalNode ELSE() { return getToken(LanguageParser.ELSE, 0); }
		public StatementContext statement() {
			return getRuleContext(StatementContext.class,0);
		}
		public TerminalNode LBRACK() { return getToken(LanguageParser.LBRACK, 0); }
		public TerminalNode RBRACK() { return getToken(LanguageParser.RBRACK, 0); }
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public ElseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_else; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LanguageVisitor ) return ((LanguageVisitor<? extends T>)visitor).visitElse(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ElseContext else_() throws RecognitionException {
		ElseContext _localctx = new ElseContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_else);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(117);
			match(ELSE);
			setState(124);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case LBRACK:
				{
				{
				setState(118);
				match(LBRACK);
				setState(120);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 244805353472L) != 0)) {
					{
					setState(119);
					block();
					}
				}

				setState(122);
				match(RBRACK);
				}
				}
				break;
			case NOT:
			case SUB:
			case LPAR:
			case FUNC:
			case RETURN:
			case LOCK:
			case UNLOCK:
			case FORK:
			case JOIN:
			case WHILE:
			case PRINT:
			case IF:
			case BOOL:
			case INTEGER:
			case ID:
				{
				setState(123);
				statement();
				}
				break;
			default:
				throw new NoViableAltException(this);
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
	public static class WhileLoopContext extends ParserRuleContext {
		public TerminalNode WHILE() { return getToken(LanguageParser.WHILE, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public StatementContext statement() {
			return getRuleContext(StatementContext.class,0);
		}
		public TerminalNode LBRACK() { return getToken(LanguageParser.LBRACK, 0); }
		public TerminalNode RBRACK() { return getToken(LanguageParser.RBRACK, 0); }
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public WhileLoopContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_whileLoop; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LanguageVisitor ) return ((LanguageVisitor<? extends T>)visitor).visitWhileLoop(this);
			else return visitor.visitChildren(this);
		}
	}

	public final WhileLoopContext whileLoop() throws RecognitionException {
		WhileLoopContext _localctx = new WhileLoopContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_whileLoop);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(126);
			match(WHILE);
			setState(127);
			expression(0);
			setState(135);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case LBRACK:
				{
				{
				setState(128);
				match(LBRACK);
				setState(130);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 244805353472L) != 0)) {
					{
					setState(129);
					block();
					}
				}

				setState(132);
				match(RBRACK);
				}
				}
				break;
			case NOT:
			case SUB:
			case LPAR:
			case FUNC:
			case RETURN:
			case LOCK:
			case UNLOCK:
			case FORK:
			case JOIN:
			case WHILE:
			case PRINT:
			case IF:
			case BOOL:
			case INTEGER:
			case ID:
				{
				setState(133);
				statement();
				}
				break;
			case T__2:
				{
				setState(134);
				match(T__2);
				}
				break;
			default:
				throw new NoViableAltException(this);
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
	public static class ParContext extends ExpressionContext {
		public TerminalNode LPAR() { return getToken(LanguageParser.LPAR, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode RPAR() { return getToken(LanguageParser.RPAR, 0); }
		public ParContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LanguageVisitor ) return ((LanguageVisitor<? extends T>)visitor).visitPar(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ComparisonContext extends ExpressionContext {
		public Token op;
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode LT() { return getToken(LanguageParser.LT, 0); }
		public TerminalNode GT() { return getToken(LanguageParser.GT, 0); }
		public TerminalNode LE() { return getToken(LanguageParser.LE, 0); }
		public TerminalNode GE() { return getToken(LanguageParser.GE, 0); }
		public ComparisonContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LanguageVisitor ) return ((LanguageVisitor<? extends T>)visitor).visitComparison(this);
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
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LanguageVisitor ) return ((LanguageVisitor<? extends T>)visitor).visitAssignment(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class AddSubContext extends ExpressionContext {
		public Token op;
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode ADD() { return getToken(LanguageParser.ADD, 0); }
		public TerminalNode SUB() { return getToken(LanguageParser.SUB, 0); }
		public AddSubContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LanguageVisitor ) return ((LanguageVisitor<? extends T>)visitor).visitAddSub(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ImplicitDeclarationContext extends ExpressionContext {
		public TerminalNode ID() { return getToken(LanguageParser.ID, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public ImplicitDeclarationContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LanguageVisitor ) return ((LanguageVisitor<? extends T>)visitor).visitImplicitDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class MulDivContext extends ExpressionContext {
		public Token op;
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode TIMES() { return getToken(LanguageParser.TIMES, 0); }
		public TerminalNode DIV() { return getToken(LanguageParser.DIV, 0); }
		public MulDivContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LanguageVisitor ) return ((LanguageVisitor<? extends T>)visitor).visitMulDiv(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class EqualContext extends ExpressionContext {
		public Token op;
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode EQUAL() { return getToken(LanguageParser.EQUAL, 0); }
		public TerminalNode NOT_EQUAL() { return getToken(LanguageParser.NOT_EQUAL, 0); }
		public EqualContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LanguageVisitor ) return ((LanguageVisitor<? extends T>)visitor).visitEqual(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class AndOrContext extends ExpressionContext {
		public Token op;
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode AND() { return getToken(LanguageParser.AND, 0); }
		public TerminalNode OR() { return getToken(LanguageParser.OR, 0); }
		public AndOrContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LanguageVisitor ) return ((LanguageVisitor<? extends T>)visitor).visitAndOr(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class NotContext extends ExpressionContext {
		public TerminalNode NOT() { return getToken(LanguageParser.NOT, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public NotContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LanguageVisitor ) return ((LanguageVisitor<? extends T>)visitor).visitNot(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ThreadStartContext extends ExpressionContext {
		public TerminalNode FORK() { return getToken(LanguageParser.FORK, 0); }
		public TerminalNode ID() { return getToken(LanguageParser.ID, 0); }
		public TerminalNode LPAR() { return getToken(LanguageParser.LPAR, 0); }
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode RPAR() { return getToken(LanguageParser.RPAR, 0); }
		public ThreadStartContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LanguageVisitor ) return ((LanguageVisitor<? extends T>)visitor).visitThreadStart(this);
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
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LanguageVisitor ) return ((LanguageVisitor<? extends T>)visitor).visitExprId(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class FuncCallContext extends ExpressionContext {
		public TerminalNode ID() { return getToken(LanguageParser.ID, 0); }
		public TerminalNode LPAR() { return getToken(LanguageParser.LPAR, 0); }
		public TerminalNode RPAR() { return getToken(LanguageParser.RPAR, 0); }
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public FuncCallContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LanguageVisitor ) return ((LanguageVisitor<? extends T>)visitor).visitFuncCall(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExpressionContext expression() throws RecognitionException {
		return expression(0);
	}

	private ExpressionContext expression(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		ExpressionContext _localctx = new ExpressionContext(_ctx, _parentState);
		ExpressionContext _prevctx = _localctx;
		int _startState = 16;
		enterRecursionRule(_localctx, 16, RULE_expression, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(180);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,19,_ctx) ) {
			case 1:
				{
				_localctx = new ExprLitContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(138);
				literal();
				}
				break;
			case 2:
				{
				_localctx = new ThreadStartContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(139);
				match(FORK);
				setState(140);
				match(ID);
				setState(152);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,16,_ctx) ) {
				case 1:
					{
					setState(141);
					match(LPAR);
					setState(142);
					expression(0);
					setState(147);
					_errHandler.sync(this);
					_la = _input.LA(1);
					while (_la==T__3) {
						{
						{
						setState(143);
						match(T__3);
						setState(144);
						expression(0);
						}
						}
						setState(149);
						_errHandler.sync(this);
						_la = _input.LA(1);
					}
					setState(150);
					match(RPAR);
					}
					break;
				}
				}
				break;
			case 3:
				{
				_localctx = new FuncCallContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(154);
				match(ID);
				setState(155);
				match(LPAR);
				setState(164);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 240652992512L) != 0)) {
					{
					setState(156);
					expression(0);
					setState(161);
					_errHandler.sync(this);
					_la = _input.LA(1);
					while (_la==T__3) {
						{
						{
						setState(157);
						match(T__3);
						setState(158);
						expression(0);
						}
						}
						setState(163);
						_errHandler.sync(this);
						_la = _input.LA(1);
					}
					}
				}

				setState(166);
				match(RPAR);
				}
				break;
			case 4:
				{
				_localctx = new ExprIdContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(167);
				match(ID);
				}
				break;
			case 5:
				{
				_localctx = new ParContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(168);
				match(LPAR);
				setState(169);
				expression(0);
				setState(170);
				match(RPAR);
				}
				break;
			case 6:
				{
				_localctx = new NotContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(172);
				match(NOT);
				setState(173);
				expression(5);
				}
				break;
			case 7:
				{
				_localctx = new ImplicitDeclarationContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(174);
				match(ID);
				setState(175);
				match(T__4);
				setState(176);
				expression(2);
				}
				break;
			case 8:
				{
				_localctx = new AssignmentContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(177);
				match(ID);
				setState(178);
				match(T__1);
				setState(179);
				expression(1);
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(199);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,21,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(197);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,20,_ctx) ) {
					case 1:
						{
						_localctx = new MulDivContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(182);
						if (!(precpred(_ctx, 8))) throw new FailedPredicateException(this, "precpred(_ctx, 8)");
						setState(183);
						((MulDivContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==TIMES || _la==DIV) ) {
							((MulDivContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(184);
						expression(9);
						}
						break;
					case 2:
						{
						_localctx = new AddSubContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(185);
						if (!(precpred(_ctx, 7))) throw new FailedPredicateException(this, "precpred(_ctx, 7)");
						setState(186);
						((AddSubContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==ADD || _la==SUB) ) {
							((AddSubContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(187);
						expression(8);
						}
						break;
					case 3:
						{
						_localctx = new ComparisonContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(188);
						if (!(precpred(_ctx, 6))) throw new FailedPredicateException(this, "precpred(_ctx, 6)");
						setState(189);
						((ComparisonContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 3840L) != 0)) ) {
							((ComparisonContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(190);
						expression(7);
						}
						break;
					case 4:
						{
						_localctx = new EqualContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(191);
						if (!(precpred(_ctx, 4))) throw new FailedPredicateException(this, "precpred(_ctx, 4)");
						setState(192);
						((EqualContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==EQUAL || _la==NOT_EQUAL) ) {
							((EqualContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(193);
						expression(5);
						}
						break;
					case 5:
						{
						_localctx = new AndOrContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(194);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(195);
						((AndOrContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==AND || _la==OR) ) {
							((AndOrContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(196);
						expression(4);
						}
						break;
					}
					} 
				}
				setState(201);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,21,_ctx);
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
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LanguageVisitor ) return ((LanguageVisitor<? extends T>)visitor).visitLiteral(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LiteralContext literal() throws RecognitionException {
		LiteralContext _localctx = new LiteralContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_literal);
		try {
			setState(204);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case SUB:
			case INTEGER:
				enterOuterAlt(_localctx, 1);
				{
				setState(202);
				int_();
				}
				break;
			case BOOL:
				enterOuterAlt(_localctx, 2);
				{
				setState(203);
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
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LanguageVisitor ) return ((LanguageVisitor<? extends T>)visitor).visitBool(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BoolContext bool() throws RecognitionException {
		BoolContext _localctx = new BoolContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_bool);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(206);
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
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LanguageVisitor ) return ((LanguageVisitor<? extends T>)visitor).visitInt(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IntContext int_() throws RecognitionException {
		IntContext _localctx = new IntContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_int);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(209);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==SUB) {
				{
				setState(208);
				match(SUB);
				}
			}

			setState(211);
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
		case 8:
			return expression_sempred((ExpressionContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean expression_sempred(ExpressionContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 8);
		case 1:
			return precpred(_ctx, 7);
		case 2:
			return precpred(_ctx, 6);
		case 3:
			return precpred(_ctx, 4);
		case 4:
			return precpred(_ctx, 3);
		}
		return true;
	}

	public static final String _serializedATN =
		"\u0004\u0001\'\u00d6\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0002"+
		"\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004\u0002"+
		"\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007\u0007\u0007\u0002"+
		"\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b\u0007\u000b\u0001"+
		"\u0000\u0001\u0000\u0001\u0001\u0004\u0001\u001c\b\u0001\u000b\u0001\f"+
		"\u0001\u001d\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002"+
		"\u0003\u0002%\b\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002"+
		"\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002"+
		"\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002"+
		"\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002"+
		"\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002"+
		"\u0001\u0002\u0005\u0002D\b\u0002\n\u0002\f\u0002G\t\u0002\u0003\u0002"+
		"I\b\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002"+
		"\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002"+
		"\u0003\u0002V\b\u0002\u0001\u0003\u0001\u0003\u0005\u0003Z\b\u0003\n\u0003"+
		"\f\u0003]\t\u0003\u0001\u0003\u0003\u0003`\b\u0003\u0001\u0004\u0001\u0004"+
		"\u0001\u0004\u0001\u0004\u0003\u0004f\b\u0004\u0001\u0004\u0001\u0004"+
		"\u0003\u0004j\b\u0004\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005"+
		"\u0003\u0005p\b\u0005\u0001\u0005\u0001\u0005\u0003\u0005t\b\u0005\u0001"+
		"\u0006\u0001\u0006\u0001\u0006\u0003\u0006y\b\u0006\u0001\u0006\u0001"+
		"\u0006\u0003\u0006}\b\u0006\u0001\u0007\u0001\u0007\u0001\u0007\u0001"+
		"\u0007\u0003\u0007\u0083\b\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0003"+
		"\u0007\u0088\b\u0007\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001"+
		"\b\u0001\b\u0005\b\u0092\b\b\n\b\f\b\u0095\t\b\u0001\b\u0001\b\u0003\b"+
		"\u0099\b\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0005\b\u00a0\b\b\n"+
		"\b\f\b\u00a3\t\b\u0003\b\u00a5\b\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001"+
		"\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001"+
		"\b\u0003\b\u00b5\b\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001"+
		"\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0005"+
		"\b\u00c6\b\b\n\b\f\b\u00c9\t\b\u0001\t\u0001\t\u0003\t\u00cd\b\t\u0001"+
		"\n\u0001\n\u0001\u000b\u0003\u000b\u00d2\b\u000b\u0001\u000b\u0001\u000b"+
		"\u0001\u000b\u0000\u0001\u0010\f\u0000\u0002\u0004\u0006\b\n\f\u000e\u0010"+
		"\u0012\u0014\u0016\u0000\u0005\u0001\u0000\u0011\u0012\u0001\u0000\u000f"+
		"\u0010\u0001\u0000\b\u000b\u0001\u0000\u0006\u0007\u0001\u0000\f\r\u00f3"+
		"\u0000\u0018\u0001\u0000\u0000\u0000\u0002\u001b\u0001\u0000\u0000\u0000"+
		"\u0004U\u0001\u0000\u0000\u0000\u0006W\u0001\u0000\u0000\u0000\ba\u0001"+
		"\u0000\u0000\u0000\nk\u0001\u0000\u0000\u0000\fu\u0001\u0000\u0000\u0000"+
		"\u000e~\u0001\u0000\u0000\u0000\u0010\u00b4\u0001\u0000\u0000\u0000\u0012"+
		"\u00cc\u0001\u0000\u0000\u0000\u0014\u00ce\u0001\u0000\u0000\u0000\u0016"+
		"\u00d1\u0001\u0000\u0000\u0000\u0018\u0019\u0003\u0002\u0001\u0000\u0019"+
		"\u0001\u0001\u0000\u0000\u0000\u001a\u001c\u0003\u0004\u0002\u0000\u001b"+
		"\u001a\u0001\u0000\u0000\u0000\u001c\u001d\u0001\u0000\u0000\u0000\u001d"+
		"\u001b\u0001\u0000\u0000\u0000\u001d\u001e\u0001\u0000\u0000\u0000\u001e"+
		"\u0003\u0001\u0000\u0000\u0000\u001f \u0005%\u0000\u0000 !\u0005\u0001"+
		"\u0000\u0000!$\u0005\"\u0000\u0000\"#\u0005\u0002\u0000\u0000#%\u0003"+
		"\u0010\b\u0000$\"\u0001\u0000\u0000\u0000$%\u0001\u0000\u0000\u0000%&"+
		"\u0001\u0000\u0000\u0000&V\u0005\u0003\u0000\u0000\'(\u0005\u001e\u0000"+
		"\u0000()\u0003\u0010\b\u0000)*\u0005\u0003\u0000\u0000*V\u0001\u0000\u0000"+
		"\u0000+V\u0003\u0006\u0003\u0000,-\u0003\u0010\b\u0000-.\u0005\u0003\u0000"+
		"\u0000.V\u0001\u0000\u0000\u0000/V\u0003\u000e\u0007\u000001\u0005\u001c"+
		"\u0000\u000012\u0005%\u0000\u00002V\u0005\u0003\u0000\u000034\u0005\u0019"+
		"\u0000\u000045\u0005%\u0000\u00005V\u0005\u0003\u0000\u000067\u0005\u001a"+
		"\u0000\u000078\u0005%\u0000\u00008V\u0005\u0003\u0000\u00009:\u0005\u0017"+
		"\u0000\u0000:;\u0005%\u0000\u0000;H\u0005\u0013\u0000\u0000<=\u0005%\u0000"+
		"\u0000=>\u0005\u0001\u0000\u0000>E\u0005\"\u0000\u0000?@\u0005\u0004\u0000"+
		"\u0000@A\u0005%\u0000\u0000AB\u0005\u0001\u0000\u0000BD\u0005\"\u0000"+
		"\u0000C?\u0001\u0000\u0000\u0000DG\u0001\u0000\u0000\u0000EC\u0001\u0000"+
		"\u0000\u0000EF\u0001\u0000\u0000\u0000FI\u0001\u0000\u0000\u0000GE\u0001"+
		"\u0000\u0000\u0000H<\u0001\u0000\u0000\u0000HI\u0001\u0000\u0000\u0000"+
		"IJ\u0001\u0000\u0000\u0000JK\u0005\u0014\u0000\u0000KL\u0005\u0001\u0000"+
		"\u0000LM\u0005\"\u0000\u0000MN\u0005\u0015\u0000\u0000NO\u0003\u0002\u0001"+
		"\u0000OP\u0005\u0016\u0000\u0000PV\u0001\u0000\u0000\u0000QR\u0005\u0018"+
		"\u0000\u0000RS\u0003\u0010\b\u0000ST\u0005\u0003\u0000\u0000TV\u0001\u0000"+
		"\u0000\u0000U\u001f\u0001\u0000\u0000\u0000U\'\u0001\u0000\u0000\u0000"+
		"U+\u0001\u0000\u0000\u0000U,\u0001\u0000\u0000\u0000U/\u0001\u0000\u0000"+
		"\u0000U0\u0001\u0000\u0000\u0000U3\u0001\u0000\u0000\u0000U6\u0001\u0000"+
		"\u0000\u0000U9\u0001\u0000\u0000\u0000UQ\u0001\u0000\u0000\u0000V\u0005"+
		"\u0001\u0000\u0000\u0000W[\u0003\b\u0004\u0000XZ\u0003\n\u0005\u0000Y"+
		"X\u0001\u0000\u0000\u0000Z]\u0001\u0000\u0000\u0000[Y\u0001\u0000\u0000"+
		"\u0000[\\\u0001\u0000\u0000\u0000\\_\u0001\u0000\u0000\u0000][\u0001\u0000"+
		"\u0000\u0000^`\u0003\f\u0006\u0000_^\u0001\u0000\u0000\u0000_`\u0001\u0000"+
		"\u0000\u0000`\u0007\u0001\u0000\u0000\u0000ab\u0005\u001f\u0000\u0000"+
		"bi\u0003\u0010\b\u0000ce\u0005\u0015\u0000\u0000df\u0003\u0002\u0001\u0000"+
		"ed\u0001\u0000\u0000\u0000ef\u0001\u0000\u0000\u0000fg\u0001\u0000\u0000"+
		"\u0000gj\u0005\u0016\u0000\u0000hj\u0003\u0004\u0002\u0000ic\u0001\u0000"+
		"\u0000\u0000ih\u0001\u0000\u0000\u0000j\t\u0001\u0000\u0000\u0000kl\u0005"+
		" \u0000\u0000ls\u0003\u0010\b\u0000mo\u0005\u0015\u0000\u0000np\u0003"+
		"\u0002\u0001\u0000on\u0001\u0000\u0000\u0000op\u0001\u0000\u0000\u0000"+
		"pq\u0001\u0000\u0000\u0000qt\u0005\u0016\u0000\u0000rt\u0003\u0004\u0002"+
		"\u0000sm\u0001\u0000\u0000\u0000sr\u0001\u0000\u0000\u0000t\u000b\u0001"+
		"\u0000\u0000\u0000u|\u0005!\u0000\u0000vx\u0005\u0015\u0000\u0000wy\u0003"+
		"\u0002\u0001\u0000xw\u0001\u0000\u0000\u0000xy\u0001\u0000\u0000\u0000"+
		"yz\u0001\u0000\u0000\u0000z}\u0005\u0016\u0000\u0000{}\u0003\u0004\u0002"+
		"\u0000|v\u0001\u0000\u0000\u0000|{\u0001\u0000\u0000\u0000}\r\u0001\u0000"+
		"\u0000\u0000~\u007f\u0005\u001d\u0000\u0000\u007f\u0087\u0003\u0010\b"+
		"\u0000\u0080\u0082\u0005\u0015\u0000\u0000\u0081\u0083\u0003\u0002\u0001"+
		"\u0000\u0082\u0081\u0001\u0000\u0000\u0000\u0082\u0083\u0001\u0000\u0000"+
		"\u0000\u0083\u0084\u0001\u0000\u0000\u0000\u0084\u0088\u0005\u0016\u0000"+
		"\u0000\u0085\u0088\u0003\u0004\u0002\u0000\u0086\u0088\u0005\u0003\u0000"+
		"\u0000\u0087\u0080\u0001\u0000\u0000\u0000\u0087\u0085\u0001\u0000\u0000"+
		"\u0000\u0087\u0086\u0001\u0000\u0000\u0000\u0088\u000f\u0001\u0000\u0000"+
		"\u0000\u0089\u008a\u0006\b\uffff\uffff\u0000\u008a\u00b5\u0003\u0012\t"+
		"\u0000\u008b\u008c\u0005\u001b\u0000\u0000\u008c\u0098\u0005%\u0000\u0000"+
		"\u008d\u008e\u0005\u0013\u0000\u0000\u008e\u0093\u0003\u0010\b\u0000\u008f"+
		"\u0090\u0005\u0004\u0000\u0000\u0090\u0092\u0003\u0010\b\u0000\u0091\u008f"+
		"\u0001\u0000\u0000\u0000\u0092\u0095\u0001\u0000\u0000\u0000\u0093\u0091"+
		"\u0001\u0000\u0000\u0000\u0093\u0094\u0001\u0000\u0000\u0000\u0094\u0096"+
		"\u0001\u0000\u0000\u0000\u0095\u0093\u0001\u0000\u0000\u0000\u0096\u0097"+
		"\u0005\u0014\u0000\u0000\u0097\u0099\u0001\u0000\u0000\u0000\u0098\u008d"+
		"\u0001\u0000\u0000\u0000\u0098\u0099\u0001\u0000\u0000\u0000\u0099\u00b5"+
		"\u0001\u0000\u0000\u0000\u009a\u009b\u0005%\u0000\u0000\u009b\u00a4\u0005"+
		"\u0013\u0000\u0000\u009c\u00a1\u0003\u0010\b\u0000\u009d\u009e\u0005\u0004"+
		"\u0000\u0000\u009e\u00a0\u0003\u0010\b\u0000\u009f\u009d\u0001\u0000\u0000"+
		"\u0000\u00a0\u00a3\u0001\u0000\u0000\u0000\u00a1\u009f\u0001\u0000\u0000"+
		"\u0000\u00a1\u00a2\u0001\u0000\u0000\u0000\u00a2\u00a5\u0001\u0000\u0000"+
		"\u0000\u00a3\u00a1\u0001\u0000\u0000\u0000\u00a4\u009c\u0001\u0000\u0000"+
		"\u0000\u00a4\u00a5\u0001\u0000\u0000\u0000\u00a5\u00a6\u0001\u0000\u0000"+
		"\u0000\u00a6\u00b5\u0005\u0014\u0000\u0000\u00a7\u00b5\u0005%\u0000\u0000"+
		"\u00a8\u00a9\u0005\u0013\u0000\u0000\u00a9\u00aa\u0003\u0010\b\u0000\u00aa"+
		"\u00ab\u0005\u0014\u0000\u0000\u00ab\u00b5\u0001\u0000\u0000\u0000\u00ac"+
		"\u00ad\u0005\u000e\u0000\u0000\u00ad\u00b5\u0003\u0010\b\u0005\u00ae\u00af"+
		"\u0005%\u0000\u0000\u00af\u00b0\u0005\u0005\u0000\u0000\u00b0\u00b5\u0003"+
		"\u0010\b\u0002\u00b1\u00b2\u0005%\u0000\u0000\u00b2\u00b3\u0005\u0002"+
		"\u0000\u0000\u00b3\u00b5\u0003\u0010\b\u0001\u00b4\u0089\u0001\u0000\u0000"+
		"\u0000\u00b4\u008b\u0001\u0000\u0000\u0000\u00b4\u009a\u0001\u0000\u0000"+
		"\u0000\u00b4\u00a7\u0001\u0000\u0000\u0000\u00b4\u00a8\u0001\u0000\u0000"+
		"\u0000\u00b4\u00ac\u0001\u0000\u0000\u0000\u00b4\u00ae\u0001\u0000\u0000"+
		"\u0000\u00b4\u00b1\u0001\u0000\u0000\u0000\u00b5\u00c7\u0001\u0000\u0000"+
		"\u0000\u00b6\u00b7\n\b\u0000\u0000\u00b7\u00b8\u0007\u0000\u0000\u0000"+
		"\u00b8\u00c6\u0003\u0010\b\t\u00b9\u00ba\n\u0007\u0000\u0000\u00ba\u00bb"+
		"\u0007\u0001\u0000\u0000\u00bb\u00c6\u0003\u0010\b\b\u00bc\u00bd\n\u0006"+
		"\u0000\u0000\u00bd\u00be\u0007\u0002\u0000\u0000\u00be\u00c6\u0003\u0010"+
		"\b\u0007\u00bf\u00c0\n\u0004\u0000\u0000\u00c0\u00c1\u0007\u0003\u0000"+
		"\u0000\u00c1\u00c6\u0003\u0010\b\u0005\u00c2\u00c3\n\u0003\u0000\u0000"+
		"\u00c3\u00c4\u0007\u0004\u0000\u0000\u00c4\u00c6\u0003\u0010\b\u0004\u00c5"+
		"\u00b6\u0001\u0000\u0000\u0000\u00c5\u00b9\u0001\u0000\u0000\u0000\u00c5"+
		"\u00bc\u0001\u0000\u0000\u0000\u00c5\u00bf\u0001\u0000\u0000\u0000\u00c5"+
		"\u00c2\u0001\u0000\u0000\u0000\u00c6\u00c9\u0001\u0000\u0000\u0000\u00c7"+
		"\u00c5\u0001\u0000\u0000\u0000\u00c7\u00c8\u0001\u0000\u0000\u0000\u00c8"+
		"\u0011\u0001\u0000\u0000\u0000\u00c9\u00c7\u0001\u0000\u0000\u0000\u00ca"+
		"\u00cd\u0003\u0016\u000b\u0000\u00cb\u00cd\u0003\u0014\n\u0000\u00cc\u00ca"+
		"\u0001\u0000\u0000\u0000\u00cc\u00cb\u0001\u0000\u0000\u0000\u00cd\u0013"+
		"\u0001\u0000\u0000\u0000\u00ce\u00cf\u0005#\u0000\u0000\u00cf\u0015\u0001"+
		"\u0000\u0000\u0000\u00d0\u00d2\u0005\u0010\u0000\u0000\u00d1\u00d0\u0001"+
		"\u0000\u0000\u0000\u00d1\u00d2\u0001\u0000\u0000\u0000\u00d2\u00d3\u0001"+
		"\u0000\u0000\u0000\u00d3\u00d4\u0005$\u0000\u0000\u00d4\u0017\u0001\u0000"+
		"\u0000\u0000\u0018\u001d$EHU[_eiosx|\u0082\u0087\u0093\u0098\u00a1\u00a4"+
		"\u00b4\u00c5\u00c7\u00cc\u00d1";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}