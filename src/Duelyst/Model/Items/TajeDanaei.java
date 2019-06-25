package Duelyst.Model.Items;

import Duelyst.Model.Battle.Battle;
import Duelyst.Model.Battle.Player;

public class TajeDanaei extends Item {


    public TajeDanaei() {
        super("TajeDanaei" ,"Mana Ra Dar 3 nobate Avval Yeki Ziad Mikonad",
                300, true, false,"res/Items/TajeDanaei/TajeDanaei.png");
    }

    @Override
    public void applyItem() {
        if (Battle.getRunningBattle().getTurn() <= 6)
            getPlayer().setMana(getPlayer().getMana() + 1);
        //TODO Bayad Baede Set Mana Seda Zade Shan +++++
    }
}
