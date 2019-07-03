package Duelyst.Exceptions;
import static Duelyst.View.Constants.*;
public class CardOutOfStock extends MyException {

    public CardOutOfStock(){
        super(CARD_OUT_OF_STOCK_CONTENT,CARD_OUT_OF_STOCK_TITLE);
    }
}
