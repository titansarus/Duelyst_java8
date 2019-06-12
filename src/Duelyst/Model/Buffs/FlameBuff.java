package Duelyst.Model.Buffs;

public class FlameBuff extends Buff {
    private boolean isForCard;

    public FlameBuff(BuffName buffName, boolean isPositive, int numberOfTurn, boolean isForCard) {
        super(buffName, isPositive, numberOfTurn);
        this.isForCard = isForCard;
    }

    public boolean isForCard() {
        return isForCard;
    }
}
