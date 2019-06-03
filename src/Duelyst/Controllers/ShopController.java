package Duelyst.Controllers;

import Duelyst.View.ViewClasses.CardView;
import com.jfoenix.controls.JFXMasonryPane;
import com.jfoenix.controls.JFXNodesList;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;

public class ShopController {

    @FXML
    ScrollPane msn;
    @FXML
    HBox msnH;

    @FXML
    public void initialize()
    {
        for (int i =0;i<100;i++)
        {
            msnH.getChildren().add(new CardView());
        }
    }
}
