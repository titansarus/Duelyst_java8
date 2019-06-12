package Duelyst.Model.Items;

import Duelyst.Model.Battle.Player;

public class KingWisdom extends Item {


    public KingWisdom(Player player) {
        super(player, true);
        setManaCost(9000);
    }

    @Override
    public void applyItem() {
        getPlayer().setMana(getPlayer().getMana() + 1);
    }
}
