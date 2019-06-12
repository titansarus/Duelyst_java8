package Duelyst.Model.Items;

import Duelyst.Model.Battle.Battle;
import Duelyst.Model.Battle.Player;

public class TajeDanaei extends Item {


    public TajeDanaei(Player player) {
        super(player, true, false);
        setManaCost(300);
    }

    @Override
    public void applyItem() {
        if (Battle.getRunningBattle().getTurn() <= 6)
            getPlayer().setMana(getPlayer().getMana() + 1);
        //TODO Bayad Baede Set Mana Seda Zade Shan +++++
    }
}
