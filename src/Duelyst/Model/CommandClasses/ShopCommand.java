package Duelyst.Model.CommandClasses;

import Duelyst.Model.Card;

import java.util.ArrayList;

public class ShopCommand extends CommandClass {
    private ShopCommandsKind shopCommandsKind;
    private ArrayList<Card> cards;
    private Card newCard;
    private Card auctionCard;

    public ShopCommand(ShopCommandsKind shopCommandsKind) {
        super(CommandKind.SHOP);
        this.shopCommandsKind = shopCommandsKind;
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public void setCards(ArrayList<Card> cards) {
        this.cards = cards;
    }

    public Card getNewCard() {
        return newCard;
    }

    public void setNewCard(Card newCard) {
        this.newCard = newCard;
    }

    public Card getAuctionCard() {
        return auctionCard;
    }

    public void setAuctionCard(Card auctionCard) {
        this.auctionCard = auctionCard;
    }

    public ShopCommandsKind getShopCommandsKind() {
        return shopCommandsKind;
    }

    public void setShopCommandsKind(ShopCommandsKind shopCommandsKind) {
        this.shopCommandsKind = shopCommandsKind;
    }
}