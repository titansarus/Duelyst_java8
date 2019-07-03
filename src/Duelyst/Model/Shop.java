package Duelyst.Model;


import Duelyst.Client.SendMessage;
import Duelyst.Exceptions.MyException;
import Duelyst.Exceptions.NoCardSelectedInShopException;
import Duelyst.Exceptions.NotEnoughDarickException;
import Duelyst.Model.CommandClasses.ShopCommand;
import Duelyst.Model.CommandClasses.ShopCommandsKind;
import com.gilecode.yagson.YaGson;
import com.gilecode.yagson.YaGsonBuilder;

import java.io.FileWriter;
import java.io.Writer;
import java.util.ArrayList;

public class Shop {

    private ArrayList<Card> cards = new ArrayList<>();
    private ArrayList<String> numberOfCards = new ArrayList<>();//TODO send to shop of server
    private ArrayList<Card> auctionCards = new ArrayList<>();
    private static Shop instance;
    private ShopMode shopMode = ShopMode.BUY;
    private ArrayList<Card> finishedCard;

    private Card selectedCard = null;

    private Shop() {}

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


        ShopCommand shopCommand = new ShopCommand(ShopCommandsKind.SELL);
        shopCommand.setSellCard(selectedCard);
        SendMessage.getSendMessage().sendMessage(shopCommand);
        CardCollection cardCollection = Account.getLoggedAccount().getCardCollection();
        Account.getLoggedAccount().increaseDarick((selectedCard.getDarikCost()*4)/5);
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
        ShopCommand shopCommand = new ShopCommand(ShopCommandsKind.BUY);
        shopCommand.setBuyCard(selectedCard);
        SendMessage.getSendMessage().sendMessage(shopCommand);
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

    public ArrayList<String> getNumberOfCards() {
        return numberOfCards;
    }



    public ArrayList<Card> getAuctionCards() {
        return auctionCards;
    }

    public void setAuctionCards(ArrayList<Card> auctionCards) {
        this.auctionCards = auctionCards;
    }

    public ArrayList<Card> getFinishedCard() {
        return finishedCard;
    }

    public void setFinishedCard(ArrayList<Card> finishedCard) {
        this.finishedCard = finishedCard;
    }

}
