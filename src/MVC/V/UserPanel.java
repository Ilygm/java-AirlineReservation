package MVC.V;

enum UserPanelMenu {
    TICKET_LIST("Ticket searching and booking"),
    OWNED_TICKETS("Owned tickets"),
    ADD_CHARGE("Add charge"),
    CHANGE_PASS("Change password"),
    SIGN_OUT("Sign out");

    private final String line;

    UserPanelMenu(String line) {
        this.line = line;
    }

    static void printMenu(String username, double balance) {
        System.out.printf("""
                %56c-------------------------------------------------
                %56c|                                               |
                %56c|             Welcome : %-15s         |
                %56c|                                               |
                %56c|     Your balance is : %-15.2f         |
                %56c|                                               |
                %56c-------------------------------------------------
                """, ' ', ' ', ' ', username, ' ', ' ', balance, ' ', ' ');
        int i = 1;
        for (UserPanelMenu value : UserPanelMenu.values()) {
            System.out.printf("%65c%d - %s\n", ' ', i, value.line);
            i++;
        }
        System.out.printf("\n%65c>> ", ' ');
    }

    static UserPanelMenu valueOf(int idx) {
        try {
            return UserPanelMenu.values()[idx - 1];
        } catch (ArrayIndexOutOfBoundsException e) {
            return null;
        }
    }
}

public class UserPanel {
    private final String username;
    private double userBalance = 0.0;

    public UserPanel(String allInfo) {
        String[] infoList = allInfo.split(",");
        username = infoList[0];
        try {
            userBalance = Double.parseDouble(infoList[1]);
        } catch (NumberFormatException e) {
            return;
        }
        showMenu();
    }

    public void showMenu() {
        while (true) {
            Utils.clearScreen();
            UserPanelMenu.printMenu(username, userBalance);
            switch (UserPanelMenu.valueOf(Utils.getInt())){
                case ADD_CHARGE -> {} // TODO: 5/23/23
                case OWNED_TICKETS -> {   } // TODO: 5/23/23
                case TICKET_LIST -> {   } // TODO: 5/23/23
                case CHANGE_PASS -> {  } // TODO: 5/23/23
                case SIGN_OUT -> {return;}
                case null, default -> Utils.printMissInputWarning();
            }
        }
    }
}