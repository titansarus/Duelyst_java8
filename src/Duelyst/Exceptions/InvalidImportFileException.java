package Duelyst.Exceptions;
import static Duelyst.View.Constants.*;


public class InvalidImportFileException extends MyException {
    public InvalidImportFileException() {
        super(INVALID_IMPORT_FILE_CONTENT, INVALID_IMPORT_FILE_TITLE);
    }
}
