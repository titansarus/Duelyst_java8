package Duelyst.Model.Buffs;

public abstract class Buff {

    private BuffName buffName;
    private boolean isPositive;

    public Buff(BuffName buffName, boolean isPositive){
        this.buffName = buffName;
        this.isPositive = isPositive;
    }


    public BuffName getBuffName() {
        return buffName;
    }

    public boolean isPositive() {
        return isPositive;
    }
}
