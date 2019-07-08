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

    public static ClientHandler getOpponent(Account account) {
        Account opponent = null;
        for (ServerTV serverTV :
                runningGames) {
            if (account.getUsername().equals(serverTV.getAccount1().getUsername()) || account.getUsername().equals(serverTV.getAccount2().getUsername())) {
                if (account.getUsername().equals(serverTV.getAccount1().getUsername())) {
                    opponent = serverTV.getAccount2();
                } else {
                    opponent = serverTV.getAccount1();
                }
            }
        }
        for (ClientHandler c :
                ClientHandler.getClientHandlers()) {
            try {
                if (c.isLoggedIn() && opponent != null && c.getUserName().equals(opponent.getUsername())) {
                    return c;
                }
            }catch (Exception e){
                System.out.println("Client Handler Problem");
            }
        }
        return null;
    }

    public static ServerTV getServerTvOfBattle(Account account) {
        ServerTV serverTV1 = null;
        for (ServerTV serverTV :
                runningGames) {
            if (account.getUsername().equals(serverTV.getAccount1().getUsername()) || account.getUsername().equals(serverTV.getAccount2().getUsername())) {
                serverTV1 = serverTV;
            }
        }
        return serverTV1;
    }

    public void endGame() {
        runningGames.remove(this);
        finishedGames.add(this);
    }

    public static void setRunningGames(ArrayList<ServerTV> runningGames) {
        ServerTV.runningGames = runningGames;
    }
}
