package Duelyst.Controllers;

import Duelyst.Exceptions.*;
import Duelyst.Main;
import Duelyst.Model.Account;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXProgressBar;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Popup;
import javafx.util.Duration;


import java.io.IOException;

import static Duelyst.View.Constants.*;

public class LoginController {


    @FXML
    Label loading_label;

    @FXML
    JFXProgressBar login_pb;

    @FXML
    StackPane stackPane;

    @FXML
    JFXTextField username_tf;

    @FXML
    JFXPasswordField password_tf;


    Timeline timeline = new Timeline();

    @FXML
    public void initialize() {
        runTimeline();
    }

    public void runTimeline() {
        timeline = new Timeline(new KeyFrame(Duration.ZERO, event -> {

        }), new KeyFrame(Duration.seconds(1)));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }


    public void handleGoToMainMenu() {
        if (Account.getLoggedAccount() == null) {
            Container.exceptionGenerator(new NotExistLoginUserException(), stackPane);
            return;
        }
        Pane root = null;
        FXMLLoader fxmlLoader = null;
        try {
            fxmlLoader = new FXMLLoader(getClass().getResource("../View/FXMLFiles/MainMenu.fxml"));
            root = fxmlLoader.load();
            int i = 0;
            System.out.println(i);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Container.runNextScene(root, MAIN_MENU);
    }



    public void handleLoginBtn() {
        String username = getUsername_tf().getText();
        String password = getPassword_tf().getText();
        Account account = Account.findAccountInArrayList(username, Account.getAccounts());
        if (!Account.accountExistInArrayList(username, Account.getAccounts())) {
            Container.exceptionGenerator(new UserNotExistException(), stackPane);
            //Container.exceptionGenerator(new UserNotExistException());
            return;
        }
        if (!account.getPassword().equals(password)) {
            Container.exceptionGenerator(new InvalidPasswordException(), stackPane);
            //Container.exceptionGenerator(new InvalidPasswordException());
            return;
        }
        Account.setLoggedAccount(account);
        handleProgressBar();
    }


    private void handleProgressBar() {
        login_pb.setOpacity(1);
        loading_label.setOpacity(1);

        long startTime = System.nanoTime();

        //2Seconds Time Delay For Loading :))...
        Thread thread = new Thread(() -> {
            while ((System.nanoTime() - startTime) / 1000000 < 2000) {
            }
            login_pb.setOpacity(0);
            loading_label.setOpacity(0);
            getUsername_tf().setText("");
            getPassword_tf().setText("");
            Platform.runLater(this::handleGoToMainMenu);
        });
        thread.start();
    }

    public void handleSignUpBtn() {
        String username = getUsername_tf().getText();
        String password = getPassword_tf().getText();

        if (Account.accountExistInArrayList(username, Account.getAccounts())) {
            Container.exceptionGenerator(new UserExistException(), stackPane);
//            Container.exceptionGenerator(new UserExistException());
            return;
        }
        new Account(username, password);
        Account.saveAccount();
        handleLoginBtn();
    }

    public void handleExit() {
        System.exit(0);
    }


    public JFXTextField getUsername_tf() {
        return username_tf;
    }

    public JFXPasswordField getPassword_tf() {
        return password_tf;
    }


}
