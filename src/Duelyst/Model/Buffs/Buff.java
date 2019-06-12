package Duelyst.Model.Buffs;

import Duelyst.Model.Battle.Cell;
import Duelyst.Model.Card;

public abstract class Buff {

    private BuffName buffName;
    private boolean isPositive;
    private int numberOfTurn;
    private Card card;
    private Cell cell;

    public Buff(BuffName buffName, boolean isPositive, int numberOfTurn) {
        this.buffName = buffName;
        this.isPositive = isPositive;
        this.numberOfTurn = numberOfTurn;
    }

    public BuffName getBuffName() {
        return buffName;
    }

    public boolean isPositive() {
        return isPositive;
    }

    public int getNumberOfTurn() {
        return numberOfTurn;
    }

    public Card getCard() {
        return card;
    }

    public Cell getCell() {
        return cell;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public void setCell(Cell cell) {
        this.cell = cell;
    }
}
