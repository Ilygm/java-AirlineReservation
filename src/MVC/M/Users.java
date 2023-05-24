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

    public User makeTempUser(String username, String password) {
        return new User(username, password);
    }

    public double getUserBalance(String username) {
        return findEntry(username).getBalance();
    }

    public String getUserInfo(User user) {
        User tempUser = findEntry(user.getUsername());
        if (tempUser != null && tempUser.getPassword().equals(user.getPassword())) return tempUser.toString();
        else return null;
    }
}
