package Duelyst.Controllers;

import Duelyst.Exceptions.MyException;
import Duelyst.Exceptions.UserExistException;
import Duelyst.Exceptions.UserNotExistException;
import Duelyst.Model.Account;
import Duelyst.View.Constants;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.util.Duration;


import java.io.IOException;

import static Duelyst.View.Constants.*;

public class LoginController {

    @FXML
    JFXTextField username_tf;

    @FXML
    JFXPasswordField password_tf;

    @FXML
    Label loginedAccount_lbl;

    @FXML
    Label accountDarick_lbl;

    Timeline timeline = new Timeline();

    @FXML
    public void initialize()
    {
        runTimeline();
    }

    public void runTimeline()
    {
        timeline = new Timeline(new KeyFrame(Duration.ZERO, event -> {
            updateLoginedUser();
            updateDarick();
        }), new KeyFrame(Duration.seconds(1)));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    public void stopTimeline()
    {
        timeline.stop();;
    }

    private void updateDarick() {
        if (Account.getLoginedAccount() == null) {
            getAccountDarick_lbl().setText("0");
        } else {
            getAccountDarick_lbl().setText(String.valueOf(Account.getLoginedAccount().getDarick()));
        }
    }

    private void updateLoginedUser() {
        if (Account.getLoginedAccount() == null) {
            getLoginedAccount_lbl().setText(NO_USER_LOGINED);
            return;
        }
        getLoginedAccount_lbl().setText(Account.getLoginedAccount().getUsername());
    }


    public void handleLoginBtn() {
        String username = getUsername_tf().getText();
        String password = getPassword_tf().getText();
        if (!Account.accountExistInArrayList(username, Account.getAccounts())) {
            Container.exceptionGenerator(new UserNotExistException());
            return;
        }
        Account.setLoginedAccount(Account.findAccountInArrayList(username, Account.getAccounts()));
        Container.notificationShower(USER_LOGINED_CONTENT, USER_LOGINED);


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

    public Label getLoginedAccount_lbl() {
        return loginedAccount_lbl;
    }

    public void setLoginedAccount_lbl(Label loginedAccount_lbl) {
        this.loginedAccount_lbl = loginedAccount_lbl;
    }

    public Label getAccountDarick_lbl() {
        return accountDarick_lbl;
    }

    public void setAccountDarick_lbl(Label accountDarick_lbl) {
        this.accountDarick_lbl = accountDarick_lbl;
    }
}
