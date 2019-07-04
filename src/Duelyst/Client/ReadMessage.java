package Duelyst.Client;

import Duelyst.Controllers.Container;
import Duelyst.Controllers.LoginController;
import Duelyst.Controllers.MainMenu;
import Duelyst.Controllers.ShopController;
import Duelyst.Exceptions.CardOutOfStock;
import Duelyst.Exceptions.UserNotExistException;
import Duelyst.Main;
import Duelyst.Model.Account;
import Duelyst.Model.Card;
import Duelyst.Model.CommandClasses.ChatRoomCommand;
import Duelyst.Model.CommandClasses.CommandClass;
import Duelyst.Model.CommandClasses.LoginCommand;
import Duelyst.Model.CommandClasses.ShopCommand;
import Duelyst.Model.Shop;
import com.gilecode.yagson.YaGson;
import com.gilecode.yagson.YaGsonBuilder;
import javafx.application.Platform;


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
                case CHAT_ROOM:
                    handleChatRoomCommand((ChatRoomCommand)commandClass);
                    break;
            }

        }
    }
    private void handleChatRoomCommand(ChatRoomCommand chatRoomCommand){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                MainMenu mainMenu = (MainMenu) Container.controllerClass;
                mainMenu.addToChat(chatRoomCommand);
            }
        });
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
                break;
            case BUY:
                handleBuyCard(shopCommand);
                break;
            case SELL:
                break;
            case GET_AUCTION_CARDS:
                setAuctionCards(shopCommand);
                break;
        }
    }

    private void setAuctionCards(ShopCommand shopCommand) {
        Shop.getInstance().setAuctionCards(shopCommand.getAuctionCards());
    }

    private void handleGetCards(ShopCommand shopCommand) {
        ArrayList<Card> cards = shopCommand.getCards();
        Shop.getInstance().getCards().addAll(cards);
    }

    private void handleBuyCard(ShopCommand shopCommand) {
        if (shopCommand.getMyException() instanceof CardOutOfStock) {
            ShopController.setMyException(shopCommand.getMyException());
        }
    }

}
