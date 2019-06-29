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
            //
            System.out.println("insert==---------------------------------------------------=>> : " + cards.size());
            //
            Random random = new Random();
            Card card = cards.get(random.nextInt(cards.size()));
            battle.setSelectedCard(card);
            battle.findValidCell(KindOfActionForValidCells.INSERT);
            Cell cell = battle.getValidCells().get(random.nextInt(battle.getValidCells().size()));
            getBattleController().handleInsertCardClickAi(cell, card);
//             battle.insertSelectedCard(cell.getColumn(), cell.getRow());
        } catch (Exception e) {
            System.out.println("Ai can not insert");
        }
    }

    private void move() {
        try {
            Player player = battle.getPlayer2();
            ArrayList<Card> cards = player.getInGameCards();
            //
            System.out.println("move==---------------------------------------------------=>> : " + cards.size());
            //
            Random random = new Random();
            Card card = cards.get(random.nextInt(cards.size()));
//            for (Card card:
//                 cards) {
            try {
                System.out.println("================================><><> " + card.getCardName());
                battle.setSelectedCell(battle.getCellOfWarrior((Warrior) card));

                battle.findValidCell(KindOfActionForValidCells.MOVE);
                Cell cell = battle.getValidCells().get(random.nextInt(battle.getValidCells().size()));
                getBattleController().moveAnimationRunAi(new int[]{cell.getRow(), cell.getColumn()}, (Warrior) card); //CHECK THAT CARD IS WARRIOR
//                    battle.move(cell.getColumn(), cell.getRow());
            } catch (Exception e) {
//                    e.printStackTrace();
                System.out.println("Ai can not move");
            }
//            }
        } catch (Exception e) {
//            e.printStackTrace();
            System.out.println("Ai can not move");
        }
    }

    private void attack() {
        try {
            Player player = battle.getPlayer2();
            ArrayList<Card> cards = player.getInGameCards();
            Random random = new Random();
            System.out.println("attack==---------------------------------------------------=>> : " + cards.size());
            for (Card card :
                    cards) {
                try {
                    System.out.println("================================><><> " + card.getCardName());
                    battle.setSelectedCell(battle.getCellOfWarrior((Warrior) card));
                    battle.findValidCell(KindOfActionForValidCells.ATTACK);
                    Cell cell = battle.getValidCells().get(random.nextInt(battle.getValidCells().size()));
                    Warrior attacker = (Warrior) card;
                    Warrior attackedWarrior = cell.getWarrior();
                    getBattleController().handleAttackFromAi(attacker, attackedWarrior);
                } catch (Exception e) {
//                    e.printStackTrace();
                    System.out.println("Ai can not attack");
                }
            }
            //battle.attack(attacker, attackedWarrior, false);
        } catch (Exception e) {
//            e.printStackTrace();
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
