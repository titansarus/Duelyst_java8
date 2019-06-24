package Duelyst.Model;

public class Ai extends Account {

    public Ai(int numberOfAi) {
        super("AI:)","AI:)");
        if (numberOfAi == 1) {
            this.setMainDeck(Deck.AiDeckBuilder(1,this));
        } else if (numberOfAi == 2) {
            this.setMainDeck(Deck.AiDeckBuilder(2,this));
        } else if (numberOfAi == 3) {
            this.setMainDeck(Deck.AiDeckBuilder(3,this));
        }
        setDarick(1000000000);
    }

    public void setMainDeck(Deck deck) {
      getCardCollection().setMainDeck(deck);
    }
}
