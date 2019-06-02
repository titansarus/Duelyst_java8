package Duelyst.Controllers;

import Duelyst.Model.Account;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXScrollPane;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Cell;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

import java.util.ArrayList;

import org.apache.commons.lang3.StringUtils;


public class LeaderboardController {
    @FXML
    JFXScrollPane scrollPane;


    @FXML
    VBox vbox;

    @FXML
    public void initialize()
    {
        ArrayList<Account> accounts = Account.accountsSorter(Account.getAccounts());
//        scrollPane.setMinHeight(500);
//        scrollPane.setMinWidth(1000);
        ArrayList<Label> labels = new ArrayList<>();
        for (int i =0;i<accounts.size();i++)
        {
            String output = String.format("%-3d %s %-3d",i+1,StringUtils.center(accounts.get(i).getUsername(),20,".") ,accounts.get(i).getCountOfWins());
            Label label = new Label();
            label.setFont(Font.font("System", FontWeight.BOLD, FontPosture.REGULAR,33));
            label.setText(output);
            label.setPrefWidth(600);
            label.setPrefHeight(50);
            label.setAlignment(Pos.CENTER);
            labels.add(label);
        }





        scrollPane.setContent(vbox);
        vbox.getChildren().addAll(labels);
        vbox.setAlignment(Pos.CENTER);
        ImageView i= new ImageView("chapter24_background@2x.jpg");
        scrollPane.getTopBar().maxHeightProperty().set(200);
        i.fitWidthProperty().bind(scrollPane.getTopBar().maxWidthProperty());
        i.fitHeightProperty().bind(scrollPane.getTopBar().maxHeightProperty());

//        scrollPane.getMidBar().setVisible(false);
       // scrollPane.getCondensedHeader().setVisible(false);

        scrollPane.getTopBar().getChildren().add(i);
        Label label = new Label("Leaderboards");
        label.setFont(Font.font("System",FontWeight.BOLD,FontPosture.REGULAR,33));
        label.setTextFill(Color.DARKBLUE);
        scrollPane.getTopBar().getChildren().add(label);

    }


    public void handleBackBtn()
    {
        if (Container.scenes.size() > 0) {
            Container.scenes.removeLast();
            Container.stage.setScene(Container.scenes.getLast());
            Container.stage.show();

        }
    }
}
