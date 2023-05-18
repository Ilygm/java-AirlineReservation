package MVC.C;

import MVC.V.MainMenu;

import java.util.Objects;

public class MainController {
    public void mainMenu(int idx){
        switch (Objects.requireNonNull(MainMenu.valueOf(idx))){
            case SIGN_IN -> {/**/}
            case SIGN_UP -> {}
            case EXIT -> {/* */}
        }
    }
}
