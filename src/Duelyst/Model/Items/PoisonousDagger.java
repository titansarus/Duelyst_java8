package Duelyst.Model.Items;

import Duelyst.Model.Battle.Battle;
import Duelyst.Model.Battle.Player;
import Duelyst.Model.Buffs.Buff;
import Duelyst.Model.Buffs.BuffName;
import Duelyst.Model.Buffs.PoisonBuff;
import Duelyst.Model.Buffs.WeaknessBuff;
import Duelyst.Model.Warrior;

import java.util.Random;

public class PoisonousDagger extends Item {

    public PoisonousDagger() {
        super("PoisonousDagger", "Dar Hengame Zarbeye Nirooye Khodi  Baraye Yek Nobat Rooye Nirooye Tasadofie Doshman PosionBuff Emal Mikonad",
                7000, false, false,"res/Items/PoisonousDagger/PoisonousDagger.png");
    }

    @Override
    public void applyItem() {
        //TODO Hengame Attack Bayad Emal Shavad :D
        Player player;
        if (Battle.getRunningBattle().getTurn() % 2 == 1)
            player = Battle.getRunningBattle().getPlayer2();
        else
            player = Battle.getRunningBattle().getPlayer1();

        if (player.getInGameCards().size() == 0)
            return;

        Random random = new Random();
        int randomIndex = random.nextInt(player.getInGameCards().size());
        Buff buff = new PoisonBuff(BuffName.POISON_BUFF, false, 1, true);
        buff.setWarrior((Warrior) player.getInGameCards().get(randomIndex));
        Battle.getRunningBattle().getPassiveBuffs().add(buff);
    }
}
