package Duelyst.Model.Battle;

import Duelyst.Model.*;

import java.util.ArrayList;

public class BattleRecord {
    private static ArrayList<ArrayList<BattleRecord>> battleRecords = new ArrayList<>();

    private BattleRecordEnum typeOfRecord;

    //FOR INITIALIZE
    private String firstPlayerUsername, secondPlayerUsername;
    private GameGoal gameGoal;
    private GameMode gameMode;
    private Deck firstPlayerDeck;
    private Deck secondPlayerDeck;
    private ArrayList<Card> firstPlayerHand;
    private ArrayList<Card> secondPlayerHand;


    //FOR ATTACK
    private String attackerCardId, attackedCardId;
    private boolean hasBuff = false;
    private boolean hasCounterAttack = false;
    private Warrior attacker, attacked;

    //FOR MOVE
    private String moveCardId;
    private int fromRow, fromColumn;
    private int moveRow, moveColumn;
    private boolean isMoveHoldFlag = false;
    private boolean isMoveCollectibleFlag = false;
    private boolean isMoveCollectibleItem = false;
    private Flag flag;

    //FOR DEATH
    private String deathCardId;
    private boolean isHaveFlag = false;
    private Warrior deathWarrior;

    //FOR END GAME
    private boolean isDraw;
    private String winnerUsername, loserUsername;


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

    public boolean isMoveHoldFlag() {
        return isMoveHoldFlag;
    }

    public void setMoveHoldFlag(boolean moveHoldFlag) {
        isMoveHoldFlag = moveHoldFlag;
    }

    public boolean isMoveCollectibleFlag() {
        return isMoveCollectibleFlag;
    }

    public void setMoveCollectibleFlag(boolean moveCollectibleFlag) {
        isMoveCollectibleFlag = moveCollectibleFlag;
    }

    public boolean isMoveCollectibleItem() {
        return isMoveCollectibleItem;
    }

    public void setMoveCollectibleItem(boolean moveCollectibleItem) {
        isMoveCollectibleItem = moveCollectibleItem;
    }

    public boolean isHasBuff() {
        return hasBuff;
    }

    public void setHasBuff(boolean hasBuff) {
        this.hasBuff = hasBuff;
    }

    public boolean isHasCounterAttack() {
        return hasCounterAttack;
    }

    public void setHasCounterAttack(boolean hasCounterAttack) {
        this.hasCounterAttack = hasCounterAttack;
    }

    public boolean isHaveFlag() {
        return isHaveFlag;
    }

    public void setHaveFlag(boolean haveFlag) {
        isHaveFlag = haveFlag;
    }

    public boolean isDraw() {
        return isDraw;
    }

    public void setDraw(boolean draw) {
        isDraw = draw;
    }

    public String getWinnerUsername() {
        return winnerUsername;
    }

    public void setWinnerUsername(String winnerUsername) {
        this.winnerUsername = winnerUsername;
    }

    public String getLoserUsername() {
        return loserUsername;
    }

    public void setLoserUsername(String loserUsername) {
        this.loserUsername = loserUsername;
    }

    public void setFromRow(int fromRow) {
        this.fromRow = fromRow;
    }

    public void setFromColumn(int fromColumn) {
        this.fromColumn = fromColumn;
    }

    public int getFromRow() {
        return fromRow;
    }

    public int getFromColumn() {
        return fromColumn;
    }

    public Flag getFlag() {
        return flag;
    }

    public void setFlag(Flag flag) {
        this.flag = flag;
    }

    public Warrior getAttacker() {
        return attacker;
    }

    public void setAttacker(Warrior attacker) {
        this.attacker = attacker;
    }

    public Warrior getAttacked() {
        return attacked;
    }

    public void setAttacked(Warrior attacked) {
        this.attacked = attacked;
    }

    public Warrior getDeathWarrior() {
        return deathWarrior;
    }

    public void setDeathWarrior(Warrior deathWarrior) {
        this.deathWarrior = deathWarrior;
    }
}

