import java.util.ArrayList;

public class User {
    public final ArrayList<Flight> boughtFlights = new ArrayList<>();
    private final String username;
    private String password;
    private int balance = 0;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getBalance() {
        return balance;
    }

    public void changeBalance(int balance) {
        this.balance += balance;
    }
}
