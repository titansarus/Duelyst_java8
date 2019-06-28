package Duelyst.Controllers;

import Duelyst.Exceptions.*;
import Duelyst.Model.Account;
import Duelyst.Model.Card;
import Duelyst.Model.Deck;
import Duelyst.View.Constants;
import Duelyst.View.ViewClasses.CardView;
import com.gilecode.yagson.YaGson;
import com.gilecode.yagson.YaGsonBuilder;
import com.jfoenix.controls.*;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;


import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static Duelyst.View.Constants.*;

public class CardCollectionController {

    public ImageView item_img;
    public Label showDeckItem_lbl;
    @FXML
    JFXButton back_btn;

    @FXML
    Label MainDeck;

    @FXML
    JFXButton sendToDeck;

    @FXML
    JFXButton sendToCollection;

    @FXML
    JFXButton Previous;

    @FXML
    JFXButton next;

    @FXML
    Label loginedAccount_lbl;

    @FXML
    Label accountDarick_lbl;

    @FXML
    JFXTextField search_txtf;

    @FXML
    ScrollPane scrollPane;

    @FXML
    JFXToggleButton Toggle;


    @FXML
    HBox cardCollectionCards_HB;

    @FXML
    StackPane stackePane;

    @FXML
    HBox deck_hbox;

    @FXML
    ScrollPane scrollPane_2;

    @FXML
    JFXButton createDeck_btn;

    @FXML
    Label mainDeck_lbl;

    @FXML
    JFXComboBox<String> listOfDecks_cb;

    @FXML
    JFXButton selectAsMainDeck_btn;

    @FXML
    JFXButton deleteDeck_btn;

    @FXML
    JFXButton exportDeck_btn;


    private String createDeckName; //These Are used just for the time we are getting input from user.
    private String importDeckName;
    private double deckScroll = 0;
    private double collectionScroll = 0;
    ArrayList<CardView> cardViewsOfCollection = new ArrayList<>();
    ArrayList<CardView> cardViewsOfDeck = new ArrayList<>();

    private Timeline slowTimeline;
    Timeline fastTimeline;


    @FXML
    public void initialize() {
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane_2.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane_2.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        back_btn.setGraphic(new ImageView(backImg));
        createDeck_btn.setGraphic(new ImageView(createDeckImg));
        next.setGraphic(new ImageView(nextImg));
        Previous.setGraphic(new ImageView(previousImg));
        sendToCollection.setGraphic(new ImageView(Constants.sendToCollection));
        sendToDeck.setGraphic(new ImageView(sendToDeckImg));
        MainDeck.setGraphic(new ImageView(MainDeckImg));


        runSlowTimeline();
        runFastTimeLine();
    }

    public void runFastTimeLine() {
        fastTimeline = new Timeline(new KeyFrame(Duration.ZERO, event -> {
            updateColor();
        }), new KeyFrame(Duration.millis(100)));
        fastTimeline.setCycleCount(Animation.INDEFINITE);
        fastTimeline.play();
    }

    public void handleSelectAsMainDeck() {
        String nameOfDeck = listOfDecks_cb.getValue();

        if (nameOfDeck != null) {
            Deck deck = Deck.findDeckInArrayList(nameOfDeck, Account.getLoggedAccount().getCardCollection().getDecks());

            if (deck != null) {
                Account.getLoggedAccount().getCardCollection().setMainDeck(deck);
            }
        } else {
            Container.exceptionGenerator(new NoDeckSelectedException(), stackePane);
        }
    }

    public void handleDeleteDeck() {
        String nameOfDeck = listOfDecks_cb.getValue();

        if (nameOfDeck != null) {
            Deck deck = Deck.findDeckInArrayList(nameOfDeck, Account.getLoggedAccount().getCardCollection().getDecks());
            if (deck != null) {
                Deck.giveCardOfDeckToCardCollection(deck, Account.getLoggedAccount().getCardCollection());
                if (deck.equals(Account.getLoggedAccount().getCardCollection().getMainDeck())) {
                    Account.getLoggedAccount().getCardCollection().setMainDeck(null);
                }
                Account.getLoggedAccount().getCardCollection().getDecks().remove(deck);
                Deck.getDecks().remove(deck);
            }
        } else {
            Container.exceptionGenerator(new NoDeckSelectedException(), stackePane);
        }


    }

    public void importDeckFromReaderOrString(Reader reader, String jsonString) {
        YaGson yaGson = new YaGsonBuilder().setPrettyPrinting().create();
        Deck deck = null;
        try {
            if (reader != null) {
                deck = yaGson.fromJson(reader, Deck.class);
                reader.close();
            } else {
                deck = yaGson.fromJson(jsonString, Deck.class);
            }

        } catch (Exception e) {
            Container.exceptionGenerator(new InvalidImportFileException(), stackePane);
        }

        try {

            Deck.sendExportedDeckToCardCollection(deck, Account.getLoggedAccount().getCardCollection());
        } catch (NotEnoughCardsToImportException e) {
            Container.exceptionGenerator(e, stackePane);
        }
        setImportDeckName("");
    }


    public void importDeckfromAddress(String address) {

        Reader reader = null;
        try {
            reader = new FileReader(address);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        importDeckFromReaderOrString(reader, null);


    }

    public void handleDragOver(DragEvent event) {
        if (event.getDragboard().hasFiles()) {
            event.acceptTransferModes(TransferMode.ANY);
        }
    }

    public void handleDragDropped(DragEvent event) throws IOException {
        List<File> files = event.getDragboard().getFiles();
        File file = files.get(0);
        String jsonString;
        BufferedReader br = new BufferedReader(new FileReader(file));
        try {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            jsonString = sb.toString();
        } finally {
            br.close();
        }

        //System.out.println(jsonString);
        importDeckFromReaderOrString(null, jsonString);


    }


    public void handleImportDeck() {
        JFXButton cancel = new JFXButton();
        JFXButton accept = new JFXButton();
        JFXTextField textField = new JFXTextField();
        JFXDialogLayout jfxDialogLayout = Container.jfxInputDialogLayoutMaker(accept, cancel, textField, CHOOSE_IMPORT_DECK_NAME, CHOOSE_IMPORT_DECK_NAME);


        JFXDialog jfxDialog = new JFXDialog(stackePane, jfxDialogLayout, JFXDialog.DialogTransition.CENTER);
        accept.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                setImportDeckName(textField.getText());
                if (getImportDeckName().length() > 0) {
                    String adderss = "./saved/Accounts/" + Account.getLoggedAccount().getUsername() + "/Decks/" + getImportDeckName() + ".json";
                    importDeckfromAddress(adderss);
                }
                jfxDialog.close();

            }
        });
        cancel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                jfxDialog.close();
            }
        });

        jfxDialog.show();


    }

    public void handelPreviousBtn() {

        if (Toggle.isSelected()) {
            if (deckScroll != 0) {
                deckScroll--;
            }
            scrollPane_2.setHvalue(deckScroll * (4.0 / cardCollectionCards_HB.getChildren().size()));

        } else {
            if (collectionScroll != 0) {
                collectionScroll--;
            }
            scrollPane.setHvalue(collectionScroll * (4.0 / cardCollectionCards_HB.getChildren().size()));
        }
        System.out.println(deckScroll * (4.0 / cardCollectionCards_HB.getChildren().size()));
    }

    public void handelNextBtn() {
        if (Toggle.isSelected()) {
            if (scrollPane_2.getHvalue() < 0.95 || true) { //TODO CHECK THIS 0.95 ? Another Number??
                deckScroll++;
            }
            scrollPane_2.setHvalue(deckScroll * (4.0 / cardCollectionCards_HB.getChildren().size()));
        } else {
            if (scrollPane.getHvalue() < 0.95 || true) {
                collectionScroll++;
            }
            scrollPane.setHvalue(collectionScroll * (4.0 / cardCollectionCards_HB.getChildren().size()));
        }
        System.out.println(deckScroll * (4.0 / cardCollectionCards_HB.getChildren().size()));
    }

    public void handleExportDeckBtn() {

        if (Account.getLoggedAccount().getCardCollection().getMainDeck() != null) {
            String nameOfDeck = Account.getLoggedAccount().getCardCollection().getMainDeck().getDeckName();
            String address = "./saved/Accounts/" + Account.getLoggedAccount().getUsername() + "/Decks";
            new File(address).mkdirs();
            YaGson yaGson = new YaGsonBuilder().setPrettyPrinting().create();
            String exportedDeck = yaGson.toJson(Account.getLoggedAccount().getCardCollection().getMainDeck());
            try (FileWriter fileWriter = new FileWriter(address + "/" + nameOfDeck + ".json")) {
                fileWriter.write(exportedDeck);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Container.exceptionGenerator(new NoDeckSelectedException(), stackePane);
        }
    }

    public void handleValidateDeck() {
        if (Account.getLoggedAccount().getCardCollection().getMainDeck() == null) {
            Container.exceptionGenerator(new NoMainDeckSelectedException(), stackePane);
            return;
        }
        if (Deck.validateDeck(Account.getLoggedAccount().getCardCollection().getMainDeck())) {
            Container.notificationShower(DECK_IS_VALID_CONTENT, DECK_IS_VALID_TITLE, stackePane);
        } else {
            Container.exceptionGenerator(new DeckIsNotValidException(), stackePane);
        }

    }

    public void exportDeck(String nameOfExportedDeck) {
    }


    public void handleCreateDeck() {
        JFXButton cancel = new JFXButton();
        JFXButton accept = new JFXButton();
        JFXTextField textField = new JFXTextField();
        JFXDialogLayout jfxDialogLayout = Container.jfxInputDialogLayoutMaker(accept, cancel, textField, CHOOSE_DECK_NAME, CHOOSE_DECK_NAME);


        JFXDialog jfxDialog = new JFXDialog(stackePane, jfxDialogLayout, JFXDialog.DialogTransition.CENTER);

        accept.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                createDeck(textField.getText());
                jfxDialog.close();

            }
        });
        cancel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                jfxDialog.close();
            }
        });

        jfxDialog.show();
    }

    public void createDeck(String deckName) {
        if (Deck.deckExist(deckName, Account.getLoggedAccount().getCardCollection().getDecks())) {
            Container.exceptionGenerator(new DeckExistException(), stackePane);
        } else {
            Deck deck = new Deck(deckName, Account.getLoggedAccount());
            Account.getLoggedAccount().getCardCollection().getDecks().add(deck);
            Account.getLoggedAccount().getCardCollection().setMainDeck(deck);
        }
    }

    public void handleSendToDeck() {
        if (Account.getLoggedAccount().getCardCollection().getSelectedCard() == null ||
                !Account.getLoggedAccount().getCardCollection().cardExist(Account.getLoggedAccount().getCardCollection().getSelectedCard())) {
            Container.exceptionGenerator(new NoCardSelectedFromCollectionException(), stackePane);
            return;
        }

        if (Account.getLoggedAccount().getCardCollection().getSelectedCard() != null) {
            if (Account.getLoggedAccount().getCardCollection().getMainDeck() != null) {


                Account.getLoggedAccount().getCardCollection().getMainDeck().addCard(Account.getLoggedAccount().getCardCollection().getSelectedCard());
                Account.getLoggedAccount().getCardCollection().getCards().remove(Account.getLoggedAccount().getCardCollection().getSelectedCard());
                Account.getLoggedAccount().getCardCollection().setSelectedCard(null);
            } else {
                Container.exceptionGenerator(new NoMainDeckSelectedException(), stackePane);
            }
        }

    }

    public void handleSendBackToCollection() {
        if (Account.getLoggedAccount().getCardCollection().getSelectedCard() != null) {
            if (Account.getLoggedAccount().getCardCollection().getMainDeck() != null) {
                if (Deck.deckHasCard(Account.getLoggedAccount().getCardCollection().getSelectedCard().getCardId(), Account.getLoggedAccount().getCardCollection().getMainDeck())) {

                    Account.getLoggedAccount().getCardCollection().getCards().add(Account.getLoggedAccount().getCardCollection().getSelectedCard());
                    Account.getLoggedAccount().getCardCollection().getMainDeck().getCards().remove(Account.getLoggedAccount().getCardCollection().getSelectedCard());
                    Account.getLoggedAccount().getCardCollection().setSelectedCard(null);

                } else {
                    Container.exceptionGenerator(new NoCardSelectedFromDeckException(), stackePane);
                }
            } else {
                Container.exceptionGenerator(new NoMainDeckSelectedException(), stackePane);
            }
        } else {
            Container.exceptionGenerator(new NoCardSelectedFromDeckException(), stackePane);
        }

    }

    public void populateComboBox() {
        ArrayList<String> deckNames = new ArrayList<>();
        ArrayList<Deck> decks = Account.getLoggedAccount().getCardCollection().getDecks();

        for (int i = 0; i < decks.size(); i++) {
            if (decks.get(i) != null) {
                deckNames.add(decks.get(i).getDeckName());
            }
        }

        listOfDecks_cb.getItems().clear();
        listOfDecks_cb.getItems().addAll(deckNames);

        listOfDecks_cb.hide();
        listOfDecks_cb.setVisibleRowCount(5);
        listOfDecks_cb.show();

    }

    public void IClicked() {//for Debug Only
        System.out.println(listOfDecks_cb.getValue());
    }


    public void runSlowTimeline() {
        slowTimeline = new Timeline(new KeyFrame(Duration.ZERO, event -> {
            updateLoggedInUser();
            updateDarick();
            updateMainDeck();
            updateColor();
            makeCardListOfCollection(Account.getLoggedAccount().getCardCollection().getCards());
            if (Account.getLoggedAccount().getCardCollection().getMainDeck() != null) {
                makeCardListOfDeck(Account.getLoggedAccount().getCardCollection().getMainDeck().getCards());
            } else {
                makeCardListOfDeck(null);
            }
        }), new KeyFrame(Duration.millis(1000)));
        slowTimeline.setCycleCount(Animation.INDEFINITE);
        slowTimeline.play();
    }

    public void updateMainDeck() {
        if (Account.getLoggedAccount().getCardCollection().getMainDeck() != null) {

            mainDeck_lbl.setText(Account.getLoggedAccount().getCardCollection().getMainDeck().getDeckName());
        } else {
            mainDeck_lbl.setText(NO_MAIN_DECK);
        }
    }


    private void makeCardListOfCollection(ArrayList<Card> cards) { //TODO DUPLICATE REFACTOR
        makeCardListGeneralized(cards, cardCollectionCards_HB, cardViewsOfCollection, search_txtf, true);
    }

    private void makeCardListGeneralized(ArrayList<Card> cards, HBox hBox, ArrayList<CardView> cardViews, JFXTextField search, boolean isCollection) {
        hBox.getChildren().clear();
        hBox.setPrefWidth(629);
        cardViews.clear();
        if ((isCollection && !Toggle.isSelected())) {
            deckScroll = 0;
            scrollPane_2.setDisable(true);
            deck_hbox.setDisable(true);
            item_img.setImage(null);
            showDeckItem_lbl.setText("");

            scrollPane_2.setVisible(false);
            scrollPane.setVisible(true);

            scrollPane.setDisable(false);
            cardCollectionCards_HB.setDisable(false);
        } else if ((!isCollection && Toggle.isSelected())) {
            collectionScroll = 0;
            scrollPane.setDisable(true);
            cardCollectionCards_HB.setDisable(true);
            if (Account.getLoggedAccount().getCardCollection().getMainDeck().getItem() != null) {
                item_img.setImage(new Image(Account.getLoggedAccount().getCardCollection().getMainDeck().getItem().getAddressOfImage()));
                showDeckItem_lbl.setText("Item: " + Account.getLoggedAccount().getCardCollection().getMainDeck().getItem().getCardName());
            }
            scrollPane.setVisible(false);
            scrollPane_2.setVisible(true);

            scrollPane_2.setDisable(false);
            deck_hbox.setDisable(false);
        }
        if ((!isCollection && Toggle.isSelected()) || (isCollection && !Toggle.isSelected())) {
            if (cards == null) {
                return;
            }
            for (int i = 0; i < cards.size(); i++) {
                if (search.getText().length() == 0 || cards.get(i).getCardName().contains(search.getText())) {
                    VBox vBox = new VBox();
                    vBox.setPrefWidth(268);
                    CardView cardView = new CardView(cards.get(i));
                    cardViews.add(cardView);
                    vBox.getChildren().add(cardView);
                    hBox.getChildren().add(vBox);
                    hBox.setPrefWidth(hBox.getPrefWidth() + 268);
                }
            }
        }
    }


    private void makeCardListOfDeck(ArrayList<Card> cards) {
        makeCardListGeneralized(cards, deck_hbox, cardViewsOfDeck, search_txtf, false);
    }


    public void updateColor() {
        updateColorOfCardViewArray(cardViewsOfCollection);
        updateColorOfCardViewArray(cardViewsOfDeck);

    }

    public void handleItemImage() {
        if (Account.getLoggedAccount().getCardCollection().getMainDeck().getItem() != null) {
            Account.getLoggedAccount().getCardCollection().getCards().add(Account.getLoggedAccount().getCardCollection().getMainDeck().getItem());
            Account.getLoggedAccount().getCardCollection().getMainDeck().setItem(null);
            item_img.setImage(null);
            showDeckItem_lbl.setText("");
        }
    }

    public void updateColorOfCardViewArray(ArrayList<CardView> cardViews) {
        for (int i = 0; i < cardViews.size(); i++) {
            if (cardViews.get(i).getCard().equals(Account.getLoggedAccount().getCardCollection().getSelectedCard())) {
                cardViews.get(i).getCardController().changeToSelected();
            } else {
                cardViews.get(i).getCardController().changeToNotSelected();
            }
        }
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
            getLoggedAccount_lbl().setText(NO_USER_LOGINED);
            return;
        }
        getLoggedAccount_lbl().setText(Account.getLoggedAccount().getUsername());
    }


    public void stopTimeline() {
        slowTimeline.stop();
    }


    public void handleBackBtn() {
        Account.saveAccount();
        if (Container.scenes.size() > 0) {
            stopTimeline();
            Container.handleBack();

        }
    }

    public JFXButton getBack_btn() {
        return back_btn;
    }

    public Label getLoggedAccount_lbl() {
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

    public ArrayList<CardView> getCardViewsOfCollection() {
        return cardViewsOfCollection;
    }

    public void setCardViewsOfCollection(ArrayList<CardView> cardViewsOfCollection) {
        this.cardViewsOfCollection = cardViewsOfCollection;
    }

    public String getCreateDeckName() {
        return createDeckName;
    }

    public void setCreateDeckName(String createDeckName) {
        this.createDeckName = createDeckName;
    }

    public String getImportDeckName() {
        return importDeckName;
    }

    public void setImportDeckName(String importDeckName) {
        this.importDeckName = importDeckName;
    }
}
