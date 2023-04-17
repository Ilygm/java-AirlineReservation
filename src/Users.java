import java.util.HashMap;

public class Users {
    public HashMap<String, User> usersDB = new HashMap<>();

    public void addUser(String username, String password) {
        usersDB.put(username.toLowerCase(), new User(username, password));
    }

    public boolean isUserPassCorrect(String username, String password) {
        if (usersDB.containsKey(username)) {
            return usersDB.get(username).getPassword().equals(password);
        }
        return false;
    }
}
