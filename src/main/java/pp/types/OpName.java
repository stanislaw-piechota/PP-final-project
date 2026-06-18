package pp.types;

public enum OpName {
    ADD("add"),
    SUB("sub"),
    MUL("mul"),
    AND("and"),
    OR("or"),
    EQ("eq"),
    NEQ("neq"),
    LT("lt"),
    LE("le"),
    GT("gt"),
    GE("ge");

    private final String opName;

    OpName(String opName) {
        this.opName = opName;
    }

    public String getOpName() {
        return opName;
    }

    public static OpName getOpName(String opName) {
        for (OpName op : OpName.values()) {
            if (op.getOpName().equals(opName)) {
                return op;
            }
        }
        return null;
    }
}
