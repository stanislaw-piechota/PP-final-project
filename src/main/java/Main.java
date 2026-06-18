import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.tree.ParseTree;
import pp.Compiler;
import pp.errors.ErrorListener;
import pp.grammar.LanguageBaseListener;
import pp.grammar.LanguageLexer;
import pp.grammar.LanguageParser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class Main extends LanguageBaseListener {
    public final static String PATH = "src/main/resources/samples/";
    public final static String INPUT_PATH = PATH + "expressions.lang";
    public final static String OUTPUT_PATH = PATH + "output.json";

    public static void main(String[] args) throws IOException {
        CharStream chars = CharStreams.fromPath(new File(INPUT_PATH).toPath());
        ErrorListener errorListener = new ErrorListener();
        Lexer lexer = new LanguageLexer(chars);
        lexer.removeErrorListeners();
        lexer.addErrorListener(errorListener);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        LanguageParser parser = new LanguageParser(tokens);
        parser.removeErrorListeners();
        parser.addErrorListener(errorListener);
        ParseTree tree = parser.program();

        Compiler compiler = new Compiler();
        System.out.println(tree.toStringTree(parser));

        String result = compiler.compile(tree);
        System.out.println(result);
        errorListener.throwException();

        File output = new File(OUTPUT_PATH);
        Files.write(output.toPath(), result.getBytes());
    }
}
