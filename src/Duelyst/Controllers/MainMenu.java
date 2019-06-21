package Duelyst.Controllers;

import Duelyst.Model.Account;
import Duelyst.Model.Battle.Battle;
import Duelyst.Model.Shop;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.util.Duration;

import static Duelyst.View.Constants.*;

import java.io.IOException;

import static Duelyst.View.Constants.NO_USER_LOGINED;

public class MainMenu {

    int singleOrMulti = 0; //1 == Single , 2 == Multi
    int storyModeLevel = 0; //1 == 1 , 2 == 2 , 3 ==3;
    int multiplayerModeGoal = 0; //1 == hero , 2 == capture_flags , 3== hold_flag

    @FXML
    Label loginedAccount_lbl;

    @FXML
    Label accountDarick_lbl;

    @FXML
    JFXButton play_btn;

    @FXML
    JFXButton leaderboard_btn;

    @FXML
    JFXButton quit_btn;


    @FXML
    JFXButton shop_btn;


    @FXML
    JFXButton collection_btn;


    @FXML
    JFXButton back_btn;


    @FXML
    StackPane stackPane;

    @FXML
    JFXButton cardCreator_btn;


    Timeline timeline = new Timeline();

    @FXML
    public void initialize() {
        runTimeline();


//        play_btn.setGraphic(new ImageView(playImg));
//        shop_btn.setGraphic(new ImageView(shopImg));
//        quit_btn.setGraphic(new ImageView(quitImg));
//        collection_btn.setGraphic(new ImageView(collectionImg));
//        leaderboard_btn.setGraphic(new ImageView(leaderboardsImg));
//        back_btn.setGraphic(new ImageView(backImg));

    }

    public void runTimeline() {
        timeline = new Timeline(new KeyFrame(Duration.ZERO, event -> {
            updateLoginedUser();
            updateDarick();
        }), new KeyFrame(Duration.seconds(1)));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
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
    public void handleCardCreator()
    {
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

    public void gotoBattle(Battle battle) {
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
        bc.setBattle(battle);
        bc.setHandHbox();
        bc.makeAccountNames();
        bc.makeGrids();
        bc.runTimelines();
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

                jfxDialog.close();

            }
        });

        captureFlag.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                setMultiplayerModeGoal(CAPTURE_FLAG_INT);
                System.out.println(getMultiplayerModeGoal());
                jfxDialog.close();
            }
        });
        holdFlag.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                setMultiplayerModeGoal(HOLD_FLAG_INT);
                System.out.println(getMultiplayerModeGoal());
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

    public void singleModeSelection() {
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
                Battle battle = new Battle(Account.getLoggedAccount(), Account.getLoggedAccount());
                gotoBattle(battle);
                jfxDialog.close();

            }
        });

        story_level_2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                setStoryModeLevel(LEVEL_2);
                jfxDialog.close();
            }
        });
        story_level_3.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                setStoryModeLevel(LEVEL_3);
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

        multiplayer.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                setSingleOrMulti(MULTIPLAYER_INT);
                System.out.println(singleOrMulti);
                jfxDialog.close();

                multiGoalSelection();
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

}
