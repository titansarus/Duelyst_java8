package Duelyst.Model.CommandClasses;


import Duelyst.Exceptions.MyException;
import com.gilecode.yagson.YaGson;
import com.gilecode.yagson.YaGsonBuilder;

public class CommandClass {

    private CommandKind commandKind;
    private MyException myException;

    public CommandClass(CommandKind commandKind) {
        this.commandKind = commandKind;
    }


    public static String makeJson(CommandClass commandClass) {
        YaGson yaGson = new YaGsonBuilder().create();
        return yaGson.toJson(commandClass);
    }


    public CommandKind getCommandKind() {
        return commandKind;
    }

    public MyException getMyException() {
        return myException;
    }

    public void setMyException(MyException myException) {
        this.myException = myException;
    }
}
