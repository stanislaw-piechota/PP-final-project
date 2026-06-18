package pp.types;

public record OpType(TypeName leftOp, TypeName rightOp, TypeName result) {
    public static OpType empty() {
        return new OpType(null, null, null);
    }
}
