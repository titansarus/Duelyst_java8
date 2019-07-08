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


       try {
            Thread thread = new Thread(new Server());
            thread.start();
            DatabaseCollectioner.DatabaseGenerator();
            ServerShop.getInstance().getCards().addAll(CreateCardFromDatabaseCard.createCards(DatabaseCard.getDatabaseCards()));
            ArrayList<String> numberOfCards = new ArrayList<>();

            YaGson yaGson = new YaGsonBuilder().setPrettyPrinting().create();
            Reader reader = new FileReader("numberOfCards.json");
            String[] strings = yaGson.fromJson(reader, (Type) String[].class);
            numberOfCards.addAll(Arrays.asList(strings));
            ServerShop.getInstance().setNumberOfCards(numberOfCards);

            initItems();
            initSpells();
            initAccounts();//TODO Initialize Server Account ArrayList
        } catch (BindException e){


        //TODO Run Client And Make Connection To The Server
        Client client = new Client();
        client.getReader().start();

        launch(args);
        }
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

    private static void initAccounts() {
        try {
            YaGson yaGson = new YaGsonBuilder().setPrettyPrinting().create();
            Reader reader = new FileReader("accounts.json");
            Account[] accounts = yaGson.fromJson(reader, (Type) Account[].class);
            if (accounts != null) {
                for (Account account : accounts) {
                    Account.getAccounts().add(account);
                    Server.addAccount(account);//TODO Temporary Adding Should be Removed In Final Version
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void initItems() {
        Collections.addAll(ServerShop.getInstance().getCards(), new TajeDanaei(), new NamooseSepar(), new KamaneDamol(), new PareSimorgh(),
                new TerrorHood(), new KingWisdom(), new AssassinationDagger(), new PoisonousDagger(), new ShockHammer(), new SoulEater(), new GhosleTaemid());
    }


    private static void initSpells() {
        Spell spell = new Spell("Total Disarm", "", 0, 1000, TargetCommunity.ENEMY_WARRIOR);
        spell.getBuffs().add(new DisarmBuff(100));
        ServerShop.getInstance().getCards().add(spell);


        spell = new Spell("Area Dispel", "", 2, 1500, TargetCommunity.CELLS);
        //TODO Hard Code
        ServerShop.getInstance().getCards().add(spell);

        spell = new Spell("Empower", "", 1, 250, TargetCommunity.FRIENDLY_WARRIOR);
        spell.getBuffs().add(new PowerBuff(1, true, 2));
        ServerShop.getInstance().getCards().add(spell);

        spell = new Spell("Fire Ball", "", 1, 400, TargetCommunity.ENEMY_WARRIOR);
        spell.getBuffs().add(new WeaknessBuff(1, false, 4));
        ServerShop.getInstance().getCards().add(spell);

        spell = new Spell("God Strength", "", 2, 450, TargetCommunity.FRIENDLY_WARRIOR);
        spell.getBuffs().add(new PowerBuff(1, true, 1));
        ServerShop.getInstance().getCards().add(spell);


        spell = new Spell("Hell Fire", "", 3, 600, TargetCommunity.CELLS);
        spell.getBuffs().add(new FlameBuff(2, false));
        ServerShop.getInstance().getCards().add(spell);

        //TODO edame doros ni

        spell = new Spell("Lighting Bolt", "", 2, 1250, TargetCommunity.ENEMY_WARRIOR);
        spell.getBuffs().add(new WeaknessBuff(1, false, 8));
        ServerShop.getInstance().getCards().add(spell);


        spell = new Spell("Poison Lake", "", 5, 900, TargetCommunity.CELLS);
        spell.getBuffs().add(new PoisonBuff(1, false));
        ServerShop.getInstance().getCards().add(spell);

        spell = new Spell("Madness", "", 0, 650, TargetCommunity.FRIENDLY_WARRIOR);
        spell.getBuffs().add(new DisarmBuff(1));
        spell.getBuffs().add(new PowerBuff(3, true, 4));
        ServerShop.getInstance().getCards().add(spell);

        spell = new Spell("All Disarm", "", 9, 2000, TargetCommunity.ALL_OF_ENEMY);
        spell.getBuffs().add(new DisarmBuff(1));
        ServerShop.getInstance().getCards().add(spell);

        spell = new Spell("All Poison", "", 8, 1500, TargetCommunity.ALL_OF_ENEMY);
        spell.getBuffs().add(new PoisonBuff(4, true));
        ServerShop.getInstance().getCards().add(spell);

        spell = new Spell("Dispel", "", 0, 2100, TargetCommunity.ENEMY_WARRIOR);
        //TODO wtf?!
        ServerShop.getInstance().getCards().add(spell);

        spell = new Spell("Health with profit", "", 0, 2250, TargetCommunity.FRIENDLY_WARRIOR);
        spell.getBuffs().add(new WeaknessBuff(1, false, 6));
        spell.getBuffs().add(new HolyBuff(2, 3));
        ServerShop.getInstance().getCards().add(spell);

        spell = new Spell("Power up", "", 2, 2500, TargetCommunity.FRIENDLY_WARRIOR);
        spell.getBuffs().add(new PowerBuff(1, true, 6));
        ServerShop.getInstance().getCards().add(spell);

        spell = new Spell("All Power", "", 4, 2000, TargetCommunity.ALL_OF_FRIEND);
        spell.getBuffs().add(new PowerBuff(500, true, 2));
        ServerShop.getInstance().getCards().add(spell);

        spell = new Spell("Weakening", "", 1, 1000, TargetCommunity.ENEMY_WARRIOR);
        spell.getBuffs().add(new WeaknessBuff(1, false, 4));
        ServerShop.getInstance().getCards().add(spell);

        spell = new Spell("Sacrifice", "", 2, 1600, TargetCommunity.FRIENDLY_WARRIOR);
        spell.getBuffs().add(new WeaknessBuff(1, false, 6));
        spell.getBuffs().add(new PowerBuff(1, true, 8));
        ServerShop.getInstance().getCards().add(spell);

        spell = new Spell("Shock", "", 1, 1200, TargetCommunity.ENEMY_WARRIOR);
        spell.getBuffs().add(new StunBuff(2));
        ServerShop.getInstance().getCards().add(spell);

    }
}
