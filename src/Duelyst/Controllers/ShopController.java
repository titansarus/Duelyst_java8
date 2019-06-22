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
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.util.Duration;

import java.util.ArrayList;

import static Duelyst.View.Constants.*;

public class ShopController {

    @FXML
    JFXButton next_btn;

    @FXML
    JFXButton previous_btn;

    @FXML
    ScrollPane buyScrollPane;

    @FXML
    ScrollPane sellScrollPane;

    @FXML
    HBox buy_aPane;

    @FXML
    HBox sell_aPane;

    @FXML
    TabPane shopList_tabPane;

    @FXML
    Tab buy_tab;

    @FXML
    Tab sell_tab;

    @FXML
    HBox listOfCards_HBox;


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
        next_btn.setGraphic(new ImageView(nextImg));
        previous_btn.setGraphic(new ImageView(previousImg));
        runFastTimeLine();
        runSlowTimeline();
        makeCardList();
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

//        if (buySell_tgb.isSelected()) {
//            makeCardListOfSell();
//        } else {
//            makeCardListOfBuy();
//        }

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
        makeCardList(Shop.getInstance().getCards(), buy_aPane);
    }

    private void makeCardListOfSell() {
        makeCardList(Account.getLoggedAccount().getCardCollection().getCards(), sell_aPane);
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

    private void updateButton() {
        if (sell_tab.isSelected()) {
            ImageView i = new ImageView(sellImg);
            Shop.getInstance().setShopMode(ShopMode.SELL);


            getAction_btn().setGraphic(i);
        } else {
            ImageView i = new ImageView(buyImg);
            Shop.getInstance().setShopMode(ShopMode.BUY);
            getAction_btn().setGraphic(i);
        }

        if (isStanceOfTGB() != sell_tab.isSelected()) {
            setStanceOfTGB(sell_tab.isSelected());
            Shop.setSelectedCard(null);
        }
    }

    public void handleActionBtn() {
        if (!sell_tab.isSelected()) {
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

    public HBox getListOfCards_HBox() {
        return listOfCards_HBox;
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
