package Duelyst.Exceptions;

import static Duelyst.View.Constants.*;

public class EmptyFieldsException extends MyException {
    public EmptyFieldsException() {
        super(EMPTY_FIELD_EXCEPTION_CONTENT, EMPTY_FIELD_EXCEPTION_TITLE);
    }
}
