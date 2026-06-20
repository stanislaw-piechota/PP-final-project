package pp.types;

import java.util.List;

public class Type {
    private final TypeName typeName;
    private final TypeName returnType;
    private final List<TypeName> args;
    private final boolean valuePresent;

    public Type(TypeName typeName, TypeName returnType, List<TypeName> args, boolean valuePresent) {
        this.typeName = typeName;
        this.returnType = returnType;
        this.args = args;
        this.valuePresent = valuePresent;
    }

    public Type(TypeName typeName, boolean valuePresent) {
        this.typeName = typeName;
        this.valuePresent = valuePresent;
        this.args = null;
        this.returnType = typeName;
    }

    public List<TypeName> getArgs() {
        return args;
    }

    public TypeName typeName() {
        return typeName;
    }

    public TypeName returnType() {
        return returnType;
    }

    public boolean empty() {
        return !valuePresent;
    }
}
