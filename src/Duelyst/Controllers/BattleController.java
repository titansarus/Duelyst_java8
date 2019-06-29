package Duelyst.Controllers;

import Duelyst.Exceptions.MyException;
import Duelyst.Model.Battle.*;
import Duelyst.Model.Card;
import Duelyst.Model.Items.*;
import Duelyst.Model.Warrior;
import Duelyst.Utility.ImageHolder;
import Duelyst.View.ViewClasses.CardForBattle;
import javafx.animation.*;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import static Duelyst.View.Constants.*;

public class BattleController {


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

    @FXML
    HBox hand_hBox;
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


    private double heightOfPoly_Y;
    private double heightOfPoly_X;
    private double width;

    private ArrayList<CardForBattle> hand = new ArrayList<>();
    private ArrayList<CardOnField> cardsOnField = new ArrayList<>();

    private Polygon[][] rectangles = new Polygon[BATTLE_ROWS][BATTLE_COLUMNS];

    @FXML
    public void initialize() {
//        BackgroundImage backgroundImage = new BackgroundImage(endTurnBtn , BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT , BackgroundSize.DEFAULT);
//        end_turn_btn.setBackground(new Background( backgroundImage));
    }

    private Battle battle;
    private Timeline slowTimeline = new Timeline();
    private Timeline fastTimeLine = new Timeline();

    void runTimelines() {
        runSlowTimeline();
        runVeryFastTimeLine();
    }

    private void runSlowTimeline() {
        slowTimeline = new Timeline(new KeyFrame(Duration.ZERO, event -> {
            hand.get(0).getView();
            updateMana();
            emptyCardsOfHand();
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

    private void emptyCardsOfHand() {
        for (int i = 0; i < getHand().size(); i++) {
            if (getHand().get(i) != null && getHand().get(i).getCard() == null) {
                getHand().get(i).getCardController().setImageOfCard(null);
                getHand().get(i).getCardController().setImageOfCardSelection(battleCardNotSelectedImage);
            }
        }
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
                rectangles[i][j].setOnMouseClicked(event -> rectangleOnMouseClicked(event));

                int finalJ = j;
                int finalI = i;
                rectangles[i][j].setOnMouseEntered(event -> rectangleOnMouseEnter(event, finalI, finalJ));


                rectangles[i][j].setOnMouseExited(event -> rectangleOnMouseExited(event, finalI, finalJ));
            }
        }
    }

    public void initFlagImages() {
        for (int i = 0; i < battle.getGrid().length; i++) {
            for (int j = 0; j < battle.getGrid()[i].length; j++) {
                if (battle.getGrid()[i][j].getFlag() != null) {
                    setFlagImage(i, j, battle.getGrid()[i][j].getFlag());
                }
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
        flag.setImageView(imageView);
        anchorPane.getChildren().add(imageView);

    }

    public void removeFlagImage(Flag flag) {
        anchorPane.getChildren().remove(flag.getImageView());
    }


    public double calculateMidXFromPoint(ObservableList<Double> points) {
        return (points.get(0) + points.get(2) + points.get(4) + points.get(6)) / 4.00 - width - heightOfPoly_X;
    }


    public double calculateMidYFromPoint(ObservableList<Double> points) {
        return (points.get(1) + points.get(3) + points.get(5) + points.get(7)) / 4.00 - width - heightOfPoly_X;
    }


    public void rectangleOnMouseClicked(MouseEvent event) {

        Polygon p = (Polygon) event.getSource();
        int[] coordinate = findPolygonCoordinate(p);

        if (getBattle().getSelectedCell() != null && getBattle().getSelectedCell().getWarrior() != null && getBattle().getPlayingPlayer().getInGameCards().contains(getBattle().getSelectedCell().getWarrior())) {
            if (getBattle().getSelectedCell() != getBattle().getGrid()[coordinate[0]][coordinate[1]] && getBattle().getGrid()[coordinate[0]][coordinate[1]].getWarrior() == null) {

                battle.findValidCell(KindOfActionForValidCells.MOVE);
                ArrayList<Cell> cells = battle.getValidCells();
                System.out.println(cells.size());
                for (int i = 0; i < cells.size(); i++) {
                    System.out.println(cells.get(i).getRow() + " <======> " + cells.get(i).getColumn());
                }
//                if (!cells.contains(battle.getGrid()[coordinate[0]][coordinate[1]])) {
//                    //TODO throw exception
//
//                    return;
//                }

                if (Cell.calculateManhattanDistance(getBattle().getSelectedCell(), getBattle().getGrid()[coordinate[0]][coordinate[1]]) <= 2) {
                    if (getBattle().getPlayingPlayer().checkIfCardIsInGame(getBattle().getSelectedCell().getWarrior()) && getBattle().getSelectedCell().getWarrior().isValidToMove()) {
                        moveAnimationRun(coordinate);
                    }
                }
                return;
            } else if (getBattle().getSelectedCell() != getBattle().getGrid()[coordinate[0]][coordinate[1]] && getBattle().getGrid()[coordinate[0]][coordinate[1]].getWarrior() != null) {
                //TODO ADD CHECK OF RANGE AND MELEE OR HYBRID

                if (getBattle().getPlayingPlayer().checkIfCardIsInGame(getBattle().getGrid()[coordinate[0]][coordinate[1]].getWarrior())) {
                    System.out.println("YOUR CARD!!!");
                    getBattle().setSelectedCell(null);
                    getBattle().setSelectedCard(null);
                } else if (getBattle().getSelectedCell().getWarrior().isValidToAttack()) {
                    if (!isValidAttack(getBattle().getSelectedCell(), getBattle().getGrid()[coordinate[0]][coordinate[1]])) {
                        System.out.println("natoonne bazene!!!");
                        return;
                    }
                    System.out.println("Attack");
                    handleAttackAnimation(coordinate);
                    getBattle().setSelectedCell(null);
                    getBattle().setSelectedCard(null);

                }

                return;
            }
        }


        getBattle().setSelectedCell(getBattle().getGrid()[coordinate[0]][coordinate[1]]);

        if (getBattle().getSelectedCard() != null) {//TODO SOME MORE CHECKS NEEDED
            handleInsertCardClick(getBattle().getSelectedCell());

            return;
        }
    }

    private boolean isValidAttack(Cell targetCell, Cell sourceCell) {

        switch (sourceCell.getWarrior().getAttackKind()) {
            case MELEE:
                return isValidMeleeAttack(targetCell, sourceCell);
            case RANGED:
                return isValidRangedAttack(sourceCell, targetCell, sourceCell.getWarrior());
            case HYBRID:
                boolean flag1 = isValidMeleeAttack(targetCell, sourceCell);
                boolean flag2 = isValidRangedAttack(sourceCell, targetCell, sourceCell.getWarrior());
                return (flag1 || flag2);
        }
        return true;
    }

    private boolean isValidRangedAttack(Cell sourceCell, Cell targetCell, Warrior warrior) {

        if (Cell.calculateManhattanDistance(targetCell, sourceCell) <= 1) {
            return false;
        } else {
            System.out.println("==>> " + warrior.getCardName() + " - " + warrior.getAttackRange() + " " + (Cell.calculateManhattanDistance(targetCell, targetCell) <= warrior.getAttackRange()));
            return Cell.calculateManhattanDistance(sourceCell, targetCell) <= warrior.getAttackRange();
        }
    }

    private boolean isValidMeleeAttack(Cell targetCell, Cell sourceCell) {
        return Cell.calculateManhattanDistance(targetCell, sourceCell) <= 1;
    }


    public void handleAttackAnimation(int[] coordinate) {
        getBattle().setAttackedCard(getBattle().getGrid()[coordinate[0]][coordinate[1]].getWarrior());
        CardOnField cardOnFieldAttacker = CardOnField.findCardOnFieldFromArrayList(cardsOnField, getBattle().getSelectedCell().getWarrior());
        CardOnField cardOnFieldAttacked = CardOnField.findCardOnFieldFromArrayList(cardsOnField, getBattle().getAttackedCard());
        int resultOfattack = getBattle().attack(((Warrior) cardOnFieldAttacker.getCard()), ((Warrior) cardOnFieldAttacked.getCard()), false);
        boolean animationEnded = false;

        cardOnFieldAttacker.getImageView().setImage(ImageHolder.findImageInImageHolders(cardOnFieldAttacker.getCard().getAddressOfAttackGif()));

        TranslateTransition tt1 = new TranslateTransition(Duration.millis(2000), cardOnFieldAttacker.getImageView());
        tt1.setOnFinished(event1 -> {
            cardOnFieldAttacker.getImageView().setImage(ImageHolder.findImageInImageHolders(cardOnFieldAttacker.getCard().getAddressOfIdleGif()));
        });
        cardOnFieldAttacked.getImageView().setImage(ImageHolder.findImageInImageHolders(cardOnFieldAttacked.getCard().getAddressOfGetDamageGif()));
        TranslateTransition tt2 = new TranslateTransition(Duration.millis(2000), cardOnFieldAttacked.getImageView());
        tt2.setOnFinished(event1 -> {
            cardOnFieldAttacked.getImageView().setImage(ImageHolder.findImageInImageHolders(cardOnFieldAttacked.getCard().getAddressOfIdleGif()));

        });

        ParallelTransition parallelTransition = new ParallelTransition(tt1, tt2);
        if (resultOfattack == Battle.VALID_COUNTER_WITH_BUFF || resultOfattack == Battle.INVALID_COUNTER_WITH_BUFF) {
            ImageView imageView = new ImageView(buffEffect);
            anchorPane.getChildren().add(imageView);
            imageView.relocate(cardOnFieldAttacked.getImageView().getLayoutX(), cardOnFieldAttacked.getImageView().getLayoutY());
            TranslateTransition effectTransition = new TranslateTransition(Duration.millis(500), imageView);
            effectTransition.setOnFinished(event2 -> {
                anchorPane.getChildren().remove(imageView);
            });
            parallelTransition.getChildren().add(effectTransition);
        }
        parallelTransition.setOnFinished(event1 -> {
            if (resultOfattack == Battle.VALID_COUNTER_WITH_BUFF || resultOfattack == Battle.VALID_COUNTER_WITHOUT_BUFF) {
                int newResultOfAttack = getBattle().attack(((Warrior) cardOnFieldAttacked.getCard()), ((Warrior) cardOnFieldAttacker.getCard()), true);
                cardOnFieldAttacked.getImageView().setImage(ImageHolder.findImageInImageHolders(cardOnFieldAttacked.getCard().getAddressOfAttackGif()));
                TranslateTransition tt3 = new TranslateTransition(Duration.millis(2000), cardOnFieldAttacked.getImageView());
                tt3.setOnFinished(event2 -> {
                    cardOnFieldAttacked.getImageView().setImage(ImageHolder.findImageInImageHolders(cardOnFieldAttacked.getCard().getAddressOfIdleGif()));
                });
                cardOnFieldAttacker.getImageView().setImage(ImageHolder.findImageInImageHolders(cardOnFieldAttacker.getCard().getAddressOfGetDamageGif()));
                TranslateTransition tt4 = new TranslateTransition(Duration.millis(2000), cardOnFieldAttacker.getImageView());
                tt4.setOnFinished(event2 -> {
                    cardOnFieldAttacker.getImageView().setImage(ImageHolder.findImageInImageHolders(cardOnFieldAttacker.getCard().getAddressOfIdleGif()));

                });
                ParallelTransition parallelTransition2 = new ParallelTransition(tt3, tt4);
                if (newResultOfAttack == Battle.VALID_COUNTER_WITH_BUFF || newResultOfAttack == Battle.INVALID_COUNTER_WITH_BUFF) {
                    ImageView imageView = new ImageView(buffEffect);
                    anchorPane.getChildren().add(imageView);
                    imageView.relocate(cardOnFieldAttacker.getImageView().getLayoutX(), cardOnFieldAttacker.getImageView().getLayoutY());
                    TranslateTransition effectTransition2 = new TranslateTransition(Duration.millis(500), imageView);
                    effectTransition2.setOnFinished(event2 -> {
                        anchorPane.getChildren().remove(imageView);
                    });
                    parallelTransition2.getChildren().add(effectTransition2);
                }

                parallelTransition2.play();
            }
        });

        battle.deleteDeathCardsFromMap(); // Check For Death Cards

        parallelTransition.play();

    }

    public void handleAttackFromAi(Warrior attacker, Warrior attacked) {
        CardOnField cardOnFieldAttacker = CardOnField.findCardOnFieldFromArrayList(cardsOnField, attacker);
        CardOnField cardOnFieldAttacked = CardOnField.findCardOnFieldFromArrayList(cardsOnField, attacked);
        int resultOfattack = getBattle().attack(((Warrior) cardOnFieldAttacker.getCard()), ((Warrior) cardOnFieldAttacked.getCard()), false);
        boolean animationEnded = false;

        cardOnFieldAttacker.getImageView().setImage(ImageHolder.findImageInImageHolders(cardOnFieldAttacker.getCard().getAddressOfAttackGif()));

        TranslateTransition tt1 = new TranslateTransition(Duration.millis(2000), cardOnFieldAttacker.getImageView());
        tt1.setOnFinished(event1 -> {
            cardOnFieldAttacker.getImageView().setImage(ImageHolder.findImageInImageHolders(cardOnFieldAttacker.getCard().getAddressOfIdleGif()));
        });
        cardOnFieldAttacked.getImageView().setImage(ImageHolder.findImageInImageHolders(cardOnFieldAttacked.getCard().getAddressOfGetDamageGif()));
        TranslateTransition tt2 = new TranslateTransition(Duration.millis(2000), cardOnFieldAttacked.getImageView());
        tt2.setOnFinished(event1 -> {
            cardOnFieldAttacked.getImageView().setImage(ImageHolder.findImageInImageHolders(cardOnFieldAttacked.getCard().getAddressOfIdleGif()));

        });

        ParallelTransition parallelTransition = new ParallelTransition(tt1, tt2);
        if (resultOfattack == Battle.VALID_COUNTER_WITH_BUFF || resultOfattack == Battle.INVALID_COUNTER_WITH_BUFF) {
            ImageView imageView = new ImageView(buffEffect);
            anchorPane.getChildren().add(imageView);
            imageView.relocate(cardOnFieldAttacked.getImageView().getLayoutX(), cardOnFieldAttacked.getImageView().getLayoutY());
            TranslateTransition effectTransition = new TranslateTransition(Duration.millis(500), imageView);
            effectTransition.setOnFinished(event2 -> {
                anchorPane.getChildren().remove(imageView);
            });
            parallelTransition.getChildren().add(effectTransition);
        }
        parallelTransition.setOnFinished(event1 -> {
            if (resultOfattack == Battle.VALID_COUNTER_WITH_BUFF || resultOfattack == Battle.VALID_COUNTER_WITHOUT_BUFF) {
                int newResultOfAttack = getBattle().attack(((Warrior) cardOnFieldAttacked.getCard()), ((Warrior) cardOnFieldAttacker.getCard()), true);
                cardOnFieldAttacked.getImageView().setImage(ImageHolder.findImageInImageHolders(cardOnFieldAttacked.getCard().getAddressOfAttackGif()));
                TranslateTransition tt3 = new TranslateTransition(Duration.millis(2000), cardOnFieldAttacked.getImageView());
                tt3.setOnFinished(event2 -> {
                    cardOnFieldAttacked.getImageView().setImage(ImageHolder.findImageInImageHolders(cardOnFieldAttacked.getCard().getAddressOfIdleGif()));
                });
                cardOnFieldAttacker.getImageView().setImage(ImageHolder.findImageInImageHolders(cardOnFieldAttacker.getCard().getAddressOfGetDamageGif()));
                TranslateTransition tt4 = new TranslateTransition(Duration.millis(2000), cardOnFieldAttacker.getImageView());
                tt4.setOnFinished(event2 -> {
                    cardOnFieldAttacker.getImageView().setImage(ImageHolder.findImageInImageHolders(cardOnFieldAttacker.getCard().getAddressOfIdleGif()));

                });
                ParallelTransition parallelTransition2 = new ParallelTransition(tt3, tt4);
                if (newResultOfAttack == Battle.VALID_COUNTER_WITH_BUFF || newResultOfAttack == Battle.INVALID_COUNTER_WITH_BUFF) {
                    ImageView imageView = new ImageView(buffEffect);
                    anchorPane.getChildren().add(imageView);
                    imageView.relocate(cardOnFieldAttacker.getImageView().getLayoutX(), cardOnFieldAttacker.getImageView().getLayoutY());
                    TranslateTransition effectTransition2 = new TranslateTransition(Duration.millis(500), imageView);
                    effectTransition2.setOnFinished(event2 -> {
                        anchorPane.getChildren().remove(imageView);
                    });
                    parallelTransition2.getChildren().add(effectTransition2);
                } else {

                    battle.deleteDeathCardsFromMap();
                }

                parallelTransition2.play();
            }
        });
        parallelTransition.play();

    }

    public void insertPlayerHeroes() {
        getBattle().setPlayingPlayer(getBattle().getPlayer1());
        getBattle().getPlayingPlayer().setHero(getBattle().getPlayer1().getDeck().getHero());
        handleInsertCardClickAi(getBattle().getGrid()[2][0], getBattle().getPlayer1().getDeck().getHero());
        getBattle().getPlayer1().getInGameCards().add(getBattle().getPlayer1().getDeck().getHero());


        getBattle().setPlayingPlayer(getBattle().getPlayer2());
        getBattle().getPlayingPlayer().setHero(getBattle().getPlayer2().getDeck().getHero());
        handleInsertCardClickAi(getBattle().getGrid()[2][8], getBattle().getPlayer2().getDeck().getHero());
        getBattle().getPlayer2().getInGameCards().add(getBattle().getPlayer2().getDeck().getHero());


        getBattle().setPlayingPlayer(getBattle().getPlayer1());
    }


    public void handleInsertCardClickAi(Cell cell, Card card) {
        int[] battleCoordinate = getBattle().findCellCoordinate(cell);
        CardForBattle cardForBattle = CardForBattleController.findCardForBattleWithCard(getHand(), card);
        try {

            getBattle().insertSelectedCard(battleCoordinate[0], battleCoordinate[1]);
//            getBattle().getPlayingPlayer().getInGameCards().add(card);
            Polygon polygon = rectangles[battleCoordinate[0]][battleCoordinate[1]];
            ObservableList<Double> points = polygon.getPoints();


            CardOnField cardOnField = new CardOnField();
            cardOnField.setCard(card);
            cardsOnField.add(cardOnField);
            sendIdleImageViewToCenterOfCell(cardOnField, polygon);

            getBattle().setSelectedCard(null);
            //   getHand().remove(cardForBattl e);
            //          getBattle().setSelectedCard(null);
//            cardForBattle.setCard(null);
        } catch (MyException e) {
            Container.exceptionGenerator(e, stackPane);
        }
        getBattle().setSelectedCell(null);
        battle.deleteDeathCardsFromMap(); // Check For Death Cards

    }


    public void handleInsertCardClick(Cell cell) {

        int[] battleCoordinate = getBattle().findCellCoordinate(cell);
//        battle.findValidCell(KindOfActionForValidCells.INSERT);
//        ArrayList<Cell> cells = battle.getValidCells();
//        if (!cells.contains(battle.getGrid()[battleCoordinate[0]][battleCoordinate[1]])) {
//            //TODO throw exception
//            return;
//        }
        CardForBattle cardForBattle = CardForBattleController.findCardForBattleWithCard(getHand(), getBattle().getSelectedCard());
        try {
            getBattle().insertSelectedCard(battleCoordinate[0], battleCoordinate[1]);
            Polygon polygon = rectangles[battleCoordinate[0]][battleCoordinate[1]];


            CardOnField cardOnField = new CardOnField();
            cardOnField.setCard(getBattle().getSelectedCard());
            cardsOnField.add(cardOnField);
            sendIdleImageViewToCenterOfCell(cardOnField, polygon);

            //   getHand().remove(cardForBattle);
            getBattle().setSelectedCard(null);
            cardForBattle.setCard(null);
        } catch (MyException e) {
            Container.exceptionGenerator(e, stackPane);
        }
        getBattle().setSelectedCell(null);
        battle.deleteDeathCardsFromMap(); // Check For Death Cards
    }

    public void handleInsertCollectibleItem(Cell cell) {

        int[] battleCoordinate = getBattle().findCellCoordinate(cell);

        try {

            Polygon polygon = rectangles[battleCoordinate[0]][battleCoordinate[1]];


            CardOnField cardOnField = new CardOnField();
            cardsOnField.add(cardOnField);
            cardOnField.setCard(cell.getCollectibleItem());
            sendIdleImageViewToCenterOfCell(cardOnField, polygon);

        } catch (MyException e) {
            Container.exceptionGenerator(e, stackPane);
        }
        battle.deleteDeathCardsFromMap(); // Check For Death Cards
    }

    public void moveAnimationRunAi(int[] coordinate, Warrior warrior) {
        CardOnField cardOnField = CardOnField.findCardOnFieldFromArrayList(cardsOnField, warrior);

        Integer srcRow = getBattle().getSelectedCell().getRow();
        Integer srcCol = getBattle().getSelectedCell().getColumn();
        Polygon srcPolygon = rectangles[srcRow][srcCol];
        ObservableList<Double> srcPoints = srcPolygon.getPoints();
        double srcx = calculateMidXFromPoint(srcPoints);
        double srcy = calculateMidYFromPoint(srcPoints);

        Polygon destPolygon = rectangles[coordinate[0]][coordinate[1]];
        ObservableList<Double> destPoints = destPolygon.getPoints();
        double x = calculateMidXFromPoint(destPoints);
        double y = calculateMidYFromPoint(destPoints);


        anchorPane.getChildren().remove(cardOnField.getImageView());

        cardOnField.setImageView(new ImageView(ImageHolder.findImageInImageHolders(getBattle().getSelectedCell().getWarrior().getAddressOfRunGif())));

        getBattle().getSelectedCell().setWarrior(null);

        anchorPane.getChildren().add(cardOnField.getImageView());
        TranslateTransition tt = new TranslateTransition(Duration.millis(500), cardOnField.getImageView());
        tt.setFromX(srcx);
        tt.setFromY(srcy);
        tt.setToX(x);
        tt.setToY(y);
        tt.setOnFinished(event1 -> {
            getBattle().setSelectedCell(getBattle().getGrid()[coordinate[0]][coordinate[1]]);
            getBattle().getGrid()[coordinate[0]][coordinate[1]].setWarrior(((Warrior) cardOnField.getCard()));
            getBattle().move(coordinate[0], coordinate[1]);

            cardOnField.getImageView().setImage(new Image(cardOnField.getCard().getAddressOfIdleGif()));
            System.out.println("-0-0-0-0-0-00-0-0--0-0-0-0-0-0-0-0-");
//            getBattle().setSelectedCell(null);
        });
        tt.play();
        return;
    }

    public void moveAnimationRun(int[] coordinate) {
        CardOnField cardOnField = CardOnField.findCardOnFieldFromArrayList(cardsOnField, getBattle().getSelectedCell().getWarrior());

        Integer srcRow = getBattle().getSelectedCell().getRow();
        Integer srcCol = getBattle().getSelectedCell().getColumn();
        Polygon srcPolygon = rectangles[srcRow][srcCol];
        ObservableList<Double> srcPoints = srcPolygon.getPoints();
        double srcx = calculateMidXFromPoint(srcPoints);
        double srcy = calculateMidYFromPoint(srcPoints);

        Polygon destPolygon = rectangles[coordinate[0]][coordinate[1]];
        ObservableList<Double> destPoints = destPolygon.getPoints();
        double x = calculateMidXFromPoint(destPoints);
        double y = calculateMidYFromPoint(destPoints);


        anchorPane.getChildren().remove(cardOnField.getImageView());

        cardOnField.setImageView(new ImageView(ImageHolder.findImageInImageHolders(getBattle().getSelectedCell().getWarrior().getAddressOfRunGif())));

        anchorPane.getChildren().add(cardOnField.getImageView());
        TranslateTransition tt = new TranslateTransition(Duration.millis(500), cardOnField.getImageView());
        tt.setFromX(srcx);
        tt.setFromY(srcy);
        tt.setToX(x);
        tt.setToY(y);
        tt.setOnFinished(event1 -> {
            getBattle().move(coordinate[0], coordinate[1]);
            cardOnField.getImageView().setImage(new Image(cardOnField.getCard().getAddressOfIdleGif()));
//            getBattle().getGrid()[coordinate[0]][coordinate[1]].setWarrior(((Warrior) cardOnField.getCard()));
            getBattle().setSelectedCell(null);
        });
        tt.play();
    }

    public void sendIdleImageViewToCenterOfCell(CardOnField cardOnField, Polygon polygon) {
        ObservableList<Double> points = polygon.getPoints();
        cardOnField.setImageView(new ImageView(ImageHolder.findImageInImageHolders(cardOnField.getCard().getAddressOfIdleGif())));
        double x = calculateMidXFromPoint(points);
        double y = calculateMidYFromPoint(points);
        cardOnField.getImageView().relocate(x, y);
        anchorPane.getChildren().add(cardOnField.getImageView());
    }

    public void removeImageViewFromCell(Card card) {

        CardOnField cardOnField = CardOnField.getCardOnField(card);
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
        Container.runNextScene(root, GRAVE_YARD);

    }

    public void stopTimeline() {
        slowTimeline.stop();
        fastTimeLine.stop();
    }

    public void animationOfDeath(Warrior warrior) {
        CardOnField cardOnField = CardOnField.getCardOnField(warrior);
        if (cardOnField != null) {

//            Polygon polygon = null;
//            Cell cell = battle.getCellOfWarrior(warrior);
//            for (int i = 0; i < battle.getGrid().length; i++) {
//                for (int j = 0; j < battle.getGrid().length; j++) {
//                    if (battle.getGrid()[i][j].equals(cell)) {
//                        System.out.println("Peida Kard :DDDDDDDDDDDDDDDDDDDDDDDDDDD");
//                        polygon = rectangles[i][j];
//                        break;
//                    }
//                }
//            }
//            ObservableList<Double> points = polygon.getPoints();
//
//
//            double x = calculateMidXFromPoint(points);
//            double y = calculateMidYFromPoint(points);
//            System.out.println(x + "  ][  " + y);
//            cardOnField.getImageView().relocate(x, y);

            System.out.println("Oomade Ke Bekesheeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
            cardOnField.getImageView().setImage(ImageHolder.findImageInImageHolders(cardOnField.getCard().getAddressOfDeathGif()));

            TranslateTransition tt = new TranslateTransition(Duration.millis(2000), cardOnField.getImageView());
//            tt.setOnFinished(event -> anchorPane.getChildren().remove(cardOnField.getImageView()));
            tt.play();


            System.out.println("Giiiiiiiiiiiiiiiiiii");
        }
    }

    public void rectangleOnMouseEnter(MouseEvent event, int i, int j) {
        Polygon p = (Polygon) event.getSource();
        if (getBattle().getGrid()[i][j].getWarrior() != null) {
            attackPower_lbl.setText("" + getBattle().getGrid()[i][j].getWarrior().getActionPower());
            healthPoint_lbl.setText("" + getBattle().getGrid()[i][j].getWarrior().getHealthPoint());
        }
        p.setStyle("-fx-fill: YELLOW");
    }


    public void rectangleOnMouseExited(MouseEvent event, int i, int j) {
        Polygon p = (Polygon) event.getSource();
        if (getBattle().getGrid()[i][j].getWarrior() != null) {
            attackPower_lbl.setText("");
            healthPoint_lbl.setText("");
        }
        p.setStyle("-fx-fill: BLACK; -fx-opacity: 0.5");
    }

    public void setHandHbox() {
        getHand().clear();
        getHand().clear();
        makeHandView();
    }

    public void handCardOnMouseClicked(MouseEvent e) {
        CardForBattle cardForBattle = (CardForBattle) e.getSource();
        if (cardForBattle.getCard() != null) {
            if (getBattle().getSelectedCard() != null) {
                CardForBattle cardForBattleWithCard = CardForBattleController.findCardForBattleWithCard(getHand(), getBattle().getSelectedCard());
                double rotationgAngle = cardForBattleWithCard.getCardController().getCardSelection_iv().getRotate();
                cardForBattleWithCard.getCardController().setImageOfCardSelection(battleCardNotSelectedImage);
                cardForBattleWithCard.getCardController().getCardSelection_iv().setRotate(rotationgAngle);
            }
            if (cardForBattle.getCard() == getBattle().getSelectedCard()) {
                getBattle().setSelectedCard(null);
            } else {

                getBattle().setSelectedCard(cardForBattle.getCard());
                double rotationgAngle = cardForBattle.getCardController().getCardSelection_iv().getRotate();
                cardForBattle.getCardController().setImageOfCardSelection(battleCardSelectedImage);
                cardForBattle.getCardController().getCardSelection_iv().setRotate(rotationgAngle);
            }
            getBattle().setSelectedCell(null);
        }
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

    public void updateManaOfPlayer(HBox hbox, Player player) {
        hbox.getChildren().clear();
        int countOfActiveMana = 0;
        if (player.equals(battle.getPlayingPlayer())) {
            for (int i = 0; i < getBattle().calculateMaxAmountOfMana(); i++) {
                ImageView imageView = null;
                if (countOfActiveMana < player.getMana()) {
                    imageView = new ImageView(manaIconSml);
                    countOfActiveMana++;
                } else {
                    imageView = new ImageView(manaInActiveSml);
                }
                hbox.getChildren().add(imageView);
            }
        } else {
            ImageView imageView = new ImageView(manaInActiveSml);
            hbox.getChildren().add(imageView);
        }
        Label label = new Label();
        String text = player.getMana() + "/" + battle.calculateMaxAmountOfMana();
        label.setText(text);
        label.setFont(Font.font("System", FontWeight.BOLD, 20));
        label.setStyle("-fx-text-fill: RED");
        hbox.getChildren().add(label);
    }


    public void makeAccountNames() {
        p1Acc_lbl.setText(this.getBattle().getPlayer1().getAccount().getUsername());
        p2Acc_lbl.setText(this.getBattle().getPlayer2().getAccount().getUsername());

    }

    public void updateMana() {
        updateManaOfPlayer(p1Mana_hb, battle.getPlayer1());
        updateManaOfPlayer(p2Mana_hb, battle.getPlayer2());

    }

    public void rotateCardImageViews() {
        for (int i = 0; i < getHand().size(); i++) {
            CardForBattle cardForBattle = getHand().get(i);
            if (cardForBattle != null) {
                cardForBattle.getCardController().getCardSelection_iv().setRotate(cardForBattle.getCardController().getCardSelection_iv().getRotate() + 0.5);
            }
        }
    }

    public void handleEndTurnBtn() {
        battle.nextTurn();
        updateHand();
    }

    public void endTurnButtonGlow() {
        endTurn_img.setImage(new Image("res/ui/button_end_turn_mine_glow.png"));//TODO Az Image Holder Estefade Nakardam
    }

    public void endTurnButtonGlowDisappear() {
        endTurn_img.setImage(new Image("res/ui/button_end_turn_mine.png"));//TODO Az Image Holder Estefade Nakardam
    }


    public void updateHand() {
        //TODO IT IS THE SAME AS setHandHbox but maybe some of them need more checks. so currently they are two different method.;
        getHand_hBox().getChildren().clear();
        getHand().clear();
        makeHandView();
    }

    public void makeHandView() {
        for (int i = 0; i < getBattle().getPlayingPlayer().getHand().size(); i++) {
            if (getBattle().getPlayingPlayer().getHand().get(i) != null) {
                CardForBattle cardForBattle = new CardForBattle(getBattle().getPlayingPlayer().getHand().get(i));
                cardForBattle.getCardController().setBattle(getBattle());
                getHand().add(cardForBattle);
                getHand().get(i).setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        handCardOnMouseClicked(event);
                    }
                });
                getHand_hBox().getChildren().add(getHand().get(i));
            }
        }
    }

    public HBox getHand_hBox() {
        return hand_hBox;
    }

    public void setHand_hBox(HBox hand_hBox) {
        this.hand_hBox = hand_hBox;
    }

    public Battle getBattle() {
        return battle;
    }

    public void setBattle(Battle battle) {
        this.battle = battle;
    }

    public ArrayList<CardForBattle> getHand() {
        return hand;
    }

    public void backToMenuInEndOfGame(int loseOrWinOrDraw) {//lose 1 , //win 2 , //draw 3
        gameResultAnimation(loseOrWinOrDraw);
    }

    private void gameResultAnimation(int kind) {
        if (kind == 1) {
            gameResult_img.setImage(new Image("res/ui/notification_enemy_turn@2x.png"));
            gameResult_lbl.setText("You Loose");
        } else if (kind == 2) {
            gameResult_img.setImage(new Image("res/ui/notification_go@2x.png"));
            gameResult_lbl.setText("You Win");
        } else {
            gameResult_img.setImage(new Image("res/ui/notification_go@2x.png"));
            gameResult_lbl.setText("Draw!");
        }
        anchorPane.getChildren().remove(gameResult_img);
        anchorPane.getChildren().remove(gameResult_lbl);
        anchorPane.getChildren().add(gameResult_img);
        anchorPane.getChildren().add(gameResult_lbl);
        FadeTransition ft = new FadeTransition(Duration.millis(3000), gameResult_img);
        FadeTransition ft2 = new FadeTransition(Duration.millis(3000), gameResult_lbl);
        ft.setOnFinished(event -> {
            if (Container.scenes.size() > 0) {
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

    public void randomCollectibleItemGenerator() {

        collectibleItemSet();
    }


    private void collectibleItemSet() {
        Cell cell = getRandomCellForCollectibleIteInsert();
        if (cell == null)
            return;
        cell.setCollectibleItem(getRandomCollectibleItem());
        handleInsertCollectibleItem(cell);
    }

    public void deleteItemImage(Item item) {
        CardOnField cardOnField = CardOnField.getCardOnField(item);
        anchorPane.getChildren().remove(cardOnField.getImageView());
    }


    public Item getRandomCollectibleItem() {
        Random random = new Random();
        int number = 1 + random.nextInt(9);
        switch (number) {
            case 1:
                return new NooshDaroo();
            case 2:
                return new TireDoshakh();
            case 3:
                return new Exir();
            case 4:
                return new MajooneMana();
            case 5:
                return new MajooneRooeinTani();
            case 6:
                return new NefrineMarg();
            case 7:
                return new RandomDamage();
            case 8:
                return new BladesOfAgility();
            case 9:
                return new ShamshireChini();
        }
        return null;
    }


    private Cell getRandomCellForCollectibleIteInsert() {
        int row, column;
        Random random = new Random();
        row = random.nextInt(5);
        column = random.nextInt(9);
        if (battle.getGrid()[row][column].getWarrior() != null || battle.getGrid()[row][column].getFlag()
                != null || battle.getGrid()[row][column].getCollectibleItem() != null) {
            return null;
        }
        return battle.getGrid()[row][column];
    }

}

class CardOnField {

    private static ArrayList<CardOnField> allCardOnFields = new ArrayList<>();
    private ImageView imageView;
    private Card card;

    CardOnField() {
        allCardOnFields.add(this);
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

    static CardOnField findCardOnFieldFromArrayList(ArrayList<CardOnField> arrayList, Card card) {
        if (card != null && arrayList != null && arrayList.size() > 0) {
            for (CardOnField cardOnField : arrayList) {
                if (cardOnField.getCard().equals(card)) {
                    return cardOnField;
                }
            }
        }
        return null;
    }

    static CardOnField getCardOnField(Card card) {
        for (CardOnField allCardOnField : allCardOnFields) {
            if (allCardOnField.getCard().equals(card))
                return allCardOnField;
        }
        return null;
    }

    public static ArrayList<CardOnField> getAllCardOnFields() {
        return allCardOnFields;
    }

}

