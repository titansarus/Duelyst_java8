package Duelyst.Model.Items;

import Duelyst.Model.Battle.Battle;
import Duelyst.Model.Battle.Player;

public class MajooneMana extends Item {


    public MajooneMana(Player player) {
        super(player, false, true);
    }

    @Override
    public void applyItem() {
        getPlayer().setMana(getPlayer().getMana() + 3);
    }
}
