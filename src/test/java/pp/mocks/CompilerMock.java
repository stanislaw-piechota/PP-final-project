package pp.mocks;

import pp.Compiler;

public class CompilerMock extends Compiler {
    @Override
    public String getCmdString(boolean save) {
        String outputPath = "../"+super.output.toString();
        if (!save)
            return String.format("cd stack-my-lang && stack run -- %s",
                    outputPath);
        return String.format("cd stack-my-lang && stack run -- %s %s",
                outputPath, outputPath.replace(".json", ".spril"));
    }

    @Override
    public String call() throws Exception {
        saveOutput.set(true);
        return super.call();
    }
}
