package Duelyst.Exceptions;

import static Duelyst.View.Constants.*;

public class NoCardSelectedFromCollectionException extends MyException {
    public NoCardSelectedFromCollectionException() {
        super(NO_CARD_SELECTED_FROM_COLLECTION_CONTENT, NO_CARD_SELECTED_FROM_COLLECTION_TITLE);
    }
}
