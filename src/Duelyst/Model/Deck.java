
package Duelyst.Model;

import Duelyst.Exceptions.NotEnoughCardsToImportException;
import Duelyst.Model.Items.Item;
import Duelyst.Model.Spell.Spell;
import com.rits.cloning.Cloner;

import java.util.ArrayList;

public class Deck implements Cloneable {
    private static ArrayList<Deck> decks = new ArrayList<>();
    private ArrayList<Card> cards;
    private ArrayList<Minion> minions;
    private Hero hero;
    private Item item;
    private Account account;
    private String deckName;
    private boolean isValid = false;

    public Deck(String deckName, Account account) {
        setDeckName(deckName);
        setAccount(account);
        setCards(new ArrayList<>());
        setMinions(new ArrayList<>());
        decks.add(this);
    }

    public static Deck deepClone(Deck deck) {
        Cloner cloner = new Cloner();
        cloner.dontClone(Account.class);
        return cloner.deepClone(deck);
    }

    public static void deleteDeck(Deck deck) {
        if (deck != null) {
            decks.remove(deck);
        }
    }

    public static void giveCardOfDeckToCardCollection(Deck deck, CardCollection cardCollection) {
        if (deck != null && cardCollection != null) {
            for (int i = 0; i < deck.getCards().size(); i++) {
                if (deck.getCards().get(i) != null) {
                    cardCollection.addCard(deck.getCards().get(i));
                }
            }
        }
    }

    public static void sendExportedDeckToCardCollection(Deck deck, CardCollection cardCollection) {
        boolean exportAvailable = true;
        if (Deck.findDeckInArrayList(deck.getDeckName(), cardCollection.getDecks()) != null) {
            Cloner cloner = new Cloner();
            CardCollection cardCollection1 = cloner.deepClone(cardCollection);
            Deck.giveCardOfDeckToCardCollection(Deck.findDeckInArrayList(deck.getDeckName(), cardCollection1.getDecks()), cardCollection1);
            exportAvailable = checkImportAvailabitiy(deck, cardCollection1);
        } else {
            exportAvailable = checkImportAvailabitiy(deck, cardCollection);
        }


        if (!exportAvailable) {
            throw new NotEnoughCardsToImportException();//TODO
        }


        if (Deck.findDeckInArrayList(deck.getDeckName(), cardCollection.getDecks()) != null) {
            Deck.giveCardOfDeckToCardCollection(Deck.findDeckInArrayList(deck.getDeckName(), cardCollection. getDecks()),cardCollection );
            cardCollection.getDecks().remove(Deck.findDeckInArrayList(deck.getDeckName(),cardCollection.getDecks()));
            cardCollection.setMainDeck(null);
        }

        Deck exportedDeck = new Deck(deck.getDeckName(),cardCollection.getAccount());
        for (int i =0;i<deck.getCards().size();i++)
        {
            Card card = deck.getCards().get(i);
            if (card!=null)
            {
                card.setAccount(cardCollection.getAccount());
                card.setCardId(Card.makeNewID(cardCollection.getAccount().getUsername(),card.getCardName(),CardCollection.getCountOfCard(cardCollection.getCards(),card)));
                //TODO CHECK FOR HERO
                exportedDeck.addCard(card);
                cardCollection.removeCard(Card.findCardInArrayListByName(card.getCardName(),cardCollection.getCards()));
            }
        }
        cardCollection.getDecks().add(exportedDeck);

    }

    public static boolean checkImportAvailabitiy(Deck deck, CardCollection cardCollection) {
        boolean result = true;

        for (int i = 0; i < deck.getCards().size(); i++) {
            Card card = deck.getCards().get(i);
            if (card != null) {
                if (Card.countNumberOfCardsWithNameInArrayList(card.getCardName(), cardCollection.getCards()) < Card.countNumberOfCardsWithNameInArrayList(card.getCardName(), deck.getCards())) {
                    result = false;
                    return result;
                }
            }
        }
        return result;
    }

    public static boolean deckExist(String deckName, ArrayList<Deck> decks) {
        return findDeckInArrayList(deckName, decks) != null;
    }

    public static Deck findDeckInArrayList(String deckName, ArrayList<Deck> decks) {
        if (decks == null)
            return null;
        for (int i = 0; i < decks.size(); i++) {
            if (decks.get(i) != null && decks.get(i).getDeckName().equals(deckName)) {
                return decks.get(i);
            }
        }
        return null;
    }


    public void deleteItem() {
        setItem(null);
    }

    public static boolean validateDeck(Deck deck) {
        return deck.getHero() != null && countOfCardsWithoutHeroInDeck(deck) == 20;
    }

    public static boolean deckHasCard(String cardId, Deck deck) {
        for (int i = 0; i < deck.getCards().size(); i++) {
            if (deck.getCards().get(i).getCardId().equals(cardId))
                return true;
        }
        return false;
    }


    private static int countOfCardsWithoutHeroInDeck(Deck deck) {
        int sum = 0;
        for (int i = 0; i < deck.getCards().size(); i++) {
            if (deck.getCards().get(i).getCardKind() != CardKind.HERO) {
                sum++;
            }
        }
        return sum;
    }


    public void addCard(Card card) {
        if (card != null) {
            if (card instanceof Hero) {
                addHero((Hero) card);
            } else if (card instanceof Minion) {
                addMinion((Minion) card);
            } else getCards().add(card);
        }
    }

    private void addHero(Hero hero) {
        if (hero != null) {
            if (getHero() != null) {
                getCards().remove(getHero());
                this.account.getCardCollection().addCard(getHero());
                setHero(hero);
                getCards().add(hero);
            } else {
                setHero(hero);
                getCards().add(hero);
            }
        }
    }

    private void addMinion(Minion minion) {
        if (minion != null) {
            getMinions().add(minion);
            getCards().add(minion);
        }
    }

    public static void removeCardFromDeck(String cardId, String deckName) {
        Deck deck = findDeck(deckName);
        if (deck != null) {
            deck.removeCard(cardId);
        }
    }

    public void removeCard(String cardId) {
        Card card = Card.findCardInArrayList(cardId, this.getCards());
        if (card != null) {
            getCards().remove(card);
        }

    }

    private static Deck findDeck(String deckName) {
        return findDeckInArrayList(deckName, decks);
    }


    public static ArrayList<Deck> getDecks() {
        return decks;
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public void setCards(ArrayList<Card> cards) {
        this.cards = cards;
    }

    public ArrayList<Minion> getMinions() {
        return minions;
    }

    public void setMinions(ArrayList<Minion> minions) {
        this.minions = minions;
    }

    public Hero getHero() {
        return hero;
    }

    public void setHero(Hero hero) {
        this.hero = hero;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String getDeckName() {
        return deckName;
    }

    public void setDeckName(String deckName) {
        this.deckName = deckName;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(deckName).append(" \n");
        ArrayList<Card> cards = Card.cardArrayListSorter(Card.cardArrayListSorter(this.cards));
        int iter = 1;
        sb.append("Hero: \n");
        for (int i = 0; i < cards.size(); i++) {
            if (cards.get(i) != null && cards.get(i).getCardKind().equals(CardKind.HERO)) {
                sb.append(iter++).append(". ").append(cards.get(i).toString()).append("\n");
            }
        }
        sb.append("Items: \n");
        if (this.item != null) {
            sb.append(item.toString()).append(" \n");
        }
        iter = 1;
        sb.append("Cards: \n");
        for (int i = 0; i < cards.size(); i++) {
            if (cards.get(i) != null && !cards.get(i).getCardKind().equals(CardKind.HERO)) {
                sb.append(iter++).append(". ").append(cards.get(i).toString()).append("\n");
            }
        }
        String result = sb.toString();

        return result;
    }

    public void setValid(boolean valid) {
        isValid = validateDeck(this);
        isValid = valid;
    }

    public boolean isValid() {
        return isValid;
    }

    public static Deck AiDeckBuilder(int i,Ai ai) {
        int[] heroNumber = {1, 5, 7};
        int[][] spellNumbers = {{1, 7, 10, 11, 12, 18, 20}, {2, 3, 5, 9, 8, 13, 19}, {6, 10, 12, 14, 15, 16, 17}};
        int[][] minionNumbers = {{1, 9, 11, 11, 13, 17, 18, 21, 22, 26, 38, 36, 40}, {2, 3, 5, 8, 12, 15, 15, 19, 23, 27, 30, 33, 39}, {6, 7, 10, 14, 16, 16, 20, 24, 25, 28, 29, 31, 34}};
        int[] item = {1, 10, 5};
        return AiDeckBuilderUtility(heroNumber[i - 1], spellNumbers[i - 1], minionNumbers[i - 1], item[i - 1],ai);
    }
    public static Deck AiDeckBuilderUtility(int heroNumber, int[] spellNumbers, int[] minionNumbers, int itemNumber,Ai ai) {
        Deck deck = new Deck("AiDeck",ai);
        Hero hero = (Hero) Card.findCardInArrayList("Hero_" + heroNumber, Shop.getInstance().getCards());
        deck.addCard(hero);
        for (int minionNumber : minionNumbers) {
            Minion minion = (Minion) Card.findCardInArrayList("Minion_" + minionNumber, Shop.getInstance().getCards());
            deck.addCard(minion);
        }
        for (int spellNumber:
             spellNumbers) {
            Spell spell  =(Spell) Card.findCardInArrayList("Spell_"+spellNumber,Shop.getInstance().getCards());
            deck.addCard(spell);
        }
        //TODO Item Should be Added
        return deck;
    }
}
