package Duelyst.Model.CommandClasses;

enum LoginCommandsKind {
    LOGIN, SIGN_UP;
}

public class LoginCommand extends CommandClass {

    private LoginCommandsKind loginCommandsKind;
    private String userName;
    private String passWord;

    public LoginCommand(LoginCommandsKind loginCommandsKind, String userName, String passWord) {
        super(CommandKind.LOGIN);
        this.loginCommandsKind = loginCommandsKind;
        this.userName = userName;
        this.passWord = passWord;
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
}
