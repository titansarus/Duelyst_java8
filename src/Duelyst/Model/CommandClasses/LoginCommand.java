package Duelyst.Model.CommandClasses;

import Duelyst.Model.Account;

public class LoginCommand extends CommandClass {

    private LoginCommandsKind loginCommandsKind;
    private String userName;
    private String passWord;
    private Account account;

    public LoginCommand(LoginCommandsKind loginCommandsKind, String userName, String passWord) {
        super(CommandKind.LOGIN);
        this.loginCommandsKind = loginCommandsKind;
        this.userName = userName;
        this.passWord = passWord;
    }

    public LoginCommand(LoginCommandsKind loginCommandsKind) {
        super(CommandKind.LOGIN);
        this.loginCommandsKind = loginCommandsKind;
    }

    public LoginCommand(Account account) {
        super(CommandKind.LOGIN);
        this.account = account;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public LoginCommandsKind getLoginCommandsKind() {
        return loginCommandsKind;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
