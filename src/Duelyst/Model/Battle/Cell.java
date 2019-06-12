package Duelyst.Model.Battle;

import Duelyst.Model.Card;
import Duelyst.Model.Warrior;

public class Cell {
    private Integer row , column;
    private Warrior warrior;

    public Cell(Integer row, Integer column) {
        this.row = row;
        this.column = column;
    }

    public Integer getRow() {
        return row;
    }

    public void setRow(Integer row) {
        this.row = row;
    }

    public Integer getColumn() {
        return column;
    }

    public void setColumn(Integer column) {
        this.column = column;
    }

    public Warrior getWarrior() {
        return warrior;
    }

    public void setWarrior(Warrior warrior) {
        this.warrior = warrior;
    }

    public boolean isEmpty()
    {
        return warrior==null;
    }
}
