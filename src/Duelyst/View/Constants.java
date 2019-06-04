package Duelyst.View;

import javafx.scene.image.Image;

public class Constants {

    //ERRORS
    public static final String USER_EXIST_CONTENT = "A User Exist with this username", USER_NOT_EXIST_CONTENT = "No User Exist with this username",
            NOT_EXIST_LOGIN_USER_CONTENT = "You can not go to Main Menu , First Login or Sign Up",
            INVALID_PASSWORD_CONTENT = "Wrong Password ! ", NOT_ENOUGH_DARICK_CONTENT = "Your Darick Is not Enough to buy!",
            NO_CARD_SELECTED_IN_SHOP_CONTENT = "No Card is selected. Please select a card!";

    //ERROR TITLE
    public static final String USER_EXIST_TITLE = "User Exist", USER_NOT_EXIST_TITLE = "User Not Exist", NOT_EXIST_LOGIN_USER_TITLE = "First Login!",
            INVALID_PASSWORD_TITLE = "Try again!", NOT_ENOUGH_DARICK_TITLE = "Not Enough Money",
            NO_CARD_SELECTED_IN_SHOP_TITLE = "Select a Card";

    //NOTIFICATION
    public static final String USER_CREATED_CONTENT = "User Created Successfully", USER_LOGINED_CONTENT = "User Logined Successfully", BUY_CONTENT = "Buy was Successful!",
            SELL_CONTENT = "Sell was Successful";

    //NOTIFICATION TITLE
    public static final String USER_CREATED_TITLE = "User Created", USER_LOGINED = "User Logined", BUY_TITLE = "Done!" , SELL_TITLE = "Done!";

    public static final String NO_USER_LOGINED = "NO USER LOGINED";


    public static final int INITIAL_DARICK = 10000;


    //TITLE
    public static final String SHOP = "SHOP", MAIN_MENU = "MAIN_MENU", LOGIN = "LOGIN", LEADERBOARD = "LEADERBOARD" , COLLECTION = "COLLECTION";


    public static final Image playImg = new Image(Constants.class.getResourceAsStream("../../res/ui/play.png"));
    public static final Image shopImg = new Image(Constants.class.getResourceAsStream("../../res/ui/shop.png"));
    public static final Image quitImg = new Image(Constants.class.getResourceAsStream("../../res/ui/quit.png"));
    public static final Image collectionImg = new Image(Constants.class.getResourceAsStream("../../res/ui/collection.png"));
    public static final Image leaderboardsImg = new Image(Constants.class.getResourceAsStream("../../res/ui/leaderboards.png"));
    public static final Image buyImg = new Image(Constants.class.getResourceAsStream("../../res/ui/buy.png"));
    public static final Image sellImg = new Image(Constants.class.getResourceAsStream("../../res/ui/Sell.png"));
    public static final Image backImg = new Image(Constants.class.getResourceAsStream("../../res/ui/back.png"));
    public static final Image heroImg = new Image(Constants.class.getResourceAsStream("../../res/Characters/generals/general_f1.png"));

}
