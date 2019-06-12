package Duelyst.Model.Battle;

import Duelyst.Model.Account;
import Duelyst.Model.Card;
import Duelyst.Model.Deck;
import Duelyst.Model.Hero;
import com.rits.cloning.Cloner;

import java.util.ArrayList;
import java.util.Random;

import static Duelyst.View.Constants.*;

public class Player {
    private Account account;
    private Deck deck;
    private ArrayList<Card> hand;
    private Integer mana;
    private Hero hero;
    private ArrayList<Card> inGameCards;

    private Integer manaYIntercpet = 2; //Arz az mabdae system afzayesh mana. Baraye test ya cheat mode roe addad balatr az 2 set mishavad.


    public Player(Account account, Deck deck) {
        Cloner cloner = new Cloner();
        this.account = account;
        this.deck = cloner.deepClone(deck);
        this.hand = new ArrayList<>();
        this.inGameCards = new ArrayList<>();
        initializeHand();
    }

    public void initializeHand() {
        Random random = new Random();
        for (int i = 0; i < SIZE_OF_HAND; i++) {
            if (deck.getCards().size() > 0) {
                int index = random.nextInt(deck.getCards().size());
                giveCardFromDeckToHand(index);
            }
        }
    }

    public void setManaFromTurn(int turn) {
        setMana(Math.floorDiv(turn, 2) + manaYIntercpet); //FOR TEST, set +2 to +1000
        if (getMana() > 9) {
            setMana(9);
        }
    }

    public void nextCardToHand() {
        Random random = new Random();
        if (hand.size() < SIZE_OF_HAND) {
            int index = random.nextInt(deck.getCards().size());
            giveCardFromDeckToHand(index);
        }
    }

    private void giveCardFromDeckToHand(int index) {
        Card card = getDeck().getCards().get(index);
        getHand().add(card);
        getDeck().getCards().remove(index);
    }


    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Deck getDeck() {
        return deck;
    }

    public void setDeck(Deck deck) {
        this.deck = deck;
    }

    public ArrayList<Card> getHand() {
        return hand;
    }

    public void setHand(ArrayList<Card> hand) {
        this.hand = hand;
    }

    public Integer getMana() {
        return mana;
    }

    public void setMana(Integer mana) {
        this.mana = mana;
    }

    public Hero getHero() {
        return hero;
    }

    public void setHero(Hero hero) {
        this.hero = hero;
    }

    public Integer getManaYIntercpet() {
        return manaYIntercpet;
    }

    public void setManaYIntercpet(Integer manaYIntercpet) {
        this.manaYIntercpet = manaYIntercpet;
    }

    public ArrayList<Card> getInGameCards() {
        return inGameCards;
    }
}
