package Duelyst.Model.Buffs;

import Duelyst.Model.Battle.Cell;
import Duelyst.Model.Card;
import Duelyst.Model.Warrior;

public abstract class Buff {

    private BuffName buffName;
    private boolean isPositive;
    private int numberOfTurn;
    private Warrior warrior = null;
    private Cell cell = null;

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

    public Warrior getWarrior() {
        return warrior;
    }

    public Cell getCell() {
        return cell;
    }

    public void setWarrior(Warrior warrior) {
        this.warrior = warrior;
    }

    public void setCell(Cell cell) {
        this.cell = cell;
    }

    public void decreaseNumberOfTurn() {
        numberOfTurn--;
    }
}
