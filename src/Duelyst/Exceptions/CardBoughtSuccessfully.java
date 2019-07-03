package Duelyst.Exceptions;
import static Duelyst.View.Constants.*;
public class CardBoughtSuccessfully extends MyException {

    public CardBoughtSuccessfully(){
        super(CARD_BOUGHT_SUCCESSFULLY_CONTENT, CARD_BOUGHT_SUCCESSFULLY_TITLE);

    }

}
