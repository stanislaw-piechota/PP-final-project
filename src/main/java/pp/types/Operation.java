package pp.types;

import java.util.Arrays;
import java.util.List;

import static pp.types.TypeName.*;

public enum Operation {
    ADD("add", List.of(new OpType(INT, INT, INT))),
    SUB("sub", List.of(new OpType(INT, INT, INT))),
    MUL("mul", List.of(new OpType(INT, INT, INT))),
    AND("and", List.of(new OpType(BOOL, BOOL, BOOL))),
    OR("or", List.of(new OpType(BOOL, BOOL, BOOL))),
    EQ("eq", List.of(
            new OpType(BOOL, BOOL, BOOL),
            new OpType(BOOL, INT, INT)
    )),
    NEQ("neq", List.of(
            new OpType(BOOL, BOOL, BOOL),
            new OpType(BOOL, INT, INT)
    )),
    LT("lt", List.of(new OpType(BOOL, INT, INT))),
    LE("le", List.of(new OpType(BOOL, INT, INT))),
    GT("gt", List.of(new OpType(BOOL, INT, INT))),
    GE("ge", List.of(new OpType(BOOL, INT, INT))),
    PRINT("print", List.of(
            new OpType(VOID, INT),
            new OpType(VOID, BOOL)
    )),
    FORK("fork", List.of(new OpType(INT, FUNC))),
    JOIN("join", List.of(new OpType(VOID, INT)));

    private final String opName;
    private final List<OpType> ops;

    Operation(String opName, List<OpType> ops) {
        this.opName = opName;
        this.ops = ops;
    }

    public String getOpName() {
        return opName;
    }

    public TypeName getResultType(TypeName ...args) {
        List<TypeName> argsList = Arrays.asList(args);
        return ops
                .stream()
                .filter(op -> {
                    List<TypeName> opArgs = Arrays.asList(op.args());
                    if (opArgs.size() != argsList.size())
                        return false;

                    for (int i = 0; i < opArgs.size(); i++) {
                        if (!opArgs.get(i).equals(argsList.get(i)))
                            return false;
                    }
                    return true;
                })
                .findFirst()
                .orElse(OpType.empty())
                .result();
    }
}
