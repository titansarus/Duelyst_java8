package Duelyst.Model.Items;

import Duelyst.Model.Battle.Battle;
import Duelyst.Model.Battle.Player;
import Duelyst.Model.Buffs.Buff;
import Duelyst.Model.Buffs.BuffName;
import Duelyst.Model.Buffs.PowerBuff;
import Duelyst.Model.Warrior;

import java.util.Random;

public class SoulEater extends Item {

    public SoulEater(Player player) {
        super(player, true);
        setManaCost(25000);
    }

    @Override
    public void applyItem() {
        Random random = new Random();
        int randomIndex = random.nextInt(getPlayer().getInGameCards().size());
        Buff buff = new PowerBuff(BuffName.POWER_BUFF, true, 1, true, 1);
        buff.setWarrior((Warrior) getPlayer().getInGameCards().get(randomIndex));
        Battle.getRunningBattle().getPassiveBuffs().add(buff);
    }
}
