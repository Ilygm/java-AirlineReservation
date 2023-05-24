package MVC.C;

public class AdminController {
    public boolean checkAdminPassword (String username, String password) {
        return (username.equalsIgnoreCase("admin") && password.equals("Admin"));
    }
}
