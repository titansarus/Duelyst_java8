package Duelyst.Controllers;

import Duelyst.Exceptions.MyException;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.Deque;
import java.util.LinkedList;

import static Duelyst.View.Constants.COLLECTION;

public class Container {
    public static Stage stage = new Stage();
    public static Deque<Scene> scenes = new LinkedList<>();
    public static Deque<String> nameOfMenus = new LinkedList<>();


    public static void exceptionGenerator(MyException e) {
        alertShower(e, e.getTitle());
    }

    static void alertShower(Exception e, String title) {
        Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
        alert.setTitle(title);
        alert.setHeaderText(title);
        alert.show();
    }

    static void notificationShower(String msg, String title) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, msg);
        alert.setTitle(title);
        alert.setHeaderText(title);
        alert.show();
    }

    static void runNextScene(Pane root, String titleOfNextScene) {
        Scene scene = new Scene(root);
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
