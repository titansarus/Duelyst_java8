package Duelyst.Exceptions;
import static Duelyst.View.Constants.*;
public class CreateCardFieldNotCompleteException extends MyException {
    public CreateCardFieldNotCompleteException() {
        super(CREATE_CARD_FIELD_NOT_COMPLETE_CONTENT, CREATE_CARD_FIELD_NOT_COMPLETE_TITLE);
    }
}
