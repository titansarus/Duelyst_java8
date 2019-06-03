package Duelyst.Model;

import java.util.ArrayList;

public class Hero extends Warrior implements Cloneable {

    private static ArrayList<Hero>  allHeros = new ArrayList<>();
    public Hero(String cardName, String cardDescription, int manaCost, int darikCost) {
        super(cardName, cardDescription, manaCost, darikCost);
        setCardKind(CardKind.HERO);

    addHero(this);
    }



    public static void addHero(Hero hero)
    {
        getAllHeros().add(hero);


    }


    public static ArrayList<Hero> getAllHeros() {
        return allHeros;
    }
}
