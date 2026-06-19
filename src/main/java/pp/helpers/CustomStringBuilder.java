package pp.helpers;

public class CustomStringBuilder {
    private final StringBuilder sb = new StringBuilder();

    public void append(char c) {
        sb.append(c);
    }

    public void append(String str, boolean removeComma) {
        if (removeComma && !sb.isEmpty() && sb.charAt(sb.length() - 1) == ',')
            sb.deleteCharAt(sb.length() - 1);
        sb.append(str);
    }

    public void append(String str) {
        append(str, true);
    }

    @Override
    public String toString() {
        return sb.toString();
    }
}