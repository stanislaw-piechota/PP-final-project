import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.tree.ParseTree;
import pp.LanguageCompiler;
import pp.errors.ErrorListener;
import pp.grammar.LanguageLexer;
import pp.grammar.LanguageParser;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    public final static String PATH = "src/main/resources/samples/";
    public final static String INPUT_PATH = PATH + "threads.lang";
    public final static String OUTPUT_PATH = PATH + "output.json";
    public static final Path STACK_PATH = Paths.get("stack");
    public static final boolean COMPILE = false;

    public static void main(String[] args) throws IOException, InterruptedException {
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

        LanguageCompiler compiler = new LanguageCompiler();
        System.out.println(tree.toStringTree(parser));
        errorListener.throwException();

        String result = compiler.compile(tree);
        System.out.println(result);

        File output = new File(OUTPUT_PATH);
        Files.write(output.toPath(), result.getBytes());

        if (COMPILE) {
            String[] command = new String[]{
                    STACK_PATH.toString(),
                    "run",
            };

            Process child = Runtime.getRuntime().exec(command, null, Paths.get("stack-my-lang").toFile());
            OutputStream out = child.getOutputStream();
            out.write(result.getBytes(StandardCharsets.UTF_8));
            out.close();

            InputStream in = child.getInputStream();
            InputStream err = child.getErrorStream();
            child.waitFor();
            System.out.println(new String(in.readAllBytes()).strip());
            System.out.println(new String(err.readAllBytes()).strip());
        }
    }
}
