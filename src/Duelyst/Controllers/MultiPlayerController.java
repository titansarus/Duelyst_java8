package Duelyst.Controllers;

import Duelyst.Client.SendMessage;
import Duelyst.Model.Account;
import Duelyst.Model.CommandClasses.BattleCommand;
import Duelyst.Model.CommandClasses.ChatRoomCommand;
import Duelyst.Model.GameGoal;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Collections;

public class MultiPlayerController {

    private GameGoal gameGoal;
    public ImageView KillHero_img;
    public ImageView CollectFlag_img;
    public ImageView HoldFlag_img;
    public ImageView cancelBattle_img;
    public ImageView bottomPlate_img;
    public ImageView topPlate_img;
    public ImageView centerCircle_img;
    public Pane searching_pane;
    public AnchorPane anchorPane;
    public ImageView chatRoomArrow_img;
    public JFXTextField textMessage;
    public Label globalChat_lbl;
    public Pane chatRoom_pane;
    public ScrollPane chatRoom_Scroll;
    public ImageView tv_img;
    public Pane runningBattles_pane;
    Timeline slowTimeline;


    @FXML
    public void initialize() {
        chatRoomArrow_img.setOnMouseClicked(event1 -> handleChatRoomArrowImageClicked());
        chatRoomArrow_img.setOnMouseEntered(event12 -> handleChatRoomArrowImageMouseEntered());
        chatRoomArrow_img.setOnMouseExited(event13 -> handleChatRoomArrowImageMouseExited());
        runSlowTimeline();
    }

    public void runSlowTimeline() {
        slowTimeline = new Timeline(new KeyFrame(Duration.ZERO, event -> {

        }), new KeyFrame(Duration.millis(500)));
        slowTimeline.setCycleCount(Animation.INDEFINITE);
        slowTimeline.play();
    }

    public void handleChatRoomArrowImageMouseEntered() {
        TranslateTransition tt1 = new TranslateTransition(Duration.millis(500), chatRoomArrow_img);
        tt1.setFromX(chatRoomArrow_img.getTranslateX());
        tt1.setToX(chatRoomArrow_img.getTranslateX() + 30);
        TranslateTransition tt2 = new TranslateTransition(Duration.millis(500), globalChat_lbl);
        tt2.setFromX(chatRoomArrow_img.getTranslateX());
        tt2.setToX(chatRoomArrow_img.getTranslateX() + 30);
        tt1.play();
        tt2.play();
    }

    public void handleChatRoomArrowImageMouseExited() {
        TranslateTransition tt1 = new TranslateTransition(Duration.millis(500), chatRoomArrow_img);
        tt1.setFromX(chatRoomArrow_img.getTranslateX());
        tt1.setToX(chatRoomArrow_img.getX());
        TranslateTransition tt2 = new TranslateTransition(Duration.millis(500), globalChat_lbl);
        tt2.setFromX(chatRoomArrow_img.getTranslateX());
        tt2.setToX(chatRoomArrow_img.getX());
        tt1.play();
        tt2.play();
    }

    public void handleChatRoomArrowImageClicked() {
        SendMessage.getSendMessage().sendMessage(new ChatRoomCommand(null, null));
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("ChatRoom");
        TranslateTransition tt = new TranslateTransition(Duration.millis(1000), chatRoom_pane);
        tt.setFromX(-695);
        tt.setToX(-329);
        tt.play();
        chatRoomArrow_img.setOnMouseClicked(null);
        chatRoomArrow_img.setOnMouseEntered(null);
        chatRoomArrow_img.setOnMouseExited(null);
        tt.setOnFinished(event -> chatRoomArrow_img.setOnMouseClicked(event1 -> handleBackImage()));
    }

    public void handleBackImage() {
        TranslateTransition tt = new TranslateTransition(Duration.millis(1000), chatRoom_pane);
        tt.setFromX(-329);
        tt.setToX(-695);
        tt.play();
        tt.setOnFinished(event -> {
            handleChatRoomArrowImageMouseExited();
            chatRoomArrow_img.setOnMouseEntered(event22 -> handleChatRoomArrowImageMouseEntered());
            chatRoomArrow_img.setOnMouseExited(event2 -> handleChatRoomArrowImageMouseExited());
            chatRoomArrow_img.setOnMouseClicked(event1 -> handleChatRoomArrowImageClicked());
        });
    }

    public void handleSendMessageImage() {
        ChatRoomCommand command = new ChatRoomCommand(textMessage.getText(), Account.getLoggedAccount().getUsername());
        textMessage.clear();
        SendMessage.getSendMessage().sendMessage(command);
    }

    public void addToChat(ChatRoomCommand chatRoomCommand) {
        ArrayList<ChatRoomCommand> chatRoomCommands = chatRoomCommand.getChatRoomCommands();
        Group group = new Group();
        int i = 1;
        for (ChatRoomCommand command :
                chatRoomCommands) {
            Text text = new Text(command.getPmOwner() + " : " + command.getPm());
            text.setFill(Color.WHITE);
            if (command.getPmOwner().equals(Account.getLoggedAccount().getUsername()))
                text.setFill(Color.GOLD);
            text.setStyle("-fx-font-style: italic");
            text.setEffect(new DropShadow(10, 0, 0, Color.GREY));
            System.out.println(command.getPmOwner() + " : " + command.getPm());
            text.setWrappingWidth(150);
            text.relocate(0, (i++) * 50);
            System.out.println("------------>>>>> " + i);
            group.getChildren().add(text);
        }
        chatRoom_Scroll.setContent(group);
    }


    private void searchingPaneAnimation() {
        anchorPane.setDisable(true);
        FadeTransition ft1 = new FadeTransition(Duration.millis(500), topPlate_img);
        ft1.setFromValue(0);
        ft1.setToValue(1);
        FadeTransition ft2 = new FadeTransition(Duration.millis(500), bottomPlate_img);
        ft2.setFromValue(0);
        ft2.setToValue(1);
        FadeTransition ft3 = new FadeTransition(Duration.millis(500), centerCircle_img);
        ft3.setFromValue(0);
        ft3.setToValue(1);
        ft1.play();
        ft2.play();
        ft3.play();

        TranslateTransition tt1 = new TranslateTransition(Duration.millis(1000), topPlate_img);
        tt1.setFromY(-15);
        tt1.setToY(-180);
        TranslateTransition tt2 = new TranslateTransition(Duration.millis(1000), bottomPlate_img);
        tt2.setFromY(15);
        tt2.setToY(180);

        FadeTransition ft4 = new FadeTransition(Duration.millis(500), searching_pane);
        ft4.setFromValue(0);
        ft4.setToValue(1);
        tt1.setOnFinished(event -> ft4.play());


        ft1.setOnFinished(event -> centerCircleAnimation(event1 -> {
            tt1.play();
            tt2.play();
        }));
        ft4.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                BattleCommand battleCommand = new BattleCommand();
                battleCommand.start(gameGoal, Account.getLoggedAccount());
                SendMessage.getSendMessage().sendMessage(battleCommand);
            }
        });
    }

    private void centerCircleAnimation(EventHandler eventHandler) {
        DropShadow dropShadow = new DropShadow(10, Color.GOLD);
        dropShadow.setBlurType(BlurType.THREE_PASS_BOX);
        centerCircle_img.setEffect(dropShadow);
        KeyValue keyValue = new KeyValue(dropShadow.widthProperty(), 255);
        KeyFrame keyFrame = new KeyFrame(Duration.millis(1000), keyValue);
        FadeTransition ft = new FadeTransition(Duration.millis(500), centerCircle_img);
        ft.setFromValue(1);
        ft.setToValue(0);
        ft.setOnFinished(eventHandler);
        Timeline timeline = new Timeline(keyFrame);
        timeline.play();
        timeline.setOnFinished(event -> ft.play());

    }

    public void handleCancelBattleButton() {
        cancelAnimation();

        //TODO Cancel Searching For Battle

    }

    private void cancelAnimation() {
        FadeTransition ft1 = new FadeTransition(Duration.millis(500), searching_pane);
        ft1.setFromValue(1);
        ft1.setToValue(0);

        TranslateTransition tt1 = new TranslateTransition(Duration.millis(1000), topPlate_img);
        tt1.setFromY(-180);
        tt1.setToY(-15);
        TranslateTransition tt2 = new TranslateTransition(Duration.millis(1000), bottomPlate_img);
        tt2.setFromY(180);
        tt2.setToY(15);
        ft1.setOnFinished(event -> {
            tt1.play();
            tt2.play();
        });

        FadeTransition ft2 = new FadeTransition(Duration.millis(500), centerCircle_img);
        ft2.setToValue(1);
        ft2.setFromValue(0);

        tt1.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ft2.play();
            }
        });

        FadeTransition ft3 = new FadeTransition(Duration.millis(500), centerCircle_img);
        ft3.setFromValue(1);
        ft3.setToValue(0);

        FadeTransition ft4 = new FadeTransition(Duration.millis(500), topPlate_img);
        ft4.setFromValue(1);
        ft4.setToValue(0);

        FadeTransition ft5 = new FadeTransition(Duration.millis(500), bottomPlate_img);
        ft5.setFromValue(1);
        ft5.setToValue(0);

        ft2.setOnFinished(event -> {
            ft3.play();
            ft4.play();
            ft5.play();
        });
        ft3.setOnFinished(event -> {
            anchorPane.setDisable(false);
            centerCircle_img.setEffect(null);
        });
        ft1.play();
    }

    public void handleKillHeroButton() {
        gameGoal = GameGoal.KILL_HERO;
        searchingPaneAnimation();

    }

    public void handleCollectFlagButton() {
        gameGoal = GameGoal.COLLECT_FLAG;
        searchingPaneAnimation();
    }

    public void handleHoldFlagButton() {
        gameGoal = GameGoal.HOLD_FLAG;
        searchingPaneAnimation();

    }

    public void tvImageGlow(){
        InnerShadow innerShadow = new InnerShadow(10,Color.GOLD);
        innerShadow.setWidth(40);
        innerShadow.setHeight(40);
        tv_img.setEffect(innerShadow);
    }

    public void tvImageGlowDisappear(){
        tv_img.setEffect(null);
    }

    public void handleTvImage(){


        anchorPane.setDisable(true);
        TranslateTransition tt = new TranslateTransition(Duration.millis(1000),runningBattles_pane);
        tt.setFromY(-600);
        tt.setToY(0);
        tt.play();
    }

    public void handleTvCancelButton(){
        TranslateTransition tt = new TranslateTransition(Duration.millis(700),runningBattles_pane);
        tt.setFromY(runningBattles_pane.getTranslateY());
        tt.setToY(-600);
        tt.setOnFinished(event -> anchorPane.setDisable(false));
        tt.play();
    }


    public void killHeroButtonGlow() {
        KillHero_img.setImage(new Image("res/ui/button_icon_left_glow.png"));
    }

    public void killHeroButtonGlowDisappear() {
        KillHero_img.setImage(new Image("res/ui/button_icon_left.png"));
    }

    public void collectFlagButtonGlow() {
        CollectFlag_img.setImage(new Image("res/ui/button_icon_middle_glow.png"));
    }

    public void collectFlagButtonGlowDisappear() {
        CollectFlag_img.setImage(new Image("res/ui/button_icon_middle.png"));
    }

    public void holdFlagButtonGlow() {
        HoldFlag_img.setImage(new Image("res/ui/button_icon_right_glow.png"));
    }

    public void holdFlagButtonGlowDisappear() {
        HoldFlag_img.setImage(new Image("res/ui/button_icon_right.png"));
    }

    public void battleCancelButtonGlow() {
        cancelBattle_img.setImage(new Image("res/ui/button_cancel_glow@2x.png"));
    }

    public void battleCancelButtonGlowDisappear() {
        cancelBattle_img.setImage(new Image("res/ui/button_cancel@2x.png"));
    }


    public void handleBackButton() {
        Container.handleBack();
    }


}
