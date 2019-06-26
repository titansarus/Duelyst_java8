package Duelyst.Model.Battle;

import Duelyst.Model.Warrior;

public class Flag {
    private Warrior warrior;
    private KindOfFlag kindOfFlag;
    private int numberOfTurn = 0;
    private int x;
    private int y;

    public Flag(KindOfFlag kindOfFlag, int x, int y) {
        this.kindOfFlag = kindOfFlag;
        this.x = x;
        this.y = y;
    }

    public Warrior getWarrior() {
        return warrior;
    }

    public void setWarrior(Warrior warrior) {
        this.warrior = warrior;
    }

    public KindOfFlag getKindOfFlag() {
        return kindOfFlag;
    }

    public void setKindOfFlag(KindOfFlag kindOfFlag) {
        this.kindOfFlag = kindOfFlag;
    }

    public int getNumberOfTurn() {
        return numberOfTurn;
    }

    public void setNumberOfTurn(int numberOfTurn) {
        this.numberOfTurn = numberOfTurn;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
