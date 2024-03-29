package Duelyst.Server;


import Duelyst.Model.*;
import Duelyst.Model.CommandClasses.CustomCardCommand;
import com.gilecode.yagson.YaGson;
import com.gilecode.yagson.YaGsonBuilder;

import java.io.FileWriter;
import java.io.Writer;
import java.util.ArrayList;

public class ServerShop {

    private ArrayList<Card> cards = new ArrayList<>();
    private ArrayList<String> numberOfCards = new ArrayList<>();
    private ArrayList<Card> auctionCards = new ArrayList<>();
    private static ServerShop instance;
    private ArrayList<Card> finishedCard;
    private ArrayList<CustomCardCommand> customCardCommands = new ArrayList<>();

    private Card selectedCard = null;

    private ServerShop() {
    }

    public static ServerShop getInstance() {
        if (instance == null)
            instance = new ServerShop();
        return instance;
    }

    public void selectCardForBuy(String name) {
        selectedCard = Card.findCardInArrayListByName(name, cards);
    }

    public void selectCardForSell(String id) {
        selectedCard = Card.findCardInArrayList(id, Account.getLoggedAccount().getCardCollection().getCards());
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

    public void saveTheUpdateOfNumberOfCards() {//TODO send to server
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

    public boolean isFinished(Card card) {
        String cardName = card.getCardName();
        for (String s :
                numberOfCards) {
            if (s.split(" ")[0].equals(cardName)) {
                System.out.println(Integer.parseInt(s.split(" ")[1]));
                return Integer.parseInt(s.split(" ")[1]) < 1;
            }
        }
        return true;
    }

    public int getNumberOfCard(String cardName) {
        int number = -1;
        for (String s :
                numberOfCards) {
            if (s.split(" ")[0].equals(cardName)) {
                number = Integer.parseInt(s.split(" ")[1]);
            }
        }
        return number;
    }

    public void increaseNumberOfCard(String cardName) {//TODO send to server
        String oldNumber = "";
        String newNumber = "";
        for (String s :
                numberOfCards) {
            if (s.split(" ")[0].equals(cardName)) {
                oldNumber = s;
                int number = Integer.parseInt(s.split(" ")[1]);
                number++;
                newNumber = s.split(" ")[0] + " " + number;
                break;
            }
        }

        numberOfCards.remove(oldNumber);
        numberOfCards.add(newNumber);

        saveTheUpdateOfNumberOfCards();
    }


    public void decreaseNumberOfCard(String cardName) {//TODO send to server
        System.out.println("in Server shop -> decrease");
        String oldNumber = "";
        String newNumber = "";
        for (String s :
                numberOfCards) {
            if (s.split(" ")[0].equals(cardName)) {
                oldNumber = s;
                int number = Integer.parseInt(s.split(" ")[1]);
                number--;
                newNumber = s.split(" ")[0] + " " + number;
                break;
            }
        }
        numberOfCards.remove(oldNumber);
        numberOfCards.add(newNumber);

        saveTheUpdateOfNumberOfCards();
    }

    public ArrayList<Card> removeCardFromAuctions(Card card) {
        auctionCards.remove(card);
        return auctionCards;
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

    public void addAuctionCards(Card auctionCard) {
        auctionCards.add(auctionCard);
    }

    public void removeAuctionCards(Card auctionCard) {
        auctionCards.remove(auctionCard);
    }

    public ArrayList<CustomCardCommand> getCustomCardCommands() {
        return customCardCommands;
    }
}
