package Duelyst.Model.Buffs;

import Duelyst.Model.Card;

public class PowerBuff extends Buff {
    private boolean isForPower;
    private int increaseNumber;

    public PowerBuff(int numberOfTurn, boolean isForPower, int increaseNumber) {
        super(BuffName.POWER_BUFF, true, numberOfTurn);
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
