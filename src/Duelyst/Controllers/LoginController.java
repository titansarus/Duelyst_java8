package Duelyst.Controllers;

import Duelyst.Exceptions.MyException;
import Duelyst.Exceptions.UserExistException;
import Duelyst.Model.Account;
import Duelyst.View.Constants;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;

import static Duelyst.View.Constants.*;

public class LoginController {

    @FXML
    JFXTextField username_tf;

    @FXML
    JFXPasswordField password_tf;


    public void handleLoginBtn() {


    }

    public void handleSignUpBtn() {
        String username = getUsername_tf().getText();
        String password = getPassword_tf().getText();

        if (Account.accountExistInArrayList(username, Account.getAccounts())) {
            Container.exceptionGenerator(new UserExistException());
            return;
        }
        new Account(username, password);
        Container.notificationShower(USER_CREATED_CONTENT, USER_CREATED_TITLE);

    }


    public JFXTextField getUsername_tf() {
        return username_tf;
    }

    public JFXPasswordField getPassword_tf() {
        return password_tf;
    }
}
