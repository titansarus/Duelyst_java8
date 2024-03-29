package Duelyst.Model;

import Duelyst.Client.Client;
import Duelyst.Client.SendMessage;
import Duelyst.Model.Battle.Battle;
import Duelyst.Model.CommandClasses.LoginCommand;
import Duelyst.Model.CommandClasses.LoginCommandsKind;
import com.gilecode.yagson.YaGson;
import com.gilecode.yagson.YaGsonBuilder;


import java.io.FileWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Comparator;

import static Duelyst.View.Constants.*;

public class Account implements Cloneable {
    private static ArrayList<Account> accounts = new ArrayList<>();
    private static Account loggedAccount = null;
    private ArrayList<String> battleHistory = new ArrayList<>();

    private int darickBeforeCheat = 0;
    private boolean isCheatModeEnable = false;

    //THIS IS NOT USED. WILL USE CARD_COLLECTION
    private CardCollection cardCollection;

    private String username;
    private String password;
    private int countOfWins;
    private int darick;

    public static Account nullAccount = new Account("IT_IS_A_NULL_USER_NAME" , "IT_IS_A_NULL_USER_NAME");

    public Account(String username, String password) {
        this.username = username;
        this.password = password;

        darick = INITIAL_DARICK;
        cardCollection = new CardCollection(this);
    }

    public static ArrayList<Account> getAccounts() {
        return accounts;
    }

    public static void setAccounts(ArrayList<Account> accounts) {
        Account.accounts = accounts;
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

    public static void saveAccount() {
        LoginCommand loginCommand = new LoginCommand(LoginCommandsKind.SAVE_ACCOUNTS);
        loginCommand.setAccount(Account.getLoggedAccount());
        SendMessage.getSendMessage().sendMessage(loginCommand);
    }

    public void enableCheatMode() {
        setCheatModeEnable(true);
        setDarickBeforeCheat(getDarick());
        setDarick(Integer.MAX_VALUE);
    }

    public void getGift(int darick) {
        this.darick += darick;
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


    public static Account getLoggedAccount() {
        return loggedAccount;
    }

    public static void setLoggedAccount(Account loggedAccount) {
        Account.loggedAccount = loggedAccount;
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

