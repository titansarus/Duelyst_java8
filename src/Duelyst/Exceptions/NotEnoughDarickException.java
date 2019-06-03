package Duelyst.Exceptions;
import static Duelyst.View.Constants.*;


public class NotEnoughDarickException extends MyException {


    public NotEnoughDarickException() {
        super(NOT_ENOUGH_DARICK_CONTENT, NOT_ENOUGH_DARICK_TITLE);
    }
}
