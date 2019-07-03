package Duelyst.Server;

import Duelyst.Controllers.Container;
import Duelyst.Exceptions.InvalidPasswordException;
import Duelyst.Exceptions.MyException;
import Duelyst.Exceptions.UserExistException;
import Duelyst.Exceptions.UserNotExistException;
import Duelyst.Model.Account;
import Duelyst.Model.CommandClasses.*;
import Duelyst.Model.Shop;
import com.gilecode.yagson.YaGson;
import com.gilecode.yagson.YaGsonBuilder;

import java.io.IOException;
import java.net.Socket;
import java.util.Formatter;
import java.util.Scanner;

public class ClientHandler implements Runnable {

    private Scanner netIn;
    private Socket socket;
    private String userName;
    private Formatter formatter;
    private boolean loggedIn;

    ClientHandler(Socket socket) throws IOException {
        this.socket = socket;
        netIn = new Scanner(this.socket.getInputStream());
        formatter = new Formatter(this.socket.getOutputStream());
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
                        case LOGIN:
                            handleLoginAccount(loginCommand);
                            break;
                        case SIGN_UP:
                            handleSignUpAccount(loginCommand);
                            break;
                        case EXIT:
                            setLoggedIn(false);
                            Server.saveAccount();
                            formatter.close();
                            try {
                                socket.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            return;
                        case LOGOUT:

                            break;
                    }
                    break;
                case SHOP:
                    handleShopCommand((ShopCommand) command);
                    break;
                case BATTLE:
                    //TODO Dastoorat Ra Migirad Va Baraye Cliente Harif Mifrestad
            }


        }
    }

    private void handleLoginAccount(LoginCommand loginCommand) {
        String username = loginCommand.getUserName();
        String password = loginCommand.getPassWord();
        Account account = Server.findAccountInArrayList(username, Account.getAccounts());
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

        if (Server.accountExistInArrayList(username, Account.getAccounts())) {
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
                break;
            case SELL:
                sell(shopCommand);
                break;
            case BUY:
                buy(shopCommand);
                break;
            case GET_FINISHED_CARD:
                getFinishedCard();
        }
    }

    public void getCards() {
        ShopCommand command = new ShopCommand(ShopCommandsKind.GET_CARDS);
        command.setCards(Shop.getInstance().getCards());
        formatter.format("%s\n", CommandClass.makeJson(command));
        formatter.flush();
    }

    private void buy(ShopCommand shopCommand) {
        String cardName = shopCommand.getBuyCard().getCardName();
        Shop.getInstance().decreaseNumberOfCard(cardName);
    }

    private void sell(ShopCommand shopCommand) {
        String cardName = shopCommand.getBuyCard().getCardName();
        Shop.getInstance().increaseNumberOfCard(cardName);
    }
    public void getFinishedCard(){
        System.out.println("eee chera???");
        ShopCommand command = new ShopCommand(ShopCommandsKind.GET_FINISHED_CARD);
        command.setFinishedCard(Shop.getInstance().getFinishedCards());
        formatter.format("%s\n", CommandClass.makeJson(command));
        formatter.flush();
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
}
