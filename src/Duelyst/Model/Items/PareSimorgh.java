package Duelyst.Model.Items;

import Duelyst.Model.AttackKind;
import Duelyst.Model.Battle.Battle;
import Duelyst.Model.Battle.Player;
import Duelyst.Model.Warrior;

public class PareSimorgh extends Item {

    public PareSimorgh() {
        super("PareSimorgh" ,"Baraye Hybrid Va Ranged: Az Heroe Harif 2 Vahed Ghodrate Zarbe Kam Mikonad (Yani Agar Ghahremane Harfi Hybrid Ya Ranged Bood In Ettefagh Mioftad)",
                3500, true, true,"res/Items/PareSimorgh/PareSimorgh.png");
    }

    @Override
    public void applyItem() {
        Battle battle = Battle.getRunningBattle();
        if (battle.getTurn() % 2 == 1) {
            if (battle.getPlayer2().getHero().getAttackKind() != AttackKind.MELEE)
                battle.getPlayer2().getHero().decreaseActionPower(2);
        } else {
            if (battle.getPlayer1().getHero().getAttackKind() != AttackKind.MELEE)
                battle.getPlayer1().getHero().decreaseActionPower(2);
        }
    }
}
