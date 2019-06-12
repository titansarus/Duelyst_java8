package Duelyst.Model.Buffs;

public class PoisonBuff extends Buff {
    private boolean isForCard;

    public PoisonBuff(BuffName buffName, boolean isPositive, int numberOfTurn, boolean isForCard) {
        super(buffName, isPositive, numberOfTurn);
        this.isForCard = isForCard;
    }

    public boolean isForCard() {
        return isForCard;
    }
}
