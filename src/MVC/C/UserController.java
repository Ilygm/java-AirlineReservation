package MVC.C;

import MVC.M.Users;

public class UserController {

    private final Users users = new Users();

    public boolean checkUserCredentials(String username, String password) {
        return users.login(Users.makeTempUser(username, password));
    }

    public String getUserInfo(String username, String password) {
        return users.getUserInfo(Users.makeTempUser(username, password));
    }

    public void addBalanceToUser(String username, double amount) {
        users.addUserBalance(Users.makeTempUser(username, null), amount);
    }

    public void setNewPass(String username, String newPass) {
        users.changeUsersPass(Users.makeTempUser(username, null), newPass);
    }
}