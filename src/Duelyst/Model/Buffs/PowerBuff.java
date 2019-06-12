package Duelyst.Model.Buffs;

import Duelyst.Model.Card;

public class PowerBuff extends Buff {
    private boolean isForPower;
    private int increaseNumber;

    public PowerBuff(BuffName buffName, boolean isPositive, int numberOfTurn, boolean isForPower, int increaseNumber) {
        super(buffName, isPositive, numberOfTurn);
        this.isForPower = isForPower;
        this.increaseNumber = increaseNumber;
    }

    public boolean isForPower() {
        return isForPower;
    }

    public int getIncreaseNumber() {
        return increaseNumber;
    }


}
