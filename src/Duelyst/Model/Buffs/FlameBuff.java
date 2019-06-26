package Duelyst.Model.Buffs;

public class FlameBuff extends Buff {
    private boolean isForCard;

    public FlameBuff(int numberOfTurn, boolean isForCard) {
        super(BuffName.FLAME_BUFF, false, numberOfTurn);
        this.isForCard = isForCard;
    }

    public boolean isForCard() {
        return isForCard;
    }
}
