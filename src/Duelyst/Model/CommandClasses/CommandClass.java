package Duelyst.Model.CommandClasses;


import com.gilecode.yagson.YaGson;
import com.gilecode.yagson.YaGsonBuilder;

public class CommandClass {

    private CommandKind commandKind;

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
}
