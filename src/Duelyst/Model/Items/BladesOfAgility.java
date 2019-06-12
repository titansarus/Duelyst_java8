package Duelyst.Model.Items;

import Duelyst.Model.Battle.Player;
import Duelyst.Model.Warrior;

import java.util.Random;

public class BladesOfAgility extends Item {


    public BladesOfAgility(Player player) {
        super(player, false);
    }

    @Override
    public void applyItem() {

        if (getPlayer().getInGameCards().size() == 0)
            return;
        Random random = new Random();
        int randomIndex = random.nextInt(getPlayer().getInGameCards().size());
        ((Warrior) getPlayer().getInGameCards().get(randomIndex)).increaseActionPower(6);
    }
}
