package Duelyst.Exceptions;
import static Duelyst.View.Constants.*;


public class UserExistException extends MyException {
    public UserExistException() {
        super(USER_EXIST_CONTENT,USER_EXIST_TITLE);
    }
}
