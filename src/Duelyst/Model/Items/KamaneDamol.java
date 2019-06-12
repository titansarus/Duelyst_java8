package Duelyst.Model.Items;

import Duelyst.Model.AttackKind;
import Duelyst.Model.Battle.Battle;
import Duelyst.Model.Battle.Player;
import Duelyst.Model.Buffs.Buff;
import Duelyst.Model.Buffs.BuffName;
import Duelyst.Model.Buffs.DisarmBuff;
import Duelyst.Model.Warrior;

public class KamaneDamol extends Item {

    public KamaneDamol(Player player) {
        super(player, true , false);
        setManaCost(30000);
    }

    @Override
    public void applyItem() {
        if (getPlayer().getHero().getAttackKind() != AttackKind.MELEE) {
            Buff buff = new DisarmBuff(BuffName.DISARM_BUFF, false, 1);
            Battle.getRunningBattle().getOnAttackBuffs().add(buff);
            buff.setWarrior((Warrior) Battle.getRunningBattle().getAttackedCard());
        }
    }
}
