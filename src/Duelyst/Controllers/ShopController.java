package Duelyst.Controllers;

import Duelyst.Exceptions.MyException;
import Duelyst.Model.Account;
import Duelyst.Model.Card;
import Duelyst.Model.Shop;
import Duelyst.Model.ShopMode;
import Duelyst.View.ViewClasses.CardView;
import com.jfoenix.controls.*;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.util.ArrayList;

import static Duelyst.View.Constants.*;

public class ShopController {

    @FXML
    ScrollPane scrollPane;
    @FXML
    HBox listOfCards_HBox;

    @FXML
    JFXToggleButton buySell_tgb;

    @FXML
    JFXButton action_btn;

    @FXML
    JFXTextField search_txtf;

    @FXML
    Label loginedAccount_lbl;

    @FXML
    Label accountDarick_lbl;

    @FXML
    JFXButton back_btn;

    @FXML
    StackPane stackPane;

    private boolean stanceOfTGB = false;


    ArrayList<CardView> cardViews = new ArrayList<>();

    Timeline slowTimeline;
    Timeline fastTimeline;

    @FXML
    public void initialize() {
        back_btn.setGraphic(new ImageView(backImg));
        runFastTimeLine();
        runSlowTimeline();
    }


    public void runFastTimeLine() {
        fastTimeline = new Timeline(new KeyFrame(Duration.ZERO, event -> {
            updateColor();
            updateButton();
        }), new KeyFrame(Duration.millis(100)));
        fastTimeline.setCycleCount(Animation.INDEFINITE);
        fastTimeline.play();
    }


    public void runSlowTimeline() {
        slowTimeline = new Timeline(new KeyFrame(Duration.ZERO, event -> {
            makeCardList();
            updateColor();
            updateButton();
            updateLoginedUser();
            updateDarick();
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

    private void makeCardList() {
        if (buySell_tgb.isSelected()) {
            makeCardListOfSell();
        } else {
            makeCardListOfBuy();
        }
//        listOfCards_HBox.getChildren().clear();
//        listOfCards_HBox.setPrefWidth(629);
//        cardViewsOfCollection.clear();
//        for (int i = 0; i < Shop.getInstance().getCards().size(); i++) {
//            if (search_txtf.getText().length() == 0 || Shop.getInstance().getCards().get(i).getCardName().contains(search_txtf.getText())) {
//                VBox vBox = new VBox();
//                vBox.setPrefWidth(275);
//                CardView cardView = new CardView(Shop.getInstance().getCards().get(i));
//                getCardViewsOfCollection().add(cardView);
//                vBox.getChildren().add(cardView);
//                listOfCards_HBox.getChildren().add(vBox);
//                listOfCards_HBox.setPrefWidth(listOfCards_HBox.getPrefWidth() + 275);
//            }
//        }
    }

    private void makeCardListOfBuy() {
        makeCardList(Shop.getInstance().getCards());
    }

    private void makeCardListOfSell() {
        makeCardList(Account.getLoggedAccount().getCardCollection().getCards());
    }

    private void makeCardList(ArrayList<Card> cards) {

        listOfCards_HBox.getChildren().clear();
        listOfCards_HBox.setPrefWidth(629);
        cardViews.clear();
        for (int i = 0; i < cards.size(); i++) {
            if (search_txtf.getText().length() == 0 || cards.get(i).getCardName().contains(search_txtf.getText())) {
                VBox vBox = new VBox();
                vBox.setPrefWidth(275);
                CardView cardView = new CardView(cards.get(i));
                getCardViews().add(cardView);
                vBox.getChildren().add(cardView);
                listOfCards_HBox.getChildren().add(vBox);
                listOfCards_HBox.setPrefWidth(listOfCards_HBox.getPrefWidth() + 275);
            }
        }
    }

    private void updateButton() {
        if (getBuySell_tgb().isSelected()) {
            ImageView i = new ImageView(sellImg);
            Shop.getInstance().setShopMode(ShopMode.SELL);


            getAction_btn().setGraphic(i);
        } else {
            ImageView i = new ImageView(buyImg);
            Shop.getInstance().setShopMode(ShopMode.BUY);
            getAction_btn().setGraphic(i);
        }

        if (isStanceOfTGB() != getBuySell_tgb().isSelected()) {
            setStanceOfTGB(getBuySell_tgb().isSelected());
            Shop.setSelectedCard(null);
        }
    }

    public void handleActionBtn() {
        if (!getBuySell_tgb().isSelected()) {
            buy();
        } else {
            sell();
        }
        Account.saveAccount();
    }

    public void buy() {
        try {
            Shop.getInstance().buy();
        } catch (MyException e) {
            Container.exceptionGenerator(e, stackPane);
            return;
        }
        System.out.println("buyed");
        Container.notificationShower(BUY_CONTENT, BUY_TITLE, stackPane);
    }

    public void sell() {
        try {
            Shop.getInstance().sell();
        } catch (MyException e) {
            Container.exceptionGenerator(e, stackPane);
            return;
        }
        System.out.println("sell");
        Container.notificationShower(SELL_CONTENT, SELL_TITLE, stackPane);
    }

    private void updateDarick() {
        if (Account.getLoggedAccount() == null) {
            getAccountDarick_lbl().setText("0");
        } else {
            getAccountDarick_lbl().setText(String.valueOf(Account.getLoggedAccount().getDarick()));
        }
    }

    private void updateLoginedUser() {
        if (Account.getLoggedAccount() == null) {
            getLoginedAccount_lbl().setText(NO_USER_LOGINED);
            return;
        }
        getLoginedAccount_lbl().setText(Account.getLoggedAccount().getUsername());
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

    public Timeline getFastTimeline() {
        return fastTimeline;
    }

    public ScrollPane getScrollPane() {
        return scrollPane;
    }

    public HBox getListOfCards_HBox() {
        return listOfCards_HBox;
    }

    public JFXToggleButton getBuySell_tgb() {
        return buySell_tgb;
    }

    public JFXButton getAction_btn() {
        return action_btn;
    }

    public JFXTextField getSearch_txtf() {
        return search_txtf;
    }

    public Label getLoginedAccount_lbl() {
        return loginedAccount_lbl;
    }

    public Label getAccountDarick_lbl() {
        return accountDarick_lbl;
    }

    public ArrayList<CardView> getCardViews() {
        return cardViews;
    }

    public Timeline getSlowTimeline() {
        return slowTimeline;
    }

    public boolean isStanceOfTGB() {
        return stanceOfTGB;
    }

    public void setStanceOfTGB(boolean stanceOfTGB) {
        this.stanceOfTGB = stanceOfTGB;
    }
}
