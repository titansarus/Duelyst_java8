package Duelyst.Model.Buffs;

import Duelyst.Model.Card;

public class DisarmBuff extends Buff {
    private Card card;

    public DisarmBuff(BuffName buffName, boolean isPositive, int numberOfTurn, Card card) {
        super(buffName, isPositive,numberOfTurn);
        this.card = card;
    }

}
