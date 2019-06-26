package Duelyst.Model.Items;

import Duelyst.Model.Battle.Battle;
import Duelyst.Model.Battle.Player;

public class TajeDanaei extends Item {

    private boolean applied;
    private boolean returnToDefault;

    public TajeDanaei() {
        super("TajeDanaei", "Mana Ra Dar 3 nobate Avval Yeki Ziad Mikonad",
                300, true, false, "res/Items/TajeDanaei/TajeDanaei.png");
    }

    @Override
    public void applyItem() {
        if (!applied && Battle.getRunningBattle().getTurn() <= 6) {
            getPlayer().setManaYIntercpet(getPlayer().getManaYIntercpet() + 1);
            applied = true;
            returnToDefault = true;
        } else if (returnToDefault) {
            getPlayer().setManaYIntercpet(getPlayer().getManaYIntercpet() - 1);
            returnToDefault = false;
        }

        //TODO Bayad Baede Set Mana Seda Zade Shan +++++
    }
}
