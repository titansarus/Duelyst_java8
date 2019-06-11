package Duelyst.Model.Buffs;

import Duelyst.Model.Card;

public class WeaknessBuff extends Buff {
    private boolean isForPower;
    private int increaseNumber;
    private Card card;

    public WeaknessBuff(BuffName buffName, boolean isPositive, int numberOfTurn, boolean isForPower, int increaseNumber, Card card){
        super(buffName, isPositive, numberOfTurn);
        this.isForPower = isForPower;
        this.increaseNumber = increaseNumber;
        this.card = card;
    }

}
