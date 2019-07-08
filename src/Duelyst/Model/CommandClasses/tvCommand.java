package Duelyst.Model.CommandClasses;

import java.util.ArrayList;

public class tvCommand extends CommandClass {

    private ArrayList<String> runningGames;
    private ArrayList<String> finishedGames;

    public tvCommand(){
        super(CommandKind.TV);
    }

    public ArrayList<String> getRunningGames() {
        return runningGames;
    }

    public void setRunningGames(ArrayList<String> runningGames) {
        this.runningGames = runningGames;
    }

    public ArrayList<String> getFinishedGames() {
        return finishedGames;
    }

    public void setFinishedGames(ArrayList<String> finishedGames) {
        this.finishedGames = finishedGames;
    }
}
