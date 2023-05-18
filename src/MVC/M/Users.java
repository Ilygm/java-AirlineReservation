package MVC.M;

public class Users extends MapHandler<String, User> {
    public boolean login(String username, String password) {
        try {
        return entryExists(username) && findEntry(username).getPassword().equals(password);
        } catch (NullPointerException e){
            return false;
        }
    }
}
