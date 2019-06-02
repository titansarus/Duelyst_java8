package Duelyst.Exceptions;

import static Duelyst.View.Constants.*;

public class NotExistLoginUserException extends MyException {
    public NotExistLoginUserException() {
        super(NOT_EXIST_LOGIN_USER_CONTENT,NOT_EXIST_LOGIN_USER_TITLE);
    }

}
