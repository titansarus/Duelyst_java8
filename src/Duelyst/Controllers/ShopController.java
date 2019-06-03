package Duelyst.Controllers;

import Duelyst.Model.Shop;
import Duelyst.View.ViewClasses.CardView;
import com.jfoenix.controls.*;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.util.ArrayList;

public class ShopController {

    @FXML
    ScrollPane msn;
    @FXML
    HBox msnH;

    @FXML
    JFXToggleButton buySell_tgb;

    @FXML
    JFXButton action_btn;

    @FXML
    JFXTextField search_txtf;

    ArrayList<CardView> cardViews = new ArrayList<>();

    Timeline timeline;

    @FXML
    public void initialize() {
        runTimeline();
    }

    public void runTimeline() {
        timeline = new Timeline(new KeyFrame(Duration.ZERO, event -> {
            makeCardList();
            updateColor();
            updateButton();
        }), new KeyFrame(Duration.millis(500)));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    public void updateColor() {
        for (int i = 0; i < cardViews.size(); i++) {
            if (cardViews.get(i).getCard().equals(Shop.getSelectedCard())) {
                cardViews.get(i).getCardController().changeToSelected();
            } else {
                cardViews.get(i).getCardController().changeToNotSelected();
            }
        }
    }

    public void makeCardList() {
        msnH.getChildren().clear();
        msnH.setPrefWidth(629);
        cardViews.clear();
        for (int i = 0; i < Shop.getInstance().getCards().size(); i++) {
            if (search_txtf.getText().length() == 0 || Shop.getInstance().getCards().get(i).getCardName().contains(search_txtf.getText())) {
                VBox vBox = new VBox();
                vBox.setPrefWidth(275);
                CardView cardView = new CardView(Shop.getInstance().getCards().get(i));
                cardViews.add(cardView);
                vBox.getChildren().add(cardView);
                msnH.getChildren().add(vBox);
                msnH.setPrefWidth(msnH.getPrefWidth() + 275);
            }
        }
    }

    public void updateButton() {
        if (!buySell_tgb.isSelected()) {
            action_btn.setText("Buy");
        } else {
            action_btn.setText("Sell");
        }
    }

    public void handleActionBtn() {
        if (!buySell_tgb.isSelected()) {
            buy();
        } else {
            sell();
        }
    }

    public void buy() {
        System.out.println("Buy");
    }

    public void sell() {
        System.out.println("sell");
    }

}
