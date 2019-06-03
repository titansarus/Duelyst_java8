package Duelyst.Controllers;

import Duelyst.Model.Account;
import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;

import java.util.ArrayList;

import static Duelyst.View.Constants.*;


public class Leaderboard {
    @FXML
    TableView<AccountInfo> leaderboard_tbv;

    @FXML
    JFXButton back_btn;

    @FXML
    ImageView title_iv;


    @FXML
    public void initialize() {
        updateTable();
        back_btn.setGraphic(new ImageView(backImg));
        title_iv.setImage(leaderboardsImg);
    }

    public void updateTable()
    {
        ArrayList<Account> accounts = Account.accountsSorter(Account.getAccounts());
//        scrollPane.setMinHeight(500)
        ArrayList<AccountInfo> accountInfos = new ArrayList<>();
        int count = 0;
        for (int i = 0; i < accounts.size(); i++) {
            if (accounts.get(i) != null) {
                Account account = accounts.get(i);
                accountInfos.add(new AccountInfo(++count,account.getUsername(),account.getCountOfWins()));
            }
        }


        TableColumn<AccountInfo, String> column1 = new TableColumn<>("Rank");
        column1.setCellValueFactory(new PropertyValueFactory<>("rank"));

        TableColumn<AccountInfo, String> column2 = new TableColumn<>("Username");
        column2.setCellValueFactory(new PropertyValueFactory<>("username"));

        TableColumn<AccountInfo, Integer> column3 = new TableColumn<>("Wins");
        column3.setCellValueFactory(new PropertyValueFactory<>("wins"));

        column1.setPrefWidth(100);
        column3.setPrefWidth(100);


        TableView tableView = leaderboard_tbv;

        tableView.getColumns().add(column1);
        tableView.getColumns().add(column2);
        tableView.getColumns().add(column3);


        for (int i = 0; i < accountInfos.size(); i++) {
            AccountInfo account = accountInfos.get(i);
            tableView.getItems().add(account);
        }
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

