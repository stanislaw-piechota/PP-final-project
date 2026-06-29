package pp.helpers;

public class ChangeableOnce<T> {
    private T value;
    private boolean changed = false;

    public  ChangeableOnce(T value) {
        this.value = value;
    }

    public T get() {
        return value;
    }

    public T set(T value) {
        if (changed)
            return this.value;
        changed = true;
        return (this.value = value);
    }
}
