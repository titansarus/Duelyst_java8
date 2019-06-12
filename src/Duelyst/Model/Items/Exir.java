package Duelyst.Model.Items;

import Duelyst.Model.Battle.Battle;
import Duelyst.Model.Battle.Player;
import Duelyst.Model.Buffs.Buff;
import Duelyst.Model.Buffs.BuffName;
import Duelyst.Model.Buffs.PowerBuff;
import Duelyst.Model.Warrior;

import java.util.Random;

public class Exir extends Item {

    public Exir(Player player) {
        super(player, false, true);
    }

    @Override
    public void applyItem() {
        Random random = new Random();
        int randomIndex = random.nextInt(getPlayer().getInGameCards().size());
        if (getPlayer().getInGameCards().size() > 0) {
            ((Warrior) getPlayer().getInGameCards().get(randomIndex)).increaseHealthPoint(3);
            Buff buff = new PowerBuff(BuffName.POWER_BUFF, true, 100000, true, 3);
            buff.setWarrior((Warrior) getPlayer().getInGameCards().get(randomIndex));
            Battle.getRunningBattle().getPassiveBuffs().add(buff);

        }

    }
}
