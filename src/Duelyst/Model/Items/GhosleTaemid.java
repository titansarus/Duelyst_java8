package Duelyst.Model.Items;

import Duelyst.Model.Battle.Player;

public class GhosleTaemid extends Item {

    public GhosleTaemid(Player player) {
        super(player, true , false);
        setManaCost(20000);
    }

    @Override
    public void applyItem() {
        //TODO dar battle Emal shavad moghe insert Kardan CardHa, Faghat Bayad Yek HolyBuff Ba 2 dast mandegari be minion dad
    }
}
