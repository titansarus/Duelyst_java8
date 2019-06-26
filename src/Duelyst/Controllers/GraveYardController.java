package Duelyst.Controllers;

import Duelyst.Exceptions.MyException;
import Duelyst.Model.Account;
import Duelyst.Model.Battle.Battle;
import Duelyst.Model.Card;
import Duelyst.Model.Shop;
import Duelyst.Model.ShopMode;
import Duelyst.View.ViewClasses.CardView;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;

import static Duelyst.View.Constants.*;

public class GraveYardController {

    @FXML
    public StackPane stackPane;

    @FXML
    public TabPane shopList_tabPane;

    @FXML
    public Tab buy_tab;

    @FXML
    public ScrollPane buyScrollPane;

    @FXML
    public HBox buy_aPane;

    @FXML
    public ScrollPane sellScrollPane;

    @FXML
    public HBox sell_aPane;

    @FXML
    public JFXTextField search_txtf;

    @FXML
    public JFXButton next_btn;

    @FXML
    public JFXButton previous_btn;

    @FXML
    public Tab sell_tab;


    ArrayList<CardView> cardViews = new ArrayList<>();

    Timeline slowTimeline;
    Timeline fastTimeline;

    @FXML
    public void initialize() {
        runFastTimeLine();
        runSlowTimeline();
        makeCardList();
    }


    public void runFastTimeLine() {
        fastTimeline = new Timeline(new KeyFrame(Duration.ZERO, event -> {
            updateColor();
        }), new KeyFrame(Duration.millis(100)));
        fastTimeline.setCycleCount(Animation.INDEFINITE);
        fastTimeline.play();
    }


    public void runSlowTimeline() {
        slowTimeline = new Timeline(new KeyFrame(Duration.ZERO, event -> {
            updateColor();
        }), new KeyFrame(Duration.millis(500)));
        slowTimeline.setCycleCount(Animation.INDEFINITE);
        slowTimeline.play();
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

    public void handleNextButton() {
        if (buy_tab.isSelected()) {
            buyScrollPane.setHvalue(buyScrollPane.getHvalue() + 0.02);//TODO andazeye GhadamHa bayad daghighTar Tanzim Shan
        } else {
            sellScrollPane.setHvalue(sellScrollPane.getHvalue() + 0.02);
        }
    }

    public void handlePreviousButton() {
        if (buy_tab.isSelected()) {
            buyScrollPane.setHvalue(buyScrollPane.getHvalue() - 0.02);
        } else {
            sellScrollPane.setHvalue(sellScrollPane.getHvalue() - 0.02);
        }
    }

    private void makeCardList() {
        if (buy_tab.isSelected()) {
            makeCardListOfBuy();
        } else if (sell_tab.isSelected())
            makeCardListOfSell();
    }

    private void makeCardListOfBuy() {
        makeCardList(Battle.getRunningBattle().getPlayer1().getGraveyard(), buy_aPane);
    }

    private void makeCardListOfSell() {
        makeCardList(Battle.getRunningBattle().getPlayer2().getGraveyard(), sell_aPane);
    }

    private void makeCardList(ArrayList<Card> cards, HBox Hbox) {

        Hbox.getChildren().clear();
        Hbox.setPrefWidth(629);
        cardViews.clear();
        for (int i = 0; i < cards.size(); i++) {
            if (search_txtf != null && (search_txtf.getText().length() == 0 || cards.get(i).getCardName().contains(search_txtf.getText()))) {
                AnchorPane anchorPane = new AnchorPane();
                anchorPane.setPrefWidth(275);
                CardView cardView = new CardView(cards.get(i));
                cardView.setCache(true);
                getCardViews().add(cardView);
                anchorPane.getChildren().add(cardView);
                Hbox.getChildren().add(anchorPane);
                anchorPane.setLayoutX(280 * i);
                Hbox.setPrefWidth(Hbox.getPrefWidth() + 275);
            }
        }
    }

    public void stopTimeline() {
        getSlowTimeline().stop();
        getFastTimeline().stop();
    }


    public void handleBackBtn() {
        if (Container.scenes.size() > 0) {
            stopTimeline(); //Necessary for Optimization. Don't Delete This.
            Container.handleBack();

        }
    }


    public void handleSearchInputChanged() {
        System.out.println("Search");
        makeCardList();

    }

    public void handleTabSelectionChanged() {
        makeCardList();
    }

    public Timeline getFastTimeline() {
        return fastTimeline;
    }


    public JFXTextField getSearch_txtf() {
        return search_txtf;
    }


    public ArrayList<CardView> getCardViews() {
        return cardViews;
    }

    public Timeline getSlowTimeline() {
        return slowTimeline;
    }


}
