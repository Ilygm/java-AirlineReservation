public class User {
    private final String username, password;
    private int balance = 1000;

    public User(String username, String password){
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void addBalance(int charged) {
        balance += charged;
    }

    public int getBalance() {
        return balance;
    }
}