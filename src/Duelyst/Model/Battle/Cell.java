package Duelyst.Model.Battle;

import Duelyst.Model.Card;
import Duelyst.Model.Items.Item;
import Duelyst.Model.Warrior;

import static Duelyst.View.Constants.*;

public class Cell {
    private Integer row, column;
    private Warrior warrior;
    private Flag flag;
    private Item collectibleItem;

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

    public boolean isEmpty() {
        return warrior == null;
    }

    public static boolean isSelectedCardOnField(Cell[][] grids, Card card) {
        for (int i = 0; i < BATTLE_ROWS; i++) {
            for (int j = 0; j < BATTLE_COLUMNS; j++) {
                if (grids[i][j].getWarrior().equals(card)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static int calculateManhattanDistance(Cell cell1, Cell cell2) {
//        System.out.println("==============>>>> "+ Math.abs(cell1.column - cell2.column) + Math.abs(cell1.row - cell2.row));
        return Math.abs(cell1.column - cell2.column) + Math.abs(cell1.row - cell2.row);
    }

    public Flag getFlag() {
        return flag;
    }

    public void setFlag(Flag flag) {
        this.flag = flag;
    }

    @Override
    public boolean equals(Object obj) {
        Cell cell = (Cell) obj;
        return cell.getRow().equals(getRow()) && cell.getColumn().equals(getColumn());
    }

    public Item getCollectibleItem() {
        return collectibleItem;
    }

    public void setCollectibleItem(Item collectibleItem) {
        this.collectibleItem = collectibleItem;
    }
}
