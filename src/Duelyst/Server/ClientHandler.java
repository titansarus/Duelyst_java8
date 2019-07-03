package Duelyst.Server;

import Duelyst.Controllers.Container;
import Duelyst.Exceptions.InvalidPasswordException;
import Duelyst.Exceptions.MyException;
import Duelyst.Exceptions.UserExistException;
import Duelyst.Exceptions.UserNotExistException;
import Duelyst.Model.Account;
import Duelyst.Model.CommandClasses.CommandClass;
import Duelyst.Model.CommandClasses.CommandKind;
import Duelyst.Model.CommandClasses.LoginCommand;
import Duelyst.Model.CommandClasses.LoginCommandsKind;
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
                    if (loginCommand.getLoginCommandsKind() == LoginCommandsKind.LOGIN) {
                        handleLoginAccount(loginCommand, yaGson);
                    } else {
                        handleSignUpAccount(loginCommand, yaGson);
                    }
                    break;
                case SHOP:
                    //TODO Bayad liste Card Ha Ra Baraye Client Befrestad
                    break;
                case BATTLE:
                    //TODO Dastoorat Ra Migirad Va Baraye Cliente Harif Mifrestad
            }


        }
    }

    private void handleLoginAccount(LoginCommand loginCommand, YaGson yaGson) {
        String username = loginCommand.getUserName();
        String password = loginCommand.getPassWord();
        Account account = Server.findAccountInArrayList(username, Account.getAccounts());
        if (!Server.accountExistInArrayList(username, Server.getAllAccounts())) {
            LoginCommand temp = new LoginCommand(LoginCommandsKind.LOGIN, userName, password);
            temp.setMyException(new UserNotExistException());
            formatter.format("%s\n", yaGson.toJson(temp));
            formatter.flush();
        } else if (!account.getPassword().equals(password)) {
            LoginCommand temp = new LoginCommand(LoginCommandsKind.LOGIN, userName, password);
            temp.setMyException(new InvalidPasswordException());
            formatter.format("%s\n", yaGson.toJson(temp));
            formatter.flush();
        } else {
            LoginCommand temp = new LoginCommand(LoginCommandsKind.LOGIN, username, password);
            temp.setAccount(account);
            formatter.format("%s\n", yaGson.toJson(account));
            formatter.flush();
        }
    }

    private void handleSignUpAccount(LoginCommand loginCommand, YaGson yaGson) {

        String password = loginCommand.getPassWord();
        String username = loginCommand.getUserName();

        if (Server.accountExistInArrayList(username, Account.getAccounts())) {
            LoginCommand temp = new LoginCommand(LoginCommandsKind.LOGIN, userName, password);
            temp.setMyException(new UserNotExistException());
            formatter.format("%s\n", yaGson.toJson(temp));
            formatter.flush();
        } else {
            Account account = new Account(username, password);
            LoginCommand temp = new LoginCommand(LoginCommandsKind.LOGIN, username, password);
            Server.addAccount(account);
            temp.setAccount(account);
            Server.saveAccount();
            formatter.format("%s\n", yaGson.toJson(account));
            formatter.flush();
        }


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
}
