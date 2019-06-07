package Duelyst.View.ViewClasses;

import Duelyst.Controllers.CardController;
import Duelyst.Controllers.CardForBattleController;
import Duelyst.Model.Battle.Battle;
import Duelyst.Model.Card;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Callback;

import java.io.IOException;

import static Duelyst.View.Constants.*;

public class CardForBattle extends Pane {
   public CardForBattleController cardController;
   public Node view;
   public Card card;


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


        int i =0;
    }

    public CardForBattle(Card card)
    {
        this();
        this.card = card;
        cardController.setManaCostLblText(card.getManaCost()+"");
        if (card.getAddressOfImage().length() > 0) {
            cardController.setImageOfCard( new Image(card.getAddressOfImage()));
            //System.out.println(card.getAddressOfImage());
        } else {
            cardController.setImageOfCard(heroImg);//TODO LATER WILL BE DELETED
        }
    }

    public CardForBattleController getCardController() {
        return cardController;
    }

    public void setCardController(CardForBattleController cardController) {
        this.cardController = cardController;
    }

    public Node getView() {
        return view;
    }

    public void setView(Node view) {
        this.view = view;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }


}
