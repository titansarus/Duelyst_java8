package Duelyst.Model.Spell;

import Duelyst.Model.Buffs.Buff;
import Duelyst.Model.Card;
import Duelyst.Model.CardKind;

import java.util.ArrayList;

public class Spell extends Card {

    private ArrayList<Buff> buffs = new ArrayList<>();
    private TargetCommunity targetCommunity;


    public Spell(String cardName, String cardDescription, int manaCost, int darikCost) {
        super(cardName, cardDescription, manaCost, darikCost);
        setCardKind(CardKind.SPELL);
    }

    public Spell(String cardName, String cardDescription, int manaCost, int darikCost, TargetCommunity targetCommunity) {
        super(cardName, cardDescription, manaCost, darikCost, "./res/Spells/Test.png", "./res/Spells/Test.gif");
        this.targetCommunity = targetCommunity;
        setCardKind(CardKind.SPELL);
    }

    public Spell(String cardName, String cardDescription, int manaCost, int darikCost, String addressOfImage) {
        super(cardName, cardDescription, manaCost, darikCost, addressOfImage);
        setCardKind(CardKind.SPELL);
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
}

