package bar.hse.ru.folders.utilities;

import bar.hse.ru.folders.wrappers.StringPair;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class StringEdit {
    /**
     * Из списка пар строк с первой строкой формата
     * require '...'
     * создает новый список пар строк с первой строкой формата
     * ...
     * Вторая строка остаётся неизменной
     */
    public static List<StringPair> convertRequireToAddress(List<StringPair> requireList) {
        List<StringPair> addresses = new ArrayList<>();
        Pattern pattern = Pattern.compile("'.*?'");
        for (StringPair pair: requireList) {
            Matcher matcher = pattern.matcher(pair.getFirstString());
            if (matcher.find()) {
                addresses.add(new StringPair(pair.getFirstString().substring(matcher.start() + 1, matcher.end() - 1), pair.getSecondString()));
            }
        }
        return addresses;
    }

    /**
     * Допустим, если @param rootAddress a/b, то:
     * Из списка пар строк со второй строкой формата
     * a/b/c/d
     * создает новый список пар строк со второй строкой формата
     * c/d
     * Первая строка остаётся неизменной
     * Функция нужна для сокращения абсолютных адресов до относительных
     */
    public static List<StringPair> convertPairAbsoluteAddressToRelative(List<StringPair> addressList, String rootAddress) {
        List<StringPair> newAddressList = new ArrayList<>();
        for (StringPair pair: addressList) {
            if (pair.getSecondString().contains(rootAddress)) {
                newAddressList.add(new StringPair(pair.getFirstString(), pair.getSecondString().substring(rootAddress.length() + 1)));
            }
        }
        return newAddressList;
    }

    /**
     * Функция возвращает новый список пар строк
     * В котором первые строки пар содержатьсяя в списке строк source
     * Функция для проверки существования файлов, на которые ссылается require
     */
    public static List<StringPair> validate(List<StringPair> toCheck, List<String> source) {
        List<StringPair> validated = new ArrayList<>();
        for (StringPair pair: toCheck) {
            if (source.contains(pair.getFirstString())) {
                validated.add(new StringPair(pair.getFirstString(), pair.getSecondString()));
            }
        }
        return validated;
    }
}
