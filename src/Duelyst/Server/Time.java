package Duelyst.Server;

import Duelyst.Model.Battle.Battle;
import Duelyst.Model.Card;

import java.time.Instant;

public class Time {
    private Card card;
    private Battle battle;
    private final int TIME_OF_TURN = 120 ;

    public void timer(int sec) throws InterruptedException {
        sendMessageOfterPassingTime(sec);
    }

    public void timer(int min, int sec) throws InterruptedException {
        int time = min * 60 + sec;
        sendMessageOfterPassingTime(time);
    }

    private void sendMessageOfterPassingTime(int time) throws InterruptedException {
        Instant start = Instant.now();
        while (true) {
            Thread.sleep(1000);
            Instant end = Instant.now();
            if (end.getEpochSecond() - start.getEpochSecond() > time) {
                break;
            }
        }
        //TODO send message for all clients to remove this card and sell card to best offer
    }

    public void endTurn() throws InterruptedException {
        Instant start = Instant.now();
        while (true) {
            Thread.sleep(1000);
            Instant end = Instant.now();
            if (end.getEpochSecond() - start.getEpochSecond() > TIME_OF_TURN-20){
                //TODO SEND a ٌ Warning to current client
            }
            if (end.getEpochSecond() - start.getEpochSecond() > TIME_OF_TURN) {
                break;
            }
        }
        //TODO end turn in this battle
    }


    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public Battle getBattle() {
        return battle;
    }

    public void setBattle(Battle battle) {
        this.battle = battle;
    }
}
