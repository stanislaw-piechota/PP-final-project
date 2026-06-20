package pp;

import pp.types.Type;

import java.util.*;

public class SymbolTable {
    private Map<String, Type> level = new HashMap<>();
    private final Deque<Map<String, Type>> stack = new ArrayDeque<>();

    public SymbolTable() {
        stack.push(level);
    }

    public void addLevel() {
        level = new HashMap<>();
        stack.push(level);
    }

    public void removeLevel() {
        if (level.size() > 1) {
            stack.pop();
            level = stack.peek();
        }
    }

    public Type getLatest(String name) {
        return level.get(name);
    }

    public Type get(String name) {
        Type value = null;
        for (Map<String, Type> prevLevel : stack.reversed()) {
            value = prevLevel.get(name);
            if (value != null)
                break;
        }

        return value;
    }

    public void put(String name, Type value) {
        if (level.containsKey(name))
            return;

        level.put(name, value);
    }

    public void put(String name, Type value, boolean atTop) {
        if (atTop)
            put(name, value);

        Iterator<Map<String, Type>> iter = stack.reversed().iterator();
        iter.next();
        while (iter.hasNext()) {
            Map<String, Type> prevLevel = iter.next();
            value = prevLevel.get(name);
            if (value != null)
                break;
        }
    }
}
