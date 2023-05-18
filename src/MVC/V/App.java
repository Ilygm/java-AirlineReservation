package MVC.V;

import MVC.C.MainController;

public class App {
    public static void main(String[] args) {
        Main main = new Main();
        main.showMainMenu();
    }
}

class Main{

    private final Utils utils = new Utils();

    private final MainController mainController = new MainController();

    public void showMainMenu(){
        MainMenu.printMainMenu();

    }
}
