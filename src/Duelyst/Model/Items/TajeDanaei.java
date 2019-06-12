package Duelyst.Model.Items;

import Duelyst.Model.Battle.Player;

public class TajeDanaei extends Item {


    public TajeDanaei(Player player) {
        super(player, true);
        setManaCost(300);
    }

    @Override
    public void applyItem() {
        getPlayer().setMana(getPlayer().getMana() + 1);
        //TODO Baede 3 Nobat Bayad Be Halate Standard Bargardad
    }
}
