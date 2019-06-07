package Duelyst.View.ViewClasses;

import Duelyst.Controllers.CardController;
import Duelyst.Controllers.CardForBattleController;
import Duelyst.Model.Card;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Callback;

import java.io.IOException;

import static Duelyst.View.Constants.*;

public class CardForBattle extends Pane {
    CardForBattleController cardController;
    Node view;
    Card card;

    public CardForBattle() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../FXMLFiles/CardForBattle.fxml"));
        fxmlLoader.setControllerFactory(new Callback<Class<?>, Object>() {
            @Override
            public Object call(Class<?> param) {
                return cardController = new CardForBattleController();
            }
        });
        try {
            view = (Node) fxmlLoader.load();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        getChildren().add(view);

        cardController.setCardImage_iv(new ImageView(heroImg));
    }
}
