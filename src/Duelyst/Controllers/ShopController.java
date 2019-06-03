package Duelyst.Controllers;

import Duelyst.Exceptions.NoCardSelectedInShopException;
import Duelyst.Exceptions.NotEnoughDarickException;
import Duelyst.Model.Account;
import Duelyst.Model.Card;
import Duelyst.Model.Shop;
import Duelyst.Model.ShopMode;
import Duelyst.View.Constants;
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
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.util.ArrayList;

import static Duelyst.View.Constants.*;

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

    @FXML
    Label loginedAccount_lbl;

    @FXML
    Label accountDarick_lbl;

    @FXML
    JFXButton back_btn;

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
//        msnH.getChildren().clear();
//        msnH.setPrefWidth(629);
//        cardViews.clear();
//        for (int i = 0; i < Shop.getInstance().getCards().size(); i++) {
//            if (search_txtf.getText().length() == 0 || Shop.getInstance().getCards().get(i).getCardName().contains(search_txtf.getText())) {
//                VBox vBox = new VBox();
//                vBox.setPrefWidth(275);
//                CardView cardView = new CardView(Shop.getInstance().getCards().get(i));
//                getCardViews().add(cardView);
//                vBox.getChildren().add(cardView);
//                msnH.getChildren().add(vBox);
//                msnH.setPrefWidth(msnH.getPrefWidth() + 275);
//            }
//        }
    }

    private void makeCardListOfBuy() {
        makeCardList(Shop.getInstance().getCards());
    }

    private void makeCardListOfSell() {
        makeCardList(Account.getLoginedAccount().getCardCollection().getCards());
    }

    private void makeCardList(ArrayList<Card> cards) {
        msnH.getChildren().clear();
        msnH.setPrefWidth(629);
        cardViews.clear();
        for (int i = 0; i < cards.size(); i++) {
            if (search_txtf.getText().length() == 0 || cards.get(i).getCardName().contains(search_txtf.getText())) {
                VBox vBox = new VBox();
                vBox.setPrefWidth(275);
                CardView cardView = new CardView(cards.get(i));
                getCardViews().add(cardView);
                vBox.getChildren().add(cardView);
                msnH.getChildren().add(vBox);
                msnH.setPrefWidth(msnH.getPrefWidth() + 275);
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
    }

    public void buy() {
        try {
            Shop.getInstance().buy();
        } catch (NotEnoughDarickException e) {
            Container.alertShower(e, NOT_ENOUGH_DARICK_TITLE);
            return;
        } catch (NoCardSelectedInShopException e) {
            Container.alertShower(e, NO_CARD_SELECTED_IN_SHOP_TITLE);
            return;
        }
        System.out.println("buyed");
        Container.notificationShower(BUY_CONTENT, BUY_TITLE);
    }

    public void sell() {
        try {
            Shop.getInstance().sell();
        } catch (NoCardSelectedInShopException e) {
            Container.alertShower(e, NO_CARD_SELECTED_IN_SHOP_TITLE);
            return;
        }
        System.out.println("sell");
        Container.notificationShower(SELL_CONTENT, SELL_TITLE);
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
        getSlowTimeline().stop();
        getFastTimeline().stop();
    }


    public void handleBackBtn()
    {
        if (Container.scenes.size() > 0) {
            stopTimeline(); //TODO In ba'ad az hazf slowTimeline hazf shavad
            //TODO TEST
            Container.scenes.removeLast();
            Container.nameOfMenus.removeLast();
            Container.stage.setScene(Container.scenes.getLast());
            Container.stage.show();

        }
    }

    public Timeline getFastTimeline() {
        return fastTimeline;
    }

    public ScrollPane getMsn() {
        return msn;
    }

    public HBox getMsnH() {
        return msnH;
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
