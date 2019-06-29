package Duelyst.Model;

import Duelyst.Model.Battle.Battle;
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
    private ArrayList<String> battleHistory = new ArrayList<>();

    private int darickBeforeCheat = 0;
    private boolean isCheatModeEnable = false;

    //THIS IS NOT USED. WILL USE CARD_COLLECTION
    private CardCollection cardCollection;

    private String username;
    private String password;
    private int countOfWins;
    private int darick;

    public Account(String username, String password) {
        this.username = username;
        this.password = password;

        darick = INITIAL_DARICK;
        if (!(this instanceof Ai))
            accounts.add(this);
        cardCollection = new CardCollection(this);
    }

    public int toggleCheatMode() {
        if (isCheatModeEnable()) {
            disableCheatMode();
            return CHEAT_MODE_DEACTIVATED;
        } else {
            enableCheatMode();
            return CHEAT_MODE_ACTIVATED;
        }
    }

    public void enableCheatMode() {
        setCheatModeEnable(true);
        setDarickBeforeCheat(getDarick());
        setDarick(Integer.MAX_VALUE);
    }

    public void disableCheatMode() {
        setCheatModeEnable(false);
        setDarick(getDarickBeforeCheat());
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

    public int getDarickBeforeCheat() {
        return darickBeforeCheat;
    }

    public void setDarickBeforeCheat(int darickBeforeCheat) {
        this.darickBeforeCheat = darickBeforeCheat;
    }

    public boolean isCheatModeEnable() {
        return isCheatModeEnable;
    }

    public void setCheatModeEnable(boolean cheatModeEnable) {
        isCheatModeEnable = cheatModeEnable;
    }
}

