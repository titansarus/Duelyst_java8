package Duelyst.Exceptions;

import static Duelyst.View.Constants.*;

public class UserNotExistException extends MyException {
    public UserNotExistException() {
        super(USER_NOT_EXIST_CONTENT, USER_NOT_EXIST_TITLE);
    }
}
