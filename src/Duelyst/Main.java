package Duelyst;

import Duelyst.Controllers.Container;
import Duelyst.Database.DatabaseCard;
import Duelyst.Database.DatabaseCollectioner;
import Duelyst.Model.Account;
import Duelyst.Model.Buffs.*;
import Duelyst.Model.Card;
import Duelyst.Model.Items.*;
import Duelyst.Model.Shop;
import Duelyst.Model.Spell.Spell;
import Duelyst.Model.Spell.TargetCommunity;
import Duelyst.Utility.CreateCardFromDatabaseCard;
import com.gilecode.yagson.YaGson;
import com.gilecode.yagson.YaGsonBuilder;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;

import static Duelyst.View.Constants.*;

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
        initItems();

        makeSpell();


        initAccounts();
        launch(args);
    }

    public static void initItems() {
        Collections.addAll(Shop.getInstance().getCards(), new TajeDanaei(), new NamooseSepar(), new KamaneDamol(), new PareSimorgh(),
                new TerrorHood(), new KingWisdom(), new AssassinationDagger(), new PoisonousDagger(), new ShockHammer(), new SoulEater(), new GhosleTaemid());
    }

    @Override
    public void start(Stage primaryStage) throws Exception {


        File file = new File(PATH_OF_MAIN_THEME);
        Media media = new Media(file.toURI().toString());

        Container.runMediaPlayer(Container.mainThemePlayer,media,0.1,true,Integer.MAX_VALUE , MAIN_THEME);

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
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void makeSpell() {
        Spell spell = new Spell("Total Disarm", "", 0, 1000, TargetCommunity.ENEMY_WARRIOR);
        spell.getBuffs().add(new DisarmBuff(100));
        Shop.getInstance().getCards().add(spell);


        spell = new Spell("Area Dispel", "", 2, 1500, TargetCommunity.CELLS);
        //TODO Hard Code
        Shop.getInstance().getCards().add(spell);

        spell = new Spell("Empower", "", 1, 250, TargetCommunity.FRIENDLY_WARRIOR);
        spell.getBuffs().add(new PowerBuff(1,true,2));
        Shop.getInstance().getCards().add(spell);

        spell = new Spell("Fire Ball", "", 1, 400, TargetCommunity.ENEMY_WARRIOR);
        spell.getBuffs().add(new WeaknessBuff(1,false,4));
        Shop.getInstance().getCards().add(spell);

        spell = new Spell("God Strength", "", 2, 450, TargetCommunity.FRIENDLY_WARRIOR);
        spell.getBuffs().add(new PowerBuff(1,true,1));
        Shop.getInstance().getCards().add(spell);


        spell = new Spell("Hell Fire", "", 3, 600, TargetCommunity.CELLS);
        spell.getBuffs().add(new FlameBuff(2,false));
        Shop.getInstance().getCards().add(spell);

        //TODO edame doros ni

        spell = new Spell("Lighting Bolt","",2,1250,TargetCommunity.ENEMY_WARRIOR);
        spell.getBuffs().add(new WeaknessBuff(1,false,8));
        Shop.getInstance().getCards().add(spell);


        spell = new Spell("Poison Lake","",5,900,TargetCommunity.CELLS);
        spell.getBuffs().add(new PoisonBuff(1,false));
        Shop.getInstance().getCards().add(spell);

        spell = new Spell("Madness","",0,650,TargetCommunity.FRIENDLY_WARRIOR);
        spell.getBuffs().add(new DisarmBuff(1));
        spell.getBuffs().add(new PowerBuff(3,true,4));
        Shop.getInstance().getCards().add(spell);

        spell = new Spell("All Disarm","",9,2000,TargetCommunity.ALL_OF_ENEMY);
        spell.getBuffs().add(new DisarmBuff(1));
        Shop.getInstance().getCards().add(spell);

        spell = new Spell("All Poison","",8,1500,TargetCommunity.ALL_OF_ENEMY);
        spell.getBuffs().add(new PoisonBuff(4,true));
        Shop.getInstance().getCards().add(spell);

        spell = new Spell("Dispel","",0,2100,TargetCommunity.ENEMY_WARRIOR);
        //TODO wtf?!
        Shop.getInstance().getCards().add(spell);

        spell = new Spell("Health with profit","",0,2250,TargetCommunity.FRIENDLY_WARRIOR);
        spell.getBuffs().add(new WeaknessBuff(1,false,6));
        spell.getBuffs().add(new HolyBuff(2,3));
        Shop.getInstance().getCards().add(spell);

        spell = new Spell("Power up","",2,2500,TargetCommunity.FRIENDLY_WARRIOR);
        spell.getBuffs().add(new PowerBuff(1,true,6));
        Shop.getInstance().getCards().add(spell);

        spell = new Spell("All Power","",4,2000,TargetCommunity.ALL_OF_FRIEND);
        spell.getBuffs().add(new PowerBuff(500,true,2));
        Shop.getInstance().getCards().add(spell);

        spell = new Spell("Weakening","",1,1000,TargetCommunity.ENEMY_WARRIOR);
        spell.getBuffs().add(new WeaknessBuff(1,false,4));
        Shop.getInstance().getCards().add(spell);

        spell = new Spell("Sacrifice","",2,1600,TargetCommunity.FRIENDLY_WARRIOR);
        spell.getBuffs().add(new WeaknessBuff(1,false,6));
        spell.getBuffs().add(new PowerBuff(1,true,8));
        Shop.getInstance().getCards().add(spell);

        spell = new Spell("Shock","",1,1200,TargetCommunity.ENEMY_WARRIOR);
        spell.getBuffs().add(new StunBuff(2));
        Shop.getInstance().getCards().add(spell);

    }
}
