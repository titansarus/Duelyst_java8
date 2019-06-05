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
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Duration;


import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static Duelyst.View.Constants.*;

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

    @FXML
    JFXTextField deckSearch_txtf;

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
    private String imporetDeckName;

    ArrayList<CardView> cardViewsOfCollection = new ArrayList<>();
    ArrayList<CardView> cardViewsOfDeck = new ArrayList<>();

    private Timeline slowTimeline;
    Timeline fastTimeline;


    @FXML
    public void initialize() {
        back_btn.setGraphic(new ImageView(backImg));
        createDeck_btn.setGraphic(new ImageView(createDeckImg));


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
        setImporetDeckName("");
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
                setImporetDeckName(textField.getText());
                if (getImporetDeckName().length() > 0) {
                    String adderss = "./saved/Accounts/" + Account.getLoggedAccount().getUsername() + "/Decks/" + getImporetDeckName() + ".json";
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
            updateLoginedUser();
            updateDarick();
            updateMainDeck();
            updateColor();
            makeCardListOfCollection(Account.getLoggedAccount().getCardCollection().getCards());
            if (Account.getLoggedAccount().getCardCollection().getMainDeck() != null) {
                makeCardListOfDeck(Account.getLoggedAccount().getCardCollection().getMainDeck().getCards());
            } else {
                makeCardListOfDeck(null);
            }
        }), new KeyFrame(Duration.millis(500)));
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
        makeCardListGeneralized(cards, cardCollectionCards_HB, cardViewsOfCollection, search_txtf);
    }

    private void makeCardListGeneralized(ArrayList<Card> cards, HBox hBox, ArrayList<CardView> cardViews, JFXTextField search) {
        hBox.getChildren().clear();
        hBox.setPrefWidth(629);
        cardViews.clear();
        if (cards == null) {
            return;
        }
        for (int i = 0; i < cards.size(); i++) {
            if (search.getText().length() == 0 || cards.get(i).getCardName().contains(search.getText())) {
                VBox vBox = new VBox();
                vBox.setPrefWidth(275);
                CardView cardView = new CardView(cards.get(i));
                cardViews.add(cardView);
                vBox.getChildren().add(cardView);
                hBox.getChildren().add(vBox);
                hBox.setPrefWidth(hBox.getPrefWidth() + 275);
            }
        }
    }


    private void makeCardListOfDeck(ArrayList<Card> cards) {
        makeCardListGeneralized(cards, deck_hbox, cardViewsOfDeck, deckSearch_txtf);
    }


    public void updateColor() {
        updateColorOfCardViewArray(cardViewsOfCollection);
        updateColorOfCardViewArray(cardViewsOfDeck);

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

    private void updateLoginedUser() {
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

    public String getImporetDeckName() {
        return imporetDeckName;
    }

    public void setImporetDeckName(String imporetDeckName) {
        this.imporetDeckName = imporetDeckName;
    }
}
