package Duelyst.Model;

public class Ai extends Account {
    Deck deck;

    public Ai(int numberOfAi) {
        super("AI:)","AI:)");
        if (numberOfAi == 1) {
            this.setMainDeck(Deck.AiDeckBuilder(1));
        } else if (numberOfAi == 2) {
            this.setMainDeck(Deck.AiDeckBuilder(2));
        } else if (numberOfAi == 3) {
            this.setMainDeck(Deck.AiDeckBuilder(3));
        }
        setDarick(1000000000);
    }

    public void setMainDeck(Deck deck) {
        this.deck = deck;
    }
}
