package pp.errors;

import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.Token;

import java.util.ArrayList;
import java.util.List;

public class ErrorListener extends BaseErrorListener {
    private final List<String> errors = new ArrayList<>();

    @Override
    public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line,
                            int charPositionInLine, String msg, RecognitionException e) {
        this.errors.add(String.format("line %d:%d - %s", line, charPositionInLine, msg));
    }

    public void syntaxError(int line, int charPositionInLine, String msg) {
        this.errors.add(String.format("line %d:%d - %s", line, charPositionInLine, msg));
    }

    public void syntaxError(Token token, String msg) {
        syntaxError(token.getLine(), token.getCharPositionInLine(), msg);
    }

    public boolean hasErrors() {
        return !this.errors.isEmpty();
    }

    public List<String> getErrors() {
        return this.errors;
    }

    public void throwException() throws ParseException {
        if (hasErrors()) {
            throw new ParseException(getErrors());
        }
    }
}
