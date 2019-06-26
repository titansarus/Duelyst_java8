package Duelyst.Model.Items;

import Duelyst.Model.Battle.Battle;
import Duelyst.Model.Battle.Player;
import Duelyst.Model.Buffs.Buff;
import Duelyst.Model.Buffs.BuffName;
import Duelyst.Model.Buffs.HolyBuff;

public class NamooseSepar extends Item {

    public NamooseSepar() {
        super("NamooseSepar", "Dar Heroye Khodi 12 HolyBuff Faal Mikonad",
                4000, true, true, "res/Items/NamooseSepar/NamooseSepar.png");
    }

    @Override
    public void applyItem() {
        Buff buff = new HolyBuff(12, 1);
        buff.setWarrior(getPlayer().getHero());
        Battle.getRunningBattle().getPassiveBuffs().add(buff);
    }
}
