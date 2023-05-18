package MVC.V;

public enum MainMenu {
    SIGN_IN("Sign in"),
    SIGN_UP("Sign up"),
    EXIT("Exit");

    private final String line;

    MainMenu(String line) {
        this.line = line;
    }

    public static MainMenu valueOf(int idx) {
        try {
            return MainMenu.values()[idx - 1];
        } catch (ArrayIndexOutOfBoundsException e) {
            return null;
        }
    }

    @Override
    public String toString() {
        return line;
    }

    public static void printMainMenu() {
        for (int i = 0; i < MainMenu.values().length; i++) {
            System.out.printf("%d - %s\n", i + 1, MainMenu.values()[i].line);
        }
        System.out.print(">> ");
    }
}
