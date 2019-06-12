package Duelyst.Model.Items;

import Duelyst.Model.Battle.Battle;
import Duelyst.Model.Battle.Player;

public class AssassinationDagger extends Item {

    public AssassinationDagger(Player player) {
        super(player, true, false);
        setManaCost(15000);
    }

    @Override
    public void applyItem() {
        Player player;
        if (Battle.getRunningBattle().getTurn() % 2 == 1)
            player = Battle.getRunningBattle().getPlayer2();
        else
            player = Battle.getRunningBattle().getPlayer1();
        player.getHero().decreaseHealthPoint(1);
    }
    //TODO Dar Insert Kardan Bayad Emal Shavad :)
}
