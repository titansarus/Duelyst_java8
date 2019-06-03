package Duelyst.Controllers;

public class AccountInfo {

    public Integer rank;
    public String username;
    public Integer wins;

    public AccountInfo(Integer rank, String username, Integer wins) {
        this.rank = rank;
        this.username = username;
        this.wins = wins;
    }

    public Integer getRank() {
        return rank;
    }

    public String getUsername() {
        return username;
    }

    public Integer getWins() {
        return wins;
    }
}
