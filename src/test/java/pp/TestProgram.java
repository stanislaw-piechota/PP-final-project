package pp;

import org.junit.jupiter.api.Test;
import picocli.CommandLine;
import pp.mocks.SprilCompilerMock;

import static org.junit.jupiter.api.Assertions.*;

public class TestProgram {
    public final static SprilCompiler compiler = new SprilCompilerMock();

    @Test
    public void testPrograms() {
        success("linear_function", new String[] {"-5", "-3", "-1", "1"});
        success("fib_iter", new String[] {"3", "34"});
        success("maturity", new String[] {"1", "0", "1"});
        success("offsets", new String[] {"1", "2", "3"});
    }

    private void success(String fileName, String[] expectedLines) {
        for (int i=0; i<expectedLines.length; i++) {
            expectedLines[i] = "Sprockell 0 says " + expectedLines[i]; // TODO: add support for multithreading
        }

        try {
            String output = runTest(fileName);
            String[] actualLines = output.split("\n");

            if (expectedLines.length != actualLines.length) {
                System.err.println(output);
                fail(String.format("expected %s lines but got %s", expectedLines.length, actualLines.length));
            }

            for (int i=0; i<expectedLines.length; i++)
                assertEquals(expectedLines[i], actualLines[i],
                        String.format("expected value %s but got %s", expectedLines[i], actualLines[i]));
        } catch (Exception e) {
            System.err.println(e.getMessage());
            fail(e.getMessage());
        }
    }

    private void _fail(String fileName) {
        try {
            runTest(fileName);
            fail("expected to fail, but didn't");
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    private String runTest(String fileName) throws Exception {
        String[] args = new String[] {SOURCE_DIR + fileName + SOURCE_EXT};
        CommandLine cmd = new CommandLine(compiler);
        int exitCode = cmd.execute(args);
        String output = cmd.getExecutionResult();

        if (exitCode != 0) {
            throw new Exception(String.format("Code %s: %s", exitCode, output));
        }

        if (output.contains("error")) {
            throw new Exception(output);
        }

        return output;
    }

    public final static String SOURCE_DIR = "src/test/resources/e2e/";
    public final static String SOURCE_EXT = ".lang";
}
