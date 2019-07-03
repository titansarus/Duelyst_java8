package Duelyst.Model;

import Duelyst.Controllers.BattleController;
import Duelyst.Model.Battle.Battle;
import Duelyst.Model.Battle.Cell;
import Duelyst.Model.Battle.KindOfActionForValidCells;
import Duelyst.Model.Battle.Player;

import java.util.ArrayList;
import java.util.Random;

public class Ai extends Account {
    private BattleController battleController;
    private Battle battle = null;

    public Ai(int numberOfAi) {
        super("AI:)", "AI:)");
        if (numberOfAi == 1) {
            this.setMainDeck(Deck.AiDeckBuilder(1, this));
        } else if (numberOfAi == 2) {
            this.setMainDeck(Deck.AiDeckBuilder(2, this));
        } else if (numberOfAi == 3) {
            this.setMainDeck(Deck.AiDeckBuilder(3, this));
        }
        setDarick(1000000000);
    }

    public void setBattle(Battle battle) {
        this.battle = battle;
    }

    public void setMainDeck(Deck deck) {
        getCardCollection().setMainDeck(deck);
    }

    public void playGame() {
        insertCard();
        move();
        attack();
    }

    private void insertCard() {
        try {
            Player player = battle.getPlayer2();
            ArrayList<Card> cards = player.getHand();
            Random random = new Random();
            Card card = cards.get(random.nextInt(cards.size()));
            battle.setSelectedCard(card);
            battle.findValidCell(KindOfActionForValidCells.INSERT);
            Cell cell = battle.getValidCells().get(random.nextInt(battle.getValidCells().size()));
            getBattleController().handleInsertCardClickAi(cell, card);
        } catch (Exception e) {
            System.out.println("Ai can not insert");
        }
    }

    private void move() {
        try {
            Player player = battle.getPlayer2();
            ArrayList<Card> cards = player.getInGameCards();
            Random random = new Random();
            Card card = cards.get(random.nextInt(cards.size()));
            try {
                battle.setSelectedCell(battle.getCellOfWarrior((Warrior) card));

                battle.findValidCell(KindOfActionForValidCells.MOVE);
                Cell cell = battle.getValidCells().get(random.nextInt(battle.getValidCells().size()));
                battle.move(cell.getRow(), cell.getColumn(), ((Warrior) card));
                // getBattleController().moveAnimationRunAi(new int[]{cell.getRow(), cell.getColumn()}, (Warrior) card); //CHECK THAT CARD IS WARRIOR
            } catch (Exception e) {
                System.out.println("Ai can not move");
            }
        } catch (Exception e) {
            System.out.println("Ai can not move");
        }
    }

    private void attack() {
        try {
            Player player = battle.getPlayer2();
            ArrayList<Card> cards = player.getInGameCards();
            Random random = new Random();
            for (Card card :
                    cards) {
                try {
                    battle.setSelectedCell(battle.getCellOfWarrior((Warrior) card));
                    battle.findValidCell(KindOfActionForValidCells.ATTACK);
                    Cell cell = battle.getValidCells().get(random.nextInt(battle.getValidCells().size()));
                    Warrior attacker = (Warrior) card;
                    Warrior attackedWarrior = cell.getWarrior();
                    battle.handleAttackCounterDeath(attacker, attackedWarrior);
                    // getBattleController().handleAttackFromAi(attacker, attackedWarrior);
                } catch (Exception e) {
                    System.out.println("Ai can not attack");
                }
            }
        } catch (Exception e) {
            System.out.println("Ai can not attack");
        }
    }

    public BattleController getBattleController() {
        return battleController;
    }

    public void setBattleController(BattleController battleController) {
        this.battleController = battleController;
    }
}
