package Duelyst.Model.Spell;

import Duelyst.Model.Buffs.Buff;
import Duelyst.Model.Card;

import java.util.ArrayList;

public class Spell extends Card {
    private ArrayList<Spell> spells=new ArrayList<>();
    private ArrayList<Buff> buffs = new ArrayList<>();
    private TargetCommunity targetCommunity;
    private TimeOfApply timeOfApply;


    public Spell(String cardName, String cardDescription, int manaCost, int darikCost) {
        super(cardName, cardDescription, manaCost, darikCost);
    }

    public Spell(String cardName, String cardDescription, int manaCost, int darikCost, TargetCommunity targetCommunity, TimeOfApply timeOfApply) {
        super(cardName, cardDescription, manaCost, darikCost);
        this.timeOfApply = timeOfApply;
        this.targetCommunity = targetCommunity;
    }

    public Spell(String cardName, String cardDescription, int manaCost, int darikCost, TargetCommunity targetCommunity) {
        super(cardName, cardDescription, manaCost, darikCost);
        this.targetCommunity = targetCommunity;
    }

    public Spell(String cardName, String cardDescription, int manaCost, int darikCost, String addressOfImage) {
        super(cardName, cardDescription, manaCost, darikCost, addressOfImage);
    }

    public TargetCommunity getTargetCommunity() {
        return targetCommunity;
    }

    public ArrayList<Buff> getBuffs() {
        return buffs;
    }

    public void setBuffs(ArrayList<Buff> buffs) {
        this.buffs = buffs;
    }

    public TimeOfApply getTimeOfApply() {
        return timeOfApply;
    }

    public void setTimeOfApply(TimeOfApply timeOfApply) {
        this.timeOfApply = timeOfApply;
    }
}

