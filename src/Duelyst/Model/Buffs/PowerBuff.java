package Duelyst.Model.Buffs;

import Duelyst.Model.Card;

public class PowerBuff extends Buff {
    private boolean isForPower;
    private int increaseNumber;
    private Card card;

    public PowerBuff(BuffName buffName, boolean isPositive, int numberOfTurn, boolean isForPower, int increaseNumber, Card card) {
        super(buffName, isPositive, numberOfTurn);
        this.isForPower = isForPower;
        this.increaseNumber = increaseNumber;
        this.card = card;
    }

}
