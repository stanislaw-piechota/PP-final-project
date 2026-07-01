package pp;

import pp.errors.ParseException;

import java.io.IOException;
import java.util.concurrent.Callable;

public interface Compiler extends Callable<String> {
    String runFrontend() throws IOException, ParseException;
    String runBackend() throws IOException, InterruptedException;
    void setSource(String fileName);
    void setOutput(String fileName);
}
