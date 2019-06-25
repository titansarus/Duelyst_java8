package Duelyst.Exceptions;
import static Duelyst.View.Constants.*;
public class NotValidDeckException extends MyException {
    public NotValidDeckException(){
        super(DECK_IS_INVALID_CONTENT,DECK_IS_INVALID_TITLE);
    }
}
