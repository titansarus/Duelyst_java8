package Duelyst.Model.Items;

import Duelyst.Model.Battle.Battle;
import Duelyst.Model.Battle.Player;
import Duelyst.Model.Buffs.Buff;
import Duelyst.Model.Buffs.BuffName;
import Duelyst.Model.Buffs.DisarmBuff;
import Duelyst.Model.Warrior;

public class ShockHammer extends Item {


    public ShockHammer() {
        super("ShockHammer", "Heroe Khodi  Hengame Zarbe Baraye Yek Nobat Nirooye Doshman Ra Disarm Mikonad",
                15000, true, false,"res/Items/ShockHammer/ShockHammer.png");
    }

    @Override
    public void applyItem() {
        //TODO Hengame Attack Bayad Emal Shavad :)
        if (Battle.getRunningBattle().getSelectedCard().equals(getPlayer().getHero())) {
            Buff buff = new DisarmBuff(BuffName.DISARM_BUFF, false, 1);
            buff.setWarrior((Warrior) Battle.getRunningBattle().getAttackedCard());
            Battle.getRunningBattle().getPassiveBuffs().add(buff);
        }
    }
}
