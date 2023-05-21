package MVC.M;

public class Users extends MapHandler<String, User> {
    public boolean login(String username, String password) {
        try {
            return findEntry(username).getPassword().equals(password);
        } catch (NullPointerException e) {
            return false;
        }
    }
}
