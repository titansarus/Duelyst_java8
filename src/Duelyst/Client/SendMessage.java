package Duelyst.Client;

import Duelyst.Model.CommandClasses.*;

import java.io.IOException;
import java.net.Socket;
import java.util.Formatter;

public class SendMessage {

    private Formatter formatter;
    private CommandClass commandClass;

    public SendMessage(Socket socket, CommandClass commandClass) throws IOException {
        formatter = new Formatter(socket.getOutputStream());
        this.commandClass = commandClass;
        sendMessage();
    }

    private void sendMessage() {
        String message = CommandClass.makeJson(this.commandClass);
        formatter.format("%s\n", message);
        formatter.flush();
        System.out.println("Message Sent!");
    }


}
