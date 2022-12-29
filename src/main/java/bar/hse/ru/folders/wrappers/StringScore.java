package bar.hse.ru.folders.wrappers;

public class StringScore {
    private final String string;
    private Integer score;
    public StringScore(String str1, int score) {
        this.string = str1;
        this.score = score;
    }
    public void incScore() {
        ++score;
    }
    public String getString() {
        return string;
    }
    public int getScore() {
        return score;
    }
}
