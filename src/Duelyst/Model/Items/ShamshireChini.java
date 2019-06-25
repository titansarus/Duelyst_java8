package Duelyst.Model.Items;

import Duelyst.Model.AttackKind;
import Duelyst.Model.Battle.Player;
import Duelyst.Model.Warrior;

public class ShamshireChini extends Item {

    public ShamshireChini() {
        super("ShamshireChini", "Baraye MeleeHa: 5 Ghodrate Zarbe Midahad", 0, false, true);
    }

    @Override
    public void applyItem() {
        for (int i = 0; i < getPlayer().getDeck().getCards().size(); i++) {
            if (getPlayer().getDeck().getCards().get(i) instanceof Warrior) {
                if (((Warrior) getPlayer().getDeck().getCards().get(i)).getAttackKind() == AttackKind.MELEE) {
                    ((Warrior) getPlayer().getDeck().getCards().get(i)).increaseActionPower(5);
                }
            }
        }

    }
}
