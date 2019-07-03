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
        if (!isFinished(selectedCard)){
            throw new MyException("this car is finished","can not buy");
        }

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

    private boolean isFinished(Card card){
        ShopCommand shopCommand = new ShopCommand(ShopCommandsKind.GET_FINISHED_CARD);
        SendMessage.getSendMessage().sendMessage(shopCommand);
        try {
            Thread.sleep(100);
        }catch (Exception e){
            e.printStackTrace();
        }
        if (finishedCard.contains(card))
            return true;
        return false;
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

    public void setNumberOfCards(ArrayList<String> numberOfCards) {
        this.numberOfCards = numberOfCards;
    }
    public void saveTheUpdateOfNumerOfCards(){//TODO send to server
        YaGson yaGson = new YaGsonBuilder().setPrettyPrinting().create();
        try {
            Writer writer = new FileWriter("numberOfCards.json");
            String s = yaGson.toJson(numberOfCards);
            writer.write(s);
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void increaseNumberOfCard(String cardName) {//TODO send to server
        String oldNumber = "";
        String newNumber = "";
        for (String s :
                numberOfCards) {
            if (s.equals(cardName)) {
                oldNumber = s;
                int number = Integer.parseInt(s.split(" ")[1]);
                number++;
                newNumber = s.split(" ")[0] + " " + number;
            }
        }
        numberOfCards.remove(oldNumber);
        numberOfCards.add(newNumber);
        saveTheUpdateOfNumerOfCards();
    }
    public void decreaseNumberOfCard(String cardName) {//TODO send to server
        String oldNumber = "";
        String newNumber = "";
        for (String s :
                numberOfCards) {
            if (s.equals(cardName)) {

                oldNumber = s;
                int number = Integer.parseInt(s.split(" ")[1]);
                number--;
                newNumber = s.split(" ")[0] + " " + number;
            }
        }
        numberOfCards.remove(oldNumber);
        numberOfCards.add(newNumber);
        saveTheUpdateOfNumerOfCards();
    }
    public ArrayList<Card> getFinishedCards(){//TODO send to Server
        ArrayList<Card> cards = new ArrayList<>();
        for (String s:
             numberOfCards) {
            if (Integer.parseInt(s.split(" ")[0])<1){
                for (Card c:
                     this.cards) {
                    if (c.getCardName().equals(s)){
                        cards.add(c);
                    }
                }
            }
        }
        return cards;
    }

    public void setCardsOfShop(){
        ShopCommand command = new ShopCommand(ShopCommandsKind.GET_CARDS);
        SendMessage.getSendMessage().sendMessage(command);
    }
    public ArrayList<Card> getAuctionCards() {
        return auctionCards;
    }

    public void addAuctionCards(Card auctionCard) {
        auctionCards.add(auctionCard);
    }
    public void removeAuctionCards(Card auctionCard) {
        auctionCards.remove(auctionCard);
    }

    public ArrayList<Card> getFinishedCard() {
        return finishedCard;
    }

    public void setFinishedCard(ArrayList<Card> finishedCard) {
        this.finishedCard = finishedCard;
    }

    public void setAuctionCards(ArrayList<Card> auctionCards) {
        this.auctionCards = auctionCards;
    }
}
