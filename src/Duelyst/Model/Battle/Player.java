package Duelyst.Model.Battle;

import Duelyst.Model.*;
import Duelyst.Model.Items.Item;
import com.rits.cloning.Cloner;

import java.util.ArrayList;
import java.util.Random;

import static Duelyst.View.Constants.*;

public class Player {

    private ArrayList<Card> graveyard = new ArrayList<>();
    private Account account;
    private Deck deck;
    private ArrayList<Card> hand;
    private Integer mana;
    private Hero hero;
    private Item collectibleItem;
    private ArrayList<Card> inGameCards;
    private int numberOfFlag=0;
    private Integer manaYIntercpet = 2; //Arz az mabdae system afzayesh mana. Baraye test ya cheat mode roe addad balatr az 2 set mishavad.


    public Player(Account account, Deck deck) {
        Cloner cloner = new Cloner();
        this.account = account;
        this.deck = cloner.deepClone(deck);
        this.hand = new ArrayList<>();
        this.inGameCards = new ArrayList<>();
        this.deck.removeCard(this.deck.getHero().getCardId());
        this.mana = 2;
        initializeHand();
    }

    public void changeMana(int i) {
        setMana(getMana() + i);
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

    public void getNextHand() {
        if (getHand().size() < SIZE_OF_HAND && deck.getCards().size() > 0) {
            Random random = new Random();
            int index = random.nextInt(deck.getCards().size());
            giveCardFromDeckToHand(index);
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

    public boolean checkIfCardIsInGame(Card card) {
        for (int i = 0; i < getInGameCards().size(); i++) {
            if (getInGameCards().get(i) != null && getInGameCards().get(i).equals(card)) {
                return true;
            }
        }
        return false;
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

    public ArrayList<Card> getGraveyard() {
        return graveyard;
    }

    public int getNumberOfFlag() {
        return numberOfFlag;
    }

    public void setNumberOfFlag(int numberOfFlag) {
        this.numberOfFlag = numberOfFlag;
    }

    public Item getCollectibleItem() {
        return collectibleItem;
    }

    public void setCollectibleItem(Item collectibleItem) {
        this.collectibleItem = collectibleItem;
    }
}
