package Duelyst.Model;

import Duelyst.Model.CardCollection;
import Duelyst.Model.Deck;
import Duelyst.Model.Item;


import javax.swing.text.TableView;
import java.io.FileWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import static Duelyst.View.Constants.*;

public class Account implements Cloneable {
    private static Account loginedAccount = null;
    private static ArrayList<Account> accounts = new ArrayList<>();
    private ArrayList<String> battleHistory;
    private ArrayList<Deck> decks;
    private CardCollection cardCollection;
    private Deck mainDeck;
    private String username;
    private String password;
    private int countOfWins;
    private int darick;
    private ArrayList<Item> collectableItems;

    public Account(String username , String password)
    {
        this.username = username;
        this.password = password;
        decks= new ArrayList<>();
        battleHistory= new ArrayList<>();
        mainDeck = new Deck();
        darick = INITIAL_DARICK;
        collectableItems= new ArrayList<>();
        accounts.add(this);
    }


    public static boolean accountExistInArrayList(String  username , ArrayList<Account> accounts)
    {
        return findAccountInArrayList(username, accounts) != null;
    }


    public static Account findAccountInArrayList(String username , ArrayList<Account> accounts)
    {
        if (username.length()>0 && accounts!=null)
        {
            for (int i =0;i<accounts.size();i++)
            {
                Account account = accounts.get(i);
                if (account!=null && account.getUsername().equals(username))
                {
                    return account;
                }
            }
        }
        return null;
    }




    public static Account getLoginedAccount() {
        return loginedAccount;
    }

    public static void setLoginedAccount(Account loginedAccount) {
        Account.loginedAccount = loginedAccount;
    }

    public static ArrayList<Account> getAccounts() {
        return accounts;
    }

    public static void setAccounts(ArrayList<Account> accounts) {
        Account.accounts = accounts;
    }

    public ArrayList<String> getBattleHistory() {
        return battleHistory;
    }

    public void setBattleHistory(ArrayList<String> battleHistory) {
        this.battleHistory = battleHistory;
    }

    public ArrayList<Deck> getDecks() {
        return decks;
    }

    public void setDecks(ArrayList<Deck> decks) {
        this.decks = decks;
    }

    public CardCollection getCardCollection() {
        return cardCollection;
    }

    public void setCardCollection(CardCollection cardCollection) {
        this.cardCollection = cardCollection;
    }

    public Deck getMainDeck() {
        return mainDeck;
    }

    public void setMainDeck(Deck mainDeck) {
        this.mainDeck = mainDeck;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getCountOfWins() {
        return countOfWins;
    }

    public void setCountOfWins(int countOfWins) {
        this.countOfWins = countOfWins;
    }

    public int getDarick() {
        return darick;
    }

    public void setDarick(int darick) {
        this.darick = darick;
    }

    public ArrayList<Item> getCollectableItems() {
        return collectableItems;
    }

    public void setCollectableItems(ArrayList<Item> collectableItems) {
        this.collectableItems = collectableItems;
    }
}

