package Duelyst.Model.Items;

import Duelyst.Model.AttackKind;
import Duelyst.Model.Battle.Battle;
import Duelyst.Model.Battle.Player;
import Duelyst.Model.Buffs.Buff;
import Duelyst.Model.Buffs.BuffName;
import Duelyst.Model.Buffs.DisarmBuff;
import Duelyst.Model.Warrior;

public class KamaneDamol extends Item {

    public KamaneDamol() {
        super("KamaneDamol", "Tanha Baraye Ranged va Hybrid: Heroye Khodi Hengame Zarbeye Nirooye Doshman Ra Baraye Yek Nobat Disarm Mikonad",
                30000, true, false, "res/Items/KamaneDamol/KamandeDamol.png");
    }

    @Override
    public void applyItem() {
        if (getPlayer().getHero().getAttackKind() != AttackKind.MELEE) {
            Buff buff = new DisarmBuff(2);
            Battle.getRunningBattle().getOnAttackBuffs().add(buff);
            buff.setWarrior((Warrior) Battle.getRunningBattle().getAttackedCard());
        }
    }
}
