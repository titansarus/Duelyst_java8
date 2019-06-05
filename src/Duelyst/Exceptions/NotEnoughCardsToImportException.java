package Duelyst.Exceptions;
import static Duelyst.View.Constants.*;


public class NotEnoughCardsToImportException extends MyException {
    public NotEnoughCardsToImportException() {
        super(NOT_ENOUGH_CARD_TO_IMPORT_CONTENT, NOT_ENOUGH_CARD_TO_IMPORT_TITLE);
    }
}
