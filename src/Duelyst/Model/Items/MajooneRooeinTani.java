package Duelyst.Model.Items;

import Duelyst.Model.Battle.Battle;
import Duelyst.Model.Battle.Player;
import Duelyst.Model.Buffs.Buff;
import Duelyst.Model.Buffs.BuffName;
import Duelyst.Model.Buffs.HolyBuff;
import Duelyst.Model.Warrior;

import java.util.Random;

public class MajooneRooeinTani extends Item {


    public MajooneRooeinTani() {
        super("MajooneRooeinTani", "10 HolyBuff Dar Nirooye Tasadofie Khodi Baraye 2 Nobat Faal Mikonad", 0, false, true);
    }

    @Override
    public void applyItem() {
        if (getPlayer().getInGameCards().size() == 0)
            return;
        Random random = new Random();
        int randomIndex = random.nextInt(getPlayer().getInGameCards().size());
        for (int i = 0; i < 10; i++) {
            Buff buff = new HolyBuff(BuffName.HOLY_BUFF, true, 2, 1);
            buff.setWarrior((Warrior) getPlayer().getInGameCards().get(randomIndex));
            Battle.getRunningBattle().getPassiveBuffs().add(buff);
        }

    }
}
