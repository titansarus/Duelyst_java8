package Duelyst.Model.Items;

import Duelyst.Model.AttackKind;
import Duelyst.Model.Battle.Player;
import Duelyst.Model.Warrior;

import java.util.ArrayList;
import java.util.Random;

public class TireDoshakh extends Item {


    public TireDoshakh(Player player) {
        super(player, false, true);
    }

    @Override
    public void applyItem() {
        Random random = new Random();
        if (!hasRangedOrHybrid())
            return;
        int randomIndex = random.nextInt(getPlayer().getInGameCards().size());
        while (((Warrior) getPlayer().getInGameCards().get(randomIndex)).getAttackKind() == AttackKind.MELEE)
            randomIndex = random.nextInt(getPlayer().getInGameCards().size());
        ((Warrior) getPlayer().getInGameCards().get(randomIndex)).increaseActionPower(2);
    }

    public boolean hasRangedOrHybrid() {
        for (int i = 0; i < getPlayer().getInGameCards().size(); i++) {
            if (((Warrior) getPlayer().getInGameCards().get(i)).getAttackKind() != AttackKind.MELEE)
                return true;
        }
        return false;
    }
}
