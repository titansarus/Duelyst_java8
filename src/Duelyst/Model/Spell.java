package Duelyst.Model;

import Duelyst.Model.Buffs.Buff;

import java.util.ArrayList;

public class Spell extends Card {
    private ArrayList<Buff> buffs = new ArrayList<>();


    public Spell(String cardName, String cardDescription, int manaCost, int darikCost) {
        super(cardName, cardDescription, manaCost, darikCost);
    }

    public Spell(String cardName, String cardDescription, int manaCost, int darikCost, String addressOfImage) {
        super(cardName, cardDescription, manaCost, darikCost, addressOfImage);
    }
}

