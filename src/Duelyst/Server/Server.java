package Duelyst.Server;

import Duelyst.Model.Account;
import Duelyst.Utility.NetworkConfiguration;
import com.gilecode.yagson.YaGson;
import com.gilecode.yagson.YaGsonBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Comparator;

public class Server implements Runnable {

    private ServerSocket serverSocket = new ServerSocket(NetworkConfiguration.getPort());
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
            allClientHandlers.add(newClient);
            Thread t = new Thread(newClient);
            t.start();
            System.out.println("Adding This Client To Active Client List");
        }
    }

    public static ArrayList<Account> getAllAccounts() {
        return allAccounts;
    }

    public static boolean addAccount(Account account) {
        return allAccounts.add(account);
    }

    public static boolean removeAccount(Account account) {
        return allAccounts.remove(account);
    }

    public static Account getAccount(String username) {
        for (int i = 0; i < allAccounts.size(); i++) {
            if (allAccounts.get(i).getUsername().equals(username))
                return allAccounts.get(i);
        }
        return null;
    }

    public static ArrayList<ClientHandler> getAllClientHandlers() {
        return allClientHandlers;
    }

    public static void addClientHanlder(ClientHandler clientHandler) {
        allClientHandlers.add(clientHandler);
    }

    public static void removeClientHanlder(ClientHandler clientHandler) {
        allClientHandlers.remove(clientHandler);
    }

    public static ArrayList<Account> accountsSorter(ArrayList<Account> accounts) {
        ArrayList<Account> accountsCopy = new ArrayList<>(accounts);
        accountsCopy.sort(((Comparator<Account>) (o1, o2) -> o2.getCountOfWins() - o1.getCountOfWins()).thenComparing((o1, o2) -> o1.getUsername().compareTo(o2.getUsername())));
        return accountsCopy;
    }


    public static boolean accountExistInArrayList(String username, ArrayList<Account> accounts) {
        return findAccountInArrayList(username, accounts) != null;
    }


    public static Account findAccountInArrayList(String username, ArrayList<Account> accounts) {
        if (username.length() > 0 && accounts != null) {
            for (int i = 0; i < accounts.size(); i++) {
                Account account = accounts.get(i);
                if (account != null && account.getUsername().equals(username)) {
                    return account;
                }
            }
        }
        return null;
    }

    public static void saveAccount() {
        YaGson yaGson = new YaGsonBuilder().setPrettyPrinting().create();
        try {
            Writer writer = new FileWriter("accounts.json");
            String s = yaGson.toJson(allAccounts);
            writer.write(s);
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<String> getOnlinePlayers() {
        ArrayList<String> onlinePlayers = new ArrayList<>();
        for (int i = 0; i < allClientHandlers.size(); i++) {
            if (allClientHandlers.get(i).isLoggedIn()) {
                onlinePlayers.add(allClientHandlers.get(i).getUserName());
            }
        }
        return onlinePlayers;
    }

}
