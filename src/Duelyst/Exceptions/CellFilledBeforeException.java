package Duelyst.Exceptions;
import static Duelyst.View.Constants.*;


public class CellFilledBeforeException extends MyException {
    public CellFilledBeforeException() {
        super(CELL_FILLED_BEFORE_CONTENT, CELL_FILLED_BEFORE_TITLE);
    }
}
