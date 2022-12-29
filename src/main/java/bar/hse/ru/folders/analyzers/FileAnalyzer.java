package bar.hse.ru.folders.analyzers;

import bar.hse.ru.folders.wrappers.StringPair;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.nio.charset.StandardCharsets.UTF_8;

public class FileAnalyzer {

    private File fileToAnalyze;

    public void setFile(File file) {
        fileToAnalyze = file;
    }

    /**
     * Добавляет в переданный requireList все вхождения строк типа
     * require '...'
     * в текущем файле
     */
    public void addToRequireList(List<StringPair> requireList) {
        try {
            List<String> text = Files.readAllLines(fileToAnalyze.toPath(), UTF_8);
            Pattern pattern = Pattern.compile("require '.*?'");
            for (String line: text) {
                Matcher matcher = pattern.matcher(line);
                while (matcher.find()) {
                    requireList.add(new StringPair(line.substring(matcher.start(), matcher.end()), fileToAnalyze.getAbsolutePath()));
                }
            }
        } catch (IOException a) {
            System.out.println("Wrong input, change it and try again");
        }
    }
}
