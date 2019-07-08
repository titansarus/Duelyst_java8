package Duelyst.Model.CommandClasses;

import Duelyst.Model.Battle.BattleRecord;

import java.util.ArrayList;

public class tvCommand extends CommandClass {

    private tvCommandKind tvCommandKind;
    private ArrayList<String> runningGames;
    private ArrayList<String> finishedGames;
    private ArrayList<BattleRecord> battleRecords;
    private String userNameOfFirstPlayersOfARequestedReplayOfABattle;
    private String userNameOfSecondPlayersOfARequestedReplayOfABattle;


    public tvCommand(tvCommandKind tvCommandKind){
        super(CommandKind.TV);
        this.tvCommandKind = tvCommandKind;
    }

    public ArrayList<String> getRunningGames() {
        return runningGames;
    }

    public void setRunningGames(ArrayList<String> runningGames) {
        this.runningGames = runningGames;
    }

    public ArrayList<String> getFinishedGames() {
        return finishedGames;
    }

    public void setFinishedGames(ArrayList<String> finishedGames) {
        this.finishedGames = finishedGames;
    }

    public ArrayList<BattleRecord> getBattleRecords() {
        return battleRecords;
    }

    public void setBattleRecords(ArrayList<BattleRecord> battleRecords) {
        this.battleRecords = battleRecords;
    }

    public Duelyst.Model.CommandClasses.tvCommandKind getTvCommandKind() {
        return tvCommandKind;
    }

    public String getUserNameOfFirstPlayersOfARequestedReplayOfABattle() {
        return userNameOfFirstPlayersOfARequestedReplayOfABattle;
    }

    public void setUserNameOfFirstPlayersOfARequestedReplayOfABattle(String userNameOfFirstPlayersOfARequestedReplayOfABattle) {
        this.userNameOfFirstPlayersOfARequestedReplayOfABattle = userNameOfFirstPlayersOfARequestedReplayOfABattle;
    }

    public String getUserNameOfSecondPlayersOfARequestedReplayOfABattle() {
        return userNameOfSecondPlayersOfARequestedReplayOfABattle;
    }

    public void setUserNameOfSecondPlayersOfARequestedReplayOfABattle(String userNameOfSecondPlayersOfARequestedReplayOfABattle) {
        this.userNameOfSecondPlayersOfARequestedReplayOfABattle = userNameOfSecondPlayersOfARequestedReplayOfABattle;
    }
}
