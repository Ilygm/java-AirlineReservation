package MVC.V;

import java.util.Scanner;

public class Utils {

    private final Scanner cinScanner = new Scanner(System.in);

    public int getInt() {
        try {
            return Integer.parseInt(cinScanner.nextLine());
        } catch (NumberFormatException e) {
            return -25;
        }
    }

    public void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public void printMissInputWarning() {
        printMessage(60, "!! Miss Input Detected !!", AnsiColor.FOREGROUND_BRIGHT_RED);
    }

    public void printMessage(int leftOffset, String line, String color) {
        System.out.printf("%s%s%s%s", " ".repeat(leftOffset), color, line, AnsiColor.RESET_COLOR);
    }

    public String getString(int leftOffset, String line, String mainColor, String inputColor) {
        System.out.printf("%s%s%s%s", " ".repeat(leftOffset), mainColor, line, inputColor);
        String output = cinScanner.nextLine();
        System.out.print(AnsiColor.RESET_COLOR);
        return output;
    }
}
