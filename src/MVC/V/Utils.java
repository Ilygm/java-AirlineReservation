package MVC.V;

import java.util.Scanner;

public class Utils {
    public int getInt() {
        try {
            return Integer.parseInt(new Scanner(System.in).next());
        } catch (NumberFormatException e) {
            return -25;
        }
    }

    public void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
