package Duelyst.Model.Spell;

import Duelyst.Model.Buffs.Buff;
import Duelyst.Model.Card;

import java.util.ArrayList;

public class Spell extends Card {
    private ArrayList<Buff> buffs = new ArrayList<>();
    private TargetCommunity targetCommunity;


    public Spell(String cardName, String cardDescription, int manaCost, int darikCost) {
        super(cardName, cardDescription, manaCost, darikCost);
    }

    public Spell(String cardName, String cardDescription, int manaCost, int darikCost, TargetCommunity targetCommunity) {
        super(cardName, cardDescription, manaCost, darikCost);
        this.targetCommunity = targetCommunity;
    }

    public Spell(String cardName, String cardDescription, int manaCost, int darikCost, String addressOfImage) {
        super(cardName, cardDescription, manaCost, darikCost, addressOfImage);
    }
}
