package MVC.M;

public class User {
    public static long USER_SIZE = 63;
    public static long USERNAME_SIZE = 15;
    private final String username;
    private String password;
    private double balance = 0;

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

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    public double getBalance() {
        return balance;
    }

    public User changeBalance(double byAmount) {
        this.balance += byAmount;
        return this;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof User user) return user.getUsername().equalsIgnoreCase(username);
        else return false;
    }

    @Override
    public String toString() {
        return String.format("%s,%f", username, balance);
    }

    public String userToDataLine() {
        return String.format("%15s|%25s|%20s\n", username, password, balance);
    }
}
