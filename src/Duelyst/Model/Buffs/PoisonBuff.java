package Duelyst.Model.Buffs;

import Duelyst.Model.Battle.Cell;
import Duelyst.Model.Card;

public class PoisonBuff extends Buff {
    private boolean isForCard;
    private Card card;
    private Cell cell;

    public PoisonBuff(BuffName buffName, boolean isPositive, int numberOfTurn, boolean isForCard, Card card, Cell cell) {
        super(buffName, isPositive, numberOfTurn);
        this.isForCard = isForCard;
        this.card = card;
        this.cell = cell;
    }

}
