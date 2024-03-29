package Duelyst.Controllers;

import Duelyst.Exceptions.MyException;
import Duelyst.Model.Account;
import Duelyst.Model.Battle.*;
import Duelyst.Model.Card;
import Duelyst.Model.Items.*;
import Duelyst.Model.Warrior;
import Duelyst.Utility.ImageHolder;
import Duelyst.View.ViewClasses.CardForBattle;
import javafx.animation.*;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Timer;
import java.util.TimerTask;

import static Duelyst.View.Constants.*;

public class BattleReplayController {


    public ImageView endTurn_img;

    public Label endTurn_lbl;

    public Label attackPower_lbl;

    public Label healthPoint_lbl;
    public ImageView gameResult_img;
    public Label gameResult_lbl;
    public Pane pause_pane;
    public ImageView pauseArrow_img;
    public ImageView continue_img;
    public ImageView saveGame_img;
    public ImageView quit_img;
    public Pane saveBattleNotification_pane;

    private LinkedHashMap<Flag, ImageView> flagAndImageView = new LinkedHashMap<>();


    @FXML
    Label p1Acc_lbl;
    @FXML
    Label p2Acc_lbl;
    @FXML
    HBox p2Mana_hb;
    @FXML
    HBox p1Mana_hb;

    @FXML
    Label upLeft;
    @FXML
    Label upRight;

    @FXML
    Label downLeft;
    @FXML
    Label downRight;

    @FXML
    AnchorPane anchorPane;

    @FXML
    StackPane stackPane;

    @FXML
    ImageView speed2x_iv;
    @FXML
    ImageView speed1x_iv;
    @FXML
    ImageView speed05x_iv;

    private boolean isTimerRunning = false;


    private double heightOfPoly_Y;
    private double heightOfPoly_X;
    private double width;
    private BattleSpeed battleSpeed = BattleSpeed.NORMAL;

    private boolean isAnimationRunning = false;

    private ArrayList<CardForBattle> hand = new ArrayList<>();
    private ArrayList<ReplayCardOnField> cardsOnField = new ArrayList<>();

    private Polygon[][] rectangles = new Polygon[BATTLE_ROWS][BATTLE_COLUMNS];

    int lastBattleRecordPlayed = 0;
    public ArrayList<BattleRecord> battleRecords;

    @FXML
    public void initialize() {
        speed2x_iv.setImage(ImageHolder.findImageInImageHolders("../res/ui/upSpeed.png"));
        speed05x_iv.setImage(ImageHolder.findImageInImageHolders("../res/ui/downSpeed.png"));
        speed1x_iv.setImage(ImageHolder.findImageInImageHolders("../res/ui/normalSpeed_selected.png"));

//        BackgroundImage backgroundImage = new BackgroundImage(endTurnBtn , BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT , BackgroundSize.DEFAULT);
//        end_turn_btn.setBackground(new Background( backgroundImage));
    }

    public void handle2Speed() {
        setBattleSpeed(BattleSpeed.DOUBLE);
        speed2x_iv.setImage(ImageHolder.findImageInImageHolders("../res/ui/upSpeed_selected.png"));
        speed05x_iv.setImage(ImageHolder.findImageInImageHolders("../res/ui/downSpeed.png"));
        speed1x_iv.setImage(ImageHolder.findImageInImageHolders("../res/ui/normalSpeed.png"));

    }

    public void handle1Speed() {
        setBattleSpeed(BattleSpeed.NORMAL);
        speed1x_iv.setImage(ImageHolder.findImageInImageHolders("../res/ui/normalSpeed_selected.png"));
        speed05x_iv.setImage(ImageHolder.findImageInImageHolders("../res/ui/downSpeed.png"));
        speed2x_iv.setImage(ImageHolder.findImageInImageHolders("../res/ui/upSpeed.png"));

    }

    public void handle05Speed() {
        setBattleSpeed(BattleSpeed.HALF);
        speed05x_iv.setImage(ImageHolder.findImageInImageHolders("../res/ui/downSpeed_selected.png"));
        speed2x_iv.setImage(ImageHolder.findImageInImageHolders("../res/ui/upSpeed.png"));
        speed1x_iv.setImage(ImageHolder.findImageInImageHolders("../res/ui/normalSpeed.png"));

    }

    private Timeline slowTimeline = new Timeline();
    private Timeline fastTimeLine = new Timeline();
    private Timeline animationTimeLine = new Timeline();

    void runTimelines() {
        runSlowTimeline();
        runVeryFastTimeLine();
        runAnimationTimeline();
    }


    private void runAnimationTimeline() {
        animationTimeLine = new Timeline(new KeyFrame(Duration.ZERO, event ->
        {
            if (!isAnimationRunning) {
                checkBattleRecord();
            }

        }), new KeyFrame(Duration.millis(50)));
        animationTimeLine.setCycleCount(Animation.INDEFINITE);
        animationTimeLine.play();
    }

    private void checkBattleRecord() {
        isAnimationRunning = true;

        if (lastBattleRecordPlayed >= battleRecords.size() || lastBattleRecordPlayed == -1) {
            isAnimationRunning = false;

            if (!isTimerRunning) {
                TimerTask timerTask = new TimerTask() {
                    @Override
                    public void run() {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                stopTimeline();
                                Container.handleBack();
                            }
                        });

                    }
                };
                Timer timer = new Timer();
                timer.schedule(timerTask, 5000);
                isTimerRunning = true;
            }


            return;
        } else {
            ArrayList<BattleRecord> battleRecords = this.battleRecords;
            int i = lastBattleRecordPlayed;
            BattleRecord battleRecord = battleRecords.get(i);
            lastBattleRecordPlayed++;

            switch (battleRecord.getTypeOfRecord()) {
                case MOVE: {

                    moveAnimationFromRecord(battleRecord);
                }
                break;
                case END_TURN: {

                    endTurnAnimation(battleRecord);
                    isAnimationRunning = false;
                }
                break;
                case INITIALIZE: {
                    initializeFromBattleRecord(battleRecord);
                    isAnimationRunning = false;
                }
                break;
                case ATTACK: {
                    handleAttackAnimationOfRecord(battleRecord);
                }
                break;
                case DEATH: {
                    System.out.println("DEATH RECORD");
                    deathAnimationFromRecord(battleRecord);
                }
                break;
                case END_GAME: {
                    gameResultAnimationFromRecord(battleRecord);
                }
                break;
                case INSERT: {
                    insertAnimation(battleRecord);
                }
                break;
                case INSERT_FLAG: {
                    insertFlagAniamtion(battleRecord);
                }
                break;
                case INSERT_ITEM: {
                    insertItemFromRecordAnimation(battleRecord);
                }
                break;
                case MANA_CHANGE: {
                    updateManaFromBattleRecord(battleRecord);
                    isAnimationRunning = false;
                }
            }
        }

    }

    private void initializeFromBattleRecord(BattleRecord battleRecord) {
        this.makeGrids();
        makeAccountNames(battleRecord);
    }

    private void insertItemFromRecordAnimation(BattleRecord battleRecord) {
        Item insertItem = battleRecord.getInsertItem();

        int row = battleRecord.getInsertItemRow();
        int column = battleRecord.getInsertItemColumn();

        try {

            Polygon polygon = rectangles[row][column];


            ReplayCardOnField cardOnField = new ReplayCardOnField();
            cardsOnField.add(cardOnField);
            cardOnField.setCard(insertItem);
            sendIdleImageViewToCenterOfCell(cardOnField, polygon);

        } catch (MyException e) {
            Container.exceptionGenerator(e, stackPane);
        } finally {
            isAnimationRunning = false;
        }


    }


    private void endTurnAnimation(BattleRecord battleRecord) {
        isAnimationRunning = false;
    }

    private void insertFlagAniamtion(BattleRecord battleRecord) {
        int row = battleRecord.getInsertFlagRow();
        int column = battleRecord.getInsertFlagColumn();
        Flag flag = battleRecord.getInsertFlagItself();

        Polygon polygon = rectangles[row][column];

        double x = calculateMidXFromPoint(polygon.getPoints());
        double y = calculateMidYFromPoint(polygon.getPoints());

        ImageView imageView = new ImageView(ImageHolder.findImageInImageHolders(Flag.getImage()));

        imageView.relocate(x, y);


        flagAndImageView.put(flag, imageView);

        anchorPane.getChildren().add(imageView);

        FadeTransition fadeTransition = new FadeTransition(Duration.millis(40 / getBattleSpeed().getSpeedFactor()), imageView);
        fadeTransition.setFromValue(0.3);
        fadeTransition.setToValue(1.0);

        fadeTransition.setOnFinished(event -> {
            isAnimationRunning = false;
        });
        fadeTransition.play();

    }

    private void deathAnimationFromRecord(BattleRecord battleRecord) {
        Warrior deathWarrior = battleRecord.getDeathWarrior();
        ReplayCardOnField cardOnField = ReplayCardOnField.getReplayCardOnField(deathWarrior);
        if (cardOnField != null) {

            cardOnField.getImageView().setImage(ImageHolder.findImageInImageHolders(cardOnField.getCard().getAddressOfDeathGif()));

            TranslateTransition tt = new TranslateTransition(Duration.millis(2000 / getBattleSpeed().getSpeedFactor()), cardOnField.getImageView());
            tt.setOnFinished(event -> {

                anchorPane.getChildren().remove(cardOnField.getImageView());
                isAnimationRunning = false;
            });
            tt.play();


        }

    }

    private void moveAnimationFromRecord(BattleRecord battleRecord) {
        String moveCardId = battleRecord.getMoveCardId();

        Warrior warrior = null;
        warrior = ((Warrior) battleRecord.getMoveCard());
        if (warrior == null) {
            return;
        }
        int[] coordinate = new int[2];
        coordinate[0] = battleRecord.getMoveRow();
        coordinate[1] = battleRecord.getMoveColumn();

        ReplayCardOnField cardOnField = ReplayCardOnField.findReplayCardOnFieldFromArrayList(cardsOnField, warrior);
        moveSound();
        Integer srcRow = battleRecord.getFromRow();
        Integer srcCol = battleRecord.getFromColumn();
        Polygon srcPolygon = rectangles[srcRow][srcCol];
        ObservableList<Double> srcPoints = srcPolygon.getPoints();
        double srcx = calculateMidXFromPoint(srcPoints);
        double srcy = calculateMidYFromPoint(srcPoints);

        Polygon destPolygon = rectangles[coordinate[0]][coordinate[1]];
        ObservableList<Double> destPoints = destPolygon.getPoints();
        double x = calculateMidXFromPoint(destPoints);
        double y = calculateMidYFromPoint(destPoints);

        if (battleRecord.getMoveItem() != null) {
            deleteItemImage(battleRecord.getMoveItem());
        }
        if (battleRecord.getFlag() != null) {
            removeFlagImage(battleRecord.getFlag());
        }


        anchorPane.getChildren().remove(cardOnField.getImageView());

        cardOnField.setImageView(new ImageView(ImageHolder.findImageInImageHolders(warrior.getAddressOfRunGif())));

        anchorPane.getChildren().add(cardOnField.getImageView());
        TranslateTransition tt = new TranslateTransition(Duration.millis(2000 / getBattleSpeed().getSpeedFactor()), cardOnField.getImageView());
        tt.setFromX(srcx);
        tt.setFromY(srcy);
        tt.setToX(x);
        tt.setToY(y);
        tt.setOnFinished(event1 -> {
            cardOnField.getImageView().setImage(new Image(cardOnField.getCard().getAddressOfIdleGif()));
            if (battleRecord.isMoveCollectibleFlag() || battleRecord.isMoveHoldFlag()) {
                removeFlagImage(battleRecord.getFlag());
            }
            if (battleRecord.isMoveCollectibleItem()) {
                deleteItemImage(battleRecord.getMoveItem());
            }
//            getBattle().getGrid()[coordinate[0]][coordinate[1]].setWarrior(((Warrior) cardOnField.getCard()));
            isAnimationRunning = false;

        });
        tt.play();
    }


    private void runSlowTimeline() {
        slowTimeline = new Timeline(new KeyFrame(Duration.ZERO, event -> {
            updateMana();
        }), new KeyFrame(Duration.seconds(1)));
        slowTimeline.setCycleCount(Animation.INDEFINITE);
        slowTimeline.play();
    }

    private void runVeryFastTimeLine() {
        fastTimeLine = new Timeline(new KeyFrame(Duration.ZERO, event -> {
            rotateCardImageViews();
        }), new KeyFrame(Duration.millis(20)));
        fastTimeLine.setCycleCount(Animation.INDEFINITE);
        fastTimeLine.play();
    }


    public void makeGrids() {
        double upLeftX, upLeftY, upRightX, upRightY, downLeftX, downLeftY, downRightX, downRightY;
        upLeftX = upLeft.getLayoutX();
        upRightX = upRight.getLayoutX();
        downLeftX = downLeft.getLayoutX();
        downRightX = downRight.getLayoutX();

        upLeftY = upLeft.getLayoutY();
        upRightY = upRight.getLayoutY();
        downLeftY = downLeft.getLayoutY();
        downRightY = downRight.getLayoutY();

        heightOfPoly_Y = (downLeftY - upLeftY - (BATTLE_ROWS - 1) * HEIGHT_PADDING_Y) / BATTLE_ROWS;
        heightOfPoly_X = ((downLeftX - upLeftX) - ((BATTLE_ROWS - 1) * HEIGHT_PADDING_X)) / BATTLE_ROWS;
        width = (upRightX - upLeftX - (BATTLE_COLUMNS - 1) * WIDTH_PADDING) / BATTLE_COLUMNS;

        System.out.println(heightOfPoly_X);
        System.out.println(heightOfPoly_Y);
        System.out.println(width);
        for (int i = 0; i < BATTLE_ROWS; i++) {
            for (int j = 0; j < BATTLE_COLUMNS; j++) {
                rectangles[i][j] = new Polygon();
                rectangles[i][j].getPoints().addAll(upLeftX + width * j + HEIGHT_PADDING_X + i * heightOfPoly_X + WIDTH_PADDING * j + heightOfPoly_X + width, upLeftY + i * HEIGHT_PADDING_Y + i * heightOfPoly_Y + heightOfPoly_Y,
                        upLeftX + width * j + HEIGHT_PADDING_X + i * heightOfPoly_X + width + WIDTH_PADDING * j, upLeftY + i * HEIGHT_PADDING_Y + i * heightOfPoly_Y,
                        upLeftX + width * j + HEIGHT_PADDING_X + i * heightOfPoly_X + WIDTH_PADDING * j, upLeftY + i * HEIGHT_PADDING_Y + i * heightOfPoly_Y,
                        upLeftX + width * j + HEIGHT_PADDING_X + i * heightOfPoly_X + heightOfPoly_X + WIDTH_PADDING * j, upLeftY + i * HEIGHT_PADDING_Y + i * heightOfPoly_Y + heightOfPoly_Y);
                rectangles[i][j].setStyle("-fx-fill: BLACK;-fx-opacity: 0.5");

                anchorPane.getChildren().addAll(rectangles[i][j]);

                int finalJ = j;
                int finalI = i;

            }
        }
    }


    public void setFlagImage(int i, int j, Flag flag) {

        Polygon polygon = rectangles[i][j];
        ObservableList<Double> points = polygon.getPoints();
        ImageView imageView = new ImageView(new Image(Flag.getImage()));
        double x = calculateMidXFromPoint(points);
        double y = calculateMidYFromPoint(points);
        imageView.relocate(x, y);

        flagAndImageView.put(flag, imageView);
        anchorPane.getChildren().add(imageView);

    }

    public void removeFlagImage(Flag flag) {
        anchorPane.getChildren().remove(flagAndImageView.get(flag));
    }


    public double calculateMidXFromPoint(ObservableList<Double> points) {
        return (points.get(0) + points.get(2) + points.get(4) + points.get(6)) / 4.00 - width - heightOfPoly_X;
    }


    public double calculateMidYFromPoint(ObservableList<Double> points) {
        return (points.get(1) + points.get(3) + points.get(5) + points.get(7)) / 4.00 - width - heightOfPoly_X;
    }


    public void showSpellOnFiled(ReplayCardOnField cardOnField, Polygon polygon) {
        ObservableList<Double> points = polygon.getPoints();
        double x = calculateMidXFromPoint(points);
        double y = calculateMidYFromPoint(points);
        final ImageView imageView2 = new ImageView(ImageHolder.findImageInImageHolders(cardOnField.getCard().getAddressOfIdleGif()));
        anchorPane.getChildren().add(imageView2);
        imageView2.relocate(x, y);
        TranslateTransition tt = new TranslateTransition(Duration.millis(2000 / getBattleSpeed().getSpeedFactor()), imageView2);
        tt.setOnFinished(event -> {
                    anchorPane.getChildren().remove(imageView2);
                    isAnimationRunning = false;
                }

        );

        tt.play();
    }

    private void insertAnimation(BattleRecord battleRecord) {

        int row = battleRecord.getInsertRow();
        int column = battleRecord.getInsertColumn();

        Card card = battleRecord.getInsertCard();
        boolean isSpell = battleRecord.isSpellInsert();

        if (battleRecord.getInsertCardItem() != null) {
            deleteItemImage(battleRecord.getInsertCardItem());
        }
        if (battleRecord.getFlagForInsertCard() != null) {
            removeFlagImage(battleRecord.getFlagForInsertCard());
        }


        int[] battleCoordinate = {row, column};

        if (!isSpell) {
            Polygon polygon = rectangles[battleCoordinate[0]][battleCoordinate[1]];
            double x = calculateMidXFromPoint(polygon.getPoints());
            double y = calculateMidYFromPoint(polygon.getPoints());
            ReplayCardOnField cardOnField = new ReplayCardOnField();
            cardOnField.setCard(card);
            cardsOnField.add(cardOnField);
            sendIdleImageViewToCenterOfCell(cardOnField, polygon);
            if (battleRecord.isFlag()) {
                removeFlagImage(battleRecord.getFlag());
            }
            FadeTransition fadeTransition = new FadeTransition(Duration.millis(500 / getBattleSpeed().getSpeedFactor()), cardOnField.getImageView());
            fadeTransition.setFromValue(0.1);
            fadeTransition.setToValue(1.0);
            fadeTransition.setOnFinished(event -> {
                isAnimationRunning = false;
            });
            fadeTransition.play();

        } else {
            ReplayCardOnField cardOnField = new ReplayCardOnField();
            cardOnField.setCard(card);

            Polygon polygon = rectangles[battleCoordinate[0]][battleCoordinate[1]];
            showSpellOnFiled(cardOnField, polygon);
        }

    }


    private boolean isValidAttack(Cell targetCell, Cell sourceCell) {

        switch (sourceCell.getWarrior().getAttackKind()) {
            case MELEE:
                System.out.println("Meleeeee");
                return isValidMeleeAttack(targetCell, sourceCell);
            case RANGED:
                System.out.println("Rangedddddddd");
                return isValidRangedAttack(sourceCell, targetCell, sourceCell.getWarrior());
            case HYBRID:
                System.out.println("Hybridddddddddd");
                boolean flag1 = isValidMeleeAttack(targetCell, sourceCell);
                boolean flag2 = isValidRangedAttack(sourceCell, targetCell, sourceCell.getWarrior());
                return (flag1 || flag2);
        }
        return true;
    }

    private boolean isValidRangedAttack(Cell sourceCell, Cell targetCell, Warrior warrior) {

        System.out.println(sourceCell.getRow() + "    " + sourceCell.getColumn());
        System.out.println(targetCell.getRow() + "    " + targetCell.getColumn());
        System.out.println();
        if (Cell.calculateManhattanDistance(targetCell, sourceCell) <= 1) {

            System.out.println(":DDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD");
            return false;
        } else {
            System.out.println("==>> " + warrior.getCardName() + " - " + warrior.getAttackRange() + " " + (Cell.calculateManhattanDistance(targetCell, targetCell) <= warrior.getAttackRange()));
            return Cell.calculateManhattanDistance(sourceCell, targetCell) <= warrior.getAttackRange();
        }
    }

    private boolean isValidMeleeAttack(Cell targetCell, Cell sourceCell) {
        return Cell.calculateManhattanDistance(targetCell, sourceCell) <= 1;
    }

    public void handleAttackAnimationOfRecord(BattleRecord battleRecord) {
        isAnimationRunning = true;
        Warrior attacker = battleRecord.getAttacker();
        Warrior attacked = battleRecord.getAttacked();

        ReplayCardOnField cardOnFieldAttacker = ReplayCardOnField.findReplayCardOnFieldFromArrayList(cardsOnField, attacker);
        ReplayCardOnField cardOnFieldAttacked = ReplayCardOnField.findReplayCardOnFieldFromArrayList(cardsOnField, attacked);

        attackSound();
        cardOnFieldAttacker.getImageView().setImage(ImageHolder.findImageInImageHolders(cardOnFieldAttacker.getCard().getAddressOfAttackGif()));
        cardOnFieldAttacked.getImageView().setImage(ImageHolder.findImageInImageHolders(cardOnFieldAttacked.getCard().getAddressOfGetDamageGif()));

        TranslateTransition tt1 = new TranslateTransition(Duration.millis(2000 / getBattleSpeed().getSpeedFactor()), cardOnFieldAttacker.getImageView());
        tt1.setOnFinished(event -> {
            cardOnFieldAttacker.getImageView().setImage(ImageHolder.findImageInImageHolders(cardOnFieldAttacker.getCard().getAddressOfIdleGif()));
        });

        TranslateTransition tt2 = new TranslateTransition(Duration.millis(2000 / getBattleSpeed().getSpeedFactor()), cardOnFieldAttacked.getImageView());
        tt2.setOnFinished(event -> {
            cardOnFieldAttacked.getImageView().setImage(ImageHolder.findImageInImageHolders(cardOnFieldAttacked.getCard().getAddressOfIdleGif()));
        });

        ParallelTransition parallelTransition = new ParallelTransition(tt1, tt2);

        if (battleRecord.isHasBuff()) {
            isAnimationRunning = true;
            ImageView imageViewOfBuff = new ImageView(buffEffect);
            anchorPane.getChildren().add(imageViewOfBuff);
            imageViewOfBuff.relocate(cardOnFieldAttacked.getImageView().getLayoutX(), cardOnFieldAttacked.getImageView().getLayoutY());
            TranslateTransition effectTransition = new TranslateTransition(Duration.millis(500 / getBattleSpeed().getSpeedFactor()), imageViewOfBuff);
            effectTransition.setOnFinished(event2 -> {
                anchorPane.getChildren().remove(imageViewOfBuff);
            });
            parallelTransition.getChildren().add(effectTransition);
        }

        parallelTransition.setOnFinished(event -> {
            isAnimationRunning = false;
        });

        parallelTransition.play();

    }


    public void sendIdleImageViewToCenterOfCell(ReplayCardOnField cardOnField, Polygon polygon) {
        ObservableList<Double> points = polygon.getPoints();
        cardOnField.setImageView(new ImageView(ImageHolder.findImageInImageHolders(cardOnField.getCard().getAddressOfIdleGif())));
        double x = calculateMidXFromPoint(points);
        double y = calculateMidYFromPoint(points);
        cardOnField.getImageView().relocate(x, y);
        anchorPane.getChildren().add(cardOnField.getImageView());
    }

    public void removeImageViewFromCell(Card card) {

        ReplayCardOnField cardOnField = ReplayCardOnField.getReplayCardOnField(card);
        if (cardOnField != null) {
            anchorPane.getChildren().remove(cardOnField.getImageView());
        }
    }

    public void handleGraveYardButton() {

        Pane root = null;
        FXMLLoader fxmlLoader = null;
        try {
            fxmlLoader = new FXMLLoader(getClass().getResource("../View/FXMLFiles/GraveYard.fxml"));
            root = fxmlLoader.load();
            int i = 0;
            System.out.println(i);
        } catch (IOException e) {
            e.printStackTrace();
        }
        stopTimeline();
        Container.addController(fxmlLoader);
        Container.runNextScene(root, GRAVE_YARD);

    }

    public void stopTimeline() {
        slowTimeline.stop();
        fastTimeLine.stop();
        animationTimeLine.stop();
    }


    public int[] findPolygonCoordinate(Polygon polygon) {
        int[] result = new int[]{-1, -1};
        for (int i = 0; i < BATTLE_ROWS; i++) {
            for (int j = 0; j < BATTLE_COLUMNS; j++) {
                if (rectangles[i][j].equals(polygon)) {
                    result[0] = i;
                    result[1] = j;
                    return result;
                }
            }

        }
        return result;
    }

    public void updateManaFromBattleRecord(BattleRecord battleRecord) {
        int turn = battleRecord.getNumberOfTurn();
        if (turn % 2 == 1) {
            updateActiveMana(p1Mana_hb, battleRecord.getManaMax(), battleRecord.getPlayer1Mana(), true);
            updateActiveMana(p2Mana_hb, battleRecord.getManaMax(), battleRecord.getPlayer2Mana(), false);
        } else {
            updateActiveMana(p2Mana_hb, battleRecord.getManaMax(), battleRecord.getPlayer2Mana(), true);
            updateActiveMana(p1Mana_hb, battleRecord.getManaMax(), battleRecord.getPlayer1Mana(), false);
        }

    }

    public void updateActiveMana(HBox hBox, int maximumMana, int mana, boolean isPlayingUser) {
        hBox.getChildren().clear();
        int countOfActiveMana = 0;
        if (isPlayingUser) {
            for (int i = 0; i < maximumMana; i++) {
                ImageView imageView = null;
                if (countOfActiveMana < mana) {
                    imageView = new ImageView(manaIconSml);
                    countOfActiveMana++;
                } else {
                    imageView = new ImageView(manaInActiveSml);
                }
                hBox.getChildren().add(imageView);
            }
        } else {
            ImageView imageView = new ImageView(manaInActiveSml);
            hBox.getChildren().add(imageView);
        }
        Label label = new Label();
        String text = mana + " / " + maximumMana;
        label.setText(text);
        label.setFont(Font.font("System", FontWeight.BOLD, 20));
        label.setStyle("-fx-text-fill: RED");
        hBox.getChildren().add(label);
    }

//    public void updateManaOfPlayer(HBox hbox, Player player) {
//        System.out.println(getBattle().getTurn() + " " + getBattle().getPlayingPlayer().getAccount().getUsername());
//        hbox.getChildren().clear();
//        int countOfActiveMana = 0;
//        if (player.equals(battle.getPlayingPlayer())) {
//            for (int i = 0; i < getBattle().calculateMaxAmountOfMana(); i++) {
//                ImageView imageView = null;
//                if (countOfActiveMana < player.getMana()) {
//                    imageView = new ImageView(manaIconSml);
//                    countOfActiveMana++;
//                } else {
//                    imageView = new ImageView(manaInActiveSml);
//                }
//                hbox.getChildren().add(imageView);
//            }
//        } else {
//            ImageView imageView = new ImageView(manaInActiveSml);
//            hbox.getChildren().add(imageView);
//        }
//        Label label = new Label();
//        String text = player.getMana() + "/" + battle.calculateMaxAmountOfMana();
//        label.setText(text);
//        label.setFont(Font.font("System", FontWeight.BOLD, 20));
//        label.setStyle("-fx-text-fill: RED");
//        hbox.getChildren().add(label);
//    }


    public void makeAccountNames(BattleRecord battleRecord) {
        p1Acc_lbl.setText(battleRecord.getFirstPlayerUsername());
        p2Acc_lbl.setText(battleRecord.getSecondPlayerUsername());

    }

    public void updateMana() {
//        updateManaOfPlayer(p2Mana_hb, battle.getPlayer2());
//        updateManaOfPlayer(p1Mana_hb, battle.getPlayer1());

    }

    public void rotateCardImageViews() {
        for (int i = 0; i < getHand().size(); i++) {
            CardForBattle cardForBattle = getHand().get(i);
            if (cardForBattle != null) {
                cardForBattle.getCardController().getCardSelection_iv().setRotate(cardForBattle.getCardController().getCardSelection_iv().getRotate() + 0.5);
            }
        }
    }


    public void endTurnButtonGlow() {
        endTurn_img.setImage(new Image("res/ui/button_end_turn_mine_glow.png"));//TODO Az Image Holder Estefade Nakardam
    }

    public void endTurnButtonGlowDisappear() {
        endTurn_img.setImage(new Image("res/ui/button_end_turn_mine.png"));//TODO Az Image Holder Estefade Nakardam
    }


    public ArrayList<CardForBattle> getHand() {
        return hand;
    }


    private void gameResultAnimationFromRecord(BattleRecord battleRecord) {

        if (!battleRecord.isDraw()) {
            if (!battleRecord.getWinnerUsername().equals(Account.getLoggedAccount().getUsername())) {
                gameResult_img.setImage(new Image("res/ui/notification_enemy_turn@2x.png"));
                gameResult_lbl.setText("You Loose");
                loseSound();
            } else if (battleRecord.getWinnerUsername().equals(Account.getLoggedAccount().getUsername())) {
                gameResult_img.setImage(new Image("res/ui/notification_go@2x.png"));
                gameResult_lbl.setText("You Win");
                victorySound();
            }
        } else {
            gameResult_img.setImage(new Image("res/ui/notification_go@2x.png"));
            gameResult_lbl.setText("Draw!");
            loseSound();
        }
        anchorPane.getChildren().remove(gameResult_img);
        anchorPane.getChildren().remove(gameResult_lbl);
        anchorPane.getChildren().add(gameResult_img);
        anchorPane.getChildren().add(gameResult_lbl);
        FadeTransition ft = new FadeTransition(Duration.millis(5000), gameResult_img);
        FadeTransition ft2 = new FadeTransition(Duration.millis(5000), gameResult_lbl);
        ft.setOnFinished(event -> {
            if (Container.scenes.size() > 0) {
                isAnimationRunning = false;
                stopTimeline();
                Container.handleBack();
            }
        });
        ft.setFromValue(0);
        ft.setToValue(100);
        ft2.setFromValue(0);
        ft2.setToValue(100);
        ft.setCycleCount(2);
        ft2.setCycleCount(2);
        ft.setAutoReverse(true);
        ft2.setAutoReverse(true);

        ft.play();
        ft2.play();
    }


    public void handlePauseArrowImageMouseEntered() {
        pauseArrow_img.setOpacity(1);
    }

    public void handlePauseArrowImageMouseExited() {
        pauseArrow_img.setOpacity(0.5);
    }

    public void handlePauseArrowImageClicked() {
        TranslateTransition tt = new TranslateTransition(Duration.millis(1000), pause_pane);
        anchorPane.setOpacity(0.7);
        anchorPane.setDisable(true);
        tt.setFromY(910);
        tt.setToY(300);
        tt.setRate(1);
        tt.play();
    }

    public void handleContinueImage() {
        TranslateTransition tt = new TranslateTransition(Duration.millis(1000), pause_pane);
        tt.setFromY(300);
        tt.setToY(910);
        tt.setRate(1);
        tt.setOnFinished(event -> {
            anchorPane.setDisable(false);
            anchorPane.setOpacity(1);
        });
        tt.play();
    }

    public void handleSaveGameImg() {
//        Battle battle = Battle.deepClone(Battle.getRunningBattle());
//        Battle.getUnfinishedBattles().add(battle);
        TranslateTransition tt = new TranslateTransition(Duration.millis(2000), saveBattleNotification_pane);
        tt.setFromX(900);
        tt.setToX(600);
        tt.setCycleCount(2);
        tt.setAutoReverse(true);
        tt.play();

    }

    public void handleQuitImg() {
        if (Container.scenes.size() > 0) {
            stopTimeline();
            Container.handleBack();
        }
    }

    public void continueImageGlow() {
        continue_img.setImage(new Image("res/ui/button_primary_middle_glow@2x.png"));
    }

    public void continueImageGlowDisapear() {
        continue_img.setImage(new Image("res/ui/button_primary_middle@2x.png"));
    }

    public void saveGameImageGlow() {
        saveGame_img.setImage(new Image("res/ui/button_primary_middle_glow@2x.png"));
    }

    public void saveGameImageGlowDisapear() {
        saveGame_img.setImage(new Image("res/ui/button_primary_middle@2x.png"));
    }

    public void quitImageGlow() {
        quit_img.setImage(new Image("res/ui/button_primary_middle_glow@2x.png"));
    }

    public void quitImageGlowDisapear() {
        quit_img.setImage(new Image("res/ui/button_primary_middle@2x.png"));
    }


    public void deleteItemImage(Item item) {
        ReplayCardOnField cardOnField = ReplayCardOnField.getReplayCardOnField(item);
        anchorPane.getChildren().remove(cardOnField.getImageView());
    }


    public void moveSound() {
        playSound(MOVE_SOUND);
    }

    public void loseSound() {
        playSound(LOSE_SOUND);
    }

    private void attackSound() {
        playSound(ATTACK_SOUND);
    }

    public void victorySound() {
        playSound(VICTORY_SOUND);
    }

    public void playSound(String address) {
        File file = new File(address);
        Media media = new Media(file.toURI().toString());
        if (address.equals(VICTORY_SOUND) || address.equals(LOSE_SOUND)) {
            Container.runMediaPlayer(Container.soundPlayer, media, 1, true, 1, SOUND_PLAYER);
        } else {
            Container.runMediaPlayer(Container.soundPlayer, media, 1, true, 1, SOUND_PLAYER, getBattleSpeed().getSpeedFactor());
        }
    }

    public BattleSpeed getBattleSpeed() {
        return battleSpeed;
    }

    public void setBattleSpeed(BattleSpeed battleSpeed) {
        this.battleSpeed = battleSpeed;
    }
}

class ReplayCardOnField {

    private static ArrayList<ReplayCardOnField> allReplayCardOnFields = new ArrayList<>();
    private ImageView imageView;
    private Card card;

    ReplayCardOnField() {
        allReplayCardOnFields.add(this);
    }


    ImageView getImageView() {
        return imageView;
    }

    void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    static ReplayCardOnField findReplayCardOnFieldFromArrayList(ArrayList<ReplayCardOnField> arrayList, Card card) {
        if (card != null && arrayList != null && arrayList.size() > 0) {
            for (ReplayCardOnField cardOnField : arrayList) {
                if (cardOnField.getCard().equals(card)) {
                    return cardOnField;
                }
            }
        }
        return null;
    }

    static ReplayCardOnField getReplayCardOnField(Card card) {
        for (ReplayCardOnField allReplayCardOnField : allReplayCardOnFields) {
            if (allReplayCardOnField.getCard().equals(card))
                return allReplayCardOnField;
        }
        return null;
    }


    public static ArrayList<ReplayCardOnField> getAllReplayCardOnFields() {
        return allReplayCardOnFields;
    }


}

