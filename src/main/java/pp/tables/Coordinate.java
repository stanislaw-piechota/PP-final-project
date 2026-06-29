package pp.tables;

import pp.types.Type;

public record Coordinate(Integer level, Integer offset, Type type) {
    public Coordinate withType(Type newType) {
        return new Coordinate(level, offset, newType);
    }
}
