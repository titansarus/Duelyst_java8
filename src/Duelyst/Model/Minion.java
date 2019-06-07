package Duelyst.Model;

import java.util.ArrayList;

public class Minion extends Warrior {
    private static ArrayList<Minion> allMinions = new ArrayList<>();



    public Minion(String cardName, String cardDescription, int manaCost, int darikCost) {
        super(cardName, cardDescription, manaCost, darikCost);
        setCardKind(CardKind.MINION);
        addMinion(this);
    }

    public Minion(String cardName, String cardDescription, int manaCost, int darikCost, int healthPoint, int actionPower, int attackRange, AttackKind attackKind, int shield) {
        super(cardName, cardDescription, manaCost, darikCost, healthPoint, actionPower, attackRange, attackKind, shield);
    }

    public Minion(String cardName, String cardDescription, int manaCost, int darikCost, int healthPoint, int actionPower, int attackRange, AttackKind attackKind, int shield, String addressOfImage) {
        super(cardName, cardDescription, manaCost, darikCost, healthPoint, actionPower, attackRange, attackKind, shield, addressOfImage);
    }

    private void addMinion(Minion minion) {
        getAllMinions().add(minion);
    }

    public static ArrayList<Minion> getAllMinions() {
        return allMinions;
    }

}
