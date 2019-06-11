package Duelyst.Model.Buffs;

public class ApplyBuff {


    private static ApplyBuff applyBuff = new ApplyBuff();

    private boolean isPositive;
    private boolean isHolyBuff;
    private boolean isPowerBuff;
    private boolean isPoisonBuff;
    private boolean isWeaknessBuff;
    private boolean isStunBuff;
    private boolean isDisarmBuff;


    public void applyBuff(Buff buff) {
        switch (buff.getBuffName()) {
            case HOLY_BUFF:
                holyBuff();
            case POWER_BUFF:
                powerBuff();
            case POISON_BUFF:
                poisonBuff();
            case WEAKNESS_BUFF:
                weaknessBuff();
            case STUN_BUFF:
                stunBuff();
            case DISARM_BUFF:
                disarmBuff();
        }

    }

    public static ApplyBuff getInstance() {
        return applyBuff;
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
