package pp;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
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
import pp.errors.ErrorListener;
import pp.grammar.LanguageLexer;
import pp.grammar.LanguageParser;
import pp.helpers.ChangeableOnce;

@Command(name="langC", mixinStandardHelpOptions = true,
        description = "Compile & run .lang file")
public class Compiler implements Callable<String> {
    public final static File DEFAULT_OUTPUT = new File("output");

    @Option(names = "--backend", description = "The path to backend executable", defaultValue = "bin/backend")
    File backend;

    @Parameters(index="0", arity = "1", description = "The source file to compile & run")
    File source = new File("input.lang");

    @Option(names={"-o", "--output"}, description = "The SPROCKIL output path. By default not saved")
    protected File output = null;

    @Option(names="--run", negatable = true, description = "Run the SPROCKIL implementation",
            defaultValue = "true", fallbackValue = "true")
    boolean run;

    @Option(names={"-v", "--verbose"}, description = "Show verbose logs")
    boolean verbose;

    // prevents saving the output in consecutive executions
    private final ChangeableOnce<Boolean> saveOutput = new ChangeableOnce<>(false);

    public static void main(String[] args) {
        int exitCode = new CommandLine(new Compiler()).execute(args);
        System.exit(exitCode);
    }

    @Override
    public String call() throws Exception {
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

        if (output == null) {
            saveOutput.set(false);
            output = DEFAULT_OUTPUT;
        } else
            saveOutput.set(true);
        if (!output.toPath().endsWith(".json"))
            output = new File(output.getPath().concat(".json"));
        Files.write(output.toPath(), result.getBytes());

        String processOutput = "";
        try {
            if (run) {
                processOutput = getProcessOutput();
                System.out.println(processOutput);
            }
        } catch (Exception e) {
            processOutput = e.getMessage();
        } finally {
            if (!saveOutput.get()) {
                Files.deleteIfExists(output.toPath());
                Files.deleteIfExists(Paths.get(output.toString().replace(".json", ".spril")));
            }
        }

        return processOutput;
    }

    public String getCmdString(boolean save) {
        if (save) {
            return String.format("%s %s %s",
                    backend.toString(),
                    output.toString(),
                    output.toString().replace(".json", ".spril"));
        }
        return String.format("%s %s",
                backend.toString(),
                output.toString());
    }

    private String getProcessOutput() throws IOException, InterruptedException {
        ProcessBuilder pb = new ProcessBuilder("bash", "-c", getCmdString(saveOutput.get()));

        pb.redirectErrorStream(true);
        Process child = pb.start();

        InputStream in = child.getInputStream();
        String output = new String(in.readAllBytes()).strip();

        if (child.waitFor() != 0)
            throw new RuntimeException("Script failed: "+output);
        return output;
    }
}
