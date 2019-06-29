package Duelyst.Exceptions;

public class NotValidCellForSpellException extends MyException {
    public NotValidCellForSpellException(){
        super("this cell is invalid","invalid Cell");
    }
}