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
        setCardKind(CardKind.MINION);
    }

    public Minion(String cardName, String cardDescription, int manaCost, int darikCost, int healthPoint, int actionPower, int attackRange, AttackKind attackKind, int shield, String addressOfImage) {
        super(cardName, cardDescription, manaCost, darikCost, healthPoint, actionPower, attackRange, attackKind, shield, addressOfImage);
        setCardKind(CardKind.MINION);
    }

    public Minion(String cardName, String cardDescription, int manaCost, int darikCost, int healthPoint, int actionPower, int attackRange, AttackKind attackKind, int shield, String addressOfImage , String addressOfIdleGif) {
        super(cardName, cardDescription, manaCost, darikCost, addressOfImage, addressOfIdleGif, healthPoint, actionPower, attackRange, attackKind, shield);
        setCardKind(CardKind.MINION);
    }
    public Minion(String cardName, String cardDescription, int manaCost, int darikCost, int healthPoint, int actionPower, int attackRange, AttackKind attackKind, int shield, String addressOfImage , String addressOfIdleGif , String addressOfRunGif , String addressOfAttackGif) {
        super(cardName, cardDescription, manaCost, darikCost, addressOfImage, addressOfIdleGif, addressOfRunGif,addressOfAttackGif, healthPoint, actionPower, attackRange, attackKind, shield);
        setCardKind(CardKind.MINION);
    }

    public Minion(String cardName, String cardDescription, int manaCost, int darikCost, int healthPoint, int actionPower, int attackRange, AttackKind attackKind, int shield, String addressOfImage , String addressOfIdleGif , String addressOfRunGif , String addressOfAttackGif,
                  String addressOfGetDamageGif , String addressOfDeathGif) {
        super(cardName, cardDescription, manaCost, darikCost, addressOfImage, addressOfIdleGif, addressOfRunGif,addressOfAttackGif,addressOfGetDamageGif,addressOfDeathGif,  healthPoint, actionPower, attackRange, attackKind, shield);
        setCardKind(CardKind.MINION);
    }

    private void addMinion(Minion minion) {
        getAllMinions().add(minion);
        minion.setCardKind(CardKind.MINION);
    }

    public static ArrayList<Minion> getAllMinions() {
        return allMinions;
    }

}
