package Duelyst.Model.Battle;

import Duelyst.Model.Card;
import Duelyst.Model.Deck;
import Duelyst.Model.GameGoal;
import Duelyst.Model.GameMode;

import java.util.ArrayList;

public class BattleRecord {
    private static ArrayList<ArrayList<BattleRecord>> battleRecords = new ArrayList<>();

    private BattleRecordEnum typeOfRecord;

    //FOR INITIALIZE
    private String firstPlayerUsername , secondPlayerUsername;
    private GameGoal gameGoal;
    private GameMode gameMode;
    private Deck firstPlayerDeck;
    private Deck secondPlayerDeck;
    private ArrayList<Card> firstPlayerHand;
    private ArrayList<Card> secondPlayerHand;


    //FOR ATTACK
    private String attackerCardId, attackedCardId;

    //FOR MOVE
    private String moveCardId;
    private int moveRow,moveColumn;

    //FOR DEATH
    private String deathCardId;


    public BattleRecord(BattleRecordEnum typeOfRecord) {
        this.typeOfRecord = typeOfRecord;
    }


    public static ArrayList<ArrayList<BattleRecord>> getBattleRecords() {
        return battleRecords;
    }

    public static void setBattleRecords(ArrayList<ArrayList<BattleRecord>> battleRecords) {
        BattleRecord.battleRecords = battleRecords;
    }

    public BattleRecordEnum getTypeOfRecord() {
        return typeOfRecord;
    }

    public void setTypeOfRecord(BattleRecordEnum typeOfRecord) {
        this.typeOfRecord = typeOfRecord;
    }

    public String getFirstPlayerUsername() {
        return firstPlayerUsername;
    }

    public void setFirstPlayerUsername(String firstPlayerUsername) {
        this.firstPlayerUsername = firstPlayerUsername;
    }

    public String getSecondPlayerUsername() {
        return secondPlayerUsername;
    }

    public void setSecondPlayerUsername(String secondPlayerUsername) {
        this.secondPlayerUsername = secondPlayerUsername;
    }

    public GameGoal getGameGoal() {
        return gameGoal;
    }

    public void setGameGoal(GameGoal gameGoal) {
        this.gameGoal = gameGoal;
    }

    public GameMode getGameMode() {
        return gameMode;
    }

    public void setGameMode(GameMode gameMode) {
        this.gameMode = gameMode;
    }

    public Deck getFirstPlayerDeck() {
        return firstPlayerDeck;
    }

    public void setFirstPlayerDeck(Deck firstPlayerDeck) {
        this.firstPlayerDeck = firstPlayerDeck;
    }

    public Deck getSecondPlayerDeck() {
        return secondPlayerDeck;
    }

    public void setSecondPlayerDeck(Deck secondPlayerDeck) {
        this.secondPlayerDeck = secondPlayerDeck;
    }

    public ArrayList<Card> getFirstPlayerHand() {
        return firstPlayerHand;
    }

    public void setFirstPlayerHand(ArrayList<Card> firstPlayerHand) {
        this.firstPlayerHand = firstPlayerHand;
    }

    public ArrayList<Card> getSecondPlayerHand() {
        return secondPlayerHand;
    }

    public void setSecondPlayerHand(ArrayList<Card> secondPlayerHand) {
        this.secondPlayerHand = secondPlayerHand;
    }

    public String getAttackerCardId() {
        return attackerCardId;
    }

    public void setAttackerCardId(String attackerCardId) {
        this.attackerCardId = attackerCardId;
    }

    public String getAttackedCardId() {
        return attackedCardId;
    }

    public void setAttackedCardId(String attackedCardId) {
        this.attackedCardId = attackedCardId;
    }

    public String getMoveCardId() {
        return moveCardId;
    }

    public void setMoveCardId(String moveCardId) {
        this.moveCardId = moveCardId;
    }

    public int getMoveRow() {
        return moveRow;
    }

    public void setMoveRow(int moveRow) {
        this.moveRow = moveRow;
    }

    public int getMoveColumn() {
        return moveColumn;
    }

    public void setMoveColumn(int moveColumn) {
        this.moveColumn = moveColumn;
    }

    public String getDeathCardId() {
        return deathCardId;
    }

    public void setDeathCardId(String deathCardId) {
        this.deathCardId = deathCardId;
    }
}

