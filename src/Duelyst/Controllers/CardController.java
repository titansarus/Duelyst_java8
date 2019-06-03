package Duelyst.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class CardController {
    @FXML
    private Label name_lbl;

    @FXML
    private Label dsc_lbl;

    @FXML
    private ImageView card_img;

    @FXML
    VBox vboxHolder;


    public void setNameAndDsc(String name, String dsc) {
        name_lbl.setText(name);
        dsc_lbl.setText(dsc);
    }
    public void setNameAndDscAndImg(String name, String dsc, Image img) {
        name_lbl.setText(name);
        dsc_lbl.setText(dsc);
        card_img.setImage(img);
    }

    public void setName_lbl(Label name_lbl) {
        this.name_lbl = name_lbl;
    }

    public void setDsc_lbl(Label dsc_lbl) {
        this.dsc_lbl = dsc_lbl;
    }

    public void setCard_img(ImageView card_img) {
        this.card_img = card_img;
    }

    public void changeToSelected()
    {
        vboxHolder.setStyle("-fx-background-color: Yellow");
    }

    public void changeToNotSelected()
    {
        vboxHolder.setStyle("-fx-background-color: LightGrey");
    }

}
