package Duelyst.Controllers;

import Duelyst.Client.SendMessage;
import Duelyst.Exceptions.MyException;
import Duelyst.Exceptions.NotValidDeckException;
import Duelyst.Model.*;
import Duelyst.Model.Battle.Battle;
import Duelyst.Model.Battle.BattleRecord;
import Duelyst.Model.Battle.BattleRecordEnum;
import Duelyst.Model.CommandClasses.*;
import Duelyst.Utility.ImageHolder;
import com.jfoenix.controls.*;
import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

import static Duelyst.View.Constants.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


import static Duelyst.View.Constants.NO_USER_LOGINED;

public class MainMenu {


    public Pane saveNotification_pane;
    public Pane battleHistory_pane;
    public ImageView battleHistoryClose_btn;
    public AnchorPane anchorPane;
    public ImageView battleHistoryButtonGlow_img;
    public AnchorPane battleHistoryAnchorePane_apane;
    public Label globalChat_lbl;
    public Pane leaderBoard_pane;
    public ImageView onlinePlayersCloseButton_img;
    public AnchorPane onlinePlayers_apane;
    public Pane onlinePlayers_pane;
    private int singleOrMulti = 0; //1 == Single , 2 == Multi
    private int storyModeLevel = 0; //1 == 1 , 2 == 2 , 3 ==3;
    private int multiplayerModeGoal = 0; //1 == hero , 2 == capture_flags , 3== hold_flag
    private Account selectedAccoutForMultiPlayer = null;
    public Pane chatRoom_pane;
    public ImageView chatRoomArrow_img;
    public JFXTextField textMessage;
    public ScrollPane chatRoom_Scroll;

    private ArrayList<ArrayList<BattleRecord>> allBattleRecords;
    @FXML
    Label loginedAccount_lbl;


    @FXML
    Label accountDarick_lbl;

    @FXML
    ImageView play_img;

    @FXML
    ImageView shop_img;

    @FXML
    ImageView collections_img;


    @FXML
    ImageView quit_img;


    @FXML
    ImageView logout_img;


    @FXML
    ImageView leaderbords_img;

    @FXML
    ImageView battleReplay_img;


    @FXML
    StackPane stackPane;

    @FXML
    ImageView cardCreator_img;

    @FXML
    ImageView save_img;

    @FXML
    ImageView cheat_img;

    @FXML
    TableView<AccountInfo> leaderboard_tbv;


    @FXML
    ImageView title_iv;
    @FXML
    ImageView sound_iv;

    @FXML
    Pane battleRecord_pane;

    @FXML
    ScrollPane battleRecord_scrollPane;

    @FXML
    VBox battleRecord_vbox;

    @FXML
    ImageView battleRecord_closeButton;

    private boolean canPlayButtonSound = true;
    private Timeline timeline = new Timeline();


    @FXML
    public void initialize() {
        runTimeline();
        Container.changeImageOfSoundImageView(sound_iv);
    }


    private void runTimeline() {
        timeline = new Timeline(new KeyFrame(Duration.ZERO, event -> {
            updateLoggedInUser();
            updateDarick();
        }), new KeyFrame(Duration.seconds(1)));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }


    public void handleActivateCheatMode() {
        setCanPlayButtonSound(true);
        runButtonClickSound();
        int result = Account.getLoggedAccount().toggleCheatMode();
        if (result == CHEAT_MODE_ACTIVATED) {
            Container.notificationShower(CHEAT_MODE_ACTIVE_CONTENT, CHEAT_MODE_ACTIVE_TITLE, stackPane);
        } else if (result == CHEAT_MODE_DEACTIVATED) {
            Container.notificationShower(CHEAT_MODE_DEACTIVATED_CONTENT, CHEAT_MODE_DEACTIVATED_TITLE, stackPane);
        }
        updateDarick();
    }

    public void runButtonHoverSound() {
        runSoundMediaPlayer(BUTTON_HOVER_SOUND);
    }

    public void runButtonClickSound() {
        runSoundMediaPlayer(CLICK_SOUND);
    }

    public void runSoundMediaPlayer(String address) {
        if (isCanPlayButtonSound()) {
            File file = new File(address);
            Media media = new Media(file.toURI().toString());
            Container.runMediaPlayer(Container.soundPlayer, media, 1, true, 1, SOUND_PLAYER);
            setCanPlayButtonSound(false);
        }
    }

    public void battleReplayButtonGlowWithSound() {
        runButtonHoverSound();
        battleReplayButtonGlow();
    }

    public void battleReplayButtonGlowDisapperWithSound() {
        battleReplayButtonGlowDisapper();
        setCanPlayButtonSound(true);
    }

    public void battleReplayButtonGlow() {

        setCanPlayButtonSound(false);
        battleReplay_img.setImage(ImageHolder.findImageInImageHolders("res/ui/button_secondary_glow@2x.png"));
    }

    public void battleReplayButtonGlowDisapper() {
        setCanPlayButtonSound(false);
        battleReplay_img.setImage(ImageHolder.findImageInImageHolders("res/ui/button_secondary@2x.png"));
    }

    public void playButtonGlowWithSound() {
        runButtonHoverSound();
        playButtonGlow();
    }

    public void playButtonGlowDisapearWithSound() {
        playButtonGlowDisapear();
        setCanPlayButtonSound(true);
    }

    public void playButtonGlow() {

        setCanPlayButtonSound(false);
        play_img.setImage(ImageHolder.findImageInImageHolders("res/ui/button_secondary_glow@2x.png"));
    }

    public void playButtonGlowDisapear() {
        setCanPlayButtonSound(false);
        play_img.setImage(ImageHolder.findImageInImageHolders("res/ui/button_secondary@2x.png"));
    }

    public void shopButtonGlowWithSound() {
        runButtonHoverSound();
        shopButtonGlow();
    }

    public void shopButtonGlowDisapearWithSound() {
        shopButtonGlowDisapear();
        setCanPlayButtonSound(true);
    }

    public void shopButtonGlow() {
        setCanPlayButtonSound(false);
        shop_img.setImage(ImageHolder.findImageInImageHolders("res/ui/button_secondary_glow@2x.png"));
    }

    public void shopButtonGlowDisapear() {
        setCanPlayButtonSound(false);
        shop_img.setImage(ImageHolder.findImageInImageHolders("res/ui/button_secondary@2x.png"));
    }

    public void collectionsButtonGlowWithSound() {
        runButtonHoverSound();
        collectionsButtonGlow();
    }
    //---------------

    public void collectionsButtonGlowDisapearWithSound() {
        collectionsButtonGlowDisapear();
        setCanPlayButtonSound(true);
    }

    public void collectionsButtonGlow() {
        setCanPlayButtonSound(false);
        collections_img.setImage(ImageHolder.findImageInImageHolders("res/ui/button_secondary_glow@2x.png"));
    }

    public void collectionsButtonGlowDisapear() {
        setCanPlayButtonSound(false);
        collections_img.setImage(ImageHolder.findImageInImageHolders("res/ui/button_secondary@2x.png"));
    }

    public void leaderBordsButtonGlowWithSound() {
        runButtonHoverSound();
        leaderBordsButtonGlow();
    }

    public void leaderBordsButtonGlowDisapearWithSound() {
        leaderBordsButtonGlowDisapear();
        setCanPlayButtonSound(true);
    }

    public void leaderBordsButtonGlow() {
        setCanPlayButtonSound(false);
        leaderbords_img.setImage(ImageHolder.findImageInImageHolders("res/ui/button_secondary_glow@2x.png"));
    }

    public void leaderBordsButtonGlowDisapear() {
        setCanPlayButtonSound(false);
        leaderbords_img.setImage(ImageHolder.findImageInImageHolders("res/ui/button_secondary@2x.png"));
    }


    public void cardCreatorButtonGlowWithSound() {
        runButtonHoverSound();
        cardCreatorButtonGlow();
    }

    public void cardCreatorButtonGlowDisapearWithSound() {
        cardCreatorButtonGlowDisapear();
        setCanPlayButtonSound(true);
    }


    public void cardCreatorButtonGlow() {
        setCanPlayButtonSound(false);
        cardCreator_img.setImage(ImageHolder.findImageInImageHolders("res/ui/button_secondary_glow@2x.png"));
    }

    public void cardCreatorButtonGlowDisapear() {
        setCanPlayButtonSound(false);
        cardCreator_img.setImage(ImageHolder.findImageInImageHolders("res/ui/button_secondary@2x.png"));
    }

    public void saveButtonGlowWithSound() {
        runButtonHoverSound();
        saveButtonGlow();
    }

    public void saveButtonGlowDisapearWithSound() {
        saveButtonGlowDisapear();
        setCanPlayButtonSound(true);
    }

    public void saveButtonGlow() {
        setCanPlayButtonSound(false);
        save_img.setImage(ImageHolder.findImageInImageHolders("res/ui/button_secondary_glow@2x.png"));
    }

    public void saveButtonGlowDisapear() {
        setCanPlayButtonSound(false);
        save_img.setImage(ImageHolder.findImageInImageHolders("res/ui/button_secondary@2x.png"));
    }

    public void quitButtonGlowWithSound() {
        runButtonHoverSound();
        quitButtonGlow();
    }

    public void quitButtonGlowDisapearWithSound() {
        quitButtonGlowDisapear();
        setCanPlayButtonSound(true);
    }

    public void quitButtonGlow() {
        setCanPlayButtonSound(false);
        quit_img.setImage(ImageHolder.findImageInImageHolders("res/ui/button_secondary_glow@2x.png"));
    }

    public void quitButtonGlowDisapear() {
        setCanPlayButtonSound(false);
        quit_img.setImage(ImageHolder.findImageInImageHolders("res/ui/button_secondary@2x.png"));
    }


    public void logoutButtonGlowWithSound() {
        runButtonHoverSound();
        logoutButtonGlow();
    }

    public void logoutButtonGlowDisapearWithSound() {
        logoutButtonGlowDisapear();
        setCanPlayButtonSound(true);
    }

    public void logoutButtonGlow() {
        setCanPlayButtonSound(false);
        logout_img.setImage(ImageHolder.findImageInImageHolders("res/ui/button_secondary_glow@2x.png"));

    }

    public void logoutButtonGlowDisapear() {
        setCanPlayButtonSound(false);
        logout_img.setImage(ImageHolder.findImageInImageHolders("res/ui/button_secondary@2x.png"));
    }

    public void cheatButtonGlowWithSound() {
        runButtonHoverSound();
        cheatButtonGlow();
    }

    public void cheatButtonGlowDisapearWithSound() {
        cheatButtonGlowDisapear();
        setCanPlayButtonSound(true);
    }

    public void cheatButtonGlow() {
        cheat_img.setImage(ImageHolder.findImageInImageHolders("res/ui/button_secondary_glow@2x.png"));
    }

    public void cheatButtonGlowDisapear() {
        cheat_img.setImage(ImageHolder.findImageInImageHolders("res/ui/button_secondary@2x.png"));
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
            getLoggedInAccount_lbl().setText(NO_USER_LOGINED);
            return;
        }
        getLoggedInAccount_lbl().setText(Account.getLoggedAccount().getUsername());
    }


    public void stopTimeline() {
        timeline.stop();
    }

    public void handleLeaderBoardBtn() {

        LeaderBoardCommand leaderBoardCommand = new LeaderBoardCommand();
        SendMessage.getSendMessage().sendMessage(leaderBoardCommand);
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        setCanPlayButtonSound(true);
        runButtonClickSound();
        TranslateTransition tt = new TranslateTransition(Duration.millis(1000), leaderBoard_pane);
        tt.setFromY(800);
        tt.setToY(0);
        tt.play();
        tt.setOnFinished(event -> anchorPane.setDisable(true));
    }

    public void handleLeaderBoardBackButton() {
        TranslateTransition tt = new TranslateTransition(Duration.millis(700), leaderBoard_pane);
        tt.setFromY(0);
        tt.setToY(800);
        tt.play();
        tt.setOnFinished(event -> anchorPane.setDisable(false));
    }

    public void initializeLeaderBoard(ArrayList<Account> accounts, ArrayList<String> onlineAccounts) {
        updateTable(accounts, onlineAccounts);
        title_iv.setImage(leaderboardsImg);
    }

    private void updateTable(ArrayList<Account> accounts, ArrayList<String> onlineAccounts) {
        ArrayList<AccountInfo> accountInfos = new ArrayList<>();
        int count = 0;
        for (int i = 0; i < accounts.size(); i++) {
            if (accounts.get(i) != null) {
                Account account = accounts.get(i);
                accountInfos.add(new AccountInfo(++count, account.getUsername(), account.getCountOfWins()));
            }
        }
        leaderboard_tbv.getColumns().clear();
        leaderboard_tbv.getItems().clear();

        TableColumn<AccountInfo, String> column1 = new TableColumn<>("Rank");
        column1.setCellValueFactory(new PropertyValueFactory<>("rank"));

        TableColumn<AccountInfo, String> column2 = new TableColumn<>("Username");
        column2.setCellValueFactory(new PropertyValueFactory<>("username"));

        TableColumn<AccountInfo, Integer> column3 = new TableColumn<>("Wins");
        column3.setCellValueFactory(new PropertyValueFactory<>("wins"));

        column1.setPrefWidth(100);
        column2.setPrefWidth(250);
        column3.setPrefWidth(100);


        column2.setCellFactory(e -> new TableCell<AccountInfo, String>() {
            @Override
            public void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);

                if (item == null || empty) {
                    setText(null);
                } else {
                    setText(item);

                    if (onlineAccounts.contains(accountInfos.get(getIndex()).getUsername())) {
                        this.setStyle("-fx-text-fill: YELLOW; -fx-font-weight: BOLD");
                    }
                }
            }
        });

        TableView tableView = leaderboard_tbv;

        tableView.getColumns().add(column1);
        tableView.getColumns().add(column2);
        tableView.getColumns().add(column3);

        for (int i = 0; i < accountInfos.size(); i++) {
            AccountInfo account = accountInfos.get(i);
            tableView.getItems().add(account);
        }


    }


    public void handleLogOutBtn() {
        setCanPlayButtonSound(true);
        runButtonClickSound();

        SendMessage.getSendMessage().sendMessage(new LoginCommand(LoginCommandsKind.LOGOUT));
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (Container.scenes.size() > 0) {
            stopTimeline(); //TODO In ba'ad az hazf slowTimeline hazf shavad
            //TODO TEST
            Container.handleBack();
            Shop.getInstance().getCards().removeAll(Account.getLoggedAccount().getCardCollection().getCustomCards());
        }

    }

    public void handleSave() {
        setCanPlayButtonSound(true);
        runButtonClickSound();
        Account.saveAccount();
        TranslateTransition tt = new TranslateTransition(Duration.millis(3000), saveNotification_pane);
        tt.setFromX(610);
        tt.setToX(400);
        tt.setAutoReverse(true);
        tt.setCycleCount(2);
        tt.play();
    }

    public void handleCardCreator() {
        setCanPlayButtonSound(true);
        runButtonClickSound();
        Pane root = null;
        FXMLLoader fxmlLoader = null;
        try {
            fxmlLoader = new FXMLLoader(getClass().getResource("../View/FXMLFiles/CardCreator.fxml"));
            root = fxmlLoader.load();
            int i = 0;
            System.out.println(i);
        } catch (IOException e) {
            e.printStackTrace();
        }
        stopTimeline();
        Container.addController(fxmlLoader);
        Container.runNextScene(root, CARD_CREATOR);
    }

    public void handlePlayBtn() {
        setCanPlayButtonSound(true);
        runButtonClickSound();
        setSingleOrMulti(0);
        setStoryModeLevel(0);
        setMultiplayerModeGoal(0);
        singleOrMultiPrompt();

    }

    public void gotoBattle(Account account, GameMode gameMode, GameGoal gameGoal) {
        Pane root = null;
        FXMLLoader fxmlLoader = null;
        try {
            fxmlLoader = new FXMLLoader(getClass().getResource("../View/FXMLFiles/Battle.fxml"));
            root = fxmlLoader.load();
            int i = 0;
            System.out.println(i);
        } catch (IOException e) {
            e.printStackTrace();
        }
        BattleController bc = fxmlLoader.getController();

        Battle battle = new Battle(Account.getLoggedAccount(), account, gameMode, gameGoal, bc);
        bc.setHandHbox();
        // bc.makeAccountNames();

        bc.runTimelines();
        battle.insertPlayerHeroes();
        stopTimeline();
        Container.addController(fxmlLoader);
        Container.runNextScene(root, BATTLE);
    }

    private void gotoBattle(Ai ai, GameMode gameMode, GameGoal gameGoal) {

        Pane root = null;
        FXMLLoader fxmlLoader = null;
        try {
            fxmlLoader = new FXMLLoader(getClass().getResource("../View/FXMLFiles/Battle.fxml"));
            root = fxmlLoader.load();
            int i = 0;
            System.out.println(i);
        } catch (IOException e) {
            e.printStackTrace();
        }
        BattleController bc = fxmlLoader.getController();


        Battle battle = new Battle(Account.getLoggedAccount(), ai, gameMode, gameGoal, bc);
        bc.setHandHbox();
        bc.runTimelines();
        battle.insertPlayerHeroes();
        stopTimeline();
        Container.addController(fxmlLoader);
        Container.runNextScene(root, BATTLE);

    }


    public void handleQuitBtn() {
        setCanPlayButtonSound(true);
        runButtonClickSound();
        SendMessage.getSendMessage().sendMessage(new LoginCommand(LoginCommandsKind.EXIT));
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.exit(0);
    }

    public void handleShopBtn() {
        setCardsOfShop();

        setCanPlayButtonSound(true);
        runButtonClickSound();

        Pane root = null;
        FXMLLoader fxmlLoader = null;
        try {
            fxmlLoader = new FXMLLoader(getClass().getResource("../View/FXMLFiles/Shop.fxml"));
            root = fxmlLoader.load();
            int i = 0;
            System.out.println(i);
        } catch (IOException e) {
            e.printStackTrace();
        }
        stopTimeline();
        Container.addController(fxmlLoader);
        Container.runNextScene(root, SHOP);

    }

    public void setCardsOfShop() {
        ShopCommand command = new ShopCommand(ShopCommandsKind.GET_CARDS);
        SendMessage.getSendMessage().sendMessage(command);
        try {
            Thread.sleep(200);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void handleCollectionBtn() {
        setCanPlayButtonSound(true);
        runButtonClickSound();
        Pane root = null;
        FXMLLoader fxmlLoader = null;
        try {
            fxmlLoader = new FXMLLoader(getClass().getResource("../View/FXMLFiles/CardCollection.fxml"));
            root = fxmlLoader.load();
            int i = 0;
            System.out.println(i);
        } catch (IOException e) {
            e.printStackTrace();
        }
        stopTimeline();
        Container.addController(fxmlLoader);
        Container.runNextScene(root, COLLECTION);

    }

    public Label getLoggedInAccount_lbl() {
        return loginedAccount_lbl;
    }

    public Label getAccountDarick_lbl() {
        return accountDarick_lbl;
    }

    public Timeline getTimeline() {
        return timeline;
    }

    public int getSingleOrMulti() {
        return singleOrMulti;
    }

    public void setSingleOrMulti(int singleOrMulti) {
        this.singleOrMulti = singleOrMulti;
    }

    public int getStoryModeLevel() {
        return storyModeLevel;
    }

    public void setStoryModeLevel(int storyModeLevel) {
        this.storyModeLevel = storyModeLevel;
    }

    public int getMultiplayerModeGoal() {
        return multiplayerModeGoal;
    }

    public void setMultiplayerModeGoal(int multiplayerModeGoal) {
        this.multiplayerModeGoal = multiplayerModeGoal;
    }

    private void multiGoalSelection() {
        Pane root = null;
        FXMLLoader fxmlLoader = null;
        try {
            fxmlLoader = new FXMLLoader(getClass().getResource("../View/FXMLFiles/MultiPlayer.fxml"));
            root = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Container.addController(fxmlLoader);
        Container.runNextScene(root, MULTI_PLAYER);
    }

//    private Account chooseYourOpponent() {
//        final Account[] selectedAccount = {null};
//        JFXDialogLayout jfxDialogLayout = new JFXDialogLayout();
//        jfxDialogLayout.setHeading(new Text(CHOOSE_OPPONENT_TITLE));
//        jfxDialogLayout.setBody(new Text(CHOOSE_OPPNENT_BODY));
//        JFXButton cancel = new JFXButton();
//        cancel.setText(CANCEL);
//        cancel.setStyle(DEFAULT_BUTTON_CSS);
//
//
//        ArrayList<JFXButton> jfxButtons = new ArrayList<>();
//        jfxButtons.add(cancel);
//        for (Account account :
//                Account.getAccounts()) {
//            if (account.getUsername().equals(Account.getLoggedAccount().getUsername()))
//                continue;
//            if (account.getCardCollection() != null && account.getCardCollection().getMainDeck() != null && Deck.validateDeck(account.getCardCollection().getMainDeck())) {
//                JFXButton jfxButton = new JFXButton(account.getUsername());
//                jfxButton.setStyle(DEFAULT_BUTTON_CSS);
//                jfxButtons.add(jfxButton);
//            }
//        }
//
//        jfxDialogLayout.setActions(jfxButtons);
//        for (JFXButton j :
//                jfxButtons) {
//            j.setOnAction(event -> {
//                for (Account a :
//                        Account.getAccounts()) {
//                    if (a.getUsername().equals(j.getText())) {
//                        selectedAccount[0] = a;
//                        selectedAccoutForMultiPlayer = a;
//                        System.out.println(selectedAccount[0].getUsername());
//                    }
//                }
//            });
//        }
//
//        JFXDialog jfxDialog = new JFXDialog(stackPane, jfxDialogLayout, JFXDialog.DialogTransition.CENTER);
//        cancel.setOnAction(event -> jfxDialog.close());
//
//        jfxDialog.show();
//        return selectedAccount[0];
//    }

    private void singleModeSelection() {
        JFXDialogLayout jfxDialogLayout = new JFXDialogLayout();
        jfxDialogLayout.setHeading(new Text(GAME_SINGLE_PLAYER_TITLE));
        jfxDialogLayout.setBody(new Text(CHOOSE_SINGLE_PLAYER_MODE));
        JFXButton cancel = new JFXButton();
        JFXButton customMode = new JFXButton();
        JFXButton storyMode = new JFXButton();

        jfxDialogLayout.setActions(cancel, customMode, storyMode);

        cancel.setText(CANCEL);
        customMode.setText(CUSTOM_MODE);
        storyMode.setText(STORY_MODE);

        cancel.setStyle(DEFAULT_BUTTON_CSS);
        customMode.setStyle(MODE_SELECTION_BUTTON_CSS);
        storyMode.setStyle(MODE_SELECTION_BUTTON_CSS);

        JFXDialog jfxDialog = new JFXDialog(stackPane, jfxDialogLayout, JFXDialog.DialogTransition.CENTER);

        customMode.setOnAction(event -> {
            setSingleOrMulti(SINGLEPLAYER_INT);
            System.out.println(singleOrMulti);

            jfxDialog.close();

            customModeSelection();
        });

        storyMode.setOnAction(event -> {
            setSingleOrMulti(MULTIPLAYER_INT);
            System.out.println(singleOrMulti);
            jfxDialog.close();

            storyModeSelection();
        });

        cancel.setOnAction(event -> jfxDialog.close());

        jfxDialog.show();
    }

    private void customModeSelection() {
        JFXDialogLayout jfxDialogLayout = new JFXDialogLayout();
        jfxDialogLayout.setHeading(new Text(GAME_CUSTOM_TITLE));
        jfxDialogLayout.setBody(new Text(CHOOSE_DECK));
        JFXButton cancel = new JFXButton();
        cancel.setText(CANCEL);
        cancel.setStyle(DEFAULT_BUTTON_CSS);

        ArrayList<JFXButton> jfxButtons = new ArrayList<>();
        jfxButtons.add(cancel);
        for (Deck d :
                Account.getLoggedAccount().getCardCollection().getDecks()) {
            d.setValid(Deck.validateDeck(d));
            if (d.isValid()) {
                JFXButton jfxButton = new JFXButton(d.getDeckName());
                jfxButton.setStyle(DEFAULT_BUTTON_CSS);
                jfxButtons.add(jfxButton);
            }
        }
        JFXDialog jfxDialog = new JFXDialog(stackPane, jfxDialogLayout, JFXDialog.DialogTransition.CENTER);
        jfxDialogLayout.setActions(jfxButtons);
        for (JFXButton j :
                jfxButtons) {
            j.setOnAction(event -> {
                Ai ai = new Ai(0);
                Deck deck = null;
                for (Deck d :
                        Account.getLoggedAccount().getCardCollection().getDecks()) {
                    if (d.getDeckName().equals(j.getText())) {
                        deck = d;
                    }
                }
                Deck aiDeck = Deck.deepClone(deck);
                aiDeck.changeIDOfAllCardsOfDeck("AI:)");
                ai.setMainDeck(aiDeck);
                customGameMode(ai);


                jfxDialog.close();
            });
        }

        cancel.setOnAction(event -> jfxDialog.close());

        jfxDialog.show();
    }

    public int customGameMode(Ai ai) {
        JFXDialogLayout jfxDialogLayout = new JFXDialogLayout();
        jfxDialogLayout.setHeading(new Text("choose game mode"));
        jfxDialogLayout.setBody(new Text("please select game mode of custom game"));
        JFXButton cancel = new JFXButton();
        JFXButton story_level_1 = new JFXButton();
        JFXButton story_level_2 = new JFXButton();
        JFXButton story_level_3 = new JFXButton();

        cancel.setText(CANCEL);
        story_level_1.setText("Kill Hero");
        story_level_2.setText("Collect Flag");
        story_level_3.setText("Hold Flag");
        jfxDialogLayout.setActions(cancel, story_level_1, story_level_2, story_level_3);
        cancel.setStyle(DEFAULT_BUTTON_CSS);
        story_level_2.setStyle(MODE_SELECTION_BUTTON_CSS);
        story_level_1.setStyle(MODE_SELECTION_BUTTON_CSS);
        story_level_3.setStyle(MODE_SELECTION_BUTTON_CSS);

        JFXDialog jfxDialog = new JFXDialog(stackPane, jfxDialogLayout, JFXDialog.DialogTransition.CENTER);
        int[] mode = new int[1];
        story_level_1.setOnAction(event -> {
            mode[0] = 1;
            createBattle(ai, GameMode.SINGLE_PLAYER, GameGoal.KILL_HERO);
            jfxDialog.close();

        });

        story_level_2.setOnAction(event -> {
            mode[0] = 2;
            createBattle(ai, GameMode.SINGLE_PLAYER, GameGoal.COLLECT_FLAG);
            jfxDialog.close();
        });
        story_level_3.setOnAction(event -> {
            mode[0] = 3;
            createBattle(ai, GameMode.SINGLE_PLAYER, GameGoal.HOLD_FLAG);
            jfxDialog.close();
        });
        cancel.setOnAction(event -> jfxDialog.close());


        jfxDialog.show();
        return mode[0];
    }

    private void storyModeSelection() {
        JFXDialogLayout jfxDialogLayout = new JFXDialogLayout();
        jfxDialogLayout.setHeading(new Text(CHOOSE_GAME_LEVEL));
        jfxDialogLayout.setBody(new Text(CHOOSE_GAME_LEVEL));
        JFXButton cancel = new JFXButton();
        JFXButton story_level_1 = new JFXButton();
        JFXButton story_level_2 = new JFXButton();
        JFXButton story_level_3 = new JFXButton();

        cancel.setText(CANCEL);
        story_level_1.setText(STORY_LEVEL_1);
        story_level_2.setText(STORY_LEVEL_2);
        story_level_2.setStyle(MODE_SELECTION_BUTTON_CSS);
        story_level_3.setText(STORY_LEVEL_3);
        jfxDialogLayout.setActions(cancel, story_level_1, story_level_2, story_level_3);
        cancel.setStyle(DEFAULT_BUTTON_CSS);
        story_level_1.setStyle(MODE_SELECTION_BUTTON_CSS);
        story_level_3.setStyle(MODE_SELECTION_BUTTON_CSS);

        JFXDialog jfxDialog = new JFXDialog(stackPane, jfxDialogLayout, JFXDialog.DialogTransition.CENTER);

        story_level_1.setOnAction(event -> {
            setStoryModeLevel(LEVEL_1);
            Ai ai = new Ai(0);
            Deck aiDeck = Deck.deepClone(Account.getLoggedAccount().getCardCollection().getMainDeck());
            aiDeck.changeIDOfAllCardsOfDeck("AI:)");
            ai.setMainDeck(aiDeck);
//            ai.setMainDeck(Deck.deepClone(Account.findAccountInArrayList("saman", Account.getAccounts()).getCardCollection().getMainDeck()));
            createBattle(ai, GameMode.SINGLE_PLAYER, GameGoal.COLLECT_FLAG);
            jfxDialog.close();

        });

        story_level_2.setOnAction(event -> {
            setStoryModeLevel(LEVEL_2);
            Ai ai = new Ai(0);
            Deck aiDeck = Deck.deepClone(Account.getLoggedAccount().getCardCollection().getMainDeck());
            aiDeck.changeIDOfAllCardsOfDeck("AI:)");
            ai.setMainDeck(aiDeck);//            ai.setMainDeck(Deck.deepClone(Account.findAccountInArrayList("saman", Account.getAccounts()).getCardCollection().getMainDeck()));
            createBattle(ai, GameMode.SINGLE_PLAYER, GameGoal.KILL_HERO);
            jfxDialog.close();
        });
        story_level_3.setOnAction(event -> {
            setStoryModeLevel(LEVEL_3);
            Ai ai = new Ai(0);
            Deck aiDeck = Deck.deepClone(Account.getLoggedAccount().getCardCollection().getMainDeck());
            aiDeck.changeIDOfAllCardsOfDeck("AI:)");
            ai.setMainDeck(aiDeck);
//            ai.setMainDeck(Deck.deepClone(Account.findAccountInArrayList("saman", Account.getAccounts()).getCardCollection().getMainDeck()));
            createBattle(ai, GameMode.SINGLE_PLAYER, GameGoal.HOLD_FLAG);
            jfxDialog.close();
        });
        cancel.setOnAction(event -> jfxDialog.close());


        jfxDialog.show();
    }

    public void singleOrMultiPrompt(int a) {
        //TODO Bejaye DialogLayout Pane Gozashte Shavad
    }


    public void singleOrMultiPrompt() {
        JFXDialogLayout jfxDialogLayout = new JFXDialogLayout();
        jfxDialogLayout.setHeading(new Text(GAME_MODE_TITLE));
        jfxDialogLayout.setBody(new Text(CHOOSE_GAME_MODE));
        JFXButton cancel = new JFXButton();
        JFXButton singlePlayer = new JFXButton();
        JFXButton multiplayer = new JFXButton();

        jfxDialogLayout.setActions(cancel, singlePlayer, multiplayer);
        cancel.setText(CANCEL);
        singlePlayer.setText(SINGLEPLAYER_MODE);
        multiplayer.setText(MULTIPLAYER_MODE);
        cancel.setStyle(DEFAULT_BUTTON_CSS);
        singlePlayer.setStyle(MODE_SELECTION_BUTTON_CSS);
        multiplayer.setStyle(MODE_SELECTION_BUTTON_CSS);

        JFXDialog jfxDialog = new JFXDialog(stackPane, jfxDialogLayout, JFXDialog.DialogTransition.CENTER);

        singlePlayer.setOnAction(event -> {
            setSingleOrMulti(SINGLEPLAYER_INT);
            System.out.println(singleOrMulti);

            jfxDialog.close();

            singleModeSelection();
        });

        multiplayer.setOnAction(event -> {
            setSingleOrMulti(MULTIPLAYER_INT);
            System.out.println(singleOrMulti);
            jfxDialog.close();

            multiGoalSelection();
        });

        cancel.setOnAction(event -> jfxDialog.close());

        jfxDialog.show();
    }

    private void createBattle(Account account, GameMode gameMode, GameGoal gameGoal) {
        try {
            checkDeckAtFirst(Account.getLoggedAccount(), account);
        }catch (Exception e){
            System.out.println("deck is in valid");
            //TODO send notification
        }
        if (account instanceof Ai) {
            gotoBattle((Ai) account, gameMode, gameGoal);
        } else {
            gotoBattle(account, gameMode, gameGoal);
        }
    }

    private void checkDeckAtFirst(Account firstPlayer, Account secondPlayer) throws MyException {
        if (firstPlayer.getCardCollection().getMainDeck() == null || Deck.validateDeck(firstPlayer.getCardCollection().getMainDeck())) {
            throw new NotValidDeckException();
        }
        if (secondPlayer.getCardCollection().getMainDeck() == null || Deck.validateDeck(secondPlayer.getCardCollection().getMainDeck())) {
            throw new NotValidDeckException();
        }
    }

    public void battleHistoryButtonGlow() {
        battleHistoryButtonGlow_img.setOpacity(1);
    }

    public void battleHistoryButtonGlowDisappear() {
        battleHistoryButtonGlow_img.setOpacity(0);
    }

    public void handleBattleHistoryButton() {

        TranslateTransition tt = new TranslateTransition(Duration.millis(1), battleHistory_pane);
        tt.setFromY(-650);
        tt.setToY(0);
        tt.play();

        FadeTransition ft = new FadeTransition(Duration.millis(1000), battleHistory_pane);
        ft.setFromValue(0);
        ft.setToValue(1);
        anchorPane.setOpacity(0.5);
        anchorPane.setDisable(true);
        ft.play();
        showBattleHistory();
    }

    public void handleBattleRecordButton() {
        TranslateTransition tt = new TranslateTransition(Duration.millis(1), battleRecord_pane);
        tt.setFromY(-650);
        tt.setToY(0);
        tt.play();

        FadeTransition ft = new FadeTransition(Duration.millis(1000), battleRecord_pane);
        ft.setFromValue(0);
        ft.setToValue(1);
        anchorPane.setOpacity(0.5);
        anchorPane.setDisable(true);
        ft.play();

        allBattleRecords = BattleRecord.loadAllBattleRecords();
        showBattleRecord();
    }


    public void showBattleRecord() {
        battleRecord_vbox.getChildren().clear();
        for (int i = 0; i < allBattleRecords.size(); i++) {
            Label label = new Label();
            if (allBattleRecords.get(i) != null) {
                BattleRecord battleRecord = allBattleRecords.get(i).get(0);
                if (battleRecord.getTypeOfRecord().equals(BattleRecordEnum.INITIALIZE)) {
                    label.setText(i + " " + battleRecord.getFirstPlayerUsername() + " " + battleRecord.getSecondPlayerUsername());
                } else {
                    label.setText(i + " battle Record");
                }
                label.setStyle("-fx-font-size: 20;-fx-font-weight: bold;");
                label.setAlignment(Pos.CENTER);
                label.setTextAlignment(TextAlignment.CENTER);
                label.setPrefHeight(50);
//                label.setLayoutY(100 * i);
//                label.setLayoutX(80);
                battleRecord_vbox.getChildren().add(label);
                label.setOnMouseClicked(event -> {

                    String s = label.getText();
                    String[] s1 = s.split(" ");
                    final int i1 = Integer.parseInt(s1[0]);
                    ArrayList<BattleRecord> battleRecords = allBattleRecords.get(i1);
                    gotoBattleReplay(battleRecords);


                });
            }
        }
    }

    public void gotoBattleReplay(ArrayList<BattleRecord> battleRecord) {
        Pane root = null;
        FXMLLoader fxmlLoader = null;
        try {
            fxmlLoader = new FXMLLoader(getClass().getResource("../View/FXMLFiles/BattleReplay.fxml"));
            root = fxmlLoader.load();
            int i = 0;
            System.out.println(i);
        } catch (IOException e) {
            e.printStackTrace();
        }
        BattleReplayController bc = fxmlLoader.getController();
        bc.battleRecords = battleRecord;
        bc.runTimelines();

        Container.addController(fxmlLoader);
        Container.runNextScene(root, BATTLE);
    }

    public void handleBattleHistoryCloseButton() {
        FadeTransition ft = new FadeTransition(Duration.millis(1000), battleHistory_pane);
        ft.setFromValue(1);
        ft.setToValue(0);
        ft.setOnFinished(event -> {
            anchorPane.setOpacity(1);
            anchorPane.setDisable(false);
            battleHistory_pane.setOpacity(0);
            TranslateTransition tt = new TranslateTransition(Duration.millis(1), battleHistory_pane);
            tt.setFromY(0);
            tt.setToY(-650);
            tt.play();
        });
        ft.play();
    }

    public void handleBattleRecordCloseButton() {
        FadeTransition ft = new FadeTransition(Duration.millis(1000), battleRecord_pane);
        ft.setFromValue(1);
        ft.setToValue(0);
        ft.setOnFinished(event -> {
            anchorPane.setOpacity(1);
            anchorPane.setDisable(false);
            battleRecord_pane.setOpacity(0);
            TranslateTransition tt = new TranslateTransition(Duration.millis(1), battleRecord_pane);
            tt.setFromY(0);
            tt.setToY(-650);
            tt.play();
        });
        ft.play();
    }

    private void showBattleHistory() {
        ArrayList<String> history = Account.getLoggedAccount().getBattleHistory();
        for (int i = 0; i < history.size(); i++) {
            Label label = new Label();
            label.setText((i + 1) + ") " + history.get(i));
            label.setStyle("-fx-font-size: 20; -fx-font-style: italic;-fx-font-weight: bold");
            battleHistoryAnchorePane_apane.getChildren().add(label);
            label.setLayoutY(100 * i);
            label.setLayoutX(80);
        }
    }

    public void handleOnlinePlayersButton() {

        SendMessage.getSendMessage().sendMessage(new OnlinePlayersCommand());
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        TranslateTransition tt = new TranslateTransition(Duration.millis(10), onlinePlayers_pane);
        tt.setFromX(800);
        tt.setToX(0);
        tt.play();
        FadeTransition ft = new FadeTransition(Duration.millis(1000), onlinePlayers_pane);
        ft.setFromValue(0);
        ft.setToValue(0.8);
        tt.setOnFinished(event -> {
            anchorPane.setDisable(true);
            ft.play();
        });
    }

    public void handleOnlinePlayerCloseButton() {
        FadeTransition ft = new FadeTransition(Duration.millis(700), onlinePlayers_pane);
        ft.setFromValue(0.8);
        ft.setToValue(0);
        TranslateTransition tt = new TranslateTransition(Duration.millis(10), onlinePlayers_pane);
        tt.setFromX(0);
        tt.setToX(800);
        ft.play();
        ft.setOnFinished(event -> {
            anchorPane.setDisable(false);
            tt.play();
        });
    }

    public void showOnlinePlayers(ArrayList<String> onlinePlayers) {

        for (int i = 0; i < onlinePlayers.size(); i++) {
            Label label = new Label((i + 1) + ") " + onlinePlayers.get(i));
            label.setTextFill(Color.WHITE);
            label.setStyle("-fx-font-style: italic;-fx-font-weight: bold;-fx-font-size: 20");
            onlinePlayers_apane.getChildren().add(label);
            label.setLayoutX(20);
            label.setLayoutY(75 * i);
        }

    }

    public void handleSoundMuteOrUnmute() {
        Container.changeSoundOnOrOff();
        Container.changeImageOfSoundImageView(sound_iv);

    }

    public boolean isCanPlayButtonSound() {
        return canPlayButtonSound;
    }

    public void setCanPlayButtonSound(boolean canPlayButtonSound) {
        this.canPlayButtonSound = canPlayButtonSound;
    }
}