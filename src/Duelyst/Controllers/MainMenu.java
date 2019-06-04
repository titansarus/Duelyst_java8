package Duelyst.Controllers;

import Duelyst.Model.Account;
import Duelyst.View.Constants;
import com.jfoenix.controls.JFXButton;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

import static Duelyst.View.Constants.*;

import java.io.IOException;

import static Duelyst.View.Constants.NO_USER_LOGINED;

public class MainMenu {


    @FXML
    Label loginedAccount_lbl;

    @FXML
    Label accountDarick_lbl;

    @FXML
    JFXButton play_btn;

    @FXML
    JFXButton leaderboard_btn;

    @FXML
    JFXButton quit_btn;


    @FXML
    JFXButton shop_btn;


    @FXML
    JFXButton collection_btn;


    @FXML
    JFXButton back_btn;


    @FXML
    StackPane stackPane;


    Timeline timeline = new Timeline();

    @FXML
    public void initialize() {
        runTimeline();


        play_btn.setGraphic(new ImageView(playImg));
        shop_btn.setGraphic(new ImageView(shopImg));
        quit_btn.setGraphic(new ImageView(quitImg));
        collection_btn.setGraphic(new ImageView(collectionImg));
        leaderboard_btn.setGraphic(new ImageView(leaderboardsImg));
        back_btn.setGraphic(new ImageView(backImg));

    }

    public void runTimeline() {
        timeline = new Timeline(new KeyFrame(Duration.ZERO, event -> {
            updateLoginedUser();
            updateDarick();
        }), new KeyFrame(Duration.seconds(1)));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
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

    public void stopTimeline() {
        timeline.stop();
        ;
    }

    public void handleLeaderboardBtn() {
        Pane root = null;
        FXMLLoader fxmlLoader = null;
        try {
            fxmlLoader = new FXMLLoader(getClass().getResource("../View/FXMLFiles/Leaderboards.fxml"));
            root = fxmlLoader.load();
            int i = 0;
            System.out.println(i);
        } catch (IOException e) {
            e.printStackTrace();
        }
        stopTimeline();
        Container.runNextScene(root,LEADERBOARD);
    }


    public void handleBackBtn() {
        if (Container.scenes.size() > 0) {
            stopTimeline(); //TODO In ba'ad az hazf slowTimeline hazf shavad
            //TODO TEST
          Container.handleBack();

        }

    }

    public void handlePlayBtn() {

    }

    public void handleQuitBtn() {
        System.exit(0);
    }

    public void handleShopBtn() {

        Pane root = null;
        FXMLLoader fxmlLoader = null;
        try {
            fxmlLoader = new FXMLLoader(getClass().getResource("../View/FXMLFiles/Shop.fxml"));
            root = fxmlLoader.load();
            int i = 0;
            System.out.println(i);
        } catch (IOException e) {
            e.printStackTrace();
        }
        stopTimeline();
        Container.runNextScene(root,SHOP);

    }

    public void handleCollectionBtn() {
        Pane root = null;
        FXMLLoader fxmlLoader = null;
        try {
            fxmlLoader = new FXMLLoader(getClass().getResource("../View/FXMLFiles/CardCollection.fxml"));
            root = fxmlLoader.load();
            int i = 0;
            System.out.println(i);
        } catch (IOException e) {
            e.printStackTrace();
        }
        stopTimeline();
        Container.runNextScene(root,COLLECTION);

    }

    public Label getLoginedAccount_lbl() {
        return loginedAccount_lbl;
    }

    public Label getAccountDarick_lbl() {
        return accountDarick_lbl;
    }

    public Timeline getTimeline() {
        return timeline;
    }
}
