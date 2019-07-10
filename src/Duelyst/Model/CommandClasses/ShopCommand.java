package Duelyst.Model.CommandClasses;

import Duelyst.Model.Account;
import Duelyst.Model.Card;

import java.util.ArrayList;

public class ShopCommand extends CommandClass {
    private ShopCommandsKind shopCommandsKind;
    private ArrayList<Card> cards;
    private Card newCard;
    private Card auctionCard;
    private long auctionCardTime;
    private Card buyCard;
    private Card sellCard;
    private ArrayList<Card> finishedCard;
    private ArrayList<Card> auctionCards;
    private Account auctionClient;
    private int auctionPrice;

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

    public Card getSellCard() {
        return sellCard;
    }

    public void setSellCard(Card sellCard) {
        this.sellCard = sellCard;
    }

    public Card getBuyCard() {
        return buyCard;
    }

    public void setBuyCard(Card buyCard) {
        this.buyCard = buyCard;
    }

    public ArrayList<Card> getFinishedCard() {
        return finishedCard;
    }

    public void setFinishedCard(ArrayList<Card> finishedCard) {
        this.finishedCard = finishedCard;
    }

    public ArrayList<Card> getAuctionCards() {
        return auctionCards;
    }

    public void setAuctionCards(ArrayList<Card> auctionCards) {
        this.auctionCards = auctionCards;
    }

    public long getAuctionCardTime() {
        return auctionCardTime;
    }

    public void setAuctionCardTime(long auctionCardTime) {
        this.auctionCardTime = auctionCardTime;
    }

    public Account getAuctionClient() {
        return auctionClient;
    }

    public void setAuctionClient(Account auctionClient) {
        this.auctionClient = auctionClient;
    }

    public int getAuctionPrice() {
        return auctionPrice;
    }

    public void setAuctionPrice(int auctionPrice) {
        this.auctionPrice = auctionPrice;
    }
}