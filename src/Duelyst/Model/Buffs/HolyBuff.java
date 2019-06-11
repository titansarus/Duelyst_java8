package Duelyst.Model.Buffs;

import Duelyst.Model.Card;

public class HolyBuff extends Buff {
    private Card card;
    private int numberOfHealthIncrease;


    public HolyBuff(BuffName buffName, boolean isPositive, Card card, int numberOfTurn, int numberOfHealthIncrease) {
        super(buffName, isPositive,numberOfTurn);
        this.card = card;
        this.numberOfHealthIncrease = numberOfHealthIncrease;//TODO in adad ro bde b shield card
    }


}
