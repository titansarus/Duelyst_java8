package Duelyst.Server;

import Duelyst.Model.CommandClasses.CommandClass;
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
                    //TODO Bayad Accounte Client Ra agar User Pass Motabar Bood Barayash Befrestad Ya Accounte Jadid Besazad
                    break;
                case SHOP:
                    //TODO Bayad liste Card Ha Ra Baraye Client Befrestad
                    break;
                case BATTLE:
                    //TODO Dastoorat Ra Migirad Va Baraye Cliente Harif Mifrestad
            }


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
