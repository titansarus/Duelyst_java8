package Duelyst.View;

import javafx.scene.image.Image;

public class Constants {

    //ERRORS
    public static final String USER_EXIST_CONTENT = "A User Exist with this username", USER_NOT_EXIST_CONTENT = "No User Exist with this username",
            NOT_EXIST_LOGIN_USER_CONTENT = "You can not go to Main Menu , First Login or Sign Up",
            INVALID_PASSWORD_CONTENT = "Wrong Password ! ", NOT_ENOUGH_DARICK_CONTENT = "Your Darick Is not Enough to buy!",
            NO_CARD_SELECTED_IN_SHOP_CONTENT = "No Card is selected. Please select a card!", DECK_EXIST_WITH_THIS_NAME_CONTENT = "A Deck Exists with this Name",
            NO_CARD_SELECTED_FROM_DECK_CONTENT = "No Card is Selected from Deck", NO_CARD_SELECTED_FROM_COLLECTION_CONTENT = "No Card is Selected from Collection",
            NO_MAIN_DECK_CONTENT = "Select or Create a Deck as Main Deck first", NO_DECK_SELECTED_CONTENT = "No Deck is Selected",
            NOT_ENOUGH_CARD_TO_IMPORT_CONTENT = "Not Enough Cards to Import!", INVALID_IMPORT_FILE_CONTENT = "Invalid File to Import",
            EMPTY_FIELD_EXCEPTION_CONTENT = "Please fill in all required fields!", CELL_FILLED_BEFORE_CONTENT = "Cell has a Card on it!", NOT_ENOUGHT_MANA_CONTENT = "You don't have enough mana for this action!",
            CREATE_CARD_FIELD_NOT_COMPLETE_CONTENT = "Please fill all fields that are required for card creation", DECK_IS_INVALID_TITLE = "Invalid Deck!", DECK_IS_VALID_TITLE = "Valid Deck",
            CHEAT_MODE_ACTIVE_CONTENT = "Don't cheat!", CHEAT_MODE_DEACTIVATED_CONTENT = "Thank you for not cheating!";

    //ERROR TITLE
    public static final String USER_EXIST_TITLE = "User Exist", USER_NOT_EXIST_TITLE = "User Not Exist", NOT_EXIST_LOGIN_USER_TITLE = "First Login!",
            INVALID_PASSWORD_TITLE = "Try again!", NOT_ENOUGH_DARICK_TITLE = "Not Enough Money",
            NO_CARD_SELECTED_IN_SHOP_TITLE = "Select a Card", DECK_EXIST_WITH_THIS_NAME_TITLE = "Deck Exists", NO_CARD_SELECTED_FROM_DECK_TITLE = "No Card Selected",
            NO_CARD_SELECTED_FROM_COLLECTION_TITLE = "No Card Selected", NO_MAIN_DECK_TITLE = "Main Deck is Not Selected", NO_DECK_SELECTED_TITLE = "No Deck is Selected",
            NOT_ENOUGH_CARD_TO_IMPORT_TITLE = "Not Enough Cards to Import!", INVALID_IMPORT_FILE_TITLE = "Invalid File", EMPTY_FIELD_EXCEPTION_TITLE = "Empty Fields!", CELL_FILLED_BEFORE_TITLE = "Cell is not Empty!",
            NOT_ENOUGHT_MANA_TITLE = "Not Enough Mana!", CREATE_CARD_FIELD_NOT_COMPLETE_TITLE = "Some fields don't have valid input", DECK_IS_INVALID_CONTENT = "Current main deck combination of cards is invalid!",
            DECK_IS_VALID_CONTENT = "Selected Main Deck is Valid", CHEAT_MODE_ACTIVE_TITLE = "Cheat Mode Activated!", CHEAT_MODE_DEACTIVATED_TITLE = "Cheat Mode Deactivated!";

    //NOTIFICATION
    public static final String USER_CREATED_CONTENT = "User Created Successfully", USER_LOGINED_CONTENT = "User Logined Successfully", BUY_CONTENT = "Buy was Successful!",
            SELL_CONTENT = "Sell was Successful", NO_ADDRESS = "NO_ADDRESS";

    //NOTIFICATION TITLE
    public static final String USER_CREATED_TITLE = "User Created", USER_LOGINED = "User Logined", BUY_TITLE = "Done!", SELL_TITLE = "Done!";

    public static final String NO_USER_LOGINED = "NO USER LOGINED", NO_MAIN_DECK = "No Main Deck";


    public static final int INITIAL_DARICK = 100000;

    public static final String SHOP = "SHOP", MAIN_MENU = "MAIN_MENU", LOGIN = "LOGIN", LEADERBOARD = "LEADERBOARD", COLLECTION = "COLLECTION", BATTLE = "BATTLE", CARD_CREATOR = "CARD_CREATOR", GRAVE_YARD = "GRAVE_YARD";

    //TITLE

    public static final Image shopImg = new Image(Constants.class.getResourceAsStream("../../res/ui/shop.png"));
    public static final Image quitImg = new Image(Constants.class.getResourceAsStream("../../res/ui/quit.png"));
    public static final Image collectionImg = new Image(Constants.class.getResourceAsStream("../../res/ui/collection.png"));
    public static final Image leaderboardsImg = new Image(Constants.class.getResourceAsStream("../../res/ui/leaderboards.png"));
    public static final Image buyImg = new Image(Constants.class.getResourceAsStream("../../res/ui/buy.png"));
    public static final Image sellImg = new Image(Constants.class.getResourceAsStream("../../res/ui/Sell.png"));
    public static final Image backImg = new Image(Constants.class.getResourceAsStream("../../res/ui/back.png"));
    //...//
    public static final Image nextImg = new Image(Constants.class.getResourceAsStream("../../res/ui/next.png"), 150, 50, true, true);
    public static final Image previousImg = new Image(Constants.class.getResourceAsStream("../../res/ui/previous.png"), 150, 50, true, true);
    public static final Image MainDeckImg = new Image(Constants.class.getResourceAsStream("../../res/ui/Main-Deck.png"));
    public static final Image sendToDeckImg = new Image(Constants.class.getResourceAsStream("../../res/ui/send-to-Deck.png"));
    public static final Image sendToCollection = new Image(Constants.class.getResourceAsStream("../../res/ui/send-to-Collection.png"));
    //...//
    public static final Image heroImg = new Image(Constants.class.getResourceAsStream("../../res/Characters/generals/general_f1.png"));
    public static final Image playImg = new Image(Constants.class.getResourceAsStream("../../res/ui/play.png"));
    public static final Image createDeckImg = new Image(Constants.class.getResourceAsStream("../../res/ui/create_deck_sml.png"));
    public static final Image manaIconSml = new Image(Constants.class.getResourceAsStream("../../res/CardUI/icon_mana.png"));
    public static final Image manaInActiveSml = new Image(Constants.class.getResourceAsStream("../../res/CardUI/icon_mana_inactive.png"));
    public static final Image battleCardSelectedImage = new Image(Constants.class.getResourceAsStream("../../res/CardUI/card_background_highlight@2x.png"));
    public static final Image battleCardNotSelectedImage = new Image(Constants.class.getResourceAsStream("../../res/CardUI/card_background@2x.png"));
    public static final Image buffEffect = new Image(Constants.class.getResourceAsStream("../../res/effects/explosion.gif"));


    public static final String PATH_OF_MAIN_THEME = "src/res/Music/main_theme.mp3", BUTTON_HOVER_SOUND = "src/res/Music/button_hover.m4a", CLICK_SOUND = "src/res/Music/onclick.m4a",
            ATTACK_SOUND = "src/res/Music/attack.m4a" , MOVE_SOUND = "src/res/Music/walk.mp3";
    public static final String MAIN_THEME = "MAIN_THEME", SOUND_PLAYER = "SOUND_PLAYER";


    //OTHER CONSTANTS
    public static final String ALERT_OK = "OK", GAME_MODE_TITLE = "Game Mode", GAME_GOAL_TITLE = "Game Goal",
            CHOOSE_GAME_MODE = "Choose your game mode", SINGLEPLAYER_MODE = "Single-Player", MULTIPLAYER_MODE = "Multiplayer",
            CHOOSE_GAME_GOAL = "Choose your game goal", HOLD_FLAG = "Hold Flag", CAPTURE_FLAG = "Capture Flags", KILL_HERO = "Kil Hero",
            CHOOSE_GAME_LEVEL = "Choose Level", STORY_LEVEL_1 = "Story Level 1", STORY_LEVEL_2 = "Story Level 2", STORY_LEVEL_3 = "Story Level 3", CANCEL = "Cancel",
            ACCEPT = "Accept", CHOOSE_DECK_NAME = "Give your Deck a name!", CHOOSE_IMPORT_DECK_NAME = "Give name for Import Deck",
            CHOOSE_SINGLE_PLAYER_MODE = "Choose your single player game mode", CHOOSE_DECK = "Choose Opponent Deck", GAME_SINGLE_PLAYER_TITLE = "Mode", GAME_CUSTOM_TITLE = "Mode", CUSTOM_MODE = "custom", STORY_MODE = "story", CHOOSE_OPPONENT_TITLE = "Your opponent", CHOOSE_OPPNENT_BODY = "Choose Your opponent";

    public static final int MULTIPLAYER_INT = 2, SINGLEPLAYER_INT = 1, LEVEL_1 = 1, LEVEL_2 = 2, LEVEL_3 = 3, KILL_HERO_INT = 1, CAPTURE_FLAG_INT = 2, HOLD_FLAG_INT = 3;

    //STYLE CSS

    public static final String DEFAULT_BUTTON_CSS = "-fx-background-color: #00bfff;-fx-border-radius: 10pt ; -fx-background-radius: 10pt", MODE_SELECTION_BUTTON_CSS = "-fx-background-color: #A2EF00 ; -fx-background-radius: 10pt ; -fx-border-radius: 10pt";

    //NUMERIC CONSTANTS
    public static final int BATTLE_ROWS = 5, BATTLE_COLUMNS = 9, SIZE_OF_HAND = 5, CHEAT_MODE_ACTIVATED = 1, CHEAT_MODE_DEACTIVATED = 0;
    public static final double HEIGHT_PADDING_Y = 6.0, HEIGHT_PADDING_X = 1.0, WIDTH_PADDING = 6.0, STD_CARD_WIDTH = 100.0, STD_CARD_HEIGHT = 100.0;

}
