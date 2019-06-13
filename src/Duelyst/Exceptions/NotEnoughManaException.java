package Duelyst.Exceptions;
import static Duelyst.View.Constants.*;
public class NotEnoughManaException extends MyException {
    public NotEnoughManaException() {
        super(NOT_ENOUGHT_MANA_CONTENT,NOT_ENOUGHT_MANA_TITLE);
    }
}
