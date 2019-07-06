package Duelyst.Server;

import Duelyst.Client.Client;
import Duelyst.Model.Account;
import Duelyst.Model.Card;
import Duelyst.Model.CommandClasses.CommandClass;
import Duelyst.Model.CommandClasses.ShopCommand;
import Duelyst.Model.CommandClasses.ShopCommandsKind;

import java.time.Instant;
import java.util.ArrayList;

public class Time extends Thread {
    private static ArrayList<Time> times = new ArrayList<>();
    private Instant now;
    private Instant start;
    private Card card;
    private Client[] clients = new Client[2];
    private final int TIME_OF_TURN = 120;
    private int time;

    public Time(Card card, int timeAccordingToSec) {
        times.add(this);
        this.card = card;
        this.time = timeAccordingToSec;
    }

    public Time(Client client1, Client client2) {
        clients[0] = client1;
        clients[1] = client2;
    }

    @Override
    public void run() {
        Instant start = Instant.now();
        this.start = start;
        while (true) {
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
            Instant end = Instant.now();
            this.now = end;
            if (end.getEpochSecond() - start.getEpochSecond() > time) {
                break;
            }
        }
        if (card != null) {
            ShopCommand shopCommand = new ShopCommand(ShopCommandsKind.FINISH_TIME);
            shopCommand.setAuctionCards(ServerShop.getInstance().removeCardFromAuctions(card));

            ServerShop.getInstance().getAuctionCards().remove(card);

            ShopCommand shopCommand1 = new ShopCommand(ShopCommandsKind.REMOVE_CARD);
            shopCommand1.setAuctionCard(card);

            ShopCommand shopCommand2 = new ShopCommand(ShopCommandsKind.ADD_CARD);
            shopCommand2.setAuctionCard(card);

            for (ClientHandler c :
                    ClientHandler.getClientHandlers()) {
                c.getFormatter().format("%s\n", CommandClass.makeJson(shopCommand));
                c.getFormatter().flush();
                setAccounts(shopCommand1, shopCommand2, c);
            }
        }
        //TODO for battle ...
    }

    private void setAccounts(ShopCommand shopCommand1, ShopCommand shopCommand2, ClientHandler c) {
        if (c.getUserName().equals(card.getAccount().getUsername())) {
            removeFromSeller(shopCommand1, c);
        }
        if (card.getAuctionClient() != null && c.getUserName().equals(card.getAuctionClient())) {
            addToCustomer(shopCommand1, shopCommand2, c);

        }
    }

    private void removeFromSeller(ShopCommand shopCommand1, ClientHandler c) {
        Account account = Server.getAccount(c.getUserName());
        account.setDarick(account.getDarick() + shopCommand1.getAuctionCard().getAuctionCost());
        Card card = null;
        for (Card card1 :
                account.getCardCollection().getCards()) {
            if (card1.getCardName().equals(shopCommand1.getAuctionCard().getCardName())) {
                card = card1;
            }
        }
        account.getCardCollection().getCards().remove(card);
        c.getFormatter().format("%s\n", CommandClass.makeJson(shopCommand1));
        c.getFormatter().flush();
    }

    private void addToCustomer(ShopCommand shopCommand1, ShopCommand shopCommand2, ClientHandler c) {
        Account account = Server.getAccount(c.getUserName());
        account.setDarick(account.getDarick() - shopCommand1.getAuctionCard().getAuctionCost());
        account.getCardCollection().getCards().add(shopCommand1.getAuctionCard());
        c.getFormatter().format("%s\n", CommandClass.makeJson(shopCommand2));
        c.getFormatter().flush();
    }

    private long getSec() {
        return now.getEpochSecond() - start.getEpochSecond();
    }

    public static long getExtantTimeOfCard(Card card) {
        for (Time t :
                times) {
            if (t.getCard().getCardId().equals(card.getCardId())) {
                return t.getSec();
            }
        }
        return -1;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }
}
