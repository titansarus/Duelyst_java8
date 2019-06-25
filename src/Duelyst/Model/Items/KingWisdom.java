package Duelyst.Model.Items;

import Duelyst.Model.Battle.Player;

public class KingWisdom extends Item {


    public KingWisdom() {
        super("KingWisdom", "Dar Hameye NobatHa Mana Yeki Ezafe Mishavad",
                9000, true, true,"res/Items/KingWisdom/KingWisdom.png");
    }

    @Override
    public void applyItem() {
        getPlayer().setManaYIntercpet(getPlayer().getManaYIntercpet() + 1);
    }
}
