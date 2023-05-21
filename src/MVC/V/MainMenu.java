package MVC.V;

public enum MainMenu {
    SIGN_IN("Sign in"), SIGN_UP("Sign up"), EXIT("Exit");

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

    public static void printMainMenu() {
        System.out.print("""
                                                 
                       d8888 d8b         888 d8b                       8888888b.                                                       888    d8b                  \s
                      d88888 Y8P         888 Y8P                       888   Y88b                                                      888    Y8P                  \s
                     d88P888             888                           888    888                                                      888                         \s
                    d88P 888 888 888d888 888 888 88888b.   .d88b.      888   d88P  .d88b.  .d8888b   .d88b.  888d888 888  888  8888b.  888888 888  .d88b.  88888b. \s
                   d88P  888 888 888P"   888 888 888 "88b d8P  Y8b     8888888P"  d8P  Y8b 88K      d8P  Y8b 888P"   888  888     "88b 888    888 d88""88b 888 "88b\s
                  d88P   888 888 888     888 888 888  888 88888888     888 T88b   88888888 "Y8888b. 88888888 888     Y88  88P .d888888 888    888 888  888 888  888\s
                 d8888888888 888 888     888 888 888  888 Y8b.         888  T88b  Y8b.          X88 Y8b.     888      Y8bd8P  888  888 Y88b.  888 Y88..88P 888  888\s
                d88P     888 888 888     888 888 888  888  "Y8888      888   T88b  "Y8888   88888P'  "Y8888  888       Y88P   "Y888888  "Y888 888  "Y88P"  888  888\s
                                                                        
                                                                      ---------------------------------------
                                                                      |   _______  ______ __   __ _     _   |
                                                                      |   |  |  | |______ | \\\\  | |     |   |
                                                                      |   |  |  | |______ |  \\\\_| |_____|   |
                                                                      |                                     |
                                                                      ---------------------------------------
                
                """);

        for (int i = 0; i < MainMenu.values().length; i++)
            System.out.printf("%68c%d - %s\n\n", ' ', i + 1, MainMenu.values()[i].line);
    }

    @Override
    public String toString() {
        return line;
    }
}
