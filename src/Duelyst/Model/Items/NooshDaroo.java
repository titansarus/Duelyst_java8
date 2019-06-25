package Duelyst.Model.Items;

import Duelyst.Model.Battle.Player;
import Duelyst.Model.Buffs.Buff;
import Duelyst.Model.Warrior;

import java.util.Random;

public class NooshDaroo extends Item {


    public NooshDaroo() {
        super("NooshDaroo", "6 Vahed Sathe Salamatie Nirooye" +
                " Tasadofi Ra Afzayesh Midahad", 0, false, true);
    }

    @Override
    public void applyItem() {
        Random random = new Random();
        int randomIndex = random.nextInt(getPlayer().getInGameCards().size());
        if (getPlayer().getInGameCards().size() > 0)
            ((Warrior) getPlayer().getInGameCards().get(randomIndex)).increaseHealthPoint(6);
    }
}
