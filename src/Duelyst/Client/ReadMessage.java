package Duelyst.Client;

import Duelyst.Controllers.*;
import Duelyst.Exceptions.CardOutOfStock;
import Duelyst.Exceptions.UserNotExistException;
import Duelyst.Main;
import Duelyst.Model.Account;
import Duelyst.Model.Battle.Battle;
import Duelyst.Model.Card;
import Duelyst.Model.CommandClasses.*;
import Duelyst.Model.Shop;
import com.gilecode.yagson.YaGson;
import com.gilecode.yagson.YaGsonBuilder;
import javafx.application.Platform;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class ReadMessage extends Thread {

    private Scanner netIn;
    private CommandClass commandClass;
    private static int[] randomX;
    private static int[] randomY;

    public ReadMessage(Socket socket) {
        try {
            this.netIn = new Scanner(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int[] getRandomX() {
        return randomX;
    }

    public static int[] getRandomY() {
        return randomY;
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
                    handleBattle((BattleCommand) commandClass);
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
                case CUSTOM_CARD:
                    handleCustomCardSprites((CustomCardCommand) commandClass);
                    break;
                case TV:
                    tvCommand tvCommand = (tvCommand) commandClass;
                    if (tvCommand.getTvCommandKind().equals(tvCommandKind.GET_REPLAYS_LIST))
                        handleTVCommand((tvCommand) commandClass);
                    else if(tvCommand.getTvCommandKind().equals(tvCommandKind.GET_FINISHED_BATTLES_RECORDS)) {
                        Platform.runLater(() -> {
                            MultiPlayerController multiPlayerController = (MultiPlayerController)Container.getControllerClass();
                            multiPlayerController.gotoBattleReplay(tvCommand.getBattleRecords());
                        });
                    }
                    break;
            }

        }
    }

    private void handleBattle(BattleCommand battleCommand) {
        switch (battleCommand.getBattleCommandsKind()) {
            case INSERT:
                insert(battleCommand);
                break;
            case END_TURN:
                endTurn();
                break;
            case MOVE:
                move(battleCommand);
                break;
            case ATTACK:
                attack(battleCommand);
                break;
            case ACCEPT_REQUEST:
                handleStartBattle(battleCommand);
                break;
            case END_GAME:
                disConnectOpponent();
                break;
            case FORCE_END_TURN:
                forceEndTurn();
                break;
            case END_TURN_WARNNING:
                endTurnWarnning();
                break;
        }
    }

    private void disConnectOpponent() {
        Battle.getRunningBattle().showNotification("Your Opponent Disconnected!");
        Battle.getRunningBattle().opponentGiveUp();

    }

    private void move(BattleCommand battleCommand) {
        System.out.println("*************************************** MOVE");
        Battle.getRunningBattle().multiPlayerMove(battleCommand.getDesRow(), battleCommand.getDesCol(), battleCommand.getSrcRow(), battleCommand.getSrcCol());
    }

    private void attack(BattleCommand battleCommand) {
        System.out.println("attack");
        Battle.getRunningBattle().multiPlayerAttack(battleCommand.getAttackerCardId(), battleCommand.getDefenderCardId());
    }

    private void insert(BattleCommand battleCommand) {
        System.out.println("*************************************** INSERT");
        Battle.getRunningBattle().multiPlayerInsert(battleCommand.getInsertRow(), battleCommand.getInsertCol(), battleCommand.getInsertSelectedCardId());
    }

    private void endTurn() {
        System.out.println("*************************************** END TURN");
        Battle.getRunningBattle().nextTurn();
    }

    private void endTurnWarnning() {
        System.out.println("You have 20 seconds");
        Battle.getRunningBattle().showNotification("hurry up! You have 20 seconds");
    }

    private void forceEndTurn() {
        Battle.getRunningBattle().nextTurn();
        BattleCommand battleCommand = new BattleCommand();
        battleCommand.endTurn(Account.getLoggedAccount(), Battle.getRunningBattle().getBattleRecords());
        SendMessage.getSendMessage().sendMessage(battleCommand);
    }

    private void handleStartBattle(BattleCommand battleCommand) {

        MultiPlayerController multiPlayerController = (MultiPlayerController) Container.controllerClass;
        multiPlayerController.handleCancelBattleButton();
        randomX = battleCommand.getRandomXForCollectFlag();
        randomY = battleCommand.getRandomYForCollectFlag();
        multiPlayerController.gotoBattle(battleCommand.getOpponent(), battleCommand.getGameGoal(), battleCommand.isFirstPlayer());
    }

    private void handleTVCommand(tvCommand tvCommand) {
        MultiPlayerController.setFinishedGames(tvCommand.getFinishedGames());
        MultiPlayerController.setRunningGames(tvCommand.getRunningGames());
    }

    private void handleCustomCardSprites(CustomCardCommand customCardCommand) {
        System.out.println("Custom Card Created!");

        String address = "./src/res/Characters/UnitsCreated/CustomCard";
        new File(address).mkdirs();

        if (customCardCommand.getImage() != null) {
            saveCustomCardsImages(customCardCommand.getCard().getCardName() + "_image", customCardCommand.getImage(), "png");
            customCardCommand.getCard().setAddressOfImage("./res/Characters/UnitsCreated/CustomCard/" + customCardCommand.getCard().getCardName() + "_image.png");
        }
        if (customCardCommand.getIdleGif() != null) {
            saveCustomCardsImages(customCardCommand.getCard().getCardName() + "_idle", customCardCommand.getIdleGif(), "gif");
            customCardCommand.getCard().setAddressOfIdleGif("./res/Characters/UnitsCreated/CustomCard/" + customCardCommand.getCard().getCardName() + "_idle.gif");
        }
        if (customCardCommand.getRunGif() != null) {
            saveCustomCardsImages(customCardCommand.getCard().getCardName() + "_run", customCardCommand.getRunGif(), "gif");
            customCardCommand.getCard().setAddressOfRunGif("./res/Characters/UnitsCreated/CustomCard/" + customCardCommand.getCard().getCardName() + "_run.gif");
        }
        if (customCardCommand.getAttackGif() != null) {
            saveCustomCardsImages(customCardCommand.getCard().getCardName() + "_attack", customCardCommand.getAttackGif(), "gif");
            customCardCommand.getCard().setAddressOfAttackGif("./res/Characters/UnitsCreated/CustomCard/" + customCardCommand.getCard().getCardName() + "_attack.gif");
        }
        if (customCardCommand.getDeathGif() != null) {
            saveCustomCardsImages(customCardCommand.getCard().getCardName() + "_death", customCardCommand.getDeathGif(), "gif");
            customCardCommand.getCard().setAddressOfDeathGif("./res/Characters/UnitsCreated/CustomCard/" + customCardCommand.getCard().getCardName() + "_death.gif");
        }
        if (customCardCommand.getHitGif() != null) {
            saveCustomCardsImages(customCardCommand.getCard().getCardName() + "_hit", customCardCommand.getHitGif(), "gif");
            customCardCommand.getCard().setAddressOfGetDamageGif("./res/Characters/UnitsCreated/CustomCard/" + customCardCommand.getCard().getCardName() + "_hit.gif");
        }

    }


    private void saveCustomCardsImages(String name, byte[] image, String format) {

//        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(image);
        try {
            FileOutputStream fileOutputStream = new FileOutputStream( new File("src/res/Characters/UnitsCreated/CustomCard/" + name + "." + format));
            fileOutputStream.write(image);
//            BufferedImage bufferedImage = ImageIO.read(byteArrayInputStream);
//            ImageIO.write(bufferedImage, format, new File("src/res/Characters/UnitsCreated/CustomCard/" + name + "." + format));
        } catch (IOException e) {
            e.printStackTrace();
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
            if (Container.controllerClass instanceof MultiPlayerController) {
                MultiPlayerController multiPlayerController = (MultiPlayerController) Container.controllerClass;
                multiPlayerController.addToChat(chatRoomCommand);
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
        Shop.getInstance().setCards(cards);
    }

    private void handleBuyCard(ShopCommand shopCommand) {
        if (shopCommand.getMyException() instanceof CardOutOfStock) {
            ShopController.setMyException(shopCommand.getMyException());
        }
    }

}