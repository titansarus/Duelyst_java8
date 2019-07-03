package Duelyst.Client;

import Duelyst.Model.CommandClasses.CommandClass;
import Duelyst.Model.CommandClasses.ShopCommand;
import Duelyst.Model.CommandClasses.ShopCommandsKind;

import java.io.IOException;
import java.net.Socket;
import java.util.Objects;
import java.util.Scanner;

public class Client {

    private static Client currentClient;
    private static Scanner netIn;
    private static int SERVER_PORT = 8000;
    private static final String IP_ADDRESS = "127.0.0.1";
    private ReadMessage reader;
    private Socket socket;

    public Client(){
        try {
            socket = new Socket(IP_ADDRESS, SERVER_PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        reader = new ReadMessage(Objects.requireNonNull(socket));
        setCardsOfShop();
        currentClient = this;
    }
    private void setCardsOfShop(){
        ShopCommand command = new ShopCommand(ShopCommandsKind.GET_CARDS);
        SendMessage.getSendMessage().sendMessage(command);
    }

    public static Client getCurrentClient() {
        return currentClient;
    }

    public ReadMessage getReader() {
        return reader;
    }

    public Socket getSocket() {
        return socket;
    }
}
