package Duelyst.Model.CommandClasses;

import java.util.ArrayList;

public class ChatRoomCommand extends CommandClass {
    private ArrayList<ChatRoomCommand> chatRoomCommands;
    private String Pm;
    private String PmOwner;

    public ChatRoomCommand(String Pm, String PmOwner) {
        super(CommandKind.CHAT_ROOM);
        this.Pm = Pm;
        this.PmOwner = PmOwner;
    }

    public String getPm() {
        return Pm;
    }

    public String getPmOwner() {
        return PmOwner;
    }

    public ArrayList<ChatRoomCommand> getChatRoomCommands() {
        return chatRoomCommands;
    }

    public void setChatRoomCommands(ArrayList<ChatRoomCommand> chatRoomCommands) {
        this.chatRoomCommands = chatRoomCommands;
    }
}
