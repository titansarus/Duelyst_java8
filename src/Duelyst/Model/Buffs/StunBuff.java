package Duelyst.Model.Buffs;

import Duelyst.Model.Card;

public class StunBuff extends Buff {

    public StunBuff(BuffName buffName, boolean isPositive, int numberOfTurn, Card card) {
        super(buffName, isPositive, numberOfTurn);
    }
}
