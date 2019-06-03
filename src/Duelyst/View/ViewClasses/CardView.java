package Duelyst.View.ViewClasses;

import Duelyst.Controllers.CardController;
import Duelyst.Controllers.Container;
import Duelyst.Model.Card;
import Duelyst.Model.Shop;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.util.Callback;

import java.io.IOException;

import static Duelyst.View.Constants.SHOP;
import static Duelyst.View.Constants.heroImg;

public class CardView extends Pane {
    CardController cardController;
    Node view;
    Card card;

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
            ex.printStackTrace();
        }
        getChildren().add(view);


        this.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (Container.nameOfMenus.getLast().equals(SHOP)) {
                    Shop.getInstance().selectCardForBuy(card.getCardName());
                    System.out.println(card.getCardName());
                }
            }
        });



        cardController.setNameAndDscAndImg("Card", "This is A card", heroImg);
    }

    public CardView(String name, String desc) {
        this();
        cardController.setNameAndDsc(name, desc);


    }

    public CardView(Card card) {
        this();
        if (card!=null) {
            cardController.setNameAndDsc(card.getCardName(), card.getCardDescription());
            this.card =card;
        }
        }

    public CardController getCardController() {
        return cardController;
    }

    public Node getView() {
        return view;
    }

    public Card getCard() {
        return card;
    }


}
