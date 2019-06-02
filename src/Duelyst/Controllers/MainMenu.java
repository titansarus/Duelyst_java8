package Duelyst.Controllers;

import Duelyst.Model.Account;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.io.IOException;

import static Duelyst.View.Constants.NO_USER_LOGINED;

public class MainMenu {


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

    public void stopTimeline()
    {
        timeline.stop();;
    }


    public void handleBackBtn()
    {
        if (Container.scenes.size() > 0) {
            stopTimeline(); //TODO In ba'ad az hazf timeline hazf shavad
            //TODO TEST
            Container.scenes.removeLast();
            Container.stage.setScene(Container.scenes.getLast());
            Container.stage.show();

        }

    }
    public void handlePlayBtn()
    {

    }
    public void handleQuitBtn()
    {
        System.exit(0);
    }
    public void handleShopBtn()
    {

    }
    public void handleCollectionBtn()
    {

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
