import java.util.ArrayList;

public class DataBase {
    public ArrayList<Flight> flights = new ArrayList<>();
    public ArrayList<User> users = new ArrayList<>();
    public ArrayList<User> admins = new ArrayList<>();

    public void signInPage(){
        App.clearScreen();
        System.out.print("""
                -------------------------
                |                       |
                |                       |
                |                       |
                -------------------------
                """);
    }
}
