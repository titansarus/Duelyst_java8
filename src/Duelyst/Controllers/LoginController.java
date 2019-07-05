package Duelyst.Controllers;

import Duelyst.Client.Client;
import Duelyst.Client.SendMessage;
import Duelyst.Exceptions.*;
import Duelyst.Main;
import Duelyst.Model.Account;
import Duelyst.Model.CommandClasses.LoginCommand;
import Duelyst.Model.CommandClasses.LoginCommandsKind;
import Duelyst.Model.Shop;
import Duelyst.Server.Server;
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
    private static MyException myException;

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
        Container.addController(fxmlLoader);
        Container.runNextScene(root, MAIN_MENU);
    }


    public void handleLoginBtn() {

        String password = getPassword_tf().getText();
        String username = getUsername_tf().getText();

        if (username.length() == 0 || password.length() == 0) {
            Container.exceptionGenerator(new EmptyFieldsException(), stackPane);
            return;
        }
        SendMessage.getSendMessage().sendMessage(new LoginCommand(LoginCommandsKind.LOGIN, username, password));
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        //TODO These Checks Should Be Removed And Moved To Server Side
//        Account account = Server.findAccountInArrayList(username, Account.getAccounts());
//        if (!Server.accountExistInArrayList(username, Account.getAccounts())) {
//            Container.exceptionGenerator(new UserNotExistException(), stackPane);
//            return;
//        }
//
//        if (!account.getPassword().equals(password)) {
//            Container.exceptionGenerator(new InvalidPasswordException(), stackPane);
//            return;
//        }
//        //TODO =======================================================
//        Account.setLoggedAccount(account);
//        Shop.getInstance().getCards().addAll(account.getCardCollection().getCustomCards());
        if (getMyException() != null) {
            Container.exceptionGenerator(getMyException(), stackPane);
            setMyException(null);
            return;
        }
        handleProgressBar();
    }

    public void handleSignUpBtn() {
        String username = getUsername_tf().getText();
        String password = getPassword_tf().getText();
        if (username.length() == 0 || password.length() == 0) {
            Container.exceptionGenerator(new EmptyFieldsException(), stackPane);
            return;
        }
        SendMessage.getSendMessage().sendMessage(new LoginCommand(LoginCommandsKind.SIGN_UP, username, password));
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//
//        if (Server.accountExistInArrayList(username, Account.getAccounts())) {
//            Container.exceptionGenerator(new UserExistException(), stackPane);
//            return;
//        }
//        new Account(username, password);
//        Server.saveAccount();
        if (getMyException() != null) {
            Container.exceptionGenerator(getMyException(), stackPane);
            setMyException(null);
            return;
        }
        handleLoginBtn();
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


    public void handleExit() {
        SendMessage.getSendMessage().sendMessage(new LoginCommand(LoginCommandsKind.EXIT));
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.exit(0);
    }


    public JFXTextField getUsername_tf() {
        return username_tf;
    }

    public JFXPasswordField getPassword_tf() {
        return password_tf;
    }


    public static MyException getMyException() {
        return myException;
    }

    public static void setMyException(MyException e) {
        myException = e;
    }
}
