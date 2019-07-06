package Duelyst.Controllers;

import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.util.Collections;

public class MultiPlayerController {


    public ImageView KillHero_img;
    public ImageView CollectFlag_img;
    public ImageView HoldFlag_img;
    public ImageView cancelBattle_img;
    public ImageView bottomPlate_img;
    public ImageView topPlate_img;
    public ImageView centerCircle_img;
    public Pane searching_pane;
    public AnchorPane anchorPane;
    Timeline slowTimeline;


    @FXML
    public void initialize() {
        runSlowTimeline();
    }

    public void runSlowTimeline() {
        slowTimeline = new Timeline(new KeyFrame(Duration.ZERO, event -> {

        }), new KeyFrame(Duration.millis(500)));
        slowTimeline.setCycleCount(Animation.INDEFINITE);
        slowTimeline.play();
    }


    private void searchingPaneAnimation() {
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
        ft4.setOnFinished(event -> anchorPane.setDisable(true));
        tt1.setOnFinished(event -> ft4.play());

        ft1.setOnFinished(event -> centerCircleAnimation(event1 -> {
            tt1.play();
            tt2.play();
        }));
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
        searchingPaneAnimation();
        //TODO Kill Hero Mode
    }

    public void handleCollectFlagButton() {
        searchingPaneAnimation();
        //TODO Collect Flag Mode
    }

    public void handleHoldFlagButton() {
        searchingPaneAnimation();
        //TODO Hold Flag Mode
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
