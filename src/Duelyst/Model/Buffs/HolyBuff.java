package Duelyst.Model.Buffs;

import Duelyst.Model.Card;

public class HolyBuff extends Buff {
    private int numberOfHealthIncrease;


    public HolyBuff(BuffName buffName, boolean isPositive, Card card, int numberOfTurn, int numberOfHealthIncrease) {
        super(buffName, isPositive,numberOfTurn);
        this.numberOfHealthIncrease = numberOfHealthIncrease;//TODO in adad ro bde b shield card
    }


    public int getNumberOfHealthIncrease() {
        return numberOfHealthIncrease;
    }

    public void setNumberOfHealthIncrease(int numberOfHealthIncrease) {
        this.numberOfHealthIncrease = numberOfHealthIncrease;
    }
}
