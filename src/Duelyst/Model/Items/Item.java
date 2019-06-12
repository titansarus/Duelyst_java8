package Duelyst.Model.Items;

import Duelyst.Model.Battle.Player;
import Duelyst.Model.Card;

public abstract class Item {

    private Player player;
    private boolean isUsable;
    private int manaCost;

    public Item(Player player, boolean isUsable) {
        this.player = player;
        this.isUsable = isUsable;
    }

    public abstract void applyItem();

    public Player getPlayer() {
        return player;
    }

    public boolean isUsable() {
        return isUsable;
    }

    public int getManaCost() {
        return manaCost;
    }

    public void setManaCost(int manaCost) {
        this.manaCost = manaCost;
    }
}
