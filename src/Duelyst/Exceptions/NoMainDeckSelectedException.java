package Duelyst.Exceptions;

import static Duelyst.View.Constants.*;

public class NoMainDeckSelectedException extends MyException {

    public NoMainDeckSelectedException() {
        super(NO_MAIN_DECK_CONTENT, NO_MAIN_DECK_TITLE);
    }
}
