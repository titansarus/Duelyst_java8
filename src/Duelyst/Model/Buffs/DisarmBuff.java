package Duelyst.Model.Buffs;

import Duelyst.Model.Card;

public class DisarmBuff extends Buff {

    public DisarmBuff(int numberOfTurn) {
        super(BuffName.DISARM_BUFF, false, numberOfTurn);
    }

}
