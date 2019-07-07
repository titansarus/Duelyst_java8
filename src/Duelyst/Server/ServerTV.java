package Duelyst.Server;

import Duelyst.Model.Account;
import Duelyst.Model.Battle.BattleRecord;

import java.util.ArrayList;

public class ServerTV {
    private static ArrayList<ServerTV> finishedGames = new ArrayList<>();
    private static ArrayList<ServerTV> runningGames = new ArrayList<>();
    private ArrayList<BattleRecord> battleRecords = new ArrayList<>();
    private Account account1;
    private Account account2;

    public ServerTV(Account account1, Account account2) {
        this.account1 = account1;
        this.account2 = account2;
        runningGames.add(this);
    }

    public static ArrayList<ServerTV> getFinishedGames() {
        return finishedGames;
    }

    public static void setFinishedGames(ArrayList<ServerTV> finishedGames) {
        ServerTV.finishedGames = finishedGames;
    }


    public ArrayList<BattleRecord> getBattleRecords() {
        return battleRecords;
    }

    public void setBattleRecords(ArrayList<BattleRecord> battleRecords) {
        this.battleRecords = battleRecords;
    }

    public Account getAccount1() {
        return account1;
    }

    public void setAccount1(Account account1) {
        this.account1 = account1;
    }

    public Account getAccount2() {
        return account2;
    }

    public void setAccount2(Account account2) {
        this.account2 = account2;
    }

    public static ArrayList<ServerTV> getRunningGames() {
        return runningGames;
    }

    public static void setRunningGames(ArrayList<ServerTV> runningGames) {
        ServerTV.runningGames = runningGames;
    }
}
