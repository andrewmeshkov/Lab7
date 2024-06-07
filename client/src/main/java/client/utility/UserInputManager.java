package client.utility;


import common.utility.Input;

import java.util.Scanner;

public class UserInputManager implements Input {

    private static Scanner in = new Scanner(System.in);
    private static boolean fileMode = false;

    public static void setUserScanner(Scanner in) {
        UserInputManager.in = in;
    }

    public static Scanner getUserScanner(){
      return UserInputManager.in;
    }

    public static String getString() {
        return in.nextLine();
    }

    public String getInputString() {
        return getString();
    }

    public static boolean fileMode() {
        return fileMode;
    }

    public static void setFileMode(Boolean fileMode) {
        UserInputManager.fileMode = fileMode;
    }
}
