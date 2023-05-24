package MVC.C;

import MVC.M.Users;

public class UserController {

    private final Users users = new Users();

    public boolean checkUserCredentials(String username, String password) {
        return users.login(users.makeTempUser(username, password));
    }

    public String getUserInfo(String username, String password) {
        return users.getUserInfo(users.makeTempUser(username, password));
    }
}
