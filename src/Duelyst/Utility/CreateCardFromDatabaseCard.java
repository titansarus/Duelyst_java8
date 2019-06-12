package Duelyst.Utility;

import Duelyst.Database.DatabaseCard;
import Duelyst.Model.*;
import Duelyst.Model.Spell.Spell;


import java.util.ArrayList;

public class CreateCardFromDatabaseCard {

    public static ArrayList<Card> createCards(ArrayList<DatabaseCard> databaseCards) {
        ArrayList<Card> cards = new ArrayList<>();
        if (databaseCards != null) {
            for (int i = 0; i < databaseCards.size(); i++) {
                DatabaseCard databaseCard = databaseCards.get(i);
                if (databaseCard != null) {
                    if (databaseCard.getCardType().equals(CardKind.HERO)) {
                        Hero hero = new Hero(databaseCard.getCardName(), databaseCard.getCardDescription(), databaseCard.getManaCost(), databaseCard.getDarikCost(),
                                databaseCard.getHealthPoint(), databaseCard.getActionPower(), databaseCard.getAttackRange(), databaseCard.getAttackKind(),
                                databaseCard.getShield(),databaseCard.getAddressOfImage(),databaseCard.getAddressOfIdleGif() , databaseCard.getAddressOfRunGif(),databaseCard.getAddressOfAttackGif() , databaseCard.getAddressOfGetDamageGif(),databaseCard.getAddressOfDeathGif());
                        cards.add(hero);
                    }
                    if (databaseCard.getCardType().equals(CardKind.MINION))
                    {
                        Minion minion = new Minion(databaseCard.getCardName(),databaseCard.getCardDescription(),databaseCard.getManaCost(),databaseCard.getDarikCost(),databaseCard.getHealthPoint(),databaseCard.getActionPower()
                        ,databaseCard.getAttackRange(),databaseCard.getAttackKind(),databaseCard.getShield(),databaseCard.getAddressOfImage() , databaseCard.getAddressOfIdleGif() , databaseCard.getAddressOfRunGif(),databaseCard.getAddressOfAttackGif() , databaseCard.getAddressOfGetDamageGif(),databaseCard.getAddressOfDeathGif());
                        cards.add(minion);
                    }
                    else if (databaseCard.getCardType().equals(CardKind.SPELL))
                    {
                        Spell spell = new Spell(databaseCard.getCardName(),databaseCard.getCardDescription(),databaseCard.getManaCost(),databaseCard.getDarikCost(),databaseCard.getAddressOfImage());
                        cards.add(spell);
                    }
                }
            }
        }
        return cards;
    }
}