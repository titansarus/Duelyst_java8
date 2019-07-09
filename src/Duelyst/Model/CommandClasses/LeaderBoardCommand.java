package Duelyst.Model.CommandClasses;

import Duelyst.Model.Account;

import java.util.ArrayList;

public class LeaderBoardCommand extends CommandClass {

    private ArrayList<Account> sortedListOfAccounts;
    private ArrayList<String> onlineAccounts;

    public LeaderBoardCommand() {
        super(CommandKind.LEADER_BOARD);
    }

    public ArrayList<Account> getSortedListOfAccounts() {
        return sortedListOfAccounts;
    }

    public void setSortedListOfAccounts(ArrayList<Account> sortedListOfAccounts) {
        this.sortedListOfAccounts = sortedListOfAccounts;
    }

    public ArrayList<String> getOnlineAccounts() {
        return onlineAccounts;
    }

    public void setOnlineAccounts(ArrayList<String> onlineAccounts) {
        this.onlineAccounts = onlineAccounts;
    }
}
