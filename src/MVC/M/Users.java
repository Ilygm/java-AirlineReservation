package MVC.M;

import java.io.IOException;

public class Users extends FileWriter {

    {
        appendRecord(new User("User", "Pass").userToDataLine());
        try {
            overrideUser(findUser("User").changeBalance(200000));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Users() {
        super(User.USER_SIZE, "Users.dat");
    }

    public static User makeTempUser(String username, String password) {
        return new User(username, password);
    }

    public boolean login(User user) throws IOException {
        User tempUser = findUser(user.getUsername());
        if (tempUser != null && tempUser.equals(user)) return tempUser.getPassword().equals(user.getPassword());
        return false;
    }

    @SuppressWarnings("unused")
    public double getUserBalance(User user) throws IOException {
        return findUser(user.getUsername()).getBalance();
    }

    public void addUserBalance(String username, double amount) {
        try {
            overrideUser(findUser(username).changeBalance(amount));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getUserInfo(User user) throws IOException {
        User tempUser = findUser(user.getUsername());
        if (tempUser != null && tempUser.getPassword().equals(user.getPassword())) return tempUser.toString();
        else return null;
    }

    public void changeUsersPass(User user, String newPass) throws IOException {
        overrideUser(findUser(user.getUsername()).setPassword(newPass));
    }

    public boolean reduceUserBalance(String username, double price) throws IOException {
        User temp = findUser(username);
        if (temp.getBalance() >= price) {
            temp.changeBalance(-price);
            return true;
        } else return false;
    }

    public User findUser(String username) throws IOException {
        int status = findFieldSpecific(0, 15, username);
        if (status != -1) return dataLineToUser(readRecord(status));
        else return null;
    }

    public User dataLineToUser(String dataLine) {
        String[] userData = dataLine.split("\\|");
        User tempUser = new User(userData[0].strip(), userData[1].strip());
        tempUser.changeBalance(Double.parseDouble(userData[2].strip()));
        return tempUser;
    }

    public void overrideUser(User modifiedUser) throws IOException {
        overrideRecord(modifiedUser.userToDataLine(), findFieldSpecific(0, 15, modifiedUser.getUsername()));
    }

    public void addEntry(User user) {
        appendRecord(user.userToDataLine());
    }

    public boolean entryExists(String username) throws IOException {
        return findFieldSpecific(0, 15, username) != -1;
    }
}
