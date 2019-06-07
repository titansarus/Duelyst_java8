package Duelyst;

import Duelyst.Controllers.Container;
import Duelyst.Model.Account;
import Duelyst.Model.Card;
import Duelyst.Model.Shop;
import com.gilecode.yagson.YaGson;
import com.gilecode.yagson.YaGsonBuilder;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;

import static Duelyst.View.Constants.LOGIN;

public class Main extends Application {

    {
        Pane root = null;
        FXMLLoader fxmlLoader;
        try {
            fxmlLoader = new FXMLLoader(getClass().getResource("./View/FXMLFiles/Login.fxml"));
            root = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }


        Scene scene = new Scene(root);
        Container.scenes.add(scene);
        Container.nameOfMenus.add(LOGIN);
    }


    public static void main(String[] args) {
        for (int i =0;i<100;i++)
        {
            Card card = new Card("card"+i , "desc"+ i , 10,10);
            Shop.getInstance().getCards().add(card);
        }

        initAccounts();
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage = Container.stage;
        primaryStage.initStyle(StageStyle.UTILITY);
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
