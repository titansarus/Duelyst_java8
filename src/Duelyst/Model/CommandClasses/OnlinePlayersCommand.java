package Duelyst.Model.CommandClasses;

import java.util.ArrayList;

public class OnlinePlayersCommand extends CommandClass {

    private ArrayList<String> onlineAccounts;

    public OnlinePlayersCommand() {
        super(CommandKind.ONLINE_PLAYERS);
    }

    public ArrayList<String> getOnlineAccounts() {
        return onlineAccounts;
    }

    public void setOnlineAccounts(ArrayList<String> onlineAccounts) {
        this.onlineAccounts = onlineAccounts;
    }
}
