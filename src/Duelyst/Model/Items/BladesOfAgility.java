package Duelyst.Model.Items;

import Duelyst.Model.Battle.Player;
import Duelyst.Model.Warrior;

import java.util.Random;

public class BladesOfAgility extends Item {


    public BladesOfAgility() {
        super("BladesOfAgility", "Be Ghodrate Zarbeye Yek Nirooye Khodi 6 Vahed Ezafe Mikonad",
                0, false, true);
    }

    @Override
    public void applyItem() {

        Random random = new Random();
        int randomIndex = random.nextInt(getPlayer().getInGameCards().size());
        if (getPlayer().getInGameCards().size() == 0) {
            return;
        }
        while (!(getPlayer().getInGameCards().get(randomIndex) instanceof Warrior)) {
            randomIndex = random.nextInt(getPlayer().getInGameCards().size());
        }
        ((Warrior) getPlayer().getInGameCards().get(randomIndex)).increaseActionPower(6);
    }
}
