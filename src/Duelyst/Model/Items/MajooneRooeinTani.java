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
        super("MajooneRooeinTani", "10 HolyBuff Dar Nirooye Tasadofie" +
                " Khodi Baraye 2 Nobat Faal Mikonad", 0, false, true);
    }

    @Override
    public void applyItem() {
        if (getPlayer().getInGameCards().size() == 0)
            return;
        Random random = new Random();
        int randomIndex = random.nextInt(getPlayer().getInGameCards().size());

        Buff buff = new HolyBuff(2, 10);
        buff.setWarrior((Warrior) getPlayer().getInGameCards().get(randomIndex));
        Battle.getRunningBattle().getPassiveBuffs().add(buff);

    }
}
