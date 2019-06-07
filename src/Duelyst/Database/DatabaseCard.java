package Duelyst.Database;

import Duelyst.Model.AttackKind;
import Duelyst.Model.CardKind;
import Duelyst.Model.Hero;

import java.util.ArrayList;

public class DatabaseCard {
    private static ArrayList<DatabaseCard> databaseCards = new ArrayList<>();
    private String cardName;
    private String cardDescription;
    private CardKind cardType;
    private int manaCost;
    private int darikCost;
    private int healthPoint;
    private int actionPower;
    private int attackRange;
    private AttackKind attackKind;
    private int shield;
    private String addressOfImage;

    public DatabaseCard(String cardName, String cardDescription, String cardKind, int manaCost, int darikCost, int healthPoint, int actionPower, int attackRange, String attackKind, int shield , String addressOfImage) {
        this.cardName = cardName;
        this.cardDescription = cardDescription;
        this.manaCost = manaCost;
        setCardType(cardKind);
        this.darikCost = darikCost;
        this.healthPoint = healthPoint;
        this.actionPower = actionPower;
        this.attackRange = attackRange;
        setAttackKind(attackKind);
        this.shield = shield;
        this.addressOfImage = addressOfImage;
        databaseCards.add(this);
    }


    public DatabaseCard(ArrayList<String> strings)
    {
        this(strings.get(0),strings.get(1),strings.get(2), Integer.parseInt( strings.get(3)) , Integer.parseInt(strings.get(4)),
                Integer.parseInt(strings.get(5)) , Integer.parseInt(strings.get(6)) , Integer.parseInt(strings.get(7)) , strings.get(8) , Integer.parseInt(strings.get(9)) , strings.get(10));

    }
    public void setCardType(String cardType) {
        if (cardType.trim().equalsIgnoreCase("HERO")) {
            this.cardType = CardKind.HERO;
        } else if (cardType.trim().equalsIgnoreCase("MINION")) {
            this.cardType = CardKind.MINION;
        } else if (cardType.trim().equalsIgnoreCase("SPELL")) {
            this.cardType = CardKind.SPELL;
        }
    }

    public void setAttackKind(String attackKind) {
        if (attackKind.trim().equalsIgnoreCase("MELEE")) {
            this.attackKind = AttackKind.MELEE;
        } else if (attackKind.trim().equalsIgnoreCase("RANGED")) {
            this.attackKind = AttackKind.RANGED;
        } else if (attackKind.trim().equalsIgnoreCase("HYBRID")) {
            this.attackKind = AttackKind.HYBRID;
        }
    }

    public void setAttackKind(AttackKind attackKind) {
        this.attackKind = attackKind;
    }

    public String getCardName() {
        return cardName;
    }

    public String getCardDescription() {
        return cardDescription;
    }

    public int getManaCost() {
        return manaCost;
    }

    public int getDarikCost() {
        return darikCost;
    }

    public int getHealthPoint() {
        return healthPoint;
    }

    public int getActionPower() {
        return actionPower;
    }

    public int getAttackRange() {
        return attackRange;
    }

    public AttackKind getAttackKind() {
        return attackKind;
    }

    public int getShield() {
        return shield;
    }

    public static ArrayList<DatabaseCard> getDatabaseCards() {
        return databaseCards;
    }

    public CardKind getCardType() {
        return cardType;
    }

    public String getAddressOfImage() {
        return addressOfImage;
    }
}
