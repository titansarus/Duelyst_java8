package Duelyst.Client;

import Duelyst.Controllers.Container;
import Duelyst.Controllers.LoginController;
import Duelyst.Exceptions.UserNotExistException;
import Duelyst.Model.Account;
import Duelyst.Model.Card;
import Duelyst.Model.CommandClasses.CommandClass;
import Duelyst.Model.CommandClasses.LoginCommand;
import Duelyst.Model.CommandClasses.ShopCommand;
import Duelyst.Model.Shop;
import com.gilecode.yagson.YaGson;
import com.gilecode.yagson.YaGsonBuilder;


import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class ReadMessage extends Thread {

    private Scanner netIn;
    private CommandClass commandClass;

    public ReadMessage(Socket socket) {
        try {
            this.netIn = new Scanner(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void run() {

        while (true) {
            String command = netIn.nextLine();
            YaGson yaGson = new YaGsonBuilder().create();
            this.commandClass = yaGson.fromJson(command, CommandClass.class);
            System.out.println("In reader");
            switch (commandClass.getCommandKind()) {
                case LOGIN:
                    handleLoginCommand((LoginCommand) commandClass);
                    break;
                case SHOP:
                    handleShopCommand((ShopCommand) commandClass);
                    break;
                case BATTLE:
                    //TODO Bayad Command Haye Marboot be battle ra begirad
                    break;
            }

        }
    }

    private void handleLoginCommand(LoginCommand loginCommand) {
        if (loginCommand.getMyException() != null) {
            LoginController.setMyException(loginCommand.getMyException());
            System.out.println("Have Exception");
        } else {
            Account.setLoggedAccount(loginCommand.getAccount());
            System.out.println("Account LoggedIn!");
        }
    }

    private void handleShopCommand(ShopCommand shopCommand) {
        switch (shopCommand.getShopCommandsKind()) {
            case AUCTION_CARD:
                break;
            case SEND_CARD:
                break;
            case GET_CARDS:
                handleGetCards(shopCommand);
            case GET_FINISHED_CARD:
                handleGetFinishedCards(shopCommand);
                break;
            case BUY:
                break;
            case SELL:

        }
    }

    private void handleGetCards(ShopCommand shopCommand) {
        ArrayList<Card> cards = shopCommand.getCards();
        Shop.getInstance().getCards().addAll(cards);
    }
    private void handleGetFinishedCards(ShopCommand shopCommand) {
        ArrayList<Card> cards = shopCommand.getFinishedCard();
        Shop.getInstance().getFinishedCard().addAll(cards);
    }
}
