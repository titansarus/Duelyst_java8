package Duelyst.Model;


import java.util.ArrayList;
import java.util.Random;

public class Card implements Cloneable {
    private static ArrayList<Card> allCards = new ArrayList<>();


    private String cardId;
    private int manaCost;
    private int darikCost;
    // private Cell currentCell;
    private CardKind cardKind;
    private Account account;
    private String cardDescription;
    private String cardName;
    //private ArrayList<ABuff> buffs = new ArrayList<>();
    private boolean isAbleToMove = true;
    private boolean isInGame;

    public static String makeNewID(String accountName, String cardName, int countOfCardsInPlayerCollection) {
        return accountName + "_" + cardName + "_" + (countOfCardsInPlayerCollection + 1);
    }

    public Card(String cardName, String cardDescription, int manaCost, int darikCost) {
        this.manaCost = manaCost;
        this.darikCost = darikCost;
        this.cardDescription = cardDescription;
        this.cardName = cardName;
        addCard(this);
    }


    public static int countNumberOfCardsWithNameInArrayList(String cardName, ArrayList<Card> cards) {
        int counter = 0;
        if (cards != null) {
            for (int i = 0; i < cards.size(); i++) {
                Card card = cards.get(i);
                if (card != null && card.getCardName().equals(cardName)) {
                    counter++;
                }
            }
        }
        return counter;
    }


    public void setCardId(String cardId) {
        this.cardId = cardId;
    }


    public static ArrayList<Card> selectRandomCardsForHand(ArrayList<Card> cards, int numberOfCards) {
        Random random = new Random();
        ArrayList<Card> temp = new ArrayList<>();

        for (int i = 0; i < numberOfCards; i++) {
            int randomIndex = 1 + random.nextInt(numberOfCards);
            temp.add(cards.get(randomIndex));
            cards.remove(randomIndex);
        }
        return temp;
    }

    public static Card findCardInArrayList(String cardId, ArrayList<Card> cards) {
        if (cards != null) {
            for (int i = 0; i < cards.size(); i++) {
                if (cards.get(i) != null && cards.get(i).getCardId().equals(cardId)) {
                    return cards.get(i);
                }
            }
        }
        return null;
    }


    public static Card findCardInArrayListByName(String cardName, ArrayList<Card> cards) {
        if (cards != null) {
            for (int i = 0; i < cards.size(); i++) {
                if (cards.get(i) != null && cards.get(i).getCardName().equals(cardName)) {
                    return cards.get(i);
                }
            }
        }
        return null;
    }

    public static ArrayList<Card> getAllCards() {
        return allCards;
    }

    public String getCardId() {
        return cardId;
    }

    public int getManaCost() {
        return manaCost;
    }

    public int getDarikCost() {
        return darikCost;
    }


    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }


    public void setAbleToMove(boolean flag) {
        this.isAbleToMove = flag;
    }

    public boolean isAbleToMove() {
        return this.isAbleToMove;
    }

    public String getCardDescription() {
        return cardDescription;
    }

    private void addCard(Card card) {
        getAllCards().add(card);
    }

    public static Card getCard(String cardId) {
        return findCardInArrayList(cardId, getAllCards());
    }

    public static ArrayList<Card> cardArrayListSorter(ArrayList<Card> sourceCards) {
        if (sourceCards != null) {
            ArrayList<Card> copy = (ArrayList<Card>) sourceCards.clone();
            ArrayList<Card> result = new ArrayList<>();
            for (int i = 0; i < copy.size(); i++) {
                if (copy.get(i) != null && copy.get(i).getCardKind().equals(CardKind.HERO)) {
                    result.add(copy.get(i));
                }
            }
            for (int i = 0; i < copy.size(); i++) {
                if (copy.get(i) != null && copy.get(i).getCardKind().equals(CardKind.MINION)) {
                    result.add(copy.get(i));
                }
            }
            for (int i = 0; i < copy.size(); i++) {
                if (copy.get(i) != null && copy.get(i).getCardKind().equals(CardKind.SPELL)) {
                    result.add(copy.get(i));
                }
            }


            return result;
        }
        return null;
    }

    public CardKind getCardKind() {
        return cardKind;
    }

    public boolean isInGame() {
        return isInGame;
    }

    public void setInGame(boolean inGame) {
        isInGame = inGame;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public static void setAllCards(ArrayList<Card> allCards) {
        Card.allCards = allCards;
    }

    public void setManaCost(int manaCost) {
        this.manaCost = manaCost;
    }

    public void setDarikCost(int darikCost) {
        this.darikCost = darikCost;
    }

    public void setCardKind(CardKind cardKind) {
        this.cardKind = cardKind;
    }

    public void setCardDescription(String cardDescription) {
        this.cardDescription = cardDescription;
    }

    //TODO THIS MAY NEED TO BE CHANGED. THE LOGIC IS THE SAME BUT MAYBE WE NEED TO CLONE ANOTHER ABuff ArrayList.
}
