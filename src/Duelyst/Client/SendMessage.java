package Duelyst.Client;

import Duelyst.Model.CommandClasses.*;

import java.io.IOException;
import java.net.Socket;
import java.util.Formatter;

public class SendMessage {
    private static SendMessage sendMessage = new SendMessage();
    private Formatter formatter;
    private  Socket socket = Client.getCurrentClient().getSocket();
    public SendMessage()  {
        try {
            formatter = new Formatter(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(CommandClass commandClass) {
        String message = CommandClass.makeJson(commandClass);
        formatter.format("%s\n", message);
        formatter.flush();
        System.out.println("Message Sent!");
    }


    public static SendMessage getSendMessage() {
        return sendMessage;
    }
}
