package client.utility;


import common.utility.Input;

import java.io.*;
import java.util.ArrayDeque;
import java.util.ArrayList;

/**
 * Класс работы с файловым вводом
 */

public class FileInputManager implements Input {
    private static final ArrayDeque<String> paths = new ArrayDeque<>();
    private static final ArrayDeque<BufferedReader> commands = new ArrayDeque<>();
    private static final ArrayList<String> filesHistory = new ArrayList<>();

    public static void addNewFile(String filepath) throws FileNotFoundException {
        filepath = filepath.trim();
        filesHistory.add(filepath);
        paths.push(filepath);
        commands.push(new BufferedReader(new InputStreamReader(new FileInputStream(filepath))));
    }

    public static void clearHistory() {
        filesHistory.clear();
    }

    public static String readLine() throws IOException {
        return commands.getFirst().readLine();
    }

    public static void popFile() throws IOException {
        commands.getFirst().close();
        commands.pop();
        paths.pop();
    }

    public static boolean recursionCheck(String path) {
        return filesHistory.contains(path.trim());
    }

    @Override
    public String getInputString() {
        try {
            return readLine();
        } catch (IOException e) {
            return "";
        }
    }

}
