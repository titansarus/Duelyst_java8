package Duelyst.Exceptions;
import static Duelyst.View.Constants.*;

public class DeckExistException extends MyException{
    public DeckExistException() {
        super(DECK_EXIST_WITH_THIS_NAME_CONTENT, DECK_EXIST_WITH_THIS_NAME_TITLE);
    }
}
