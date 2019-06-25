package Duelyst.Model.Battle;

import Duelyst.Controllers.BattleController;
import Duelyst.Exceptions.CellFilledBeforeException;
import Duelyst.Exceptions.NotEnoughManaException;
import Duelyst.Exceptions.NotValidDeckException;
import Duelyst.Model.*;
import Duelyst.Model.Buffs.Buff;
import Duelyst.Model.Buffs.BuffName;
import Duelyst.Model.Buffs.HolyBuff;
import Duelyst.Model.Buffs.PowerBuff;
import Duelyst.Model.Items.*;
import Duelyst.Model.Spell.Spell;

import java.util.ArrayList;

import static Duelyst.View.Constants.*;

public class Battle {
    private static Battle runningBattle;
    private BattleController battleController;
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
    private ArrayList<Cell> validCells = new ArrayList<>();
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
        this.gameGoal = gameGoal;
        this.gameMode = gameMode;
        runningBattle = this;
        if (account2 instanceof Ai) {
            ((Ai) account2).setBattle(this);
        }
        setPlayer1(new Player(account1, account1.getCardCollection().getMainDeck()));
        setPlayer2(new Player(account2, account2.getCardCollection().getMainDeck()));
        setPlayingPlayer();

        initializeCells();
        insertPlayerHeroesInMap();

        nextTurn();
    }


    private void insertPlayerHeroesInMap() {
        System.out.println("******************\n" + player1.getDeck().getHero().equals(player2.getDeck().getHero()) + "********************\n");
        getGrid()[2][0].setWarrior(player1.getDeck().getHero());
        getGrid()[2][8].setWarrior(player2.getDeck().getHero());
    }

    public void nextTurn() {
        setTrueOfValidAttackAndMove();

//        System.out.println("Reza Gikhar :)  " + getCellOfWarrior(player1.getDeck().getHero()).getColumn() + getCellOfWarrior(player2.getDeck().getHero()).getColumn());

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
    }

    private void setTrueOfValidAttackAndMove() {
        for (Cell[] c:
             getGrid()) {
            for (Cell c1:
                 c) {
                if (c1.getWarrior()!= null){
                    c1.getWarrior().setValidToAttack(true);
                    c1.getWarrior().setValidToMove(true);
                }
            }
        }
    }

    public void move(int destX, int destY) {
        findValidCell(KindOfActionForValidCells.MOVE);
        ArrayList<Cell> cells = getValidCells();
        if (!cells.contains(getGrid()[destX][destY])){
            //TODO throw exception
            return;
        }
        if (getSelectedCell().getWarrior() != null) {
            if (getSelectedCell().getColumn() != destX || getSelectedCell().getRow() != destY) {
                Cell cell = getGrid()[destX][destY];
                if (cell.getWarrior() == null) {
                    getSelectedCell().getWarrior().setValidToMove(false);
                    cell.setWarrior(getSelectedCell().getWarrior());
                    getSelectedCell().setWarrior(null);
                    setSelectedCell(null);
                }
            }
        }

    }

    public void insertSelectedCard(int i, int j) {
        findValidCell(KindOfActionForValidCells.INSERT);
        ArrayList<Cell> cells = getValidCells();
        if (!cells.contains(getGrid()[i][j])){
            //TODO throw exception
            return;
        }
        if (getSelectedCard() instanceof Warrior) {
            if (getGrid()[i][j].isEmpty()) {
                if (getPlayingPlayer().getMana() >= getSelectedCard().getManaCost()) {
                    getGrid()[i][j].setWarrior((Warrior) getSelectedCard());
                    Warrior warrior = getGrid()[i][j].getWarrior();
                    warrior.setInGame(true);

                    warrior.setValidToMove(false);
                    warrior.setValidToAttack(false);
                    getPlayingPlayer().changeMana(-warrior.getManaCost());
                    getPlayingPlayer().getHand().remove(warrior);
                    playingPlayer.getInGameCards().add(getSelectedCard());

                    if (getPlayingPlayer().getDeck().getItem() instanceof AssassinationDagger) {//AssassinationDagger Item Apply
                        getPlayingPlayer().getDeck().getItem().applyItem();
                    } else if (getPlayingPlayer().getDeck().getItem() instanceof GhosleTaemid) {//GhosleTaemid Item Apply
                        Buff buff = new HolyBuff(BuffName.HOLY_BUFF, true, 2, 1);
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
        this.attackedCard = attackedCard;
        //KamaneDamol Item Apply
        if (getPlayingPlayer().getDeck().getItem() instanceof KamaneDamol && attacker.equals(getPlayingPlayer().getDeck().getHero())) {
            getPlayingPlayer().getDeck().getItem().applyItem();
        } else if (getPlayingPlayer().getDeck().getItem() instanceof PoisonousDagger) {
            getPlayingPlayer().getDeck().getItem().applyItem();
        } else if (getPlayingPlayer().getDeck().getItem() instanceof ShockHammer) {
            getPlayingPlayer().getDeck().getItem().applyItem();
        }
        attacker.setValidToMove(false);
        attacker.setValidToAttack(false);

        setSelectedCell(null);

        //TODO CHECK FOR COUNTER ATTACK AND BUFF AND A LOT OF THINGS
        if (!isFromCounterAttack) {
            return VALID_COUNTER_WITH_BUFF; //RETURN DETERMINES THE CONTROLLER TO SHOW BUFF ANIMATION OR NOT? DO COUNTER ATTACK OR NOT?
        } else {
            return INVALID_COUNTER_WITH_BUFF;
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
            System.out.println("=========================>   " + card.getCardName());
            battleController.removeImageViewFromCell(card);
            getCellOfWarrior((Warrior) card).setWarrior(null);
        }
    }

    private void addUsedCardsToGraveYard(ArrayList<Card> firstDeathCards, ArrayList<Card> secondDeathCards) {

        for (int i = 0; i < getPlayer1().getDeck().getCards().size(); i++) {
            if (getPlayer1().getDeck().getCards().get(i) instanceof Spell && getPlayer1().getDeck().getCards().get(i).isInGame()) {
                getPlayer1().getDeck().getCards().add(getPlayer1().getDeck().getCards().get(i));
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
                System.out.println(((Warrior) playerInGameCard).getHealthPoint() + " <====================================]]");
                if (((Warrior) playerInGameCard).getHealthPoint() <= 0) {
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


    ///////////////////////////////////////////////////
    private void clearValidCellsList() {
        getValidCells().clear();
    }

    public ArrayList<Cell> getValidCells() {//
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
        if (!getSelectedCard().isAbleToMove())
            return;
        Warrior warrior = (Warrior) getSelectedCard();
        for (Cell[] cell : getGrid()) {
            for (Cell cell1 : cell) {
                if (cell1.isEmpty() && getDistanceOfTwoCell(getCellOfWarrior(warrior), cell1) <= 2 && isValidMove(cell1))
                    getValidCells().add(cell1);
            }
        }
    }

    private void findValidCellToAttack() {

        Cell[][] cells = getGrid();
        Warrior warrior = (Warrior) getSelectedCard();
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

    }

    private boolean isValidInsert(Cell destinationCell) {
        Deck deck = playingPlayer.getDeck();

        return (destinationCell != null) && destinationCell.isEmpty() && getDistanceOfTwoCell(destinationCell, getCellOfWarrior(deck.getHero())) <= 2;
    }

    private boolean isValidMove(Cell destinationCell) {
        getCellOfWarrior((Warrior) getSelectedCard());
        if (destinationCell.getRow() == getCellOfWarrior((Warrior) getSelectedCard()).getRow()) {
            if (destinationCell.getColumn() < getCellOfWarrior((Warrior) getSelectedCard()).getColumn()) {
                return getGrid()[destinationCell.getRow()][getCellOfWarrior((Warrior) getSelectedCard()).getColumn() - 1].isEmpty();
            } else {
                return getGrid()[destinationCell.getRow()][getCellOfWarrior((Warrior) getSelectedCard()).getColumn() + 1].isEmpty();
            }

        } else if (destinationCell.getColumn() == getCellOfWarrior((Warrior) getSelectedCard()).getColumn()) {
            if (destinationCell.getRow() < getCellOfWarrior((Warrior) getSelectedCard()).getRow()) {
                return getGrid()[getCellOfWarrior((Warrior) getSelectedCard()).getRow() - 1][destinationCell.getColumn()].isEmpty();
            } else {
                return getGrid()[getCellOfWarrior((Warrior) getSelectedCard()).getRow() + 1][destinationCell.getColumn()].isEmpty();
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
}
