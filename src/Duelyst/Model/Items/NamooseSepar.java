package Duelyst.Model.Items;

import Duelyst.Model.Battle.Battle;
import Duelyst.Model.Battle.Player;
import Duelyst.Model.Buffs.Buff;
import Duelyst.Model.Buffs.BuffName;
import Duelyst.Model.Buffs.HolyBuff;

public class NamooseSepar extends Item {

    public NamooseSepar(Player player) {
        super(player, true);
    }

    @Override
    public void applyItem() {
        Buff buff = new HolyBuff(BuffName.HOLY_BUFF, true, 12, 1);
        buff.setWarrior(getPlayer().getHero());
        Battle.getRunningBattle().getPassiveBuffs().add(buff);
    }
}
