package MVC.M;

public class User {
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

    public void changeBalance(int byAmount) {
        this.balance += byAmount;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof User user) return user.getUsername().equals(username);
        else return false;
    }
}
