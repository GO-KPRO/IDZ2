package bar.hse.ru.folders.runners;

import bar.hse.ru.folders.analyzers.FileAnalyzer;
import bar.hse.ru.folders.analyzers.FileScannerSystem;
import bar.hse.ru.folders.utilities.Concatenator;
import bar.hse.ru.folders.utilities.StringEdit;
import bar.hse.ru.folders.wrappers.ScoreManager;
import bar.hse.ru.folders.wrappers.StringPair;
import bar.hse.ru.folders.wrappers.StringScore;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Manager implements Runnable{
    /**
     * Последовательно выполняет действия:
     * Считать корневую папку
     * Создать список из всех файлов в корневой папке и подпапках
     * Создать список пар строк всех вхождений require '...' и абсолютных адресов файлов в которых их нашли
     * Создать список пар строк возможных адресов из require и абсолютных адресов файлов-источников
     * Создать список пар строк возможных адресов из require и относительных адресов файлов-источников
     * Создать список пар строк проверенных адресов из require и относительных адресов файлов-источников
     * Создать список строк и очков и заполнить его относительными адресами и 0-ми
     * Подсчитать очки для каждой строки
     * Вывести относительные пути в соответствии со списком очков
     */
    public void run() {
        FileScannerSystem scan = new FileScannerSystem();
        scan.updateFilesList();
        FileAnalyzer analyzer = new FileAnalyzer();
        List<StringPair> requireList = new ArrayList<>();
        for (File file: scan.getFileList()) {
            analyzer.setFile(file);
            analyzer.addToRequireList(requireList);
        }
        List<StringPair> addresses = StringEdit.convertRequireToAddress(requireList);
        List<StringPair> relativeAddresses = StringEdit.convertPairAbsoluteAddressToRelative(addresses, scan.getRoot().getAbsolutePath());
        List<StringPair> validated = StringEdit.validate(relativeAddresses, scan.getRelativeAddressList());
        List<StringScore> scores = new ArrayList<>();
        for (String str: scan.getRelativeAddressList()) {
            scores.add(new StringScore(str, 0));
        }
        ScoreManager scoreManager = new ScoreManager(scores, validated);
        scoreManager.countScore();
        if (scoreManager.scoreOut()) {
            Concatenator.concatenate(scoreManager.getScoreList(), scan.getRoot().getAbsolutePath());
        }
    }
}
