package Duelyst.Controllers;

import Duelyst.Exceptions.MyException;
import Duelyst.Model.Card;
import Duelyst.Utility.ImageHolder;
import Duelyst.View.ViewClasses.CardView;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.ImageCursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextFormatter;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.function.UnaryOperator;

import static Duelyst.View.Constants.*;

public class Container {
    public static Stage stage = new Stage();
    public static Deque<Scene> scenes = new LinkedList<>();
    public static Deque<String> nameOfMenus = new LinkedList<>();
    public static Deque<Object> controllers = new LinkedList<>();


    public static Object controllerClass;
    public static MediaPlayer mainThemePlayer;

    public static MediaPlayer soundPlayer;

    private static boolean isSoundOn = true;

    private static long turnLimitTimeMillisecond = SINGLE_PLAYER_TIME_LIMIT_MS_DEFAULT;


    public static void exceptionGenerator(MyException e, StackPane pane) {
        dialogBoxShower(e.getMessage(), e.getTitle(), pane);
    }

    public static void notificationShower(String msg, String title, StackPane stackPane) {
        dialogBoxShower(msg, title, stackPane);
    }

    static void dialogBoxShower(String msg, String title, StackPane pane) {
        JFXDialogLayout jfxDialogLayout = new JFXDialogLayout();
        jfxDialogLayout.setHeading(new Text(title));
        jfxDialogLayout.setBody(new Text(msg));
        JFXButton button = new JFXButton();
        button.setPrefSize(70, 20);
        button.setText(ALERT_OK);
        button.setStyle(DEFAULT_BUTTON_CSS);

        jfxDialogLayout.setActions(button);

        JFXDialog jfxDialog = new JFXDialog(pane, jfxDialogLayout, JFXDialog.DialogTransition.CENTER);


        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                jfxDialog.close();
            }
        });

        jfxDialog.show();

    }

    public static void runMediaPlayer(MediaPlayer mediaPlayer, Media media, double volume, boolean autoplay, int cycleCount, String typeOfPlayer) {
        runMediaPlayer(mediaPlayer, media, volume, autoplay, cycleCount, typeOfPlayer, 1.0);
    }

    public static void runMediaPlayer(MediaPlayer mediaPlayer, Media media, double volume, boolean autoplay, int cycleCount, String typeOfPlayer, double speed) {
        if (typeOfPlayer.equals(MAIN_THEME)) {
            mainThemePlayer = new MediaPlayer(media);
            mediaPlayer = mainThemePlayer;
        }
        if (typeOfPlayer.equals(SOUND_PLAYER)) {
            soundPlayer = new MediaPlayer(media);
            mediaPlayer = soundPlayer;

        }

        mediaPlayer.setVolume(volume);

        if (!isSoundOn()) {
            mediaPlayer.setVolume(0);
        }
        mediaPlayer.setAutoPlay(autoplay);
        mediaPlayer.setCycleCount(cycleCount);
        mediaPlayer.setRate(speed);
    }


    public static JFXDialogLayout jfxInputDialogLayoutMaker(JFXButton accept, JFXButton cancel, JFXTextField textField, String headerText, String bodyText) {
        JFXDialogLayout jfxDialogLayout = new JFXDialogLayout();
        jfxDialogLayout.setHeading(new Text(headerText));
        jfxDialogLayout.setBody(new Text(bodyText));
        cancel.setStyle(DEFAULT_BUTTON_CSS);
        accept.setStyle(MODE_SELECTION_BUTTON_CSS);
        cancel.setText(CANCEL);
        accept.setText(ACCEPT);
        textField.setPrefWidth(250);
        jfxDialogLayout.setActions(textField, cancel, accept);
        return jfxDialogLayout;
    }


    static void runNextScene(Pane root, String titleOfNextScene) {
        Scene scene = new Scene(root);
        //Change Cursor
        Image cursorImage = ImageHolder.findImageInImageHolders("res/ui/mouse_select.png");
        scene.setCursor(new ImageCursor(cursorImage));
        //========================
        Container.scenes.addLast(scene);
        Container.nameOfMenus.add(titleOfNextScene);
        Container.stage.setScene(Container.scenes.getLast());
        Container.stage.show();
    }

    static void handleBack() {

        Container.scenes.removeLast();
        Container.nameOfMenus.removeLast();

        //CONTROLLERS
        Container.controllers.removeLast();
        Container.controllerClass = Container.controllers.getLast();
        //


        Container.stage.setScene(Container.scenes.getLast());
        Container.stage.show();
    }

    private static UnaryOperator<TextFormatter.Change> filter = change -> {
        String text = change.getText();

        if (text.matches("[0-9]*")) {
            return change;
        }

        return null;
    };

    public static void addController(FXMLLoader fxmlLoader) {
        Object _controllerClass = fxmlLoader.getController();
        controllers.addLast(_controllerClass);
        Container.controllerClass = _controllerClass;
    }

    public static void changeSoundOnOrOff() {
        if (isSoundOn()) {
            mainThemePlayer.setVolume(0);
            if (soundPlayer != null) {
                soundPlayer.setVolume(0);
            }
            isSoundOn = false;
        } else {
            mainThemePlayer.setVolume(0.1);
            if (soundPlayer != null) {
                soundPlayer.setVolume(1.0);
                isSoundOn = true;
            }
        }

    }

    public static Deque<Object> getControllers() {
        return controllers;
    }

    public static Object getControllerClass() {
        return controllerClass;
    }

    public static TextFormatter<String> getOnlyNumberFormatter() {
        return new TextFormatter<>(filter);
    }

    public static boolean isSoundOn() {
        return isSoundOn;
    }

    public static void changeImageOfSoundImageView(ImageView sound) {
        if (isSoundOn()) {
            sound.setImage(ImageHolder.findImageInImageHolders("./res/ui/soundOn.png"));
        } else {
            sound.setImage(ImageHolder.findImageInImageHolders("./res/ui/soundOff.png"));
        }
    }

    public static void setImageOfSetting(ImageView setting) {
        setting.setImage(ImageHolder.findImageInImageHolders("./res/ui/setting.png"));
    }

    public static long getTurnLimitTimeMillisecond() {
        return turnLimitTimeMillisecond;
    }

    public static void setTurnLimitTimeMillisecond(long turnLimitTimeMillisecond) {
        Container.turnLimitTimeMillisecond = turnLimitTimeMillisecond;
    }
}
