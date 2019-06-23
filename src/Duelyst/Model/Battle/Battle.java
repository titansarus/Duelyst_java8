package Duelyst.Model.Battle;

import Duelyst.Exceptions.CellFilledBeforeException;
import Duelyst.Exceptions.NotEnoughManaException;
import Duelyst.Model.*;
import Duelyst.Model.Buffs.Buff;
import Duelyst.Model.Spell.Spell;

import java.util.ArrayList;

import static Duelyst.View.Constants.*;

public class Battle {
    private static Battle runningBattle;
    private Player player1;
    private Player player2;
    private Player playingPlayer = null;
    private Cell[][] grid = new Cell[BATTLE_ROWS][BATTLE_COLUMNS];
    private int turn = 0;//At First next turn is invocked and first turn will be 1
    private Card selectedCard;
    private Card attackedCard;//TODO Cardi ke Behesh Attack Khorde
    private Cell selectedCell;
    private ArrayList<Buff> onSpawnBuffs = new ArrayList<>();
    private ArrayList<Buff> onDefendBuffs = new ArrayList<>();
    private ArrayList<Buff> onAttackBuffs = new ArrayList<>();
    private ArrayList<Buff> onDeathBuffs = new ArrayList<>();
    private ArrayList<Buff> passiveBuffs = new ArrayList<>();
    private GameGoal gameGoal;
    private GameMode gameMode;

    public static final int VALID_COUNTER_WITH_BUFF = 1, VALID_COUNTER_WITHOUT_BUFF = 2, INVALID_COUNTER_WITH_BUFF = 3, INVALID_COUNTER_WITHOUT_BUFF = 4;

    public void initializeCells() {
        for (int i = 0; i < BATTLE_ROWS; i++) {
            for (int j = 0; j < BATTLE_COLUMNS; j++) {
                getGrid()[i][j] = new Cell(i, j);
            }
        }
    }

    public Battle(Account account1, Account account2, GameMode gameMode, GameGoal gameGoal) {
        checkDeckAtFirst(account1, account2);
        this.gameGoal = gameGoal;
        this.gameMode = gameMode;
        runningBattle = this;
        setPlayer1(new Player(account1, account1.getCardCollection().getMainDeck()));
        setPlayer2(new Player(account2, account2.getCardCollection().getMainDeck()));
        setPlayingPlayer();

        initializeCells();
        insertPlayerHeroesInMap();

        nextTurn();
    }

    private void checkDeckAtFirst(Account firstPlayer, Account secondPlayer) {
        if (firstPlayer.getCardCollection().getMainDeck() == null) {
            //TODO throw exception
        }
        if (secondPlayer.getCardCollection().getMainDeck() == null) {
            //TODO throw exception
        }
    }

    private void insertPlayerHeroesInMap() {
        getGrid()[2][0].setWarrior(player1.getHero());
        getGrid()[2][8].setWarrior(player2.getHero());
    }

    public void nextTurn() {
        turn++;
        setPlayingPlayer();
        getPlayer1().setManaFromTurn(getTurn());
        getPlayer2().setManaFromTurn(getTurn());
        setSelectedCell(null);
        setSelectedCard(null);
        getPlayingPlayer().getNextHand();

    }

    public void move(int destX, int destY) {
        if (getSelectedCell().getWarrior() != null) {
            if (getSelectedCell().getColumn() != destX || getSelectedCell().getRow() != destY) {
                Cell cell = getGrid()[destX][destY];
                if (cell.getWarrior() == null) {
                    cell.setWarrior(getSelectedCell().getWarrior());
                    getSelectedCell().setWarrior(null);
                    setSelectedCell(null);
                }
            }
        }

    }

    public void insertSelectedCard(int i, int j) {
        if (getSelectedCard() instanceof Warrior) {
            if (getGrid()[i][j].isEmpty()) {
                if (getPlayingPlayer().getMana() >= getSelectedCard().getManaCost()) {
                    getGrid()[i][j].setWarrior((Warrior) getSelectedCard());
                    Warrior warrior = getGrid()[i][j].getWarrior();

                    getPlayingPlayer().changeMana(-warrior.getManaCost());
                    getPlayingPlayer().getHand().remove(warrior);
                    playingPlayer.getInGameCards().add(getSelectedCard());
                } else {
                    throw new NotEnoughManaException();
                }
            } else {
                throw new CellFilledBeforeException();
            }
        } else if (getSelectedCard() instanceof Spell) {
            //TODO apply spell
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
        //TODO CHECK FOR COUNTER ATTACK AND BUFF AND A LOT OF THINGS
        if (!isFromCounterAttack) {
            return VALID_COUNTER_WITH_BUFF; //RETURN DETERMINES THE CONTROLLER TO SHOW BUFF ANIMATION OR NOT? DO COUNTER ATTACK OR NOT?
        } else {
            return INVALID_COUNTER_WITH_BUFF;
        }
    }


    public Player getPlayer1() {
        return player1;
    }

    public void setPlayer1(Player player1) {
        this.player1 = player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public void setPlayer2(Player player2) {
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

    public void setPlayingPlayer() {
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
}
