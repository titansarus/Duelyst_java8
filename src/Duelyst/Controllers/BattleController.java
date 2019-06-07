package Duelyst.Controllers;

import Duelyst.Model.Battle.Battle;
import Duelyst.Model.Battle.Player;
import Duelyst.View.ViewClasses.CardForBattle;
import com.jfoenix.controls.JFXButton;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;

import java.sql.Time;
import java.util.ArrayList;

import static Duelyst.View.Constants.*;

public class BattleController {
    @FXML
    HBox hand_hBox;
    @FXML
    Label p1Acc_lbl;
    @FXML
    Label p2Acc_lbl;
    @FXML
    HBox p2Mana_hb;
    @FXML
    HBox p1Mana_hb;
    @FXML
    JFXButton end_turn_btn;


    ArrayList<CardForBattle> hand = new ArrayList<>();

    @FXML
            public void initialize()
    {
//        BackgroundImage backgroundImage = new BackgroundImage(endTurnBtn , BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT , BackgroundSize.DEFAULT);
//        end_turn_btn.setBackground(new Background( backgroundImage));
    }

    Battle battle;
    Timeline timeline = new Timeline();

    public void runTimeline() {
        timeline = new Timeline(new KeyFrame(Duration.ZERO, event -> {
            hand.get(0).getView();
            updateMana();
        }), new KeyFrame(Duration.seconds(1)));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    public void updateMana() {
        updateManaOfPlayer(p1Mana_hb, battle.getPlayer1());
        updateManaOfPlayer(p2Mana_hb, battle.getPlayer2());

    }

    public void updateManaOfPlayer(HBox hbox, Player player) {
        hbox.getChildren().clear();
        if (player.equals(battle.getPlayingPlayer())) {
            for (int i = 0; i < player.getMana(); i++) {
                ImageView imageView = new ImageView(manaIconSml);
                hbox.getChildren().add(imageView);
            }
        }
        else
        {
            ImageView imageView = new ImageView(manaInActiveSml);
            hbox.getChildren().add(imageView);
        }
        Label label = new Label();
        String text = player.getMana() + "/" + battle.calculateMaxAmountOfMana();
        label.setText(text);
        label.setFont(Font.font("System", FontWeight.BOLD, 20));
        label.setStyle("-fx-text-fill: RED");
        hbox.getChildren().add(label);
    }

    public void setHandHbox() {
        for (int i = 0; i < SIZE_OF_HAND; i++) {
            CardForBattle cardForBattle = new CardForBattle(battle.getPlayingPlayer().getHand().get(i));
            cardForBattle.cardController.setBattle(battle);
            hand.add(cardForBattle);
            hand_hBox.getChildren().add(hand.get(i));
        }
    }

    public void makeAccountNames() {
        p1Acc_lbl.setText(this.getBattle().getPlayer1().getAccount().getUsername());
        p2Acc_lbl.setText(this.getBattle().getPlayer2().getAccount().getUsername());

    }

    public void handleEndTurnBtn()
    {
        battle.nextTurn();
    }

    public HBox getHand_hBox() {
        return hand_hBox;
    }

    public void setHand_hBox(HBox hand_hBox) {
        this.hand_hBox = hand_hBox;
    }

    public Battle getBattle() {
        return battle;
    }

    public void setBattle(Battle battle) {
        this.battle = battle;
    }
}
