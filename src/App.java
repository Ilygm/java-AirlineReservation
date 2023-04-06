import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class App {

    public static void main(String[] args) {
        DataBase db = new DataBase();
        // DEFAULT VALUES FOR DEBUGGING AND TESTING
        db.admins.add(new User("Admin", "Admin", Integer.MAX_VALUE));
        db.flights.add(new Flight("WX-22", "Yazd", "Tehran"));
        db.flights.add(new Flight("WX-25", "Tehran", "Yazd"));

        int option = 1;
        while (option != 0) {
            clearScreen();
            printMenu();
            option = testInput();
            switch (option) {
                case 1 -> {
                    clearScreen();
                    for (User user : db.users) {
                        System.out.printf("%-10s | %-10s | %-10s\n", user.getUsername(), user.getPassword(), user.getBalance());
                    }
                    for (int i = 0; i < 5; i++) rest();
                }
                case 2 -> db.users.add(new User(db.users));
                case 0 -> {}
                default -> {
                    System.out.print("\t\t\t\t\t\t     !! Invalid Input !!");
                    rest();
                    option = 1;
                }
            }
        }
    }

    /**
     * Main-menu's art
     */
    public static void printMenu() {
        System.out.print("""
                                        d8888 d8b         888 d8b                       8888888b.                                                       888    d8b                  \s
                                       d88888 Y8P         888 Y8P                       888   Y88b                                                      888    Y8P                  \s
                                      d88P888             888                           888    888                                                      888                         \s
                                     d88P 888 888 888d888 888 888 88888b.   .d88b.      888   d88P  .d88b.  .d8888b   .d88b.  888d888 888  888  8888b.  888888 888  .d88b.  88888b. \s
                                    d88P  888 888 888P"   888 888 888 "88b d8P  Y8b     8888888P"  d8P  Y8b 88K      d8P  Y8b 888P"   888  888     "88b 888    888 d88""88b 888 "88b\s
                                   d88P   888 888 888     888 888 888  888 88888888     888 T88b   88888888 "Y8888b. 88888888 888     Y88  88P .d888888 888    888 888  888 888  888\s
                                  d8888888888 888 888     888 888 888  888 Y8b.         888  T88b  Y8b.          X88 Y8b.     888      Y8bd8P  888  888 Y88b.  888 Y88..88P 888  888\s
                                 d88P     888 888 888     888 888 888  888  "Y8888      888   T88b  "Y8888   88888P'  "Y8888  888       Y88P   "Y888888  "Y888 888  "Y88P"  888  888\s

                                                                                    | _______  ______ __   __ _     _ |
                                                                                    | |  |  | |______ | \\\\  | |     | |
                                                                                    | |  |  | |______ |  \\\\_| |_____| |
                                                                                    |                                 |
                                                                                               1 - Sign in

                                                                                               2 - Sign up

                                                                                               0 - Exit System

                                                                                             >>""" + ' ');
    }

    /**
     * Clears Screen ノ( º _ ºノ)
     */
    public static void clearScreen() {
        // new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    /**
     * Prevents wrongful Integer Inputs
     */
    public static int testInput() {
        Scanner scan = new Scanner(System.in);
        try {
            return Integer.parseInt(scan.next());
        } catch (NumberFormatException e) {
            return -25;
        }
    }

    public static void rest() {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            System.err.println("!! Program was interrupted !!");
        }
    }
}
