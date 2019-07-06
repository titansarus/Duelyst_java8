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
import Duelyst.Model.CommandClasses.*;
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
                    handleChatRoomCommand((ChatRoomCommand) commandClass);
                    break;
                case LEADER_BOARD:
                    handleLeaderBoardCommand((LeaderBoardCommand) commandClass);
                    break;
                case ONLINE_PLAYERS:
                    handleGetOnlinePlayers((OnlinePlayersCommand) commandClass);
                    break;
            }

        }
    }

    private void handleGetOnlinePlayers(OnlinePlayersCommand onlinePlayersCommand) {
        System.out.println("Getting Online Players From Server");
        Platform.runLater(() -> {

            MainMenu mainMenu = (MainMenu) Container.controllerClass;
            mainMenu.showOnlinePlayers(onlinePlayersCommand.getOnlineAccounts());
        });
    }

    private void handleLeaderBoardCommand(LeaderBoardCommand leaderBoardCommand) {
        System.out.println("Getting LeaderBoard List");
        Platform.runLater(() -> {
            MainMenu mainMenu = (MainMenu) Container.controllerClass;
            mainMenu.initializeLeaderBoard(leaderBoardCommand.getSortedListOfAccounts());
        });
    }

    private void handleChatRoomCommand(ChatRoomCommand chatRoomCommand) {
        System.out.println("receive message : " + chatRoomCommand.getPm());
        Platform.runLater(() -> {
            if (Container.controllerClass instanceof MainMenu) {
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
            case GET_AUCTION_CARDS:
                setAuctionCards(shopCommand);
                break;
            case FINISH_TIME:
                setAuctionCardsAfterFinishCard(shopCommand);
                break;
            case ADD_CARD:
                addAuctionCard(shopCommand);
                break;
            case REMOVE_CARD:
                removeActionCard(shopCommand);
                break;
            case GET_AUCTION_CARD_TIME:
                handleGetAuctionCardTimeLeft(shopCommand);
        }
    }

    private void handleGetAuctionCardTimeLeft(ShopCommand shopCommand) {
        Platform.runLater(() -> {
            if (Container.getControllerClass() instanceof ShopController) {
                ShopController shopController = (ShopController) Container.getControllerClass();
                shopController.auctionTimeLeft_lbl.setText(shopCommand.getAuctionCardTime() + " S");
            }
        });

    }

    private void addAuctionCard(ShopCommand shopCommand) {
        System.out.println("add card                --------                       adddddd cardddddddddddd");
        Account.getLoggedAccount().setDarick(Account.getLoggedAccount().getDarick() - shopCommand.getAuctionCard().getAuctionCost());
        Account.getLoggedAccount().getCardCollection().getCards().add(shopCommand.getAuctionCard());
    }

    private void removeActionCard(ShopCommand shopCommand) {
        System.out.println("remove card");
        Account.getLoggedAccount().setDarick(Account.getLoggedAccount().getDarick() + shopCommand.getAuctionCard().getAuctionCost());
        Card card = null;
        for (Card c :
                Account.getLoggedAccount().getCardCollection().getCards()) {
            if (c.getCardName().equals(shopCommand.getAuctionCard().getCardName())) {
                card = c;
                System.out.println("found card for remove");
            }
        }
        Account.getLoggedAccount().getCardCollection().getCards().remove(card);
    }

    private void setAuctionCards(ShopCommand shopCommand) {
        Shop.getInstance().setAuctionCards(shopCommand.getAuctionCards());
    }

    private void setAuctionCardsAfterFinishCard(ShopCommand shopCommand) {
        Shop.getInstance().setAuctionCards(shopCommand.getAuctionCards());
        System.out.println("time of a card is end");
        //TODO set cards in auction page
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
