package Duelyst.Model.Battle;

import Duelyst.Model.*;
import Duelyst.Model.Items.Item;
import com.gilecode.yagson.YaGson;
import com.gilecode.yagson.YaGsonBuilder;

import java.io.*;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.Scanner;

public class BattleRecord {
    private static ArrayList<ArrayList<BattleRecord>> battleRecords = new ArrayList<>();

    private BattleRecordEnum typeOfRecord;

    //FOR INITIALIZE
    private String firstPlayerUsername, secondPlayerUsername;
    private Account firstPlayerAccount, secondPlayerAccount;
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
    private Card moveCard;
    private int fromRow, fromColumn;
    private int moveRow, moveColumn;
    private boolean isMoveHoldFlag = false;
    private boolean isMoveCollectibleFlag = false;
    private boolean isMoveCollectibleItem = false;
    private Flag flag;
    private Item moveItem;

    //FOR DEATH
    private String deathCardId;
    private boolean isHaveFlag = false;
    private Warrior deathWarrior;

    //FOR END GAME
    private boolean isDraw;
    private String winnerUsername, loserUsername;

    //FOR INSERT
    private Card insertCard;
    private boolean isSpellInsert = false;
    private int insertRow, insertColumn;
    private Flag flagForInsertCard;
    private boolean isFlag = false;
    private Item insertCardItem;

    //FOR INSERT FLAG
    private int insertFlagRow, insertFlagColumn;
    private Flag insertFlagItself;

    //FOR INSERT ITEM
    private int insertItemRow, insertItemColumn;
    private Item insertItem;

    //FOR MANA CHANGE
    private int player1Mana, player2Mana, manaMax, numberOfTurn;


    public BattleRecord(BattleRecordEnum typeOfRecord) {
        this.typeOfRecord = typeOfRecord;
    }

    public static void makeJsonOfBattleRecord(ArrayList<BattleRecord> battleRecords) {
        Iteration:
        for (int i = 0; i < 1000; i++) {
            String address = "saved/BattleRecords/" + i + ".json";
            new File("saved/BattleRecords").mkdirs();
            boolean fileExist = new File(address).isFile();
            if (fileExist) {
                continue;
            } else {
                try {
                    YaGson yaGson = new YaGsonBuilder().setPrettyPrinting().create();
                    OutputStream o = new FileOutputStream(address);
                    PrintWriter printWriter = new PrintWriter(o);

                    String s = yaGson.toJson(battleRecords);
                    printWriter.write(s);
                    printWriter.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } finally {
                    break Iteration;
                }
            }
        }
    }

    public static ArrayList<ArrayList<BattleRecord>> loadAllBattleRecords() {
        ArrayList<ArrayList<BattleRecord>> battleRecords = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            String address = "saved/BattleRecords/" + i + ".json";
            boolean fileExist = new File(address).isFile();
            if (fileExist) {
                try {
                    YaGson yaGson = new YaGson();
                    InputStream in = new FileInputStream(address);
                    Scanner scanner = new Scanner(in);

                    String s = scanner.useDelimiter("\\Z").next();

                    System.out.println(i);
                    ArrayList<BattleRecord> battleRecordArrayList = yaGson.fromJson(s, ArrayList.class);
                    System.out.println(i + "loaded");
                    battleRecords.add(battleRecordArrayList);
                    System.out.println(s);
                    scanner.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

            }
        }
        return battleRecords;
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

    public Card getInsertCard() {
        return insertCard;
    }

    public void setInsertCard(Card insertCard) {
        this.insertCard = insertCard;
    }

    public boolean isSpellInsert() {
        return isSpellInsert;
    }

    public void setSpellInsert(boolean spellInsert) {
        isSpellInsert = spellInsert;
    }

    public int getInsertRow() {
        return insertRow;
    }

    public void setInsertRow(int insertRow) {
        this.insertRow = insertRow;
    }

    public int getInsertColumn() {
        return insertColumn;
    }

    public void setInsertColumn(int insertColumn) {
        this.insertColumn = insertColumn;
    }

    public Flag getFlagForInsertCard() {
        return flagForInsertCard;
    }

    public void setFlagForInsertCard(Flag flagForInsertCard) {
        this.flagForInsertCard = flagForInsertCard;
    }

    public boolean isFlag() {
        return isFlag;
    }

    public void setFlag(boolean flag) {
        isFlag = flag;
    }

    public int getInsertFlagRow() {
        return insertFlagRow;
    }

    public void setInsertFlagRow(int insertFlagRow) {
        this.insertFlagRow = insertFlagRow;
    }

    public int getInsertFlagColumn() {
        return insertFlagColumn;
    }

    public void setInsertFlagColumn(int insertFlagColumn) {
        this.insertFlagColumn = insertFlagColumn;
    }

    public Flag getInsertFlagItself() {
        return insertFlagItself;
    }

    public void setInsertFlagItself(Flag insertFlagItself) {
        this.insertFlagItself = insertFlagItself;
    }

    public int getInsertItemRow() {
        return insertItemRow;
    }

    public void setInsertItemRow(int insertItemRow) {
        this.insertItemRow = insertItemRow;
    }

    public int getInsertItemColumn() {
        return insertItemColumn;
    }

    public void setInsertItemColumn(int insertItemColumn) {
        this.insertItemColumn = insertItemColumn;
    }

    public Item getInsertItem() {
        return insertItem;
    }

    public void setInsertItem(Item insertItem) {
        this.insertItem = insertItem;
    }

    public Item getMoveItem() {
        return moveItem;
    }

    public void setMoveItem(Item moveItem) {
        this.moveItem = moveItem;
    }

    public Item getInsertCardItem() {
        return insertCardItem;
    }

    public void setInsertCardItem(Item insertCardItem) {
        this.insertCardItem = insertCardItem;
    }

    public int getPlayer1Mana() {
        return player1Mana;
    }

    public void setPlayer1Mana(int player1Mana) {
        this.player1Mana = player1Mana;
    }

    public int getPlayer2Mana() {
        return player2Mana;
    }

    public void setPlayer2Mana(int player2Mana) {
        this.player2Mana = player2Mana;
    }

    public int getManaMax() {
        return manaMax;
    }

    public void setManaMax(int manaMax) {
        this.manaMax = manaMax;
    }

    public int getNumberOfTurn() {
        return numberOfTurn;
    }

    public void setNumberOfTurn(int numberOfTurn) {
        this.numberOfTurn = numberOfTurn;
    }

    public Account getFirstPlayerAccount() {
        return firstPlayerAccount;
    }

    public void setFirstPlayerAccount(Account firstPlayerAccount) {
        this.firstPlayerAccount = firstPlayerAccount;
    }

    public Account getSecondPlayerAccount() {
        return secondPlayerAccount;
    }

    public void setSecondPlayerAccount(Account secondPlayerAccount) {
        this.secondPlayerAccount = secondPlayerAccount;
    }

    public Card getMoveCard() {
        return moveCard;
    }

    public void setMoveCard(Card moveCard) {
        this.moveCard = moveCard;
    }
}

