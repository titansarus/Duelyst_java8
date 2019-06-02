package Duelyst.Exceptions;
import static Duelyst.View.Constants.*;

public class InvalidPasswordException extends MyException {
    public InvalidPasswordException(){
        super(INVALID_PASSWORD_CONTENT,INVALID_PASSWORD_TITLE);
    }
}
