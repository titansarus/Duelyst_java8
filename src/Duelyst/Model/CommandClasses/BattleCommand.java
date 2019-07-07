package Duelyst.Model.CommandClasses;

import Duelyst.Model.Account;
import Duelyst.Model.Battle.BattleRecord;
import Duelyst.Model.GameGoal;
import javafx.scene.layout.BorderPane;

public class BattleCommand extends CommandClass {

    private Account myAccount;
    private BattleCommandsKind battleCommandsKind;
    private int srcRow, srcCol;
    private int desRow, desCol;
    private String attackerCardId, defenderCardId;
    private String insertSelectedCardId;
    private int insertRow, insertCol;
    private BattleRecord battleRecord;
    private GameGoal gameGoal;
    private Account applicatorAccount;
    private Account opponent;
    private Account canceler;
    private boolean firstPlayer;

    public BattleCommand() {
        super(CommandKind.BATTLE);
    }

    public void cancelRequest(Account canceler) {
        battleCommandsKind = BattleCommandsKind.CANCEL_REQUEST;
        this.canceler = canceler;
    }

    public void acceptRequest(Account opponent, boolean firstPlayer) {
        this.opponent = opponent;
        battleCommandsKind = BattleCommandsKind.ACCEPT_REQUEST;
        this.firstPlayer = firstPlayer;
    }

    public void endTurn(Account myAccount) {
        battleCommandsKind = BattleCommandsKind.END_TURN;
        this.myAccount = myAccount;
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

    public BattleRecord getBattleRecord() {
        return battleRecord;
    }

    public void setBattleRecord(BattleRecord battleRecord) {
        this.battleRecord = battleRecord;
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
}
