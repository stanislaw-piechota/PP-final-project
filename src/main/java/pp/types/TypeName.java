package pp.types;


public enum TypeName {
    BOOL("bool", 2),
    INT("int", 2),
    FUNC("function", null),
    IO("io", null);

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