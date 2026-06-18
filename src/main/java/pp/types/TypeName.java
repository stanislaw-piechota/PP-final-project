package pp.types;


public enum TypeName {
    BOOL("bool"),
    INT("int");

    private final String typeName;

    TypeName(String text) {
        this.typeName = text;
    }

    public String getTypeName() {
        return typeName;
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