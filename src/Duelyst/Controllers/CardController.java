package Duelyst.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Polygon;

public class CardController {
    public Polygon manaCost_polygon;
    @FXML
    private Label name_lbl;

    @FXML
    private Label dsc_lbl;

    @FXML
    private ImageView card_img;

    @FXML
    AnchorPane anchorPaneHolder;
    public void setNameAndDsc(String name, String dsc) {
        name_lbl.setText(name);
        dsc_lbl.setText(dsc);
        manaCost_polygon.setFill(new ImagePattern(new Image("/res/CardUI/manaCost.png")));
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
        this.card_img.setCache(true);
    }

    public void changeToSelected() {
        anchorPaneHolder.setEffect(null);
        anchorPaneHolder.setEffect(new DropShadow(30, Color.GOLD));
    }

    public void changeToNotSelected() {
        anchorPaneHolder.setEffect(null);
        anchorPaneHolder.setEffect(new DropShadow(30, Color.BLACK));
    }

}
