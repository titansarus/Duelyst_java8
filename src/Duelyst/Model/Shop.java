package Duelyst.Model;


import Duelyst.Exceptions.NoCardSelectedInShopException;
import Duelyst.Exceptions.NotEnoughDarickException;
import Duelyst.Model.Items.Item;

import java.util.ArrayList;

public class Shop {

    private ArrayList<Card> cards = new ArrayList<>();
    private static Shop instance;
    private ShopMode shopMode = ShopMode.BUY;

    private Card selectedCard = null;

    private Shop() {

    }

    public static Shop getInstance() {
        if (instance == null)
            instance = new Shop();
        return instance;
    }

    public void selectCardForBuy(String name) {
        selectedCard = Card.findCardInArrayListByName(name, cards);
    }

    public void selectCardForSell(String id) {
        selectedCard = Card.findCardInArrayList(id, Account.getLoggedAccount().getCardCollection().getCards());
    }

    public void sell() {
        if (selectedCard == null) {
            throw new NoCardSelectedInShopException();
        }
        CardCollection cardCollection = Account.getLoggedAccount().getCardCollection();
        Account.getLoggedAccount().increaseDarick(selectedCard.getDarikCost());
        cardCollection.removeCard(selectedCard);
        setSelectedCard(null);



    }

    public void buy() {
        if (selectedCard == null) {
            throw new NoCardSelectedInShopException();
        }
        System.out.println("You Buyed Card");
        if (Account.getLoggedAccount().getDarick() < getSelectedCard().getDarikCost()) {
            throw new NotEnoughDarickException();
        }
        Account.getLoggedAccount().decreaseDarick(getSelectedCard().getDarikCost());
        Account.getLoggedAccount().getCardCollection().addCard(getSelectedCard());
    }

    public static Card getSelectedCard() {
        return getInstance().selectedCard;
    }

    public static void setSelectedCard(Card selectedCard) {
        Shop.getInstance().selectedCard = selectedCard;
    }

    public ShopMode getShopMode() {
        return shopMode;
    }

    public void setShopMode(ShopMode shopMode) {
        this.shopMode = shopMode;
    }

    private Card searchAndGetCard(String name) {
        return Card.findCardInArrayListByName(name, cards);
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public void addCard(Card card) {
        cards.add(card);
    }


}
