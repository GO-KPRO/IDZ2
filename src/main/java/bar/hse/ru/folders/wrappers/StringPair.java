package bar.hse.ru.folders.wrappers;

public class StringPair {
    private final String firstString;
    private final String secondString;
    public StringPair(String firstString, String secondString) {
        this.firstString = firstString;
        this.secondString = secondString;
    }
    public String getFirstString() {
        return firstString;
    }
    public String getSecondString() {
        return secondString;
    }
}
