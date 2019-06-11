package Duelyst.Model.Buffs;

public abstract class Buff {

    private BuffName buffName;
    private boolean isPositive;
    private int numberOfTurn;

    public Buff(BuffName buffName, boolean isPositive, int numberOfTurn) {
        this.buffName = buffName;
        this.isPositive = isPositive;
        this.numberOfTurn = numberOfTurn;
    }


    public BuffName getBuffName() {
        return buffName;
    }

    public boolean isPositive() {
        return isPositive;
    }
}
