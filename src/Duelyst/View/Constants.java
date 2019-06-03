package Duelyst.View;

import javafx.scene.image.Image;

public class Constants {

    //ERRORS
    public static final String USER_EXIST_CONTENT = "A User Exist with this username", USER_NOT_EXIST_CONTENT = "No User Exist with this username";
    public static final String NOT_EXIST_LOGIN_USER_CONTENT = "You can not go to Main Menu , First Login or Sign Up";
    public static final String INVALID_PASSWORD_CONTENT = "Wrong Password ! ";

    //ERROR TITLE
    public static final String USER_EXIST_TITLE = "User Exist", USER_NOT_EXIST_TITLE = "User Not Exist", NOT_EXIST_LOGIN_USER_TITLE = "First Login!";
    public static final String INVALID_PASSWORD_TITLE = "Try again!";

    //NOTIFICATION
    public static final String USER_CREATED_CONTENT = "User Created Successfully", USER_LOGINED_CONTENT = "User Logined Successfully";

    //NOTIFICATION TITLE
    public static final String USER_CREATED_TITLE = "User Created", USER_LOGINED = "User Logined";

    public static final String NO_USER_LOGINED = "NO USER LOGINED";


    public static final int INITIAL_DARICK = 10000;


    //TITLE
    public static final String SHOP = "SHOP" , MAIN_MENU = "MAIN_MENU" , LOGIN = "LOGIN" , LEADERBOARD = "LEADERBOARD";


    public static final Image playImg = new Image(Constants.class.getResourceAsStream("../../res/ui/play.png"));
    public static final Image shopImg = new Image(Constants.class.getResourceAsStream("../../res/ui/shop.png"));
    public static final Image quitImg = new Image(Constants.class.getResourceAsStream("../../res/ui/quit.png"));
    public static final Image collectionImg = new Image(Constants.class.getResourceAsStream("../../res/ui/collection.png"));
    public static final Image leaderboardsImg = new Image(Constants.class.getResourceAsStream("../../res/ui/leaderboards.png"));
    public static final Image backImg = new Image(Constants.class.getResourceAsStream("../../res/ui/back.png"));
    public static final Image heroImg = new Image(Constants.class.getResourceAsStream("../../res/Characters/generals/general_f1.png"));

}
