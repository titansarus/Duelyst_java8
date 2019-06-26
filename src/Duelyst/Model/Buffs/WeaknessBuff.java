package Duelyst.Model.Buffs;

public class WeaknessBuff extends Buff {
    private boolean isForPower;
    private int decreaseNumber;

    public WeaknessBuff(int numberOfTurn, boolean isForPower, int decreaseNumber) {
        super(BuffName.WEAKNESS_BUFF, false, numberOfTurn);
        this.isForPower = isForPower;
        this.decreaseNumber = decreaseNumber;
    }

    public boolean isForPower() {
        return isForPower;
    }

    public int getDecreaseNumber() {
        return decreaseNumber;
    }
}
