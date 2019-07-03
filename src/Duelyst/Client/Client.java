package Duelyst.Client;

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
        currentClient = this;
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
