package pp.types;

import java.util.HashMap;
import java.util.List;

import static pp.types.OpName.*;
import static pp.types.TypeName.*;

public class Operations extends HashMap<OpName, List<OpType>>{
    public static final Operations OPERATIONS = new Operations();

    private Operations() {
        put(ADD, List.of(new OpType(INT, INT, INT)));
        put(SUB, List.of(new OpType(INT, INT, INT)));
        put(MUL, List.of(new OpType(INT, INT, INT)));
        put(AND, List.of(new OpType(BOOL, BOOL, BOOL)));
        put(OR, List.of(new OpType(BOOL, BOOL, BOOL)));
        put(EQ, List.of(
                new OpType(BOOL, BOOL, BOOL),
                new OpType(INT, INT, BOOL)
        ));
        put(NEQ, List.of(
                new OpType(BOOL, BOOL, BOOL),
                new OpType(INT, INT, BOOL)
        ));
        put(LT, List.of(new OpType(INT, INT, BOOL)));
        put(GT, List.of(new OpType(INT, INT, BOOL)));
        put(LE, List.of(new OpType(INT, INT, BOOL)));
        put(GE, List.of(new OpType(INT, INT, BOOL)));
    }

    public static TypeName getResultType(OpName opName, TypeName leftOp, TypeName rightOp) {
        if (!OPERATIONS.containsKey(opName))
            return null;

        return OPERATIONS
                .get(opName)
                .stream()
                .filter(op -> op.leftOp().equals(leftOp) && op.rightOp().equals(rightOp))
                .findFirst()
                .orElse(OpType.empty())
                .result();
    }

    public static TypeName getResultType(String opName, TypeName leftOp, TypeName rightOp) {
        OpName name = OpName.getOpName(opName);
        if (name == null)
            return null;

        return getResultType(name, leftOp, rightOp);
    }
}
