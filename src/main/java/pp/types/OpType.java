package pp.types;

public record OpType(TypeName result, TypeName ...args) {
    public static OpType empty() {
        return new OpType(null);
    }

    public TypeName getArg(int i) {
        return args[i];
    }
}
