package Duelyst.Model.CommandClasses;

import Duelyst.Model.Account;
import Duelyst.Model.Battle.BattleRecord;
import Duelyst.Model.GameGoal;

import java.util.ArrayList;

public class BattleCommand extends CommandClass {

    private Account myAccount;
    private BattleCommandsKind battleCommandsKind;
    private int srcRow, srcCol;
    private int desRow, desCol;
    private String attackerCardId, defenderCardId;
    private String insertSelectedCardId;
    private int insertRow, insertCol;
    private GameGoal gameGoal;
    private Account applicatorAccount;
    private Account opponent;
    private Account canceler;
    private boolean firstPlayer;
    private Account loser;
    private int[] randomXForCollectFlag;
    private int[] randomYForCollectFlag;
    private ArrayList<BattleRecord> battleRecords = new ArrayList<>();


    public BattleCommand() {
        super(CommandKind.BATTLE);
    }

    public void cancelRequest(Account canceler) {
        battleCommandsKind = BattleCommandsKind.CANCEL_REQUEST;
        this.canceler = canceler;
    }

    public void quitGame(Account loser) {
        this.loser = loser;
        battleCommandsKind = BattleCommandsKind.END_GAME;
    }

    public void acceptRequest(Account opponent, boolean firstPlayer) {
        this.opponent = opponent;
        battleCommandsKind = BattleCommandsKind.ACCEPT_REQUEST;
        this.firstPlayer = firstPlayer;
    }

    public void endTurn(Account myAccount, ArrayList<BattleRecord> battleRecords) {
        battleCommandsKind = BattleCommandsKind.END_TURN;
        this.myAccount = myAccount;
        this.battleRecords = battleRecords;
    }
    public void forceEndTurn(){
        battleCommandsKind=BattleCommandsKind.FORCE_END_TURN;
    }

    public void endTurnWarnning(){
        battleCommandsKind=BattleCommandsKind.END_TURN_WARNING;
    }

    public void start(GameGoal gameGoal, Account account) {
        battleCommandsKind = BattleCommandsKind.START_BATTLE;
        this.gameGoal = gameGoal;
        this.applicatorAccount = account;
    }

    public void move(int srcRow, int srcCol, int desRow, int desCol, Account myAccount) {
        battleCommandsKind = BattleCommandsKind.MOVE;
        setSrcRow(srcRow);
        setSrcCol(srcCol);
        setDesRow(desRow);
        setDesCol(desCol);
        this.myAccount = myAccount;
    }

    public void attack(String attackerCardId, String defenderCardId, Account myAccount) {
        battleCommandsKind = BattleCommandsKind.ATTACK;
        setAttackerCardId(attackerCardId);
        setDefenderCardId(defenderCardId);
        this.myAccount = myAccount;
    }

    public void insert(String insertSelectedCardId, int insertRow, int insertCol, Account myAccount) {
        battleCommandsKind = BattleCommandsKind.INSERT;
        setInsertSelectedCardId(insertSelectedCardId);
        setInsertRow(insertRow);
        setInsertCol(insertCol);
        this.myAccount = myAccount;
    }

    public int getSrcRow() {
        return srcRow;
    }

    public int getSrcCol() {
        return srcCol;
    }

    public int getDesRow() {
        return desRow;
    }

    public int getDesCol() {
        return desCol;
    }

    public String getAttackerCardId() {
        return attackerCardId;
    }

    public String getDefenderCardId() {
        return defenderCardId;
    }

    public void setSrcRow(int srcRow) {
        this.srcRow = srcRow;
    }

    public void setSrcCol(int srcCol) {
        this.srcCol = srcCol;
    }

    public void setDesRow(int desRow) {
        this.desRow = desRow;
    }

    public void setDesCol(int desCol) {
        this.desCol = desCol;
    }

    public void setAttackerCardId(String attackerCardId) {
        this.attackerCardId = attackerCardId;
    }

    public void setDefenderCardId(String defenderCardId) {
        this.defenderCardId = defenderCardId;
    }

    public BattleCommandsKind getBattleCommandsKind() {
        return battleCommandsKind;
    }

    public String getInsertSelectedCardId() {
        return insertSelectedCardId;
    }

    public void setInsertSelectedCardId(String insertSelectedCardId) {
        this.insertSelectedCardId = insertSelectedCardId;
    }

    public int getInsertRow() {
        return insertRow;
    }

    public void setInsertRow(int insertRow) {
        this.insertRow = insertRow;
    }

    public int getInsertCol() {
        return insertCol;
    }

    public void setInsertCol(int insertCol) {
        this.insertCol = insertCol;
    }


    public Account getApplicatorAccount() {
        return applicatorAccount;
    }

    public void setApplicatorAccount(Account applicatorAccount) {
        this.applicatorAccount = applicatorAccount;
    }

    public GameGoal getGameGoal() {
        return gameGoal;
    }

    public void setGameGoal(GameGoal gameGoal) {
        this.gameGoal = gameGoal;
    }

    public Account getOpponent() {
        return opponent;
    }

    public void setOpponent(Account opponent) {
        this.opponent = opponent;
    }

    public Account getCanceler() {
        return canceler;
    }

    public void setCanceler(Account canceler) {
        this.canceler = canceler;
    }

    public boolean isFirstPlayer() {
        return firstPlayer;
    }

    public void setFirstPlayer(boolean firstPlayer) {
        this.firstPlayer = firstPlayer;
    }

    public Account getMyAccount() {
        return myAccount;
    }

    public void setMyAccount(Account myAccount) {
        this.myAccount = myAccount;
    }

    public Account getLoser() {
        return loser;
    }

    public void setLoser(Account loser) {
        this.loser = loser;
    }

    public ArrayList<BattleRecord> getBattleRecords() {
        return battleRecords;
    }

    public void setBattleRecords(ArrayList<BattleRecord> battleRecords) {
        this.battleRecords = battleRecords;
    }

    public int[] getRandomXForCollectFlag() {
        return randomXForCollectFlag;
    }

    public void setRandomXForCollectFlag(int[] randomXForCollectFlag) {
        this.randomXForCollectFlag = randomXForCollectFlag;
    }

    public int[] getRandomYForCollectFlag() {
        return randomYForCollectFlag;
    }

    public void setRandomYForCollectFlag(int[] randomYForCollectFlag) {
        this.randomYForCollectFlag = randomYForCollectFlag;
    }
}
