package bar.hse.ru.folders.utilities;

import bar.hse.ru.folders.wrappers.StringScore;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public final class Concatenator {
    /**
     * Функция создает файл из всех файлов с именем Final в корневой папке
     */
    public static void concatenate(List<StringScore> scores, String root)  {
        try {
        Path file = Files.createFile(Paths.get(root + "\\Final"));
        List<String> text = new ArrayList<>();
        for (StringScore curFile: scores) {
            text.addAll(Files.readAllLines(Paths.get(root + "\\" + curFile.getString())));
        }
        Files.write(file, text);
        System.out.println("Your file is saved in the root directory, named Final");
        } catch (IOException a) {
            System.out.println("Wrong file name, please delete Final file and try again");
        }
    }
}
