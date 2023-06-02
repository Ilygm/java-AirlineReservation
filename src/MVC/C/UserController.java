package MVC.C;

import MVC.M.Users;

import java.io.IOException;

public class UserController{

    protected final Users users = new Users();

    public boolean checkUserCredentials(String username, String password) {
        try {
            return users.login(Users.makeTempUser(username, password));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getUserInfo(String username, String password) {
        try {
            return users.getUserInfo(Users.makeTempUser(username, password));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void addBalanceToUser(String username, double amount) {
        users.addUserBalance(username, amount);
    }

    public void setNewPass(String username, String newPass) {
        try {
            users.changeUsersPass(Users.makeTempUser(username, null), newPass);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean reduceUserBalance(String username, int price) {
        try {
            return users.reduceUserBalance(username, price);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void addUser(String username, String password) {
        users.addEntry(Users.makeTempUser(username, password));
    }

    public boolean doesUserExist(String username) {
        try {
            return users.entryExists(username);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
