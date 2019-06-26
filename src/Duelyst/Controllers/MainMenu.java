package Duelyst.Controllers;

import Duelyst.Exceptions.NotValidDeckException;
import Duelyst.Model.*;
import Duelyst.Model.Battle.Battle;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.util.Duration;

import static Duelyst.View.Constants.*;

import java.io.IOException;
import java.util.ArrayList;


import static Duelyst.View.Constants.NO_USER_LOGINED;

public class MainMenu {


    public Pane saveNotification_pane;
    int singleOrMulti = 0; //1 == Single , 2 == Multi
    int storyModeLevel = 0; //1 == 1 , 2 == 2 , 3 ==3;
    int multiplayerModeGoal = 0; //1 == hero , 2 == capture_flags , 3== hold_flag
    Account selectedAccoutForMultiPlayer = null;

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
    StackPane stackPane;

    @FXML
    ImageView cardCreator_img;

    @FXML
    ImageView save_img;

    Timeline timeline = new Timeline();

    @FXML
    public void initialize() {
        runTimeline();
    }

    public void runTimeline() {
        timeline = new Timeline(new KeyFrame(Duration.ZERO, event -> {
            updateLoggedInUser();
            updateDarick();
        }), new KeyFrame(Duration.seconds(1)));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    public void playButtonGlow() {
        play_img.setImage(new Image("res/ui/button_secondary_glow@2x.png"));
    }

    public void playButtonGlowDisapear() {
        play_img.setImage(new Image("res/ui/button_secondary@2x.png"));
    }

    public void shopButtonGlow() {
        shop_img.setImage(new Image("res/ui/button_secondary_glow@2x.png"));
    }

    public void shopButtonGlowDisapear() {
        shop_img.setImage(new Image("res/ui/button_secondary@2x.png"));
    }

    public void collectionsButtonGlow() {
        collections_img.setImage(new Image("res/ui/button_secondary_glow@2x.png"));
    }

    public void collectionsButtonGlowDisapear() {
        collections_img.setImage(new Image("res/ui/button_secondary@2x.png"));
    }

    public void leaderBordsButtonGlow() {
        leaderbords_img.setImage(new Image("res/ui/button_secondary_glow@2x.png"));
    }

    public void leaderBordsButtonGlowDisapear() {
        leaderbords_img.setImage(new Image("res/ui/button_secondary@2x.png"));
    }

    public void cardCreatorButtonGlow() {
        cardCreator_img.setImage(new Image("res/ui/button_secondary_glow@2x.png"));
    }

    public void cardCreatorButtonGlowDisapear() {
        cardCreator_img.setImage(new Image("res/ui/button_secondary@2x.png"));
    }

    public void saveButtonGlow() {
        save_img.setImage(new Image("res/ui/button_secondary_glow@2x.png"));
    }

    public void saveButtonGlowDisapear() {
        save_img.setImage(new Image("res/ui/button_secondary@2x.png"));
    }

    public void quitButtonGlow() {
        quit_img.setImage(new Image("res/ui/button_secondary_glow@2x.png"));
    }

    public void quitButtonGlowDisapear() {
        quit_img.setImage(new Image("res/ui/button_secondary@2x.png"));
    }

    public void logoutButtonGlow() {
        logout_img.setImage(new Image("res/ui/button_secondary_glow@2x.png"));

    }

    public void logoutButtonGlowDisapear() {
        logout_img.setImage(new Image("res/ui/button_secondary@2x.png"));
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
        timeline.stop();
        ;
    }

    public void handleLeaderboardBtn() {
        Pane root = null;
        FXMLLoader fxmlLoader = null;
        try {
            fxmlLoader = new FXMLLoader(getClass().getResource("../View/FXMLFiles/Leaderboards.fxml"));
            root = fxmlLoader.load();
            int i = 0;
            System.out.println(i);
        } catch (IOException e) {
            e.printStackTrace();
        }
        stopTimeline();
        Container.runNextScene(root, LEADERBOARD);
    }


    public void handleBackBtn() {
        if (Container.scenes.size() > 0) {
            stopTimeline(); //TODO In ba'ad az hazf slowTimeline hazf shavad
            //TODO TEST
            Container.handleBack();
            Shop.getInstance().getCards().removeAll(Account.getLoggedAccount().getCardCollection().getCustomCards());
        }

    }

    public void handleSave() {
        Account.saveAccount();
        TranslateTransition tt = new TranslateTransition(Duration.millis(3000), saveNotification_pane);
        tt.setFromX(610);
        tt.setToX(400);
        tt.setAutoReverse(true);
        tt.setCycleCount(2);
        tt.play();
    }

    public void handleCardCreator() {
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
        Container.runNextScene(root, CARD_CREATOR);
    }

    public void handlePlayBtn() {
        setSingleOrMulti(0);
        setStoryModeLevel(0);
        setMultiplayerModeGoal(0);
        singleOrMutliPrompt();

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
        bc.makeAccountNames();
        bc.runTimelines();
        bc.insertPlayerHeroes();
        stopTimeline();
        Container.runNextScene(root, BATTLE);
    }

    public void gotoBattle(Ai ai, GameMode gameMode, GameGoal gameGoal) {

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
        bc.makeAccountNames();
        bc.runTimelines();
        ai.setBattleController(bc);
        bc.insertPlayerHeroes();
        stopTimeline();
        Container.runNextScene(root, BATTLE);

    }


    public void handleQuitBtn() {
        System.exit(0);
    }

    public void handleShopBtn() {

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
        Container.runNextScene(root, SHOP);

    }

    public void handleCollectionBtn() {
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
        Container.runNextScene(root, COLLECTION);

    }

    public Label getLoginedAccount_lbl() {
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


    public void multiGoalSelection() {

        JFXDialogLayout jfxDialogLayout = new JFXDialogLayout();
        jfxDialogLayout.setHeading(new Text(CHOOSE_GAME_MODE));
        jfxDialogLayout.setBody(new Text(CHOOSE_GAME_MODE));
        JFXButton cancel = new JFXButton();
        JFXButton killHero = new JFXButton();
        JFXButton captureFlag = new JFXButton();
        JFXButton holdFlag = new JFXButton();

        cancel.setText(CANCEL);
        killHero.setText(KILL_HERO);
        captureFlag.setText(CAPTURE_FLAG);
        holdFlag.setText(HOLD_FLAG);
        captureFlag.setStyle(MODE_SELECTION_BUTTON_CSS);
        cancel.setStyle(DEFAULT_BUTTON_CSS);
        jfxDialogLayout.setActions(cancel, killHero, captureFlag, holdFlag);
        killHero.setStyle(MODE_SELECTION_BUTTON_CSS);
        holdFlag.setStyle(MODE_SELECTION_BUTTON_CSS);

        JFXDialog jfxDialog = new JFXDialog(stackPane, jfxDialogLayout, JFXDialog.DialogTransition.CENTER);

        killHero.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                setMultiplayerModeGoal(KILL_HERO_INT);
                System.out.println(getMultiplayerModeGoal());
                Account account = chooseYourOpponent();
                System.out.println(selectedAccoutForMultiPlayer.getUsername());
                if (selectedAccoutForMultiPlayer != null) {
                    System.out.println(account.getUsername() + "<<--------------------------------------------");
                    createBattle(account, GameMode.MULTI_PLAYER, GameGoal.KILL_HERO);
                }
                jfxDialog.close();

            }
        });

        captureFlag.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                setMultiplayerModeGoal(CAPTURE_FLAG_INT);
                System.out.println(getMultiplayerModeGoal());
                Account account = chooseYourOpponent();
                if (selectedAccoutForMultiPlayer != null) {
                    createBattle(account, GameMode.MULTI_PLAYER, GameGoal.COLLECT_FLAG);
                }
                jfxDialog.close();


            }
        });
        holdFlag.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                setMultiplayerModeGoal(HOLD_FLAG_INT);
                System.out.println(getMultiplayerModeGoal());
                Account account = chooseYourOpponent();
                if (selectedAccoutForMultiPlayer != null) {
                    createBattle(account, GameMode.MULTI_PLAYER, GameGoal.HOLD_FLAG);
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

    public Account chooseYourOpponent() {
        final Account[] selectedAccount = {null};
        JFXDialogLayout jfxDialogLayout = new JFXDialogLayout();
        jfxDialogLayout.setHeading(new Text(CHOOSE_OPPONENT_TITLE));
        jfxDialogLayout.setBody(new Text(CHOOSE_OPPNENT_BODY));
        JFXButton cancel = new JFXButton();
        cancel.setText(CANCEL);
        cancel.setStyle(DEFAULT_BUTTON_CSS);


        ArrayList<JFXButton> jfxButtons = new ArrayList<>();
        jfxButtons.add(cancel);
        for (Account account :
                Account.getAccounts()) {
            if (account.getUsername().equals(Account.getLoggedAccount().getUsername()))
                continue;
            if (account.getCardCollection() != null && account.getCardCollection().getMainDeck() != null && Deck.validateDeck(account.getCardCollection().getMainDeck())) {
                JFXButton jfxButton = new JFXButton(account.getUsername());
                jfxButton.setStyle(DEFAULT_BUTTON_CSS);
                jfxButtons.add(jfxButton);
            }
        }

        jfxDialogLayout.setActions(jfxButtons);
        for (JFXButton j :
                jfxButtons) {
            j.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    for (Account a :
                            Account.getAccounts()) {
                        if (a.getUsername().equals(j.getText())) {
                            selectedAccount[0] = a;
                            selectedAccoutForMultiPlayer = a;
                            System.out.println(selectedAccount[0].getUsername());
                        }
                    }
                }
            });
        }

        JFXDialog jfxDialog = new JFXDialog(stackPane, jfxDialogLayout, JFXDialog.DialogTransition.CENTER);
        cancel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                jfxDialog.close();
            }
        });

        jfxDialog.show();
        return selectedAccount[0];
    }

    public void singleModeSelection() {
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

        customMode.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                setSingleOrMulti(SINGLEPLAYER_INT);
                System.out.println(singleOrMulti);

                jfxDialog.close();

                customModeSelection();
            }
        });

        storyMode.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                setSingleOrMulti(MULTIPLAYER_INT);
                System.out.println(singleOrMulti);
                jfxDialog.close();

                storyModeSelection();
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

    public void customModeSelection() {
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
            j.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    Ai ai = new Ai(0);
                    Deck deck = null;
                    for (Deck d :
                            Account.getLoggedAccount().getCardCollection().getDecks()) {
                        if (d.getDeckName().equals(j.getText())) {
                            deck = d;
                        }
                    }
                    Deck aiDeck = Deck.deepClone(deck);
                    ai.setMainDeck(aiDeck);
                    createBattle(ai, GameMode.SINGLE_PLAYER, GameGoal.KILL_HERO);
                    jfxDialog.close();
                }
            });
        }

        cancel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                jfxDialog.close();
            }
        });

        jfxDialog.show();
    }

    public void storyModeSelection() {
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

        story_level_1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                setStoryModeLevel(LEVEL_1);
                Ai ai = new Ai(0);
                ai.setMainDeck(Deck.deepClone(Account.findAccountInArrayList("saman",Account.getAccounts()).getCardCollection().getMainDeck()));
                createBattle(ai, GameMode.SINGLE_PLAYER, GameGoal.COLLECT_FLAG);
                jfxDialog.close();

            }
        });

        story_level_2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                setStoryModeLevel(LEVEL_2);
                Ai ai = new Ai(0);
                ai.setMainDeck(Deck.deepClone(Account.findAccountInArrayList("saman",Account.getAccounts()).getCardCollection().getMainDeck()));
                createBattle(ai, GameMode.SINGLE_PLAYER, GameGoal.KILL_HERO);
                jfxDialog.close();
            }
        });
        story_level_3.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                setStoryModeLevel(LEVEL_3);
                Ai ai = new Ai(0);


                ai.setMainDeck(Deck.deepClone(Account.findAccountInArrayList("saman",Account.getAccounts()).getCardCollection().getMainDeck()));
                createBattle(ai, GameMode.SINGLE_PLAYER, GameGoal.HOLD_FLAG);
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


    public void singleOrMutliPrompt() {
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

        singlePlayer.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                setSingleOrMulti(SINGLEPLAYER_INT);
                System.out.println(singleOrMulti);

                jfxDialog.close();

                singleModeSelection();
            }
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
        checkDeckAtFirst(Account.getLoggedAccount(), account);

        if (account instanceof Ai) {
            gotoBattle((Ai) account, gameMode, gameGoal);
        } else {
            gotoBattle(account, gameMode, gameGoal);
        }
    }

    private void checkDeckAtFirst(Account firstPlayer, Account secondPlayer) {
        if (firstPlayer.getCardCollection().getMainDeck() == null) {
            throw new NotValidDeckException();
        }
        if (secondPlayer.getCardCollection().getMainDeck() == null) {
            throw new NotValidDeckException();
        }
    }

}
