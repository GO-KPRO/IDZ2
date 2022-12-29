package bar.hse.ru.folders.wrappers;

import java.util.List;

public class ScoreManager {
    private final List<StringScore> scores;
    private final List<StringPair> pairList;
    public ScoreManager(List<StringScore> scores, List<StringPair> fileList) {
        this.scores = scores;
        this.pairList = fileList;
    }
    public List<StringScore> getScoreList() {
        return scores;
    }

    /**
     * Вызавает функцию count для всех пар зависимости
     */
    public void countScore() {
        for (StringPair curPair: pairList) {
            count(curPair);
        }
    }
    public void scoreSort() {
        scores.sort((o1, o2) -> o2.getScore() - o1.getScore());
    }
    /**
     * Функция выводит сообщение о цикличности, если хотя бы один файл набрал больше n(n-1)/2 очков
     * Это потолок, так как функция подсчета очков фактически считает кол-во ребер в зависящих вершинах
     * на направленном графе, в котором вершины это файлы, а зависимости - ребра
     * А в полном графе может быть лиш n(n-1)/2 ребер
     * Если таких нет, то выводит названия файлов в порядке уменьшения очков
     */
    public boolean scoreOut() {
        scoreSort();
        boolean flag = true;
        for (StringScore score: scores) {
            if (score.getScore() > (scores.size() * (scores.size() - 1)) / 2) {
                System.out.printf("%s\n", score.getString());
                flag = false;
            }
        }
        if (!flag) {
            System.out.println("Sorry, there is a problem with scoring in these files, please check them for loops");
            return false;
        }
        for (StringScore score: scores) {
            System.out.printf("%s\n", score.getString());
        }
        return true;
    }

    /**
     * Рекурсивная функция подсчета очков для одной вершины и её родителя
     * пребавляет 1 очко родителю и рекурсивно вызывает для всех пар родитель - дед
     */
    private void count(StringPair pair) {
        boolean flag = false;
        for (StringScore score: scores) {
            if ((pair.getFirstString().equals(score.getString())) && (score.getScore() <= (scores.size() * (scores.size() - 1)) / 2)) {
                score.incScore();
                flag = true;
            }
        }
        if (flag) {
            for (StringPair curPair : pairList) {
                if (curPair.getSecondString().equals(pair.getFirstString())) {
                    count(curPair);
                }
            }
        }
    }

}
