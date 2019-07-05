package Duelyst.Client;


import Duelyst.Utility.NetworkConfiguration;

import java.io.IOException;
import java.net.Socket;
import java.util.Objects;
import java.util.Scanner;

public class Client {

    private static Client currentClient;
    private static Scanner netIn;
    private ReadMessage reader;
    private Socket socket;

    public Client() {
        try {
            socket = new Socket(NetworkConfiguration.getHost(), NetworkConfiguration.getPort());
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
