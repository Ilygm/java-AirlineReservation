import java.util.HashMap;

public class Users {
    public HashMap<String, User> usersDB = new HashMap<>();

    /**
     * Adds user to the database
     * @param username Chosen username
     * @param password Chosen password
     */
    public void addUser(String username, String password) {
        usersDB.put(username.toLowerCase(), new User(username, password));
    }

    /**
     * Checks if a user exists with the given inputs
     * @param username Given username
     * @param password Given password
     * @return true if the username nad password matched
     */
    public boolean isUserPassCorrect(String username, String password) {
        if (usersDB.containsKey(username)) {
            return usersDB.get(username).getPassword().equals(password);
        }
        return false;
    }
}
