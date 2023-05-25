package MVC.V;

import java.util.Scanner;

public class Utils {
    private static final Scanner cinScanner = new Scanner(System.in);

    public static int getInt() {
        try {
            return Integer.parseInt(cinScanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
    public static double getDouble() {
        try {
            return Double.parseDouble(cinScanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static void printMissInputWarning() {
        printMessage(60, "!! Miss Input Detected !!", AnsiColor.FOREGROUND_BRIGHT_RED, 1500);
    }

    public static void printMessage(int leftOffset, String line, String color, int timerInMilli) {
        System.out.printf("%s%s%s%s", " ".repeat(leftOffset), color, line, AnsiColor.RESET_COLOR);
        waitFor(timerInMilli);
    }

    public static String getString(int leftOffset, String line, String mainColor, String inputColor) {
        System.out.printf("%s%s%s%s", " ".repeat(leftOffset), mainColor, line, inputColor);
        String output = cinScanner.nextLine();
        System.out.print(AnsiColor.RESET_COLOR);
        return output;
    }

    public static void waitFor(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.exit(85);
        }
    }
}
