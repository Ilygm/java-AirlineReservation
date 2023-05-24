package MVC.M;

public class Users extends MapHandler<String, User> {

    {
        addEntry("User", new User("User", "Pass"));
    }

    public boolean login(User user) {
        User tempUser = findEntry(user.getUsername());
        if (tempUser != null && tempUser.equals(user)) return tempUser.getPassword().equals(user.getPassword());
        return false;
    }

    public static User makeTempUser(String username, String password) {
        return new User(username, password);
    }

    public double getUserBalance(User user) {
        return findEntry(user.getUsername()).getBalance();
    }

    public void addUserBalance(User user, double amount) {
        findEntry(user.getUsername()).changeBalance(amount);
    }

    public String getUserInfo(User user) {
        User tempUser = findEntry(user.getUsername());
        if (tempUser != null && tempUser.getPassword().equals(user.getPassword())) return tempUser.toString();
        else return null;
    }

    public void changeUsersPass(User user, String newPass) {
        findEntry(user.getUsername()).setPassword(newPass);
    }

}