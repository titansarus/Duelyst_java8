package Duelyst.Controllers;

import Duelyst.Client.SendMessage;
import Duelyst.Exceptions.CardOutOfStock;
import Duelyst.Exceptions.MyException;
import Duelyst.Model.*;
import Duelyst.Model.CommandClasses.ShopCommand;
import Duelyst.Model.CommandClasses.ShopCommandsKind;
import Duelyst.View.ViewClasses.CardView;
import com.jfoenix.controls.*;
import javafx.animation.*;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.util.ArrayList;

import static Duelyst.View.Constants.*;

public class ShopController {


    public ImageView next_img;
    public ImageView previous_img;
    public AnchorPane anchorPane;
    public ImageView back_img;
    public ImageView cardInformation_img;
    public Pane cardInformation_pane;
    public Label cardName_lbl;
    public Label cardKind_lbl;
    public Text cardDescription_text;
    public Pane Auction_pane;
    public HBox AuctionPaneHBox_hbox;
    public ImageView addToAuctionButton_img;
    public ImageView AuctionBidButton_img;
    public Label currentOwnerAuction_lbl;
    public Label auctionTimeLeft_lbl;
    public Label auctionHighestBid_lbl;
    public Pane addToAuctionNotification_pane;
    public ImageView shopOthers_img;
    public ImageView shopMinions_img;
    public ImageView shopHeroes_img;
    public Label cardAttackKind_lbl;
    public Label cardRange_lbl;
    public Label cardCost_lbl;
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
    JFXButton action_btn;

    @FXML
    JFXTextField search_txtf;

    @FXML
    Label loginedAccount_lbl;

    @FXML
    Label accountDarick_lbl;

    @FXML
    StackPane stackPane;

    private boolean stanceOfTGB = false;


    private ArrayList<CardView> cardViews = new ArrayList<>();

    private Timeline slowTimeline;
    private Timeline fastTimeline;

    private CardKind shopShowCardKind = CardKind.MINION;

    private static MyException myException;

    public static MyException getMyException() {
        return myException;
    }

    public static void setMyException(MyException myException) {
        ShopController.myException = myException;
    }

    @FXML
    public void initialize() {
        runFastTimeLine();
        runSlowTimeline();
    }


    public void runFastTimeLine() {
        fastTimeline = new Timeline(new KeyFrame(Duration.ZERO, event -> {
            updateColor();
            updateButton();
            updateInformationButton();
            updateAddToAuctionButton();
        }), new KeyFrame(Duration.millis(100)));
        fastTimeline.setCycleCount(Animation.INDEFINITE);
        fastTimeline.play();
    }


    public void runSlowTimeline() {
        slowTimeline = new Timeline(new KeyFrame(Duration.ZERO, event -> {
            updateColor();
            updateButton();
            updateLoggedInUser();
            updateDarick();
            updateInformationButton();
            updateAddToAuctionButton();
        }), new KeyFrame(Duration.millis(500)));
        slowTimeline.setCycleCount(Animation.INDEFINITE);
        slowTimeline.play();
    }

    public void updateColor() {

        for (CardView cardView : cardViews) {
            if (cardView.getCard().equals(Shop.getSelectedCard())) {
                cardView.getCardController().changeToSelected();

            } else {
                cardView.getCardController().changeToNotSelected();
            }
        }
    }

    public void updateAuctionCardInformation() {
        SendMessage.getSendMessage().sendMessage(new ShopCommand(ShopCommandsKind.GET_AUCTION_CARDS));

        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ShopCommand shopCommand = new ShopCommand(ShopCommandsKind.GET_AUCTION_CARD_TIME);
        if(Shop.getInstance().getAuctionSelectedCard() == null)
            return;
        shopCommand.setAuctionCard(Shop.getInstance().getAuctionSelectedCard());
        SendMessage.getSendMessage().sendMessage(shopCommand);
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (Shop.getInstance().getAuctionSelectedCard() != null) {
            currentOwnerAuction_lbl.setText(Shop.getInstance().getAuctionSelectedCard().getAuctionClient());
            auctionHighestBid_lbl.setText(Shop.getInstance().getAuctionSelectedCard().getAuctionCost() + "");
        }
    }

    private void updateInformationButton() {
        if (Shop.getSelectedCard() == null) {
            cardInformation_img.setOpacity(0.7);
            cardInformation_img.setDisable(true);
            cardInformation_img.setEffect(null);
        } else {
            cardInformation_img.setOpacity(1);
            cardInformation_img.setDisable(false);
            cardInformation_img.setEffect(new DropShadow(10, Color.GOLD));
        }
    }

    public void handleNextButton() {
        Circle circle = new Circle(next_img.getLayoutX() + next_img.getFitWidth() / 2, next_img.getLayoutY() + next_img.getFitHeight() / 2, 0, Color.TRANSPARENT);
        buttonClickEffect(circle, 40, event -> anchorPane.getChildren().remove(circle));

        if (buy_tab.isSelected()) {
            buyScrollPane.setHvalue(buyScrollPane.getHvalue() + 0.02);//TODO andazeye GhadamHa bayad daghighTar Tanzim Shan
        } else if (sell_tab.isSelected()) {
            sellScrollPane.setHvalue(sellScrollPane.getHvalue() + 0.02);
        }
    }

    public void handlePreviousButton() {
        Circle circle = new Circle(previous_img.getLayoutX() + previous_img.getFitWidth() / 2, previous_img.getLayoutY() + previous_img.getFitHeight() / 2, 0, Color.TRANSPARENT);
        buttonClickEffect(circle, 40, event -> anchorPane.getChildren().remove(circle));

        if (buy_tab.isSelected()) {
            buyScrollPane.setHvalue(buyScrollPane.getHvalue() - 0.02);
        } else {
            sellScrollPane.setHvalue(sellScrollPane.getHvalue() - 0.02);
        }
    }

    private void buttonClickEffect(Circle circle, int endValue, EventHandler eventHandler) {
        circle.setStroke(Color.WHITE);
        circle.setEffect(new DropShadow(50, 0, 0, Color.WHITE));//TODO Nemidoonam Chera Neshoon Nemide In Effecto!
        KeyValue keyValue = new KeyValue(circle.radiusProperty(), endValue);
        KeyFrame keyFrame = new KeyFrame(Duration.millis(700), keyValue);
        Timeline timeLine = new Timeline(keyFrame);
        anchorPane.getChildren().add(circle);
        timeLine.setOnFinished(eventHandler);

        timeLine.play();

    }

//    private void makeCardList() {
//        if (buy_tab.isSelected()) {
//            makeCardListOfBuy();
//        } else if (sell_tab.isSelected())
//            makeCardListOfSell();
//    }

    public void handleShopMinionsButton() {
        shopShowCardKind = CardKind.MINION;
        if (buy_tab.isSelected()) {
            makeCardList(Shop.getInstance().getCards(), buy_aPane, CardKind.MINION);
        } else
            makeCardList(Account.getLoggedAccount().getCardCollection().getCards(), sell_aPane, CardKind.MINION);
    }

    public void handleShopHeroesButton() {
        shopShowCardKind = CardKind.HERO;
        if (buy_tab.isSelected())
            makeCardList(Shop.getInstance().getCards(), buy_aPane, CardKind.HERO);
        else
            makeCardList(Account.getLoggedAccount().getCardCollection().getCards(), sell_aPane, CardKind.HERO);
    }

    public void handleShopOthersButton() {
        shopShowCardKind = CardKind.SPELL;
        if (buy_tab.isSelected())
            makeCardList(Shop.getInstance().getCards(), buy_aPane, CardKind.SPELL);
        else
            makeCardList(Account.getLoggedAccount().getCardCollection().getCards(), sell_aPane, CardKind.SPELL);
    }

//
//    private void makeCardListOfBuy() {
//        makeCardList(Shop.getInstance().getCards(), buy_aPane);
//    }

//    private void makeCardListOfSell() {
//        makeCardList(Account.getLoggedAccount().getCardCollection().getCards(), sell_aPane);
//    }

    private void makeCardList(ArrayList<Card> cards, HBox Hbox, CardKind cardKind) {

        Hbox.getChildren().clear();
        Hbox.setPrefWidth(629);
        cardViews.clear();
        for (int i = 0; i < cards.size(); i++) {
            if (search_txtf != null && (search_txtf.getText().length() == 0 || cards.get(i).getCardName().contains(search_txtf.getText()))) {
                if (cardKind == null || (cardKind.equals(CardKind.SPELL) && (cards.get(i).getCardKind().equals(CardKind.SPELL) || cards.get(i).getCardKind().equals(CardKind.ITEM))) || cardKind.equals(cards.get(i).getCardKind())) {
                    AnchorPane anchorPane = new AnchorPane();
                    anchorPane.setPrefWidth(275);
                    CardView cardView = new CardView(cards.get(i));
                    cardView.setCache(true);
                    getCardViews().add(cardView);
                    anchorPane.getChildren().add(cardView);

                    Hbox.getChildren().add(anchorPane);
                    anchorPane.setLayoutX(280 * i);
                    anchorPane.setTranslateY(75);
                    Hbox.setPrefWidth(Hbox.getPrefWidth() + 275);
                }
            }
        }
    }

    private void updateButton() {
        if (!Shop.getInstance().getShopMode().equals(ShopMode.AUCTION)) {
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
    }

    private void updateAddToAuctionButton() {

        if (sell_tab.isSelected() && Shop.getSelectedCard() != null) {
            addToAuctionButton_img.setDisable(false);
            addToAuctionButton_img.setOpacity(1);
            addToAuctionButton_img.setEffect(new DropShadow(10, 0, 0, Color.GOLD));
        } else {
            addToAuctionButton_img.setDisable(true);
            addToAuctionButton_img.setOpacity(0.7);
            addToAuctionButton_img.setEffect(null);
        }
    }

    public void handleActionBtn() {
        if (!sell_tab.isSelected()) {
            handleServerRequestForBuy();
            if (myException instanceof CardOutOfStock) {
                Container.exceptionGenerator(myException, stackPane);
                myException = null;
                return;
            }
            buy();
        } else {
            sell();
        }
        Account.saveAccount();
    }

    private void handleServerRequestForBuy() {
        ShopCommand shopCommand = new ShopCommand(ShopCommandsKind.BUY);
        shopCommand.setBuyCard(Shop.getSelectedCard());
        SendMessage.getSendMessage().sendMessage(shopCommand);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    private void buy() {
        try {
            Shop.getInstance().buy();
        } catch (MyException e) {
            Container.exceptionGenerator(e, stackPane);
            return;
        }
        Container.notificationShower(BUY_CONTENT, BUY_TITLE, stackPane);
    }

    private void sell() {
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

    private void updateLoggedInUser() {
        if (Account.getLoggedAccount() == null) {
            getLoginedAccount_lbl().setText(NO_USER_LOGINED);
            return;
        }
        getLoginedAccount_lbl().setText(Account.getLoggedAccount().getUsername());
    }

    private void stopTimeline() {
        getSlowTimeline().stop();
        getFastTimeline().stop();
    }


    public void handleBackBtn() {
        Circle circle = new Circle(back_img.getLayoutX(), back_img.getLayoutY(), 0, Color.TRANSPARENT);

        buttonClickEffect(circle, 70, event -> {
            anchorPane.getChildren().remove(circle);
            if (Container.scenes.size() > 0) {
                stopTimeline(); //Necessary for Optimization. Don't Delete This.
                Container.handleBack();
            }
        });
    }

    public void handleSearchInputChanged() {
        System.out.println("Search");
//        if (buy_tab.isSelected())
//            makeCardList(Shop.getInstance().getCards(), buy_aPane, null);
//        else
//            makeCardList(Shop.getInstance().getCards(), sell_aPane, null);
        switch (shopShowCardKind) {
            case HERO:
                handleShopHeroesButton();
                break;
            case MINION:
                handleShopMinionsButton();
                break;
            case SPELL:
                handleShopOthersButton();
                break;
        }
    }

    public void handleCardInformationButton() {

        anchorPane.setOpacity(0.7);

        if (Shop.getSelectedCard() instanceof Warrior) {
            cardAttackKind_lbl.setText(((Warrior) Shop.getSelectedCard()).getAttackKind().toString());
        }
        if (Shop.getSelectedCard() instanceof Warrior) {
            if (((Warrior) Shop.getSelectedCard()).getAttackRange() > 0)
                cardRange_lbl.setText("Range: " + ((Warrior) Shop.getSelectedCard()).getAttackRange() + "");
            else
                cardRange_lbl.setText("");
        }

        cardCost_lbl.setText(Shop.getSelectedCard().getDarikCost() + " Dariks");
        cardName_lbl.setText(Shop.getSelectedCard().getCardName());
        cardKind_lbl.setText(Shop.getSelectedCard().getCardKind().toString());
        cardDescription_text.setText(Shop.getSelectedCard().getCardDescription());

        FadeTransition ft = new FadeTransition(Duration.millis(1000), cardInformation_pane);
        ft.setFromValue(0);
        ft.setToValue(1);

        TranslateTransition tt = new TranslateTransition(Duration.millis(100), cardInformation_pane);
        tt.setFromY(-700);
        tt.setToY(100);
        tt.setOnFinished(event -> ft.play());
        tt.play();


        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setPrefWidth(275);
        CardView cardView = new CardView(Shop.getSelectedCard());
        cardView.setCache(true);
        anchorPane.getChildren().add(cardView);
        anchorPane.setLayoutX(20);
        anchorPane.setLayoutY(50);
        anchorPane.setEffect(new DropShadow(40, Color.BLACK));

        cardInformation_pane.getChildren().add(anchorPane);
    }

    public void handleCardInformationPane() {
        FadeTransition ft = new FadeTransition(Duration.millis(500), cardInformation_pane);
        ft.setFromValue(1);
        ft.setToValue(0);
        TranslateTransition tt = new TranslateTransition(Duration.millis(100), cardInformation_pane);
        tt.setFromY(150);
        tt.setToY(-700);
        ft.setOnFinished(event -> {
            anchorPane.setOpacity(1);
            tt.play();
        });
        ft.play();
    }

    public void handleAddToAuctionButton() {
        addToAuctionButtonAnimation();
        Card card = Shop.getSelectedCard();
        ShopCommand shopCommand = new ShopCommand(ShopCommandsKind.AUCTION_CARD);
        shopCommand.setAuctionCard(card);
        SendMessage.getSendMessage().sendMessage(shopCommand);
    }

    public void addToAuctionButtonAnimation() {
        TranslateTransition tt = new TranslateTransition(Duration.millis(2000), addToAuctionNotification_pane);
        tt.setFromX(650);
        tt.setToX(400);
        tt.setCycleCount(2);
        tt.setAutoReverse(true);
        tt.play();
    }

    public void handleAuctionButton() {
        TranslateTransition tt = new TranslateTransition(Duration.millis(1500), Auction_pane);
        tt.setFromY(800);
        tt.setToY(0);
        tt.play();
        getAuctionCardsFromServer();
        Shop.getInstance().setShopMode(ShopMode.AUCTION);
        makeAuctionCardList();

    }

    public void makeAuctionCardList() {
        makeCardList(Shop.getInstance().getAuctionCards(), AuctionPaneHBox_hbox, null);
    }

    private void getAuctionCardsFromServer() {
        ShopCommand shopCommand = new ShopCommand(ShopCommandsKind.GET_AUCTION_CARDS);
        SendMessage.getSendMessage().sendMessage(shopCommand);
        try {
            Thread.sleep(100);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void handleAuctionPaneCloseButton() {
        TranslateTransition tt = new TranslateTransition(Duration.millis(750), Auction_pane);
        tt.setFromY(0);
        tt.setToY(800);
        tt.play();
        if (buy_tab.isSelected())
            Shop.getInstance().setShopMode(ShopMode.BUY);
        else
            Shop.getInstance().setShopMode(ShopMode.SELL);
    }

    public void handleAuctionBidButton() {
        Card card = Shop.getInstance().getAuctionSelectedCard();
        if (card == null) {
            return;
        }
        ShopCommand shopCommand = new ShopCommand(ShopCommandsKind.REQUEST_TO_ACTION_CARD);
        shopCommand.setAuctionCard(card);
        SendMessage.getSendMessage().sendMessage(shopCommand);
    }

    public void auctionBidButtonGlow() {
        AuctionBidButton_img.setImage(new Image("res/ui/button_primary_middle_glow@2x.png"));
    }

    public void auctionBidButtonGlowDisappear() {
        AuctionBidButton_img.setImage(new Image("res/ui/button_primary_middle@2x.png"));
    }

    public void shopMinionsButtonGlow() {
        shopMinions_img.setImage(new Image("res/ui/button_primary_middle_glow@2x.png"));
    }

    public void shopMinionsButtonGlowDisappear() {
        shopMinions_img.setImage(new Image("res/ui/button_primary_middle@2x.png"));
    }

    public void shopHeroesButtonGlow() {
        shopHeroes_img.setImage(new Image("res/ui/button_primary_middle_glow@2x.png"));
    }

    public void shopHeroesButtonGlowDisappear() {
        shopHeroes_img.setImage(new Image("res/ui/button_primary_middle@2x.png"));
    }

    public void shopOthersButtonGlow() {
        shopOthers_img.setImage(new Image("res/ui/button_primary_middle_glow@2x.png"));
    }

    public void shopOthersButtonGlowDisappear() {
        shopOthers_img.setImage(new Image("res/ui/button_primary_middle@2x.png"));
    }

    public Timeline getFastTimeline() {
        return fastTimeline;
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
