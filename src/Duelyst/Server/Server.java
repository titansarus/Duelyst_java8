package Duelyst.Server;

import Duelyst.Model.Account;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;

public class Server implements Runnable {

    private static int SERVER_PORT = 8000;
    private static String IP_ADDRESS = "127.0.0.1";
    private ServerSocket serverSocket = new ServerSocket(SERVER_PORT);
    private static final ArrayList<Account> allAccounts = new ArrayList<>();
    private static final ArrayList<ClientHandler> allClientHandlers = new ArrayList<>();

    public Server() throws IOException {
    }

    @Override
    public void run() {
        Socket socket = null;
        while (true) {

            System.out.println("Server");
            try {
                socket = serverSocket.accept();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("New Client Request Received : " + socket);
            System.out.println("Creating a new handler for this Client... ");
            ClientHandler newClient = null;
            try {
                newClient = new ClientHandler(socket);
            } catch (IOException e) {
                e.printStackTrace();
            }
            getAllClientHandlers().add(newClient);
            allClientHandlers.add(newClient);
            Thread t = new Thread(newClient);
            t.start();
            System.out.println("Adding This Client To Active Client List");
        }
    }

    public static ArrayList<Account> getAllAccounts() {
        return (ArrayList<Account>) Collections.unmodifiableList(allAccounts);
    }

    public static boolean addAccount(Account account) {
        return allAccounts.add(account);
    }

    public static boolean removeAccount(Account account) {
        return allAccounts.remove(account);
    }

    public static ArrayList<ClientHandler> getAllClientHandlers() {
        return (ArrayList<ClientHandler>) Collections.unmodifiableList(allClientHandlers);
    }
}
