package Duelyst.Model.CommandClasses;

import Duelyst.Model.Account;
import Duelyst.Model.Battle.BattleRecord;
import Duelyst.Model.GameGoal;

public class BattleCommand extends CommandClass {

    private BattleCommandsKind battleCommandsKind;
    private int srcRow, srcCol;
    private int desRow, desCol;
    private String attackerCardId, defenderCardId;
    private String insertSelectedCardId;
    private int insertRow, insertCol;
    private BattleRecord battleRecord;
    private GameGoal gameGoal;
    private Account applicatorAccount;

    public BattleCommand() {
        super(CommandKind.BATTLE);
    }

    public void endTurn() {
        battleCommandsKind = BattleCommandsKind.END_TURN;
    }

    public void start(GameGoal gameGoal, Account account) {
        battleCommandsKind = BattleCommandsKind.START_BATTLE;
        this.gameGoal = gameGoal;
        this.applicatorAccount = account;
    }

    public void move(int srcRow, int srcCol, int desRow, int desCol) {
        battleCommandsKind = BattleCommandsKind.MOVE;
        setSrcRow(srcRow);
        setSrcCol(srcCol);
        setDesRow(desRow);
        setDesCol(desCol);
    }

    public void attack(String attackerCardId, String defenderCardId) {
        battleCommandsKind = BattleCommandsKind.ATTACK;
        setAttackerCardId(attackerCardId);
        setDefenderCardId(defenderCardId);
    }

    public void insert(String insertSelectedCardId, int insertRow, int insertCol) {
        battleCommandsKind = BattleCommandsKind.INSERT;
        setInsertSelectedCardId(insertSelectedCardId);
        setInsertRow(insertRow);
        setInsertCol(insertCol);
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
}
