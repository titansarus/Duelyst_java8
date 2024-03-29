package Duelyst.Model.Items;

import Duelyst.Model.Battle.Battle;
import Duelyst.Model.Battle.Player;
import Duelyst.Model.Buffs.Buff;
import Duelyst.Model.Buffs.BuffName;
import Duelyst.Model.Buffs.PowerBuff;
import Duelyst.Model.Warrior;

import java.util.Random;

public class Exir extends Item {

    public Exir() {
        super("Exir", "3 Vahed Salamti Ra Afzayesh Midahad Va Yek PowerBuff Ba 3 Vahede" +
                " Afzayeshe Ghodrate Zarbe Baraye Minione Tasadofi Emal Mikonad", 0, false, true);
    }

    @Override
    public void applyItem() {
        if (getPlayer().getInGameCards().size() == 0)
            return;
        Random random = new Random();
        int randomIndex = random.nextInt(getPlayer().getInGameCards().size());
        while (!(getPlayer().getInGameCards().get(randomIndex) instanceof Warrior)) {
            randomIndex = random.nextInt(getPlayer().getInGameCards().size());
        }
        if (getPlayer().getInGameCards().size() > 0) {
            ((Warrior) getPlayer().getInGameCards().get(randomIndex)).increaseHealthPoint(3);
            Buff buff = new PowerBuff(100000, true, 3);
            buff.setWarrior((Warrior) getPlayer().getInGameCards().get(randomIndex));
            Battle.getRunningBattle().getPassiveBuffs().add(buff);
        }

    }
}
