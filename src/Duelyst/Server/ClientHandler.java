package Duelyst.Server;

import Duelyst.Client.SendMessage;
import Duelyst.Controllers.Container;
import Duelyst.Exceptions.*;
import Duelyst.Model.Account;
import Duelyst.Model.Battle.Battle;
import Duelyst.Model.Card;
import Duelyst.Model.CommandClasses.*;
import Duelyst.Model.Shop;
import com.gilecode.yagson.YaGson;
import com.gilecode.yagson.YaGsonBuilder;
import org.apache.poi.ss.formula.functions.T;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.Scanner;

public class ClientHandler implements Runnable {

    private static ArrayList<ClientHandler> clientHandlers = new ArrayList<>();
    private Scanner netIn;
    private Socket socket;
    private String userName;
    private Formatter formatter;
    private boolean loggedIn;
    private static ArrayList<ChatRoomCommand> pms = new ArrayList<>();
    private static Account killHeroApplicator;
    private static Account collectFlagApplicator;
    private static Account holdFlagApplicator;

    ClientHandler(Socket socket) throws IOException {
        this.socket = socket;
        netIn = new Scanner(this.socket.getInputStream());
        formatter = new Formatter(this.socket.getOutputStream());
        clientHandlers.add(this);
    }

    @Override
    public void run() {
        YaGson yaGson = new YaGsonBuilder().create();
        while (true) {
            String message = netIn.nextLine();
            CommandClass command = yaGson.fromJson(message, CommandClass.class);
            switch (command.getCommandKind()) {
                case LOGIN:
                    LoginCommand loginCommand = (LoginCommand) command;
                    switch (loginCommand.getLoginCommandsKind()) {
                        case SAVE_ACCOUNTS:
                            System.out.println("Saving Account");
                            System.out.println(loginCommand.getAccount().getUsername());
                            handleSaveAccounts(loginCommand);
                            break;
                        case LOGIN:
                            handleLoginAccount(loginCommand);
                            break;
                        case SIGN_UP:
                            handleSignUpAccount(loginCommand);
                            break;
                        case EXIT:
                            handleExit();
                            return;
                        case LOGOUT:
                            loggedIn = false;
                            break;
                    }
                    break;
                case SHOP:
                    handleShopCommand((ShopCommand) command);
                    break;
                case BATTLE:
                    handleBattle((BattleCommand) command);
                    break;
                case CHAT_ROOM:
                    handleChatRoom((ChatRoomCommand) command);
                    break;
                case LEADER_BOARD:
                    handleLeaderBoardCommand((LeaderBoardCommand) command);
                    break;

                case ONLINE_PLAYERS:
                    handleGetOnlinePlayers((OnlinePlayersCommand) command);
                    break;
                case CUSTOM_CARD:
                    handleCustomCardCommand((CustomCardCommand) command);
                    break;
                case TV:
                    handleTvCommand((tvCommand) command);
                    break;
            }


        }
    }

    private void handleTvCommand(tvCommand tvCommand) {
        ArrayList<String> finishedGames = new ArrayList<>();
        for (int i = 0; i < ServerTV.getFinishedGames().size(); i++) {
            ServerTV serverTV = ServerTV.getFinishedGames().get(i);
            finishedGames.add(serverTV.getAccount1().getUsername() + "  VS  " + serverTV.getAccount2().getUsername());
        }
        ArrayList<String> runningGames = new ArrayList<>();
        for (int i = 0; i < ServerTV.getRunningGames().size(); i++) {
            ServerTV serverTV = ServerTV.getRunningGames().get(i);
            runningGames.add(serverTV.getAccount1().getUsername() + "  VS  " + serverTV.getAccount2().getUsername());
        }
        tvCommand.setFinishedGames(finishedGames);
        tvCommand.setRunningGames(runningGames);
        formatter.format("%s\n", CommandClass.makeJson(tvCommand));
        formatter.flush();
    }

    private void handleBattle(BattleCommand battleCommand) {
        switch (battleCommand.getBattleCommandsKind()) {
            case ATTACK:
                break;
            case MOVE:
                break;
            case END_TURN:
                break;
            case INSERT:
                break;
            case START_BATTLE:
                startBattle(battleCommand);
                break;
        }
    }

    private void startBattle(BattleCommand battleCommand) {
        switch (battleCommand.getGameGoal()) {
            case HOLD_FLAG:
                startOrSetHoldFlag(battleCommand);
                break;
            case COLLECT_FLAG:
                startOrSetCollectFlag(battleCommand);
                break;
            case KILL_HERO:
                startOrSetKillHero(battleCommand);
                break;
        }
    }

    private void startOrSetKillHero(BattleCommand battleCommand) {
        if (killHeroApplicator == null) {
            killHeroApplicator = battleCommand.getApplicatorAccount();
        }
        Account account = killHeroApplicator;
        killHeroApplicator = null;

    }

    private void startOrSetCollectFlag(BattleCommand battleCommand) {
        if (collectFlagApplicator == null) {
            collectFlagApplicator = battleCommand.getApplicatorAccount();
        }
        Account account = collectFlagApplicator;
        collectFlagApplicator = null;

    }

    private void startOrSetHoldFlag(BattleCommand battleCommand) {
        if (holdFlagApplicator == null) {
            holdFlagApplicator = battleCommand.getApplicatorAccount();
        }
        Account account = holdFlagApplicator;
        holdFlagApplicator = null;

    }

    private void handleCustomCardCommand(CustomCardCommand customCardCommand) {
        ServerShop.getInstance().addCard(customCardCommand.getCard());
        ServerShop.getInstance().getCustomCardCommands().add(customCardCommand);
        ServerShop.getInstance().getNumberOfCards().add(customCardCommand.getCard().getCardName() + " 1");
        sendCustomCardToAllClients(customCardCommand);
    }

    private void sendCustomCardToAllClients(CustomCardCommand customCardCommand) {
        for (int i = 0; i < Server.getAllClientHandlers().size(); i++) {
            if (Server.getAllClientHandlers().get(i).isLoggedIn()) {
                Server.getAllClientHandlers().get(i).getFormatter().format("%s\n", CommandClass.makeJson(customCardCommand));
                Server.getAllClientHandlers().get(i).getFormatter().flush();
            }
        }
    }

    private void handleGetOnlinePlayers(OnlinePlayersCommand onlinePlayersCommand) {
        onlinePlayersCommand.setOnlineAccounts(Server.getOnlinePlayers());
        formatter.format("%s\n", CommandClass.makeJson(onlinePlayersCommand));
        formatter.flush();
    }

    private void handleExit() {
        setLoggedIn(false);
        Server.saveAccount();
        formatter.close();
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        clientHandlers.remove(this);
    }

    private void handleLeaderBoardCommand(LeaderBoardCommand leaderBoardCommand) {
        leaderBoardCommand.setSortedListOfAccounts(Server.accountsSorter(Server.getAllAccounts()));
        formatter.format("%s\n", CommandClass.makeJson(leaderBoardCommand));
        formatter.flush();
    }

    private void handleChatRoom(ChatRoomCommand chatRoomCommand) {
        if (chatRoomCommand.getPm() != null) {
            pms.add(chatRoomCommand);
        }
        chatRoomCommand.setChatRoomCommands(pms);
        for (ClientHandler c :
                clientHandlers) {
            if (c.isLoggedIn()) {
                c.getFormatter().format("%s\n", CommandClass.makeJson(chatRoomCommand));
                c.getFormatter().flush();
            }
        }
    }

    private void handleSaveAccounts(LoginCommand loginCommand) {
        Server.removeAccount(Server.findAccountInArrayList(loginCommand.getAccount().getUsername(), Server.getAllAccounts()));
        Server.addAccount(loginCommand.getAccount());
        Server.saveAccount();
    }

    private void handleLoginAccount(LoginCommand loginCommand) {
        String username = loginCommand.getUserName();
        String password = loginCommand.getPassWord();
        Account account = Server.findAccountInArrayList(username, Server.getAllAccounts());
        if (!Server.accountExistInArrayList(username, Server.getAllAccounts())) {
            LoginCommand temp = new LoginCommand(LoginCommandsKind.LOGIN, userName, password);
            temp.setMyException(new UserNotExistException());
            System.out.println("Account not Found");
            formatter.format("%s\n", CommandClass.makeJson(temp));
            formatter.flush();
        } else if (!account.getPassword().equals(password)) {
            LoginCommand temp = new LoginCommand(LoginCommandsKind.LOGIN, userName, password);
            temp.setMyException(new InvalidPasswordException());
            System.out.println("Wrong Password");
            formatter.format("%s\n", CommandClass.makeJson(temp));
            formatter.flush();
        } else {
            userName = username;
            LoginCommand temp = new LoginCommand(account);
            System.out.println("Account sent Successfully!");
            setLoggedIn(true);
            formatter.format("%s\n", CommandClass.makeJson(temp));
            formatter.flush();
        }
    }

    private void handleSignUpAccount(LoginCommand loginCommand) {

        String password = loginCommand.getPassWord();
        String username = loginCommand.getUserName();

        if (Server.accountExistInArrayList(username, Server.getAllAccounts())) {
            LoginCommand temp = new LoginCommand(LoginCommandsKind.LOGIN);
            temp.setMyException(new UserNotExistException());
            formatter.format("%s\n", CommandClass.makeJson(temp));
            formatter.flush();
        } else {
            userName = username;
            Account account = new Account(username, password);
            LoginCommand temp = new LoginCommand(account);
            Server.addAccount(account);
            Server.saveAccount();
            setLoggedIn(true);
            formatter.format("%s\n", CommandClass.makeJson(temp));
            formatter.format("%s\n", CommandClass.makeJson(temp));
            formatter.flush();
        }


    }

    private void handleShopCommand(ShopCommand shopCommand) {
        switch (shopCommand.getShopCommandsKind()) {
            case GET_CARDS:
                getCards();
                break;
            case SEND_CARD:
                break;
            case AUCTION_CARD:
                addCardToAuctionCards(shopCommand);
                break;
            case SELL:
                sell(shopCommand);
                break;
            case BUY:
                buy(shopCommand);
                break;
            case GET_AUCTION_CARDS:
                getAuctionCards();
                break;
            case REQUEST_TO_ACTION_CARD:
                handleAuctionRequest(shopCommand);
                break;
            case GET_AUCTION_CARD_TIME:
                handleAuctionCardTime(shopCommand);
                break;
        }
    }

    private void handleAuctionCardTime(ShopCommand shopCommand) {
        shopCommand.setAuctionCardTime(Time.getExtantTimeOfCard(shopCommand.getAuctionCard()));
        formatter.format("%s\n", CommandClass.makeJson(shopCommand));
        formatter.flush();
    }

    private void handleAuctionRequest(ShopCommand shopCommand) {
        Card card = null;
        for (Card c :
                ServerShop.getInstance().getAuctionCards()) {
            if (c.getCardId().equals(shopCommand.getAuctionCard().getCardId())) {
                card = c;
            }
        }
        if (card != null) {
            card.setAuctionCost(card.getAuctionCost() * 11 / 10);
            card.setAuctionClient(this.getUserName());
        }
    }

    private void getAuctionCards() {
        ShopCommand command = new ShopCommand(ShopCommandsKind.GET_AUCTION_CARDS);
        command.setAuctionCards(ServerShop.getInstance().getAuctionCards());
        formatter.format("%s\n", CommandClass.makeJson(command));
        formatter.flush();
    }

    private void addCardToAuctionCards(ShopCommand shopCommand) {
        ServerShop.getInstance().addAuctionCards(shopCommand.getAuctionCard());
        Time time = new Time(shopCommand.getAuctionCard(), 30);//TODO 30 -> 180
        time.start();
    }

    public void getCards() {
        ShopCommand command = new ShopCommand(ShopCommandsKind.GET_CARDS);
        command.setCards(ServerShop.getInstance().getCards());
        formatter.format("%s\n", CommandClass.makeJson(command));
        formatter.flush();
        for (int i = 0; i < ServerShop.getInstance().getCustomCardCommands().size(); i++) {
            formatter.format("%s\n", CommandClass.makeJson(ServerShop.getInstance().getCustomCardCommands().get(i)));
            formatter.flush();
        }
    }

    private void buy(ShopCommand shopCommand) {
        if (ServerShop.getInstance().isFinished(shopCommand.getBuyCard())) {
            shopCommand.setMyException(new CardOutOfStock());
            formatter.format("%s\n", CommandClass.makeJson(shopCommand));
            formatter.flush();
        } else {
            String cardName = shopCommand.getBuyCard().getCardName();
            ServerShop.getInstance().decreaseNumberOfCard(cardName);
            shopCommand.setMyException(new CardBoughtSuccessfully());
            formatter.format("%s\n", CommandClass.makeJson(shopCommand));
            formatter.flush();
        }
    }

    private void sell(ShopCommand shopCommand) {
        String cardName = shopCommand.getSellCard().getCardName();
        ServerShop.getInstance().increaseNumberOfCard(cardName);
    }


    public Scanner getNetIn() {
        return netIn;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Formatter getFormatter() {
        return formatter;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    public static ArrayList<ClientHandler> getClientHandlers() {
        return clientHandlers;
    }
}
