package Duelyst.Model.Items;

import Duelyst.Model.Battle.Player;

public class GhosleTaemid extends Item {

    public GhosleTaemid(Player player) {
        super(player, true);
        setManaCost(20000);
    }

    @Override
    public void applyItem() {
        //TODO dar battle Emal shavad moghe insert Kardan CardHa
    }
}
