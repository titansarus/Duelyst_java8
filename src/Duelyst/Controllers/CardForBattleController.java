package Duelyst.Controllers;

import Duelyst.Model.Battle.Battle;
import Duelyst.Model.Card;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;


public class CardForBattleController {

    @FXML
    Label manaCost_lbl;
    @FXML
    ImageView cardImage_iv;
    @FXML
    AnchorPane anchorPane;

    Battle battle;




    public void setCardImage_iv(ImageView cardImage_iv) {
        this.cardImage_iv = cardImage_iv;
    }

    public void setManaCostLblText(String manaCostLblText)
    {
        getManaCost_lbl().setText(manaCostLblText);
    }

    public ImageView getCardImage_iv() {
        return cardImage_iv;
    }

    public Label getManaCost_lbl() {
        return manaCost_lbl;
    }

    public void setImageOfCard(Image imageOfCard)
    {
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
}
