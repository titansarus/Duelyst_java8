package Duelyst.Controllers;

import Duelyst.Model.Account;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;


import static Duelyst.View.Constants.NO_USER_LOGINED;
import static Duelyst.View.Constants.backImg;

public class CardCollectionController {

    @FXML
    JFXButton back_btn;

    @FXML
    Label loginedAccount_lbl;

    @FXML
    Label accountDarick_lbl;

    @FXML
    JFXTextField search_txtf;

    @FXML
    ScrollPane scrollPane;

    @FXML
    HBox cardCollectionCards_HB;

    @FXML
    StackPane stackePane;


    private Timeline slowTimeline;


    @FXML
    public void initialize() {
        back_btn.setGraphic(new ImageView(backImg));
        runSlowTimeline();
    }


    public void runSlowTimeline() {
        slowTimeline = new Timeline(new KeyFrame(Duration.ZERO, event -> {
            updateLoginedUser();
            updateDarick();
        }), new KeyFrame(Duration.millis(500)));
        slowTimeline.setCycleCount(Animation.INDEFINITE);
        slowTimeline.play();
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
        slowTimeline.stop();
    }


    public void handleBackBtn() {
        if (Container.scenes.size() > 0) {
            stopTimeline();
            Container.handleBack();

        }
    }


    public JFXButton getBack_btn() {
        return back_btn;
    }

    public Label getLoginedAccount_lbl() {
        return loginedAccount_lbl;
    }

    public Label getAccountDarick_lbl() {
        return accountDarick_lbl;
    }

    public JFXTextField getSearch_txtf() {
        return search_txtf;
    }

    public ScrollPane getScrollPane() {
        return scrollPane;
    }

    public HBox getCardCollectionCards_HB() {
        return cardCollectionCards_HB;
    }

    public Timeline getSlowTimeline() {
        return slowTimeline;
    }
}
