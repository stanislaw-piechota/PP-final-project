package pp;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pp.errors.ParseException;
import pp.mocks.SprilCompilerMock;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class TestGrammar {
    public final static Compiler compiler = new SprilCompilerMock();

    @Test
    public void testEmpty() {
        fail("empty_program");
        fail("only_comments");
    }

    @Test
    public void testDeclarations() {
        success("var_explicit_decl");
        fail("invalid_decl_id", 2);
        fail("invalid_decl_type", 2);
        fail("invalid_decl_val", 2);
        success("var_null_decl");
        success("var_implicit_decl");
        fail("invalid_redeclaration");
    }

    @Test
    public void testAssignments() {
        success("basic_assignment");
        success("basic_assignment_with_decl");
        fail("invalid_type_assignment");
    }

    @Test
    public void testExpressions() {
        success("basic_expr");
        fail("expr_invalid_type", 3);
    }

    @Test
    public void testReference() {
        success("basic_ref");
        fail("unknown_ref", 2);
    }

    @Test
    public void testPrint() {
        success("basic_print");
        fail("empty_print");
        fail("print_stmnt");
    }

    @Test
    public void testConditional() {
        success("single_if");
        success("block_if");
        success("one_elif");
        success("multiple_elifs");
        success("if_with_else");
        fail("else_no_if");
        success("empty_block_if");
        fail("empty_single_if");
        fail("stmnt_in_cond");
        success("nested_if");
    }

    @Test
    public void testWhile() {
        success("basic_while");
        success("empty_block_while");
        success("empty_while_semicolon");
        fail("empty_while");
        success("block_while");
        fail("stmnt_in_while");
        success("nested_while");
    }

    @Test
    public void testFunctions() {
        success("basic_func");
        success("func_with_return");
        success("func_with_arg");
        fail("empty_func_def");
        fail("lack_args_func");
        fail("extra_args_func");
        fail("func_arg_mismatch");
        fail("func_redef", 2);
        success("nested_func");
        fail("func_out_of_scope", 2);
        fail("return_out_of_func");
        fail("return_mismatch");
        fail("no_return");
        fail("one_return_mismatch");
    }

    @Test
    public void testThreads() {
        success("basic_thread");
        fail("invalid_func_thread", 6);
        fail("invalid_type_join");
    }

    @Test
    public void testLocks() {
        success("basic_lock");
        fail("invalid_lock", 4);
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

    private void fail(String fileName, int errorCount) {
        try {
            runTest(fileName);
            Assertions.fail();
        } catch (ParseException e) {
            System.err.println(e.getMessages());
            assertEquals(errorCount, e.getMessages().size());
        } catch (Exception e) {
            System.err.println(e.getMessage());
            Assertions.fail();
        }
    }

    private void fail(String fileName) {
        fail(fileName, 1);
    }

    private void runTest(String fileName) throws IOException, ParseException {
        compiler.setSource(SRC_PATH + fileName + SRC_EXT);
        String result = compiler.runFrontend();

        assertTrue(jsonEqual(readFile(
                RES_PATH+fileName+RES_EXT), result));
    }

    private String readFile(String fileName) {
        File file = new File(fileName);
        try (Scanner fileReader = new Scanner(file)) {
           StringBuilder stringBuilder = new StringBuilder();
           while (fileReader.hasNextLine()) {
               stringBuilder.append(fileReader.nextLine());
           }
           return stringBuilder.toString();
        } catch (IOException e) {
            System.err.println("Error reading file " + fileName);
            Assertions.fail();
        }

        return "";
    }

    private boolean jsonEqual(String j1, String j2) {
        j1 = j1.replaceAll("[ \t\n\r]", "");
        j2 = j2.replaceAll("[ \t\n\r]", "");
        return j1.equals(j2);
    }

    public static final boolean SHOW = true;
    public static final String SRC_PATH = "src/test/resources/unit/samples/";
    public static final String RES_PATH = "src/test/resources/unit/results/";
    public static final String SRC_EXT = ".lang", RES_EXT = ".json";
}
