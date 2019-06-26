package Duelyst.Model.Buffs;

import Duelyst.Model.Card;

public class HolyBuff extends Buff {
    private int numberOfHealthIncrease;


    public HolyBuff(int numberOfTurn, int numberOfHealthIncrease) {
        super(BuffName.HOLY_BUFF, true, numberOfTurn);
        this.numberOfHealthIncrease = numberOfHealthIncrease;//TODO in adad ro bde b shield card
    }


    public int getNumberOfHealthIncrease() {
        return numberOfHealthIncrease;
    }

    public void setNumberOfHealthIncrease(int numberOfHealthIncrease) {
        this.numberOfHealthIncrease = numberOfHealthIncrease;
    }
}
