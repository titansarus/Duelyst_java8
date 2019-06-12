package Duelyst.Model.Buffs;

import Duelyst.Model.Battle.Cell;
import Duelyst.Model.Warrior;

public class ApplyBuff {

    private static ApplyBuff applyBuff = new ApplyBuff();


    private ApplyBuff() {

    }

    public void applyBuff(Buff buff) {
        if (buff.getNumberOfTurn() != 0) {
            switch (buff.getBuffName()) {
                case HOLY_BUFF:
                    holyBuff(buff);
                    break;
                case POWER_BUFF:
                    powerBuff(buff);
                    break;
                case POISON_BUFF:
                    poisonBuff(buff);
                    break;
                case WEAKNESS_BUFF:
                    weaknessBuff(buff);
                    break;
                case STUN_BUFF:
                    stunBuff(buff);
                    break;
                case DISARM_BUFF:
                    disarmBuff(buff);
                    break;
                case FLAME_BUFF:
                    flameBuff(buff);
            }
        } else {
            switch (buff.getBuffName()) {
                case HOLY_BUFF:
                    cancelHolyBuff(buff);
                    break;
                case POWER_BUFF:
                    cancelPowerBuff(buff);
                    break;
                case WEAKNESS_BUFF:
                    cancelWeaknessBuff(buff);
                    break;
                case STUN_BUFF:
                    cancelStunBuff(buff);
                    break;
                case DISARM_BUFF:
                    cancelDisarmBuff(buff);
            }
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

    public void flameBuff(Buff buff) {
        FlameBuff flameBuff = ((FlameBuff) buff);
        if (!flameBuff.isForCard()) {
            Warrior warrior = buff.getCell().getWarrior();
            warrior.increaseHealthPoint(1);
        } else {
            Warrior warrior = buff.getWarrior();
            warrior.increaseHealthPoint(1);
        }
    }

    public void cancelHolyBuff(Buff buff) {
        Warrior warrior = buff.getWarrior();
        warrior.setShield(0);
    }

    public void cancelPowerBuff(Buff buff) {
        PowerBuff powerBuff = ((PowerBuff) buff);
        Warrior warrior = buff.getWarrior();
        if (powerBuff.isForPower()) {
            warrior.decreaseActionPower(powerBuff.getIncreaseNumber());
        } else {
            warrior.decreaseHealthPoint(powerBuff.getIncreaseNumber());
        }
    }


    public void cancelDisarmBuff(Buff buff) {
        DisarmBuff disarmBuff = ((DisarmBuff) buff);
        Warrior warrior = disarmBuff.getWarrior();
        warrior.setValidCounterAttack(true);
    }

    public void cancelStunBuff(Buff buff) {
        StunBuff stunBuff = ((StunBuff) buff);
        Warrior warrior = stunBuff.getWarrior();
        warrior.setValidCounterAttack(true);
        warrior.setValidToAttack(true);
        warrior.setValidToMove(true);
    }

    public void cancelWeaknessBuff(Buff buff) {
        WeaknessBuff weaknessBuff = ((WeaknessBuff) buff);
        Warrior warrior = buff.getWarrior();
        if (weaknessBuff.isForPower()) {
            warrior.increaseActionPower(weaknessBuff.getIncreaseNumber());
        } else {
            warrior.increaseHealthPoint(weaknessBuff.getIncreaseNumber());
        }
    }


}