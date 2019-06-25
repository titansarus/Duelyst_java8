package Duelyst.Model.Items;

import Duelyst.Model.Battle.Battle;
import Duelyst.Model.Battle.Player;

public class AssassinationDagger extends Item {



    public AssassinationDagger() {
        super("AssassinationDagger", "Hengame Gozashtane Har Nirooye Khodi Yek Vahed Be Heroye Doshman Zarbe Mizanad",
                15000, true, false,"res/Items/AssasinationDagger/AssassinationDagger.png");
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
