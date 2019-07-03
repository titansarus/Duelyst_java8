package Duelyst.Client;

import Duelyst.Controllers.Container;
import Duelyst.Controllers.LoginController;
import Duelyst.Exceptions.UserNotExistException;
import Duelyst.Model.Account;
import Duelyst.Model.CommandClasses.CommandClass;
import Duelyst.Model.CommandClasses.LoginCommand;
import com.gilecode.yagson.YaGson;
import com.gilecode.yagson.YaGsonBuilder;


import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ReadMessage extends Thread {

    private Scanner netIn;
    private CommandClass commandClass;

    public ReadMessage(Socket socket){
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
            switch (commandClass.getCommandKind()) {
                case LOGIN:
                    LoginCommand loginCommand = (LoginCommand)commandClass;
                    if(loginCommand.getMyException() != null)
                        LoginController.setMyException(loginCommand.getMyException());
                    else {
                        Account.setLoggedAccount(loginCommand.getAccount());
                    }
                    break;
                case SHOP:
                    //TODO Bayad liste card haye shope server ra begirad
                    break;
                case BATTLE:
                    //TODO Bayad Command Haye Marboot be battle ra begirad
                    break;
            }

        }


    }
}
