package Duelyst.Model.Battle;

import Duelyst.Controllers.BattleController;
import Duelyst.Exceptions.CellFilledBeforeException;
import Duelyst.Exceptions.NotEnoughManaException;
import Duelyst.Exceptions.NotValidCellForSpellException;
import Duelyst.Model.*;
import Duelyst.Model.Buffs.ApplyBuff;
import Duelyst.Model.Buffs.Buff;
import Duelyst.Model.Buffs.HolyBuff;
import Duelyst.Model.Items.*;
import Duelyst.Model.Spell.Spell;
import com.rits.cloning.Cloner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import static Duelyst.View.Constants.*;

public class Battle implements Cloneable {

    private static ArrayList<Battle> unfinishedBattles = new ArrayList<>();

    private static Battle runningBattle;
    private BattleController battleController;
    private Player player1;
    private Player player2;
    private Player playingPlayer;
    private Cell[][] grid = new Cell[BATTLE_ROWS][BATTLE_COLUMNS];
    private int turn = 0;//At First next turn is invocked and first turn will be 1
    private Card selectedCard;
    private Card attackedCard;
    private Cell selectedCell;
    private ArrayList<Buff> onSpawnBuffs = new ArrayList<>();
    private ArrayList<Buff> onDefendBuffs = new ArrayList<>();
    private ArrayList<Buff> onAttackBuffs = new ArrayList<>();
    private ArrayList<Buff> onDeathBuffs = new ArrayList<>();
    private ArrayList<Buff> passiveBuffs = new ArrayList<>();
    private ArrayList<Cell> validCells = new ArrayList<>();
    private GameGoal gameGoal;
    private GameMode gameMode;
    private Account winner;
    private boolean draw = false;
    private boolean endGame;
    private Flag holdFlag;
    private ArrayList<Flag> collectableFlags;
    private ArrayList<BattleRecord> battleRecords;
    private int lastBattleRecordPlayed = 0;


    public static final int VALID_COUNTER_WITH_BUFF = 1, VALID_COUNTER_WITHOUT_BUFF = 2, INVALID_COUNTER_WITH_BUFF = 3, INVALID_COUNTER_WITHOUT_BUFF = 4;


    public static Battle deepClone(Battle battle) {
        Cloner cloner = new Cloner();
        cloner.dontClone(BattleController.class);
        return cloner.deepClone(battle);
    }

    public static ArrayList<Battle> getUnfinishedBattles() {
        return unfinishedBattles;
    }

    public void initializeCells() {
        for (int i = 0; i < BATTLE_ROWS; i++) {
            for (int j = 0; j < BATTLE_COLUMNS; j++) {
                getGrid()[i][j] = new Cell(i, j);
            }
        }
    }

    public Cell findCellOfWarrior(Warrior warrior) {
        for (int i = 0; i < BATTLE_ROWS; i++) {
            for (int j = 0; j < BATTLE_COLUMNS; j++) {
                if (grid[i][j] != null && grid[i][j].getWarrior() != null && grid[i][j].getWarrior().equals(warrior)) {
                    return grid[i][j];
                }
            }
        }
        return null;
    }

    public Battle(Account account1, Account account2, GameMode gameMode, GameGoal gameGoal, BattleController battleController) {
        this.gameGoal = gameGoal;
        this.gameMode = gameMode;
        setBattleController(battleController);
        battleController.setBattle(this);
        battleController.makeGrids();
        runningBattle = this;
        if (account2 instanceof Ai) {
            ((Ai) account2).setBattle(this);
        }
        initValidCounter(account1, account2);
        setPlayer1(new Player(account1, account1.getCardCollection().getMainDeck()));
        setPlayer2(new Player(account2, account2.getCardCollection().getMainDeck()));

        setPlayerOfItem(player1);
        setPlayerOfItem(player2);


        setPlayingPlayer();

        initializeCells();
        insertPlayerHeroesInMap();

        if (gameGoal == GameGoal.COLLECT_FLAG) {
            collectableFlags = new ArrayList<>();
            setFlagForCollectFlagGameModes();
        } else if (gameGoal == GameGoal.HOLD_FLAG) {
            holdFlag = new Flag(KindOfFlag.HOLD_FLAG, 2, 4);
            getGrid()[2][4].setFlag(holdFlag);
            battleController.initFlagImages();
        }

        battleRecords = new ArrayList<>();
        BattleRecord.getBattleRecords().add(battleRecords);

        makeInitializeBattleRecord(account1, account2);

        nextTurn();
    }

    private void makeInitializeBattleRecord(Account account1, Account account2) {
        BattleRecord battleRecord = new BattleRecord(BattleRecordEnum.INITIALIZE);
        battleRecord.setFirstPlayerUsername(account1.getUsername());
        battleRecord.setSecondPlayerUsername(account2.getUsername());

        getBattleRecords().add(battleRecord);
    }


    private void initValidCounter(Account account1, Account account2) {
        account1.getCardCollection().getMainDeck().getHero().setValidCounterAttack(true);
        account2.getCardCollection().getMainDeck().getHero().setValidCounterAttack(true);
        for (Minion m :
                account1.getCardCollection().getMainDeck().getMinions()) {
            m.setValidCounterAttack(true);
        }
        for (Minion m :
                account2.getCardCollection().getMainDeck().getMinions()) {
            m.setValidCounterAttack(true);
        }
    }

    private void setPlayerOfItem(Player player1) {
        if (player1.getDeck().getItem() != null) {
            player1.getDeck().getItem().setPlayer(player1);
        }
    }


    private void insertPlayerHeroesInMap() {
        getGrid()[2][0].setWarrior(player1.getDeck().getHero());
        getGrid()[2][8].setWarrior(player2.getDeck().getHero());
    }

    public void nextTurn() {

        if (getPlayingPlayer().getCollectibleItem() != null && getPlayingPlayer().getCollectibleItem().isApplyFirst()) {
            getPlayingPlayer().getCollectibleItem().applyItem();
        }


        if (getPlayingPlayer().getDeck().getItem() instanceof TajeDanaei) {
            getPlayingPlayer().getDeck().getItem().applyItem();
        }

        if (getTurn() % 6 == 0) {
            battleController.randomCollectibleItemGenerator();
        }

        if (gameGoal == GameGoal.HOLD_FLAG && holdFlag.getWarrior() != null) {
            holdFlag.setNumberOfTurn(holdFlag.getNumberOfTurn() + 1);
        }
        applyPassiveAndSpawnBuffs(onSpawnBuffs);
        applyPassiveAndSpawnBuffs(passiveBuffs);
        if (!endGame) {
            endGame();
        }

        setTrueOfValidAttackAndMove();
        turn++;
        setPlayingPlayer();
        getPlayer1().setManaFromTurn(getTurn());
        getPlayer2().setManaFromTurn(getTurn());
        setSelectedCell(null);
        setSelectedCard(null);
        getPlayingPlayer().getNextHand();


        if (getPlayingPlayer().getAccount() instanceof Ai) {
            System.out.println("AI");
            ((Ai) getPlayingPlayer().getAccount()).playGame();

            ((Ai) getPlayingPlayer().getAccount()).getBattleController().handleEndTurnBtn();
        }
        makeBattleRecordOfEndTurn();
    }

    private void applyPassiveAndSpawnBuffs(ArrayList<Buff> onSpawnBuffs) {
        for (Buff b1 :
                onSpawnBuffs) {
            ApplyBuff.getInstance().applyBuff(b1);
        }
    }

    private void makeBattleRecordOfEndTurn() {
        BattleRecord battleRecord = new BattleRecord(BattleRecordEnum.END_TURN);

        getBattleRecords().add(battleRecord);
    }

    private void setTrueOfValidAttackAndMove() {
        for (Cell[] c :
                getGrid()) {
            for (Cell c1 :
                    c) {
                if (c1.getWarrior() != null && !c1.getWarrior().isStun()) {
                    c1.getWarrior().setValidToAttack(true);
                    c1.getWarrior().setValidToMove(true);
                }
            }
        }
    }

    public void move(int destX, int destY) {

        boolean isHoldFlag = false, isCollectibleFlag = false, isCollectibleItem = false;
        int fromRow = -1, fromColumn = -1;

        if (getSelectedCell().getWarrior() != null) {
            if (getSelectedCell().getColumn() != destX || getSelectedCell().getRow() != destY) {
                Cell destCell = getGrid()[destX][destY];
                Cell srcCell = findCellOfWarrior(getSelectedCell().getWarrior());
                fromRow = srcCell.getRow();
                fromColumn = srcCell.getColumn();
                if (destCell.getWarrior() == null) {
                    if (getGrid()[destX][destY].getCollectibleItem() != null) {
                        getPlayingPlayer().setCollectibleItem(getGrid()[destX][destY].getCollectibleItem());
                        getGrid()[destX][destY].getCollectibleItem().setPlayer(getPlayingPlayer());
                        battleController.deleteItemImage(getGrid()[destX][destY].getCollectibleItem());
                        isCollectibleItem = true;//FOR BATTLE RECORD
                    }
                    if (gameGoal == GameGoal.HOLD_FLAG) {
                        if (holdFlag.getX() == destX && holdFlag.getY() == destY) {
                            holdFlag.setWarrior(getSelectedCell().getWarrior());
                            getGrid()[destX][destY].setFlag(null);
                            battleController.removeFlagImage(holdFlag);
                            isHoldFlag = true; //FOR BATTLE RECORD
                        }
                    }
                    if (gameGoal == GameGoal.COLLECT_FLAG) {
                        for (Flag f :
                                collectableFlags) {
                            if (f.getX() == destX && f.getY() == destY) {
                                playingPlayer.setNumberOfFlag(playingPlayer.getNumberOfFlag() + 1);
                                getGrid()[destX][destY].setFlag(null);
                                battleController.removeFlagImage(f);
                                isCollectibleFlag = true; //FOR BATTLE RECORD
                            }
                        }
                    }
                    makeBattleRecordOfMove(getSelectedCell().getWarrior(), destX, destY, isHoldFlag, isCollectibleFlag, isCollectibleItem,fromRow,fromColumn); //BATTLE RECORD
                    getSelectedCell().getWarrior().setValidToMove(false);
                    destCell.setWarrior(getSelectedCell().getWarrior());
                    getSelectedCell().setWarrior(null);
                    setSelectedCell(null);

                }
            }
        }

    }

    public void makeBattleRecordOfMove(Warrior warrior, int row, int column, boolean isHoldFlag, boolean isCollectibleFlag, boolean isCollectibleItem, int fromRow, int fromColumn) {
        BattleRecord battleRecord = new BattleRecord(BattleRecordEnum.MOVE);

        battleRecord.setMoveCardId(warrior.getCardId());
        battleRecord.setMoveRow(row);
        battleRecord.setMoveColumn(column);
        battleRecord.setFromColumn(fromColumn);
        battleRecord.setFromRow(fromRow);

        if (isHoldFlag) {
            battleRecord.setMoveHoldFlag(true);
        }
        if (isCollectibleFlag) {
            battleRecord.setMoveCollectibleFlag(true);
        }
        if (isCollectibleItem) {
            battleRecord.setMoveCollectibleItem(true);
        }
        getBattleRecords().add(battleRecord);
    }

    public void insertSelectedCard(int i, int j) {

        if (getSelectedCard() instanceof Warrior) {
            if (getGrid()[i][j].isEmpty()) {
                if (getPlayingPlayer().getMana() >= getSelectedCard().getManaCost()) {
                    getGrid()[i][j].setWarrior((Warrior) getSelectedCard());
                    Warrior warrior = getGrid()[i][j].getWarrior();

                    if (getGrid()[i][j].getCollectibleItem() != null) {
                        getPlayingPlayer().setCollectibleItem(getGrid()[i][j].getCollectibleItem());
                        battleController.deleteItemImage(getGrid()[i][j].getCollectibleItem());
                        getGrid()[i][j].getCollectibleItem().setPlayer(getPlayingPlayer());
                    }

                    deleteDeathCardsFromMap(); // Check For Death Cards

                    warrior.setInGame(true);

                    if (gameGoal == GameGoal.HOLD_FLAG) {
                        if (holdFlag.getX() == i && holdFlag.getY() == j) {
                            holdFlag.setWarrior(getSelectedCell().getWarrior());
                            battleController.removeFlagImage(holdFlag);
                            getGrid()[i][j].setFlag(null);
                        }
                    }
                    if (gameGoal == GameGoal.COLLECT_FLAG) {
                        for (Flag f :
                                collectableFlags) {
                            if (f.getX() == i && f.getY() == j) {
                                playingPlayer.setNumberOfFlag(playingPlayer.getNumberOfFlag() + 1);
                                battleController.removeFlagImage(f);
                                getGrid()[i][j].setFlag(null);
                            }
                        }
                    }


                    warrior.setValidToMove(false);
                    warrior.setValidToAttack(false);
                    getPlayingPlayer().getHand().remove(warrior);

                    getPlayingPlayer().changeMana(-warrior.getManaCost());
                    playingPlayer.getInGameCards().add(getSelectedCard());

                    if (getPlayingPlayer().getDeck().getItem() instanceof AssassinationDagger) {//AssassinationDagger Item Apply
                        getPlayingPlayer().getDeck().getItem().applyItem();
                    } else if (getPlayingPlayer().getDeck().getItem() instanceof GhosleTaemid) {//GhosleTaemid Item Apply
                        Buff buff = new HolyBuff(2, 1);
                        buff.setWarrior(warrior);
                        getPassiveBuffs().add(buff);
                    }


                } else {
                    throw new NotEnoughManaException();
                }
            } else {

                throw new CellFilledBeforeException();
            }
        } else if (getSelectedCard() instanceof Spell) {
            System.out.println("spell !! ");
            findValidCell(KindOfActionForValidCells.SPELL);

            for (Cell c :
                    validCells) {
                System.out.println(c.getRow() + "  -  " + c.getColumn());
            }

            if (!validCells.contains(getGrid()[i][j])) {
                throw new NotValidCellForSpellException();
            }
            Spell spell = (Spell) getSelectedCard();
            ArrayList<Buff> buffs = spell.getBuffs();
            getPlayingPlayer().getHand().remove(getSelectedCard());
            for (Buff b :
                    buffs) {
                switch (spell.getTargetCommunity()) {
                    case ENEMY_WARRIOR:
                    case FRIENDLY_WARRIOR:
                        b.setWarrior(getGrid()[i][j].getWarrior());
                        ApplyBuff.getInstance().applyBuff(b);
                        break;
                    case ALL_OF_FRIEND:
                        applyAllTargetBuffs(b, playingPlayer);
                        break;
                    case ALL_OF_ENEMY:
                        applyAllTargetBuffs(b, (player1.equals(playingPlayer)) ? player2 : player1);
                        break;
                    case CELLS:
                        b.setCell(getGrid()[i][j]);

                }
                ApplyBuff.getInstance().applyBuff(b);
            }
        }
        if (!endGame) {
            endGame();
        }
    }

    private void applyAllTargetBuffs(Buff b, Player player) {
        for (Card card :
                player.getInGameCards()) {
            if (card instanceof Warrior) {
                b.setWarrior((Warrior) card);
                ApplyBuff.getInstance().applyBuff(b);
            }
        }
    }


    public Integer calculateMaxAmountOfMana() {
        int res = Math.floorDiv(getTurn(), 2) + 2;
        return res > 9 ? 9 : res;
    }

    public int[] findCellCoordinate(Cell cell) {
        int[] result = new int[]{-1, -1};
        for (int i = 0; i < BATTLE_ROWS; i++) {
            for (int j = 0; j < BATTLE_COLUMNS; j++) {
                if (cell.equals(getGrid()[i][j])) {
                    result[0] = i;
                    result[1] = j;
                    return result;
                }
            }
        }
        return result;
    }

    public int attack(Warrior attacker, Warrior attackedCard, boolean isFromCounterAttack) {

        attackedCard.decreaseHealthPoint(attacker.getActionPower() - attackedCard.getShield());//TODO CHECK FOR BUFF
        this.attackedCard = attackedCard;
        //KamaneDamol Item Apply

        if (getPlayingPlayer().getDeck().getItem() instanceof KamaneDamol && attacker.equals(getPlayingPlayer().getDeck().getHero())) {
            getPlayingPlayer().getDeck().getItem().applyItem();
        } else if (attacker.equals(getPlayingPlayer().getDeck().getHero()) && getPlayingPlayer().getDeck().getItem() instanceof ShockHammer) {
            getPlayingPlayer().getDeck().getItem().applyItem();
        } else if (getPlayingPlayer().getDeck().getItem() instanceof TerrorHood) {
            getPlayingPlayer().getDeck().getItem().applyItem();
        }

        if (getPlayingPlayer().getCollectibleItem() instanceof PoisonousDagger) {
            getPlayingPlayer().getCollectibleItem().applyItem();
        }

        applyBuffOnAttackAndOnDefend(attacker, onAttackBuffs);
        applyBuffOnAttackAndOnDefend(attackedCard, onDefendBuffs);
        if (!isFromCounterAttack) {
            attacker.setValidToMove(false);
            attacker.setValidToAttack(false);
        }

        if (isFromCounterAttack || (!attackedCard.isValidCounterAttack())) {
            deleteDeathCardsFromMap(); // Check For Death Cards
            if (!endGame) {
                endGame();
            }
        }

        setSelectedCell(null);

        //TODO CHECK FOR COUNTER ATTACK AND BUFF AND A LOT OF THINGS
        if (!isFromCounterAttack && !attackedCard.isValidCounterAttack()) {
            makeBattleRecordOfAttack(attacker, attackedCard, true, false);
            return INVALID_COUNTER_WITH_BUFF;
        }
        if (!isFromCounterAttack) {
            makeBattleRecordOfAttack(attacker, attackedCard, true, true);
            return VALID_COUNTER_WITH_BUFF; //RETURN DETERMINES THE CONTROLLER TO SHOW BUFF ANIMATION OR NOT? DO COUNTER ATTACK OR NOT?
        } else {
            makeBattleRecordOfAttack(attacker, attackedCard, true, false);
            return INVALID_COUNTER_WITH_BUFF;
        }

    }

    private void makeBattleRecordOfAttack(Warrior attacker, Warrior attacked, boolean isBuff, boolean isValidCounter) {
        BattleRecord battleRecord = new BattleRecord(BattleRecordEnum.ATTACK);
        battleRecord.setHasBuff(isBuff);
        battleRecord.setHasCounterAttack(isValidCounter);
        battleRecord.setAttackerCardId(attacker.getCardId());
        battleRecord.setAttackedCardId(attacked.getCardId());

        getBattleRecords().add(battleRecord);

    }

    private void applyBuffOnAttackAndOnDefend(Warrior warrior, ArrayList<Buff> buffs) {
        for (Buff b1 :
                buffs) {
            if (b1.getWarrior().equals(warrior)) {
                ApplyBuff.getInstance().applyBuff(b1);
            }
        }
    }

    public void deleteDeathCardsFromMap() {
        ArrayList<Card> firstDeathCards = findDeathCards(getPlayer1().getInGameCards());
        ArrayList<Card> secondDeathCards = findDeathCards(getPlayer2().getInGameCards());

        System.out.println("==============================> Check Death Cards " + firstDeathCards.size());
        System.out.println(getPlayer1().getInGameCards().size());

        deleteFromMap(firstDeathCards);
        deleteFromMap(secondDeathCards);
        addUsedCardsToGraveYard(firstDeathCards, secondDeathCards);
    }

    private void deleteFromMap(ArrayList<Card> cards) {
        for (Card card : cards) {
            boolean isHaveFlag = false;
            applyDeathBuff(card);

            if (gameGoal == GameGoal.HOLD_FLAG && holdFlag.getWarrior().equals(card)) {
                getCellOfWarrior((Warrior) card).setFlag(holdFlag);
                holdFlag.setWarrior(null);
                holdFlag.setNumberOfTurn(0);
                battleController.initFlagImages();
                isHaveFlag = true;
            }
//            battleController.animationOfDeath((Warrior) card);
//            battleController.removeImageViewFromCell(card);
            makeBattleRecordOfDeath(card, isHaveFlag); //FOR BATTLE RECORD
            getCellOfWarrior((Warrior) card).setWarrior(null);

        }
    }

    private void makeBattleRecordOfDeath(Card card, boolean isHaveFlag) {
        BattleRecord battleRecord = new BattleRecord(BattleRecordEnum.DEATH);
        battleRecord.setDeathCardId(card.getCardId());
        battleRecord.setHaveFlag(isHaveFlag);

        getBattleRecords().add(battleRecord);
    }

    private void applyDeathBuff(Card card) {
        for (Buff b :
                onDeathBuffs) {
            if (b.getWarrior().equals(card)) {
                ApplyBuff.getInstance().applyBuff(b);
            }
        }
    }

    private void addUsedCardsToGraveYard(ArrayList<Card> firstDeathCards, ArrayList<Card> secondDeathCards) {

        for (int i = 0; i < getPlayer1().getDeck().getCards().size(); i++) {
            if (getPlayer1().getDeck().getCards().get(i) instanceof Spell && getPlayer1().getDeck().getCards().get(i).isInGame()) {
                getPlayer1().getDeck().getCards().add(getPlayer1().getDeck().getCards().get(i));
            }
        }
        for (int i = 0; i < getPlayer2().getDeck().getCards().size(); i++) {
            if (getPlayer2().getDeck().getCards().get(i) instanceof Spell && getPlayer2().getDeck().getCards().get(i).isInGame()) {
                getPlayer2().getDeck().getCards().add(getPlayer2().getDeck().getCards().get(i));
            }
        }
        getPlayer1().getGraveyard().addAll(firstDeathCards);
        getPlayer1().getInGameCards().removeAll(firstDeathCards);
        getPlayer2().getGraveyard().addAll(secondDeathCards);
        getPlayer2().getInGameCards().removeAll(secondDeathCards);
    }

    private ArrayList<Card> findDeathCards(ArrayList<Card> playerInGameCards) {

        ArrayList<Card> deathCards = new ArrayList<>();
        for (Card playerInGameCard : playerInGameCards) {
            if (playerInGameCard.isInGame() && (playerInGameCard instanceof Warrior)) {
//                System.out.println(((Warrior) playerInGameCard).getHealthPoint() + " <====================================]]");
                if (((Warrior) playerInGameCard).getHealthPoint() <= 0) {
                    if (getPlayingPlayer().getInGameCards().contains(playerInGameCard) && getPlayingPlayer().getDeck().getItem() instanceof SoulEater) {
                        getPlayingPlayer().getDeck().getItem().applyItem();
                    }
                    deathCards.add(playerInGameCard);
                    playerInGameCard.setInGame(false);
                }
            }
        }
        return deathCards;
    }


    public Player getPlayer1() {
        return player1;
    }

    private void setPlayer1(Player player1) {
        this.player1 = player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    private void setPlayer2(Player player2) {
        this.player2 = player2;
    }

    public Cell[][] getGrid() {
        return grid;
    }

    public void setGrid(Cell[][] grid) {
        this.grid = grid;
    }

    public int getTurn() {
        return turn;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }

    public Player getPlayingPlayer() {
        return playingPlayer;
    }

    private void setPlayingPlayer() {
        if (turn % 2 == 1) {
            this.playingPlayer = player1;
            return;
        }
        this.playingPlayer = player2;
    }

    public void setPlayingPlayer(Player playingPlayer) {
        this.playingPlayer = playingPlayer;
    }

    public Card getSelectedCard() {
        return selectedCard;
    }

    public void setSelectedCard(Card selectedCard) {
        this.selectedCard = selectedCard;
    }

    public Cell getSelectedCell() {
        return selectedCell;
    }

    public void setSelectedCell(Cell selectedCell) {
        this.selectedCell = selectedCell;
    }

    public ArrayList<Buff> getOnAttackBuffs() {
        return onAttackBuffs;
    }

    public ArrayList<Buff> getOnDeathBuffs() {
        return onDeathBuffs;
    }

    public ArrayList<Buff> getOnDefendBuffs() {
        return onDefendBuffs;
    }

    public ArrayList<Buff> getOnSpawnBuffs() {
        return onSpawnBuffs;
    }

    public ArrayList<Buff> getPassiveBuffs() {
        return passiveBuffs;
    }

    public static Battle getRunningBattle() {
        return runningBattle;
    }

    public Card getAttackedCard() {
        return attackedCard;
    }

    public void setAttackedCard(Card attackedCard) {
        this.attackedCard = attackedCard;
    }


    private void clearValidCellsList() {
        getValidCells().clear();
    }

    public ArrayList<Cell> getValidCells() {
        return validCells;
    }


    public void findValidCell(KindOfActionForValidCells kindOfActionForValidCells) {

        clearValidCellsList();
        switch (kindOfActionForValidCells) {
            case MOVE:
                findValidCellToMove();
                break;
            case ATTACK:
                findValidCellToAttack();
                break;
            case INSERT:
                findValidCellToInsert();
                break;
            case ITEM:
                findValidCellToItem();
                break;
            case SPELL:
                findValidCellToSpell();
                break;
        }
    }

    private void findValidCellToMove() {
        if (!getSelectedCell().getWarrior().isValidToMove())
            return;
        Warrior warrior = getSelectedCell().getWarrior();
        for (Cell[] cell : getGrid()) {
            for (Cell cell1 : cell) {
                if (cell1.isEmpty() && getDistanceOfTwoCell(getCellOfWarrior(warrior), cell1) <= 2)
                    getValidCells().add(cell1);
            }
        }
    }

    private void findValidCellToAttack() {

        Cell[][] cells = getGrid();
        Warrior warrior = getSelectedCell().getWarrior();
        for (Cell[] cell : cells) {
            for (Cell cell1 : cell) {
                if (!cell1.isEmpty() && isValidAttack(cell1, warrior))
                    getValidCells().add(cell1);
            }
        }
    }

    private void findValidCellToInsert() {

        Cell[][] cells = getGrid();

        for (Cell[] cell : cells) {
            for (Cell cell1 : cell) {
                if (isValidInsert(cell1))
                    getValidCells().add(cell1);
            }
        }
    }

    private void findValidCellToItem() {

    }

    private void findValidCellToSpell() {
        ArrayList<Cell> cells = new ArrayList<>();
        switch (((Spell) getSelectedCard()).getTargetCommunity()) {
            case FRIENDLY_WARRIOR:
                findValidCellForSpell(cells, playingPlayer);
                break;
            case ENEMY_WARRIOR:
                Player player = (player1.equals(playingPlayer)) ? player2 : player1;
                findValidCellForSpell(cells, player);
                break;
            case CELLS:
            case ALL_OF_ENEMY:
            case ALL_OF_FRIEND:
                getAllCells(cells);
        }
        setValidCells(cells);
    }

    private void getAllCells(ArrayList<Cell> cells) {
        for (Cell[] cells1 :
                getGrid()) {
            cells.addAll(Arrays.asList(cells1));
        }
    }

    private void findValidCellForSpell(ArrayList<Cell> cells, Player player) {
        System.out.println("shiiiiiiiiiiiiiiiiiiiiiit");
        for (Cell[] cells1 :
                getGrid()) {
            for (Cell cell :
                    cells1) {
                if (cell.getWarrior() != null && player.getInGameCards().contains(cell.getWarrior())) {
                    cells.add(cell);
                }
            }
        }
    }


    private boolean isValidInsert(Cell destinationCell) {
        Deck deck = playingPlayer.getDeck();

        return (destinationCell != null) && destinationCell.isEmpty() && getDistanceOfTwoCell(destinationCell, getCellOfWarrior(deck.getHero())) <= 2;
    }

    private boolean isValidMove(Cell destinationCell) {
        getCellOfWarrior(getSelectedCell().getWarrior());
        if (destinationCell.getRow() == getCellOfWarrior(getSelectedCell().getWarrior()).getRow()) {
            if (destinationCell.getColumn() < getCellOfWarrior(getSelectedCell().getWarrior()).getColumn()) {
                return getGrid()[destinationCell.getRow()][getCellOfWarrior(getSelectedCell().getWarrior()).getColumn() - 1].isEmpty();
            } else {
                return getGrid()[destinationCell.getRow()][getCellOfWarrior(getSelectedCell().getWarrior()).getColumn() + 1].isEmpty();
            }

        } else if (destinationCell.getColumn() == getCellOfWarrior(getSelectedCell().getWarrior()).getColumn()) {
            if (destinationCell.getRow() < getCellOfWarrior(getSelectedCell().getWarrior()).getRow()) {
                return getGrid()[getCellOfWarrior(getSelectedCell().getWarrior()).getRow() - 1][destinationCell.getColumn()].isEmpty();
            } else {
                return getGrid()[getCellOfWarrior(getSelectedCell().getWarrior()).getRow() + 1][destinationCell.getColumn()].isEmpty();
            }
        }
        return false;
    }


    private boolean isValidAttack(Cell targetCell, Warrior warrior) {
        if (targetCell.getWarrior().getAccount() == warrior.getAccount()) {
            return false;
        }
        switch (warrior.getAttackKind()) {
            case MELEE:
                return isValidMeleeAttack(targetCell, warrior);
            case RANGED:
                return isValidRangedAttack(targetCell, warrior);
            case HYBRID:
                boolean flag1 = isValidRangedAttack(targetCell, warrior);
                boolean flag2 = isValidMeleeAttack(targetCell, warrior);
                return (flag1 || flag2);
        }
        return true;
    }

    private boolean isValidRangedAttack(Cell targetCell, Warrior warrior) {

        if (getDistanceOfTwoCell(targetCell, getCellOfWarrior(warrior)) <= 1) {
            return false;
        } else {
            return getDistanceOfTwoCell(targetCell, getCellOfWarrior(warrior)) <= warrior.getAttackRange();
        }
    }

    private boolean isValidMeleeAttack(Cell targetCell, Warrior warrior) {
        return getDistanceOfTwoCell(targetCell, getCellOfWarrior(warrior)) <= 1;
    }

    public int getDistanceOfTwoCell(Cell firstCell, Cell secondCell) {
        if (firstCell == null || secondCell == null)
            return 100;
        return Math.abs(firstCell.getColumn() - secondCell.getColumn()) + Math.abs(firstCell.getRow() - secondCell.getRow());
    }

    public Cell getCellOfWarrior(Warrior warrior) {
        for (Cell[] c :
                getGrid()) {
            for (Cell c1 :
                    c) {
                if (c1.getWarrior() == null)
                    continue;
                if (c1.getWarrior().equals(warrior)) {
                    return c1;
                }
            }
        }
        return null;
    }

    public void setValidCells(ArrayList<Cell> validCells) {
        this.validCells = validCells;
    }

    public BattleController getBattleController() {
        return battleController;
    }

    public void setBattleController(BattleController battleController) {

        this.battleController = battleController;
    }

    public void endGame() {

        switch (gameGoal) {
            case HOLD_FLAG:
                endOfHoldFlagGameMode();
                break;
            case COLLECT_FLAG:
                endOfCollectFlagGameMode();
        }
        endOfKillHeroGameMode();
        if (isEndGame()) {

            int numberOfWin;
            if (draw) {
                numberOfWin = 3;
                String h1 = "*draw* vs " + player2.getAccount().getUsername();
                player1.getAccount().getBattleHistory().add(h1);
                String h2 = "-draw- vs " + player1.getAccount().getUsername();
                player2.getAccount().getBattleHistory().add(h2);

                makeBattleRecordOfEndGame(true, player1, player2);

            } else {
                if (player1.getAccount().getUsername().equals(getWinner().getUsername())) {
                    numberOfWin = 2;
                    String h1 = "*win* vs " + player2.getAccount().getUsername();
                    player1.getAccount().getBattleHistory().add(h1);
                    player1.getAccount().setCountOfWins(player1.getAccount().getCountOfWins() + 1);
                    String h2 = "-lose- vs " + player1.getAccount().getUsername();
                    player2.getAccount().getBattleHistory().add(h2);

                    makeBattleRecordOfEndGame(false, player1, player2);
                } else {
                    numberOfWin = 1;
                    String h1 = "-lose- vs " + player2.getAccount().getUsername();
                    player1.getAccount().getBattleHistory().add(h1);
                    String h2 = "*win* vs " + player1.getAccount().getUsername();
                    player2.getAccount().getBattleHistory().add(h2);
                    player2.getAccount().setCountOfWins(player2.getAccount().getCountOfWins() + 1);

                    makeBattleRecordOfEndGame(false, player2, player1);
                }
            }
            System.out.println("======================>>>> " + numberOfWin);
            battleController.backToMenuInEndOfGame(numberOfWin);
            System.out.println("Game End");
        }

    }

    private void makeBattleRecordOfEndGame(boolean isDraw, Player winner, Player loser) {
        BattleRecord battleRecord = new BattleRecord(BattleRecordEnum.END_GAME);
        battleRecord.setDraw(isDraw);
        battleRecord.setWinnerUsername(winner.getAccount().getUsername());
        battleRecord.setLoserUsername(loser.getAccount().getUsername());

        getBattleRecords().add(battleRecord);
    }

    private void endOfKillHeroGameMode() {
        boolean canDraw = false;
        if (player1.getDeck().getHero().getHealthPoint() <= 0) {
            canDraw = true;
            this.setEndGame(true);
            setWinner(player2.getAccount());
            System.out.println("hero 1 killed");
        }
        if (player2.getDeck().getHero().getHealthPoint() <= 0) {
            this.setEndGame(true);
            setWinner(player1.getAccount());
            System.out.println("hero 2 killed");
            if (canDraw) {
                draw = true;
            }
        }
    }

    private void endOfHoldFlagGameMode() {
        if (holdFlag.getNumberOfTurn() == 6) {
            setWinner(holdFlag.getWarrior().getAccount());
            setEndGame(true);
            System.out.println("you hold 6 turn hold flag");
        }
    }

    private void endOfCollectFlagGameMode() {
        if (player1.getNumberOfFlag() >= 3) {
            setWinner(player1.getAccount());
            setEndGame(true);
            System.out.println("player1 collect 3 flag");
        } else if (player2.getNumberOfFlag() >= 3) {
            setWinner(player2.getAccount());
            setEndGame(true);
            System.out.println("player2 collect 3 flag");
        }
    }


    private void setFlagForCollectFlagGameModes() {
        int[] randomX = new int[6];
        int[] randomY = new int[6];
        getNRandomNumber(randomX, randomY, 0, 3, 0);
        getNRandomNumber(randomX, randomY, 3, 6, 5);
        for (int i = 0; i < 6; i++) {
            Flag flag = new Flag(KindOfFlag.COLLECTABLE_FLAG, randomX[i], randomY[i]);
            getGrid()[randomX[i]][randomY[i]].setFlag(flag);
            collectableFlags.add(flag);
        }

        battleController.initFlagImages();
    }

    private void getNRandomNumber(int[] randomX, int[] randomY, int first, int last, int extra) {

        Random random = new Random();
        int rx, ry;
        for (int i = first; i < last; i++) {
            rx = random.nextInt(5);
            ry = random.nextInt(4) + extra;
            while (hasPoint(randomX, randomY, rx, ry)) {
                rx = random.nextInt(5);
                ry = random.nextInt(4) + extra;
            }
            randomX[i] = rx;
            randomY[i] = ry;
        }
    }

    private boolean hasPoint(int[] arrayX, int[] arrayY, int rx, int ry) {
        for (int i = 0; i < arrayX.length; i++) {
            if (arrayX[i] == rx && arrayY[i] == ry)
                return true;
        }
        return (rx == 2 && ry == 0) || (rx == 2 && ry == 8);
    }

    public void setEndGame(boolean endGame) {
        this.endGame = endGame;
    }

    public void setWinner(Account winner) {
        this.winner = winner;
    }

    public GameMode getGameMode() {
        return gameMode;
    }

    public void setGameMode(GameMode gameMode) {
        this.gameMode = gameMode;
    }

    public Account getWinner() {
        return winner;
    }

    public boolean isEndGame() {
        return endGame;
    }

    public ArrayList<BattleRecord> getBattleRecords() {
        return battleRecords;
    }

    public int getLastBattleRecordPlayed() {
        return lastBattleRecordPlayed;
    }

    public void incrementBattleRecord() {
        lastBattleRecordPlayed++;
    }
}
