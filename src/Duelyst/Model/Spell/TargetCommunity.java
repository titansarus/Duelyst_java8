package Duelyst.Model.Spell;

public enum TargetCommunity {
    ENEMY_WARRIOR,FRIENDLY_WARRIOR,CELLS,ALL_OF_ENEMY,ALL_OF_FRIEND;
    private int sizeCells;

    public TargetCommunity setSizeCells(int sizeCells) {
        this.sizeCells = sizeCells;
        return this;
    }
}
