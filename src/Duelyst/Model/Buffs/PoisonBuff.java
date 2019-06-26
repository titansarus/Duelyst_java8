package Duelyst.Model.Buffs;

public class PoisonBuff extends Buff {
    private boolean isForCard;

    public PoisonBuff(int numberOfTurn, boolean isForCard) {
        super(BuffName.POISON_BUFF, false, numberOfTurn);
        this.isForCard = isForCard;
    }

    public boolean isForCard() {
        return isForCard;
    }
}
