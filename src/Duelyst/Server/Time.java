package Duelyst.Server;

import Duelyst.Client.Client;
import Duelyst.Client.SendMessage;
import Duelyst.Model.Battle.Battle;
import Duelyst.Model.Card;
import Duelyst.Model.CommandClasses.CommandClass;
import Duelyst.Model.CommandClasses.ShopCommand;
import Duelyst.Model.CommandClasses.ShopCommandsKind;

import java.time.Instant;

public class Time extends Thread {
    private Card card;
    private Client[] clients = new Client[2];
    private final int TIME_OF_TURN = 120;
    private int time;

    public Time(Card card, int timeAccordingToSec) {
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
        while (true) {
            try {
                Thread.sleep(1000);
            }catch (Exception e){
                e.printStackTrace();
            }
            Instant end = Instant.now();
            if (end.getEpochSecond() - start.getEpochSecond() > time) {
                break;
            }
        }
        if (card!=null) {
            ShopCommand shopCommand = new ShopCommand(ShopCommandsKind.FINISH_TIME);
            shopCommand.setAuctionCards(ServerShop.getInstance().removeCardfromAuctions(card));
            for (ClientHandler c :
                    ClientHandler.getClientHandlers()) {
                c.getFormatter().format("%s\n", CommandClass.makeJson(shopCommand));
                c.getFormatter().flush();
            }
        }
        //TODO for battle ...
    }



    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }
}
