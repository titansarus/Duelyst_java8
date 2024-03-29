package Duelyst.Model.Items;

import Duelyst.Model.Battle.Battle;
import Duelyst.Model.Battle.Player;
import Duelyst.Model.Buffs.Buff;
import Duelyst.Model.Buffs.BuffName;
import Duelyst.Model.Buffs.WeaknessBuff;
import Duelyst.Model.Warrior;

import java.util.Random;

public class TerrorHood extends Item {

    public TerrorHood() {
        super("TerrorHood", "Dar Hengame Zarbe Yek WeaknessBuff Ba Kaheshe Ghodrate Zarbeye 2 Baraye Yek Nobat Be Nirooye Tasadofie Doshman Emal Mishavad",
                5000, true, false, "res/Items/TerrorHood/TerrorHood.png");
    }

    @Override
    public void applyItem() {
        Player player;
        if (Battle.getRunningBattle().getTurn() % 2 == 1)
            player = Battle.getRunningBattle().getPlayer2();
        else
            player = Battle.getRunningBattle().getPlayer1();
        Random random = new Random();
        int randomIndex = random.nextInt(player.getInGameCards().size());
        if (player.getInGameCards().size() == 0) {
            return;
        }
        while (!(getPlayer().getInGameCards().get(randomIndex) instanceof Warrior)) {
            randomIndex = random.nextInt(getPlayer().getInGameCards().size());
        }
        Buff buff = new WeaknessBuff(1, true, 2);
        buff.setWarrior((Warrior) player.getInGameCards().get(randomIndex));
        Battle.getRunningBattle().getPassiveBuffs().add(buff);
    }

}
