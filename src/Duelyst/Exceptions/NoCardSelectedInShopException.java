package Duelyst.Exceptions;
import static  Duelyst.View.Constants.*;

public class NoCardSelectedInShopException extends MyException {

    public NoCardSelectedInShopException() {
        super(NO_CARD_SELECTED_IN_SHOP_CONTENT,NO_CARD_SELECTED_IN_SHOP_TITLE);
    }
}
