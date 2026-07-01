package pp.types;


public enum TypeName {
    BOOL("bool", 1),
    INT("int", 2),
    VOID("void", null),
    FUNC("function", 4);

    private final String typeName;
    private final Integer size;

    TypeName(String text, Integer size) {
        this.typeName = text;
        this.size = size;
    }

    public String toString() {
        return typeName;
    }

    public Integer getSize() {
        return size;
    }

    public static TypeName fromTypeName(String typeName) {
        for (TypeName type : TypeName.values()) {
            if (type.typeName.equals(typeName)) {
                return type;
            }
        }
        return null;
    }
}