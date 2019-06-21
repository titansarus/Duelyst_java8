package Duelyst.Controllers;

import Duelyst.Exceptions.MyException;
import Duelyst.Model.Card;
import Duelyst.View.ViewClasses.CardView;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.ImageCursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;

import static Duelyst.View.Constants.*;

public class Container {
    public static Stage stage = new Stage();
    public static Deque<Scene> scenes = new LinkedList<>();
    public static Deque<String> nameOfMenus = new LinkedList<>();


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
        Image cursorImage = new Image("res/ui/mouse_select.png");
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
        Container.stage.setScene(Container.scenes.getLast());
        Container.stage.show();
    }


}
