package Duelyst.Model.Items;

import Duelyst.Model.Battle.Player;
import Duelyst.Model.Warrior;

import java.util.Random;

public class RandomDamage extends Item {

    public RandomDamage() {
        super("RandomDamage", "Be Nirooye Tasadofi Dar Zamin 2" +
                " Ghodrate Zarbe Midahad", 0, false, true);
    }

    @Override
    public void applyItem() {
        if (getPlayer().getInGameCards().size() == 0)
            return;

        Random random = new Random();
        int randomIndex = random.nextInt(getPlayer().getInGameCards().size());
        ((Warrior) getPlayer().getInGameCards().get(randomIndex)).increaseActionPower(2);
    }
}
