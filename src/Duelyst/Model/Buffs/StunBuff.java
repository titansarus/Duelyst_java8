package Duelyst.Model.Buffs;

import Duelyst.Model.Card;

public class StunBuff extends Buff {
    private Card card;

    public StunBuff(BuffName buffName, boolean isPositive, int numberOfTurn, Card card) {
        super(buffName, isPositive, numberOfTurn);
        this.card = card;
    }
}
