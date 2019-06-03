package Duelyst.Model;

import java.util.ArrayList;

public class Minion extends Warrior {
    private static ArrayList<Minion> allMinions = new ArrayList<>();



    public Minion(String cardName, String cardDescription, int manaCost, int darikCost) {
        super(cardName, cardDescription, manaCost, darikCost);
        setCardKind(CardKind.MINION);
        addMinion(this);
    }


    private void addMinion(Minion minion) {
        getAllMinions().add(minion);
    }

    public static ArrayList<Minion> getAllMinions() {
        return allMinions;
    }

}
