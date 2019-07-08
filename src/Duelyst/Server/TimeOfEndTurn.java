package Duelyst.Server;

import Duelyst.Client.Client;
import Duelyst.Model.Account;
import Duelyst.Model.Card;
import Duelyst.Model.CommandClasses.BattleCommand;
import Duelyst.Model.CommandClasses.CommandClass;

import java.time.Instant;
import java.util.ArrayList;

public class TimeOfEndTurn extends Thread {
    private static ArrayList<TimeOfEndTurn> times = new ArrayList<>();
    private Instant now;
    private Instant start;
    private Account account1;
    private Account account2;
    private Account playingPlayer;
    private final int TIME_OF_TURN = 120;//TODO 120
    private boolean warning = true;

    public TimeOfEndTurn(Account account1, Account account2, Account playingPlayer) {
        times.add(this);
        this.account1 = account1;
        this.account2 = account2;
        this.playingPlayer = playingPlayer;
    }

    @Override
    public void run() {
        start = Instant.now();
        while (true) {
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
            now = Instant.now();
            if (warning && now.getEpochSecond() - start.getEpochSecond() > TIME_OF_TURN - 20) {
                warning = false;
                ClientHandler clientHandler = getPlayingPlayerClient();
                BattleCommand battleCommand = new BattleCommand();
                battleCommand.endTurnWarnning();
                clientHandler.getFormatter().format("%s\n", CommandClass.makeJson(battleCommand));
                clientHandler.getFormatter().flush();
            }
            if (now.getEpochSecond() - start.getEpochSecond() > TIME_OF_TURN) {
                break;
            }
        }
        ClientHandler clientHandler = getPlayingPlayerClient();
        BattleCommand battleCommand = new BattleCommand();
        battleCommand.forceEndTurn();
        clientHandler.getFormatter().format("%s\n", CommandClass.makeJson(battleCommand));
        clientHandler.getFormatter().flush();
        finish();

    }

    public void finish() {
        times.remove(this);
    }

    public void nowIsStart() {
        start = now;
        changePlayingPlayer();
        warning = true;
    }

    private void changePlayingPlayer() {
        if (playingPlayer.equals(account1)) {
            playingPlayer = account2;
        } else {
            playingPlayer = account1;
        }
    }

    private ClientHandler getPlayingPlayerClient() {
        ClientHandler clientHandler = null;
        for (ClientHandler c :
                ClientHandler.getClientHandlers()) {
            if (c.isLoggedIn() && c.getUserName().equals(playingPlayer.getUsername())) {
                clientHandler = c;
            }
        }
        return clientHandler;
    }

    public static TimeOfEndTurn getTime(Account account) {
        TimeOfEndTurn time = null;
        for (TimeOfEndTurn t :
                times) {
            if (account.getUsername().equals(t.account1.getUsername()) || account.getUsername().equals(t.account2.getUsername())) {
                time = t;
            }
        }
        return time;
    }


    public static ArrayList<TimeOfEndTurn> getTimes() {
        return times;
    }

    public static void setTimes(ArrayList<TimeOfEndTurn> times) {
        TimeOfEndTurn.times = times;
    }

    public Instant getNow() {
        return now;
    }

    public void setNow(Instant now) {
        this.now = now;
    }

    public Instant getStart() {
        return start;
    }

    public void setStart(Instant start) {
        this.start = start;
    }

    public Account getAccount1() {
        return account1;
    }

    public void setAccount1(Account account1) {
        this.account1 = account1;
    }

    public Account getAccount2() {
        return account2;
    }

    public void setAccount2(Account account2) {
        this.account2 = account2;
    }

    public Account getPlayingPlayer() {
        return playingPlayer;
    }

    public void setPlayingPlayer(Account playingPlayer) {
        this.playingPlayer = playingPlayer;
    }
}
