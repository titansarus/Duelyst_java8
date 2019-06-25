package Duelyst.Model.Items;

import Duelyst.Model.Battle.Battle;
import Duelyst.Model.Battle.Player;

public class MajooneMana extends Item {


    public MajooneMana() {
        super("MajooneMana" , "Dar Nobate Baed Mana Ra 3 " +
                "Vahed Afzayesg Midahad" , 0, false, true);
    }

    @Override
    public void applyItem() {
        getPlayer().setMana(getPlayer().getMana() + 3);
    }
}
