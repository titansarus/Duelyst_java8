package Duelyst.Controllers;

import Duelyst.Model.Battle.Battle;
import Duelyst.View.ViewClasses.CardForBattle;
import javafx.fxml.FXML;
import javafx.scene.layout.HBox;

import java.util.ArrayList;

import static Duelyst.View.Constants.*;

public class BattleController {
    @FXML
    HBox hand_hBox;
    ArrayList<CardForBattle> hand = new ArrayList<>();

    Battle battle;


    public void setHandHbox()
    {
        for (int i =0;i<SIZE_OF_HAND;i++)
        {
            hand.add(new CardForBattle());
            hand_hBox.getChildren().add(hand.get(i));
        }

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
