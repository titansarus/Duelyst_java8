package Duelyst.Model.Items;

import Duelyst.Model.Battle.Player;

public class KingWisdom extends Item {


    public KingWisdom(Player player) {
        super(player, true, true);
        setManaCost(9000);
    }

    @Override
    public void applyItem() {
        getPlayer().setManaYIntercpet(getPlayer().getManaYIntercpet() + 1);
    }
}
