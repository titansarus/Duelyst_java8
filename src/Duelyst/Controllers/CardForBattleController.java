package Duelyst.Controllers;

import Duelyst.Model.Card;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;


public class CardForBattleController {

    @FXML
    Label manaCost_lbl;
    @FXML
    ImageView cardImage_iv;
    @FXML
    AnchorPane anchorPane;


    public void setCardImage_iv(ImageView cardImage_iv) {
        this.cardImage_iv = cardImage_iv;
    }
}
