package Duelyst.Model.Buffs;

import Duelyst.Model.Card;

public class StunBuff extends Buff {

    public StunBuff(int numberOfTurn) {
        super(BuffName.STUN_BUFF, false, numberOfTurn);
    }
}
