package Duelyst;

import Duelyst.Controllers.Container;
import Duelyst.Database.DatabaseCard;
import Duelyst.Database.DatabaseCollectioner;
import Duelyst.Model.Account;
import Duelyst.Model.Card;
import Duelyst.Model.Shop;
import Duelyst.Utility.CreateCardFromDatabaseCard;
import com.gilecode.yagson.YaGson;
import com.gilecode.yagson.YaGsonBuilder;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.image.Image;
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
        scene.setCursor(new ImageCursor(new Image("res/ui/mouse_select.png")));
        Container.scenes.add(scene);
        Container.nameOfMenus.add(LOGIN);
    }


    public static void main(String[] args) {
        DatabaseCollectioner.DatabaseGenerator();
        Shop.getInstance().getCards().addAll(CreateCardFromDatabaseCard.createCards(DatabaseCard.getDatabaseCards()));

        for (int i = 0; i < 100; i++) {
            Card card = new Card("card" + i, "desc" + i, 10, 10);
            Shop.getInstance().getCards().add(card);
        }

        initAccounts();
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage = Container.stage;
        primaryStage.initStyle(StageStyle.DECORATED);
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
