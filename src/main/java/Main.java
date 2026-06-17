import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.tree.ParseTree;
import pp.errors.ErrorListener;
import pp.grammar.LanguageBaseListener;
import pp.grammar.LanguageLexer;
import pp.grammar.LanguageParser;

import java.io.File;
import java.io.IOException;

public class Main extends LanguageBaseListener {
    public final static String PATH = "src/main/java/pp/samples/example";

    public static void main(String[] args) throws IOException {
        CharStream chars = CharStreams.fromPath(new File(PATH).toPath());
        ErrorListener errorListener = new ErrorListener();
        Lexer lexer = new LanguageLexer(chars);
        lexer.removeErrorListeners();
        lexer.addErrorListener(errorListener);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        LanguageParser parser = new LanguageParser(tokens);
        parser.removeErrorListeners();
        parser.addErrorListener(errorListener);
        ParseTree tree = parser.block();
        System.out.println(tree.toStringTree(parser));
        errorListener.throwException();
    }
}
