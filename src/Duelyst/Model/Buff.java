package Duelyst.Model;

public class Buff {

    private boolean isPositive;
    private boolean isHolyBuff;
    private boolean isPowerBuff;
    private boolean isPoisonBuff;
    private boolean isWeaknessBuff;
    private boolean isStunBuff;
    private boolean isDisarmBuff;


    public Buff(BuffName buffName, boolean isPositive) {
        switch (buffName) {
            case HOLY_BUFF:
                isHolyBuff = true;
            case POWER_BUFF:
                isPowerBuff = true;
            case POISON_BUFF:
                isPoisonBuff = true;
            case WEAKNESS_BUFF:
                isWeaknessBuff = true;
            case STUN_BUFF:
                isStunBuff = true;
            case DISARM_BUFF:
                isDisarmBuff = true;
        }
        this.isPositive = isPositive;
    }



    public void holyBuff() {

    }

    public void powerBuff() {

    }

    public void poisonBuff() {

    }

    public void weaknessBuff() {

    }

    public void stunBuff() {

    }

    public void disarmBuff() {


    }

}
