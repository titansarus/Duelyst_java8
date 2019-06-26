package Duelyst.Model;

import Duelyst.Model.Battle.Battle;
import Duelyst.Model.Items.Item;
import com.gilecode.yagson.YaGson;
import com.gilecode.yagson.YaGsonBuilder;


import java.io.FileWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Comparator;

import static Duelyst.View.Constants.*;

public class Account implements Cloneable {
    private static Account loggedAccount = null;
    private static ArrayList<Account> accounts = new ArrayList<>();
    private ArrayList<String> battleHistory;
    private ArrayList<Battle> battles = new ArrayList<>();

    //THIS IS NOT USED. WILL USE CARD_COLLECTION
    private CardCollection cardCollection;

    private String username;
    private String password = null;
    private int countOfWins;
    private int darick;
    private ArrayList<Item> collectableItems;

    public Account(String username, String password) {
        this.username = username;
        this.password = password;
        battleHistory = new ArrayList<>();

        darick = INITIAL_DARICK;
        collectableItems = new ArrayList<>();
        accounts.add(this);
        cardCollection = new CardCollection(this);
    }

    public void decreaseDarick(int amount) {
        setDarick(getDarick() - amount);
    }

    public void increaseDarick(int amount) {
        setDarick(getDarick() + amount);
    }

    public static ArrayList<Account> accountsSorter(ArrayList<Account> accounts) {


        ArrayList<Account> accountsCopy = new ArrayList<>(accounts);
        accountsCopy.sort(((Comparator<Account>) (o1, o2) -> o2.getCountOfWins() - o1.getCountOfWins()).thenComparing((o1, o2) -> o1.getUsername().compareTo(o2.getUsername())));
        return accountsCopy;
    }


    public static boolean accountExistInArrayList(String username, ArrayList<Account> accounts) {
        return findAccountInArrayList(username, accounts) != null;
    }


    public static Account findAccountInArrayList(String username, ArrayList<Account> accounts) {
        if (username.length() > 0 && accounts != null) {
            for (int i = 0; i < accounts.size(); i++) {
                Account account = accounts.get(i);
                if (account != null && account.getUsername().equals(username)) {
                    return account;
                }
            }
        }
        return null;
    }

    public static void saveAccount() {
        YaGson yaGson = new YaGsonBuilder().setPrettyPrinting().create();
        try {
            Writer writer = new FileWriter("accounts.json");
            String s = yaGson.toJson(accounts);
            writer.write(s);
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static Account getLoggedAccount() {
        return loggedAccount;
    }

    public static void setLoggedAccount(Account loggedAccount) {
        Account.loggedAccount = loggedAccount;
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


    public CardCollection getCardCollection() {
        return cardCollection;
    }

    public void setCardCollection(CardCollection cardCollection) {
        this.cardCollection = cardCollection;
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

    public ArrayList<Battle> getBattles() {
        return battles;
    }

    public void setBattles(ArrayList<Battle> battles) {
        this.battles = battles;
    }
}

