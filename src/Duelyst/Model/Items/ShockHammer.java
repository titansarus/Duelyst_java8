package Duelyst.Model.Items;

import Duelyst.Model.Battle.Battle;
import Duelyst.Model.Battle.Player;
import Duelyst.Model.Buffs.Buff;
import Duelyst.Model.Buffs.BuffName;
import Duelyst.Model.Buffs.DisarmBuff;
import Duelyst.Model.Warrior;

public class ShockHammer extends Item {


    public ShockHammer(Player player) {
        super(player, true);
        setManaCost(15000);
    }

    @Override
    public void applyItem() {
        Buff buff = new DisarmBuff(BuffName.DISARM_BUFF, false, 1);
        buff.setWarrior((Warrior) Battle.getRunningBattle().getAttackedCard());
        Battle.getRunningBattle().getPassiveBuffs().add(buff);
    }
}
