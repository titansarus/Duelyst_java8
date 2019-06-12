package Duelyst.Model.Buffs;

import Duelyst.Model.Battle.Cell;
import Duelyst.Model.Warrior;

public class ApplyBuff {

    private static ApplyBuff applyBuff = new ApplyBuff();


    private ApplyBuff() {

    }

    public void applyBuff(Buff buff) {
        switch (buff.getBuffName()) {
            case HOLY_BUFF:
                holyBuff(buff);
            case POWER_BUFF:
                powerBuff(buff);
            case POISON_BUFF:
                poisonBuff(buff);
            case WEAKNESS_BUFF:
                weaknessBuff(buff);
            case STUN_BUFF:
                stunBuff(buff);
            case DISARM_BUFF:
                disarmBuff(buff);
        }
        buff.decreaseNumberOfTurn();
    }

    public static ApplyBuff getInstance() {
        return applyBuff;
    }

    public void holyBuff(Buff buff) {
        HolyBuff holyBuff = ((HolyBuff) buff);
        Warrior warrior = buff.getWarrior();
        warrior.setShield(holyBuff.getNumberOfHealthIncrease());
    }

    public void powerBuff(Buff buff) {
        PowerBuff powerBuff = ((PowerBuff) buff);
        Warrior warrior = buff.getWarrior();
        if (powerBuff.isForPower()) {
            warrior.increaseActionPower(powerBuff.getIncreaseNumber());
        } else {
            warrior.increaseActionPower(powerBuff.getIncreaseNumber());
        }
    }

    public void poisonBuff(Buff buff) {
        PoisonBuff poisonBuff = ((PoisonBuff) buff);
        if (!poisonBuff.isForCard()) {
            Warrior warrior = buff.getCell().getWarrior();
            warrior.increaseHealthPoint(1);
        } else {
            Warrior warrior = buff.getWarrior();
            warrior.increaseHealthPoint(1);
        }
    }

    public void weaknessBuff(Buff buff) {
        WeaknessBuff weaknessBuff = ((WeaknessBuff) buff);
        Warrior warrior = buff.getWarrior();
        if (weaknessBuff.isForPower()) {
            warrior.decreaseActionPower(weaknessBuff.getIncreaseNumber());
        } else {
            warrior.decreaseHealthPoint(weaknessBuff.getIncreaseNumber());
        }
    }

    public void stunBuff(Buff buff) {
        StunBuff stunBuff = ((StunBuff) buff);
        Warrior warrior = stunBuff.getWarrior();
        warrior.setValidCounterAttack(false);
        warrior.setValidToAttack(false);
        warrior.setValidToMove(false);
    }

    public void disarmBuff(Buff buff) {
        DisarmBuff disarmBuff = ((DisarmBuff) buff);
        Warrior warrior = disarmBuff.getWarrior();
        warrior.setValidCounterAttack(false);
    }

}
