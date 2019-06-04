package Duelyst.Exceptions;
import static Duelyst.View.Constants.*;


public class NoDeckSelectedException extends MyException{
    public NoDeckSelectedException() {
        super(NO_DECK_SELECTED_CONTENT, NO_DECK_SELECTED_TITLE);
    }
}
