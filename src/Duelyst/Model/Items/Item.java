package Duelyst.Model.Items;

import Duelyst.Model.Account;
import Duelyst.Model.Battle.Player;
import Duelyst.Model.Card;

import java.util.ArrayList;

public abstract class Item extends Card{

    private static ArrayList<Item> allItems = new ArrayList<>();

    private Player player;
    private boolean isUsable;
    private boolean applyFirst;

    public Item(String itemName, String description,int darikCost, boolean isUsable, boolean applyFirst) {
        super(itemName,description,0,darikCost);
        this.isUsable = isUsable;
        this.applyFirst = applyFirst;
    }

    public Item(String itemName, String description,int darikCost, boolean isUsable, boolean applyFirst , String imageAddress) {
        super(itemName,description,0,darikCost,imageAddress);
        this.isUsable = isUsable;
        this.applyFirst = applyFirst;
    }

    public static void addItem(Item item) {
        allItems.add(item);
    }

    public static void removeItem(Item item) {
        allItems.remove(item);
    }

    public static ArrayList<Item> getAllItems() {
        return allItems;
    }

    public abstract void applyItem();

    public boolean isUsable() {
        return isUsable;
    }

    public boolean isApplyFirst() {
        return applyFirst;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
