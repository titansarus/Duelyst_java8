package Duelyst.View.ViewClasses;

import Duelyst.Controllers.CardController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.util.Callback;

import java.io.IOException;

import static Duelyst.View.Constants.heroImg;

public class CardView extends Pane {
    CardController cardController;
    Node view;

    public CardView() {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../FXMLFiles/Card.fxml"));
        fxmlLoader.setControllerFactory(new Callback<Class<?>, Object>() {
            @Override
            public Object call(Class<?> param) {
                return cardController = new CardController();
            }
        });
        try {
            view = (Node) fxmlLoader.load();

        } catch (IOException ex) {
        }
        getChildren().add(view);
        cardController.setNameAndDsc("Card", "This is A card", heroImg);
    }

}
