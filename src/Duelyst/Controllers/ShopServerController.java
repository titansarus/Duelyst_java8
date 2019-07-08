package Duelyst.Controllers;

import Duelyst.Model.Card;
import Duelyst.Server.Server;
import Duelyst.Server.ServerShop;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumnBase;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;


public class ShopServerController {
    public VBox table;

    Timeline timeline = new Timeline();

    public void runTimeLine() {
        timeline = new Timeline(new KeyFrame(Duration.ZERO, event -> {

            table.getChildren().clear();
            for (int i = 0; i < ServerShop.getInstance().getCards().size(); i++) {
                HBox hBox = new HBox();
                hBox.setPrefWidth(400);
                hBox.setPrefHeight(50);
                VBox name = new VBox();
                VBox count = new VBox();
                name.setPrefWidth(200);
                count.setPrefWidth(200);
                count.setPrefHeight(50);
                name.setPrefHeight(50);
                name.getChildren().add(new Label(ServerShop.getInstance().getCards().get(i).getCardName()));
                count.getChildren().add(new Label(ServerShop.getInstance().getNumberOfCard(ServerShop.getInstance().getCards().get(i).getCardName()) + ""));
                hBox.getChildren().addAll(name, count);
                table.getChildren().add(hBox);

            }

        }), new KeyFrame(Duration.millis(2000)));

        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }


}
