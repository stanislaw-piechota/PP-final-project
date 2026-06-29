import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.concurrent.Callable;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.tree.ParseTree;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;
import pp.LanguageElaborator;
import pp.errors.ErrorListener;
import pp.grammar.LanguageLexer;
import pp.grammar.LanguageParser;

@Command(name="langC", mixinStandardHelpOptions = true,
        description = "Compile & run .lang file")
public class Compiler implements Callable<Integer> {
    public final static File DEFAULT_OUTPUT = new File("output.json.temp");

    @Option(names = "--backend", description = "The path to backend executable", defaultValue = "bin/backend")
    File backend;

    @Parameters(index="0", arity = "1", description = "The source file to compile & run")
    File source = new File("input.lang");

    @Option(names={"-o", "--output"}, description = "The SPROCKIL output path. By default not saved")
    File output;

    @Option(names="--run", negatable = true, description = "Run the SPROCKIL implementation",
            defaultValue = "true", fallbackValue = "true")
    boolean run;

    @Option(names={"-v", "--verbose"}, description = "Show verbose logs")
    boolean verbose;

    private boolean saveOutput = false;

    public static void main(String[] args) {
        int exitCode = new CommandLine(new Compiler()).execute(args);
        System.exit(exitCode);
    }

    @Override
    public Integer call() throws Exception {
        CharStream chars = CharStreams.fromPath(source.toPath());
        ErrorListener errorListener = new ErrorListener();
        Lexer lexer = new LanguageLexer(chars);
        lexer.removeErrorListeners();
        lexer.addErrorListener(errorListener);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        LanguageParser parser = new LanguageParser(tokens);
        parser.removeErrorListeners();
        parser.addErrorListener(errorListener);
        ParseTree tree = parser.program();

        LanguageElaborator compiler = new LanguageElaborator();
        errorListener.throwException();

        String result = compiler.compile(tree);

        if (verbose) {
            System.out.println(tree.toStringTree(parser));
            System.out.println(result);
        }

        if (output == null)
            output = DEFAULT_OUTPUT;
        else
            saveOutput = true;
        Files.write(output.toPath(), result.getBytes());

        try {
            if (run) {
                String[] command = saveOutput ? new String[] {
                        backend.toString(),
                        output.toString(),
                        output.toString().replace(".json", "").concat(".spril")
                } : new String[] {
                        backend.toString(),
                        output.toString()
                };

                Process child = Runtime.getRuntime().exec(command, null);
                InputStream in = child.getInputStream();
                InputStream err = child.getErrorStream();
                child.waitFor();
                System.out.println(new String(in.readAllBytes()).strip());
                System.out.println(new String(err.readAllBytes()).strip());
            }
        } finally {
            if (!saveOutput)
                Files.delete(output.toPath());
        }

        return 0;
    }
}
