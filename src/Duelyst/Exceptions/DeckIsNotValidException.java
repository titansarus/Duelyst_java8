package Duelyst.Exceptions;

import static Duelyst.View.Constants.*;


public class DeckIsNotValidException extends MyException {
    public DeckIsNotValidException() {
        super(DECK_IS_INVALID_CONTENT, DECK_IS_INVALID_TITLE);
    }
}
