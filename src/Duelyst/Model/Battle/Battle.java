package Duelyst.Model.Battle;

import Duelyst.Model.Account;
import Duelyst.Model.Card;

import java.util.regex.Matcher;

import static Duelyst.View.Constants.*;

public class Battle {
    private Player player1;
    private Player player2;
    private Player playingPlayer = null;
    private Cell[][] grid = new Cell[BATTLE_ROWS][BATTLE_COLUMNS];
    private int turn = 0;//At First next turn is invocked and first turn will be 1
    private Card selectedCard;
    private Cell selectedCell;

    public void initializeCells() {
        for (int i = 0; i < BATTLE_ROWS; i++) {
            for (int j = 0; j < BATTLE_COLUMNS; j++) {
                getGrid()[i][j] = new Cell(i, j);
            }
        }
    }

    public Battle(Account account1, Account account2) {
        setPlayer1(new Player(account1, account1.getCardCollection().getMainDeck()));
        setPlayer2(new Player(account2, account2.getCardCollection().getMainDeck()));
        setPlayingPlayer();
        initializeCells();
        nextTurn();
    }

    public void nextTurn() {
        turn++;
        setPlayingPlayer();
        getPlayer1().setManaFromTurn(getTurn());
        getPlayer2().setManaFromTurn(getTurn());

    }

    public Integer calculateMaxAmountOfMana() {
        int res = Math.floorDiv(getTurn(), 2) + 2;
        return res > 9 ? 9 : res;
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
}
