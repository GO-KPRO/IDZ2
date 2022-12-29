package bar.hse.ru.folders.analyzers;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileScannerSystem {
    private final Path rootFolder;
    private final List<File> fileList = new ArrayList<>();

    /**
     * Конструктор проверяет существования папки, указанной в качестве корневой
     */
    public FileScannerSystem() {
        Scanner in = new Scanner(System.in);
        System.out.println("Enter the full path to the folder:");
        String curPath = in.nextLine();
        while (!(Files.exists(Paths.get(curPath)) && (Files.isDirectory(Paths.get(curPath))))) {
            System.out.println("Wrong path, try again:");
            curPath = in.nextLine();
        }
        rootFolder = Paths.get(curPath);
    }

    /**
     * Очищает fileList и вызывает searchFiles для корневой папки
     */
    public void updateFilesList() {
        fileList.clear();
        searchFiles(rootFolder.toFile());
    }
    public List<File> getFileList() {
        return fileList;
    }
    public File getRoot() {
        return rootFolder.toFile();
    }

    /**
     *Функция возвращает список относительных адресов из списка файлов fileList относительно rootFolder
     */
    public List<String> getRelativeAddressList() {
        List<String> out = new ArrayList<>();
        for (File file: fileList) {
            out.add(file.getAbsolutePath().substring(rootFolder.toFile().getAbsolutePath().length() + 1));
        }
        return out;
    }

    /**
     * Функция добавляет все файлы в текущей папке в fileList, а для всех папок рекуривно вызывается
     */
    private void searchFiles(File curFile) {
        if (curFile.isDirectory()) {
            File[] directoryFiles = curFile.listFiles();
            if (directoryFiles != null) {
                for(File file: directoryFiles) {
                    if (file.isDirectory()) {
                        searchFiles(file);
                    } else {
                        fileList.add(file);
                    }
                }
            }
        }
    }
}
