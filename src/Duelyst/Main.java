package Duelyst;

import Duelyst.Client.Client;
import Duelyst.Controllers.Container;
import Duelyst.Database.DatabaseCard;
import Duelyst.Database.DatabaseCollectioner;
import Duelyst.Model.Account;
import Duelyst.Model.Buffs.*;
import Duelyst.Model.Items.*;
import Duelyst.Model.Spell.Spell;
import Duelyst.Model.Spell.TargetCommunity;
import Duelyst.Server.Server;
import Duelyst.Server.ServerShop;
import Duelyst.Utility.CreateCardFromDatabaseCard;
import Duelyst.Utility.NetworkConfiguration;
import com.gilecode.yagson.YaGson;
import com.gilecode.yagson.YaGsonBuilder;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.*;
import java.lang.reflect.Type;
import java.net.BindException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static Duelyst.View.Constants.*;

public class Main extends Application {

    {
        Pane root = null;
        FXMLLoader fxmlLoader;
        try {
            fxmlLoader = new FXMLLoader(getClass().getResource("./View/FXMLFiles/Login.fxml"));
            root = fxmlLoader.load();
            Container.addController(fxmlLoader);
        } catch (IOException e) {
            e.printStackTrace();
        }


        Scene scene = new Scene(root);
        scene.setCursor(new ImageCursor(new Image("res/ui/mouse_select.png")));
        Container.scenes.add(scene);
        Container.nameOfMenus.add(LOGIN);
    }


    public static void main(String[] args) throws IOException {
        System.out.println("a");

        NetworkConfiguration.loadFromIni();

        System.out.println(NetworkConfiguration.getHost());
        System.out.println(NetworkConfiguration.getPort());



        //TODO Run Client And Make Connection To The Server
        Client client = new Client();
        client.getReader().start();

        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {


        File file = new File(PATH_OF_MAIN_THEME);
        Media media = new Media(file.toURI().toString());

        Container.runMediaPlayer(Container.mainThemePlayer, media, 0.1, true, Integer.MAX_VALUE, MAIN_THEME);

        System.out.println(file.toURI().toString());

        primaryStage = Container.stage;
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setX(100);
        primaryStage.setY(100);
        primaryStage.setScene(Container.scenes.getLast());
        primaryStage.show();
    }

}
