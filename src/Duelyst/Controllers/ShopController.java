package Duelyst.Controllers;

import Duelyst.Client.SendMessage;
import Duelyst.Exceptions.CardOutOfStock;
import Duelyst.Exceptions.MyException;
import Duelyst.Model.Account;
import Duelyst.Model.Card;
import Duelyst.Model.CommandClasses.ShopCommand;
import Duelyst.Model.CommandClasses.ShopCommandsKind;
import Duelyst.Model.Shop;
import Duelyst.Model.ShopMode;
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


    ArrayList<CardView> cardViews = new ArrayList<>();

    Timeline slowTimeline;
    Timeline fastTimeline;

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
        makeCardList();
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
        } else {
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

    private void makeCardList() {
        if (buy_tab.isSelected()) {
            makeCardListOfBuy();
        } else if (sell_tab.isSelected())
            makeCardListOfSell();
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
                anchorPane.setTranslateY(75);
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
            ShopCommand shopCommand = new ShopCommand(ShopCommandsKind.BUY);
            shopCommand.setBuyCard(Shop.getSelectedCard());
            SendMessage.getSendMessage().sendMessage(shopCommand);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (myException instanceof CardOutOfStock) {
                Container.exceptionGenerator(myException, stackPane);
                return;
            }
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

    private void updateLoggedInUser() {
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
        makeCardList();

    }

    public void handleCardInformationButton() {

        anchorPane.setOpacity(0.7);

        cardName_lbl.setText(Shop.getSelectedCard().getCardName());
//        cardKind_lbl.setText(Shop.getSelectedCard().getCardKind().toString());
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
        Card card = Shop.getSelectedCard();
        ShopCommand shopCommand = new ShopCommand(ShopCommandsKind.AUCTION_CARD);
        shopCommand.setAuctionCard(card);
        SendMessage.getSendMessage().sendMessage(shopCommand);
    }

    public void handleAuctionButton() {
        TranslateTransition tt = new TranslateTransition(Duration.millis(1500), Auction_pane);
        tt.setFromY(800);
        tt.setToY(0);
        tt.play();
        ShopCommand shopCommand = new ShopCommand(ShopCommandsKind.AUCTION_CARD);
        makeCardList(null, AuctionPaneHBox_hbox);//TODO Bejaye Null Bayad ArrayListe Cardaye Dar Mozayede Bashad
    }

    public void handleAuctionPaneCloseButton() {
        TranslateTransition tt = new TranslateTransition(Duration.millis(750), Auction_pane);
        tt.setFromY(0);
        tt.setToY(800);
        tt.play();
    }

    public void handleTabSelectionChanged() {
        makeCardList();
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
