package Duelyst.Model;

import Duelyst.Model.Account;
import Duelyst.Model.Card;
import com.rits.cloning.Cloner;

import java.util.ArrayList;

public class CardCollection {

    private ArrayList<Card> cards;
    private Account account;

    public CardCollection(Account account) {
        cards = new ArrayList<>();
        setAccount(account);
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public void removeCard(Card card) {
        if (card != null) {
            getCards().remove(card);
        }
    }


    public void addCard(Card card) {
        if (card != null) {
            Cloner cloner = new Cloner();
            Card card1 = cloner.deepClone(card);
            card1.setAccount(account);
            card1.setCardId( card1.makeNewID(account.getUsername(),card1.getCardName(),getCountOfCard(cards,card)));
            getCards().add(card1);
        }
    }


    public static int getCountOfCard(ArrayList<Card> cards, Card card) {
        int counter = 0;
        for (Card card1 : cards) {
            if (card1.getCardName().equals(card.getCardName()))
                counter++;
        }
        return counter;
    }

    public Card findCard(String cardId) {
        return Card.findCardInArrayListByName(cardId, getCards());
    }


    public ArrayList<String> search(String name) {
        ArrayList<String> Ids = new ArrayList<>();
        for (Card card :
                getCards()) {
            if (card.getCardName().equals(name))
                Ids.add(card.getCardId());
        }
        if (!Ids.isEmpty())
            return Ids;
        return null;
    }


    public Account getAccount() {
        return account;
    }

    private void setAccount(Account account) {
        this.account = account;
    }


    public String searchCardInCollection(String cardName) {
        for (Card card :
                Account.getLoginedAccount().getCardCollection().getCards()) {
            if (card.getCardName().equals(cardName)) {
                return card.getCardId();
            }
        }
        return null;
    }

}