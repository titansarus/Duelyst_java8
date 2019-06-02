package Duelyst;

import Duelyst.Controllers.Container;
import Duelyst.Controllers.LoginController;
import Duelyst.Model.Account;
import com.gilecode.yagson.YaGson;
import com.gilecode.yagson.YaGsonBuilder;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;

public class Main extends Application {


    {
        Pane root = null;
        FXMLLoader fxmlLoader = null;
        try {
            fxmlLoader = new FXMLLoader(getClass().getResource("./View/FXMLFiles/Login.fxml"));
            root = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }


        Scene scene = new Scene(root);
        Container.scenes.add(scene);
    }


    public static void main(String[] args) {
        initAccounts();
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage = Container.stage;


        primaryStage.setScene(Container.scenes.getLast());
        primaryStage.show();
    }

    private static void initAccounts() {
        try {
            YaGson yaGson = new YaGsonBuilder().setPrettyPrinting().create();
            Reader reader = new FileReader("accounts.json");
            Account[] accounts = yaGson.fromJson(reader, (Type) Account[].class);
            if (accounts != null) {
                for (Account account : accounts) {
                    Account.getAccounts().add(account);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
