package Duelyst.Model.Items;

import Duelyst.Model.Battle.Player;

public class AssassinationDagger extends Item {

    public AssassinationDagger(Player player){
        super(player , true);
        setManaCost(15000);
    }

    @Override
    public void applyItem() {



    }
}
