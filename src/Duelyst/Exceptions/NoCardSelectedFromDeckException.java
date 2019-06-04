package Duelyst.Exceptions;
import static Duelyst.View.Constants.*;

public class NoCardSelectedFromDeckException extends MyException {
    public NoCardSelectedFromDeckException() {
        super(NO_CARD_SELECTED_FROM_DECK_CONTENT, NO_CARD_SELECTED_FROM_DECK_TITLE);
    }
}
