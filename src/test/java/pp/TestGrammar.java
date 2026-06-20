package pp;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.tree.ParseTree;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pp.errors.ErrorListener;
import pp.errors.ParseException;
import pp.grammar.LanguageLexer;
import pp.grammar.LanguageParser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

public class TestGrammar {
    @Test
    public void testBasics() {
        success("declarations");
        success("assignments");
        success("expressions");
        success("print");
        success("conditionals");
        success("while");
        success("func_basics");
        success("func_call");
        success("func_in_func");
        success("threads");
        fail("expressions_mismatch");
    }

    private void success(String fileName) {
        try {
            runTest(fileName);
        } catch (ParseException e) {
            System.err.println(e.getMessage());
            Assertions.fail();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void fail(String fileName) {
        try {
            runTest(fileName);
            Assertions.fail();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            System.err.println(e.getMessage());
        }
    }

    private void runTest(String fileName) throws IOException, ParseException {
        Path path = new File(PATH + fileName + ".lang").toPath();
        CharStream chars = CharStreams.fromPath(path);
        ErrorListener errorListener = new ErrorListener();
        Lexer lexer = new LanguageLexer(chars);
        lexer.removeErrorListeners();
        lexer.addErrorListener(errorListener);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        LanguageParser parser = new LanguageParser(tokens);
        parser.removeErrorListeners();
        parser.addErrorListener(errorListener);
        ParseTree tree = parser.program();

        LanguageCompiler compiler = new LanguageCompiler();
        String result = compiler.compile(tree);

        if (SHOW) {
            System.out.println(result);
        }

        errorListener.throwException();
    }

    public static final boolean SHOW = true;
    public static final String PATH = "src/main/resources/samples/";
}
