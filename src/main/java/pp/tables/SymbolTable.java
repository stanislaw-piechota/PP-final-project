package pp.tables;

import pp.types.Type;

import java.util.*;

public class SymbolTable {
    private Integer funcLevel = 0, funcOffset = 0;
    private final Deque<Integer> funcOffsets = new ArrayDeque<>();
    private Map<String, Coordinate> level = new HashMap<>();
    private final Deque<Map<String, Coordinate>> stack = new ArrayDeque<>();

    public SymbolTable() {
        stack.push(level);
    }

    public void addScope() {
        level = new HashMap<>();
        stack.push(level);
    }

    public void removeScope() {
        if (stack.size() > 1) {
            stack.pop();
            level = stack.peek();
        }
    }

    public void addLevel() {
        addScope();
        funcLevel++;
        funcOffsets.push(funcOffset);
        funcOffset = 0;
    }

    public void removeLevel() {
        removeScope();
        funcLevel--;
        funcOffset = funcOffsets.pop();
    }

    public Type getLatest(String name) {
        Coordinate coordinate = level.get(name);
        if (coordinate == null)
            return null;
        return coordinate.type();
    }

    public Coordinate get(String name) {
        for (Map<String, Coordinate> prevLevel : stack) {
            Coordinate value = prevLevel.get(name);
            if (value != null)
                return value;
        }

        return null;
    }

    public Coordinate put(String name, Type value) {
        if (level.containsKey(name))
            return level.get(name);

        Coordinate newCoordinate;
        if (value.typeName().getSize() != null) {
            level.put(name, newCoordinate = new Coordinate(funcLevel, funcOffset, value));
            funcOffset += value.typeName().getSize();
        } else
            level.put(name, newCoordinate = new Coordinate(null, null, value));
        return newCoordinate;
    }

    public Coordinate put(String name, Type value, boolean atTop) {
        if (atTop)
            return put(name, value);

        for (Map<String, Coordinate> prevLevel : stack) {
            Coordinate coordinate = prevLevel.get(name);
            if (coordinate != null)
                return prevLevel.put(name, coordinate);
        }
        return null;
    }
}
