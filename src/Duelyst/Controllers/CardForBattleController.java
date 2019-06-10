package Duelyst.Controllers;

import Duelyst.Model.Battle.Battle;
import Duelyst.Model.Card;
import Duelyst.View.ViewClasses.CardForBattle;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.util.ArrayList;


public class CardForBattleController {

    @FXML
    Label manaCost_lbl;
    @FXML
    ImageView cardImage_iv;
    @FXML
    AnchorPane anchorPane;
    @FXML
    ImageView cardSelection_iv;

    Battle battle;


    public void setCardImage_iv(ImageView cardImage_iv) {
        this.cardImage_iv = cardImage_iv;
    }

    public ImageView getCardSelection_iv() {
        return cardSelection_iv;
    }

    public void setCardSelection_iv(ImageView cardSelection_iv) {
        this.cardSelection_iv = cardSelection_iv;
    }

    public void setImageOfCardSelection(Image imageOfCardSelection) {
        getCardSelection_iv().setImage(imageOfCardSelection);
    }

    public void setManaCostLblText(String manaCostLblText) {
        getManaCost_lbl().setText(manaCostLblText);
    }

    public ImageView getCardImage_iv() {
        return cardImage_iv;
    }

    public Label getManaCost_lbl() {
        return manaCost_lbl;
    }

    public void setImageOfCard(Image imageOfCard) {
        cardImage_iv.setImage(imageOfCard);
    }

    public void setManaCost_lbl(Label manaCost_lbl) {
        this.manaCost_lbl = manaCost_lbl;
    }

    public Battle getBattle() {
        return battle;
    }

    public void setBattle(Battle battle) {
        this.battle = battle;
    }

    public static CardForBattle findCardForBattleWithCard(ArrayList<CardForBattle> cardForBattles , Card card)
    {
        if (cardForBattles!=null && card!=null) {
            for (int i =0;i<cardForBattles.size();i++)
            {
                if (cardForBattles.get(i)!=null && cardForBattles.get(i).getCard()!=null && cardForBattles.get(i).getCard().equals(card))
                {
                    return cardForBattles.get(i);
                }
            }
        }
        return null;
    }
}
