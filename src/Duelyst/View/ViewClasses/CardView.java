package Duelyst.View.ViewClasses;

import Duelyst.Controllers.CardController;
import Duelyst.Controllers.Container;
import Duelyst.Model.*;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.input.*;
import javafx.scene.layout.Pane;
import javafx.util.Callback;

import java.io.IOException;

import static Duelyst.View.Constants.*;

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
                    if (Shop.getInstance().getShopMode().equals(ShopMode.BUY)) {
                        Shop.getInstance().selectCardForBuy(card.getCardName());
                        System.out.println("BUY MODE" + card.getCardName());
                    } else {
                        Shop.getInstance().selectCardForSell(card.getCardId());
                        System.out.println("SELL MODE" + card.getCardName() + " " + card.getCardId());
                    }
                } else if (Container.nameOfMenus.getLast().equals(COLLECTION)) {
                    Account.getLoggedAccount().getCardCollection().setSelectedCard(card);
                }
            }
        });

//        if (card.getAddressOfImage()!=null) {
//            cardController.setNameAndDscAndImg("Card", "This is A card", new Image(card.getAddressOfImage()));
//        }
//        else

        //    cardController.setNameAndDsc("Card", "This is A card");
    }


    public CardView(String name, String desc) {
        this();
        cardController.setNameAndDsc(name, desc);


    }

    public CardView(Card card) {
        this();
        if (card != null) {
            cardController.setNameAndDsc(card.getCardName(), card.getCardDescription());
            this.card = card;

            if (card.getAddressOfImage().length() > 0) {
                cardController.setNameAndDscAndImg(card.getCardName(), card.getCardDescription(), new Image(card.getAddressOfImage()));
                //System.out.println(card.getAddressOfImage());
            } else {
                cardController.setNameAndDscAndImg(card.getCardName(), card.getCardDescription(), heroImg);//TODO LATER WILL BE DELETED
            }
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
