package Duelyst.Controllers;

import Duelyst.Exceptions.MyException;
import Duelyst.Model.Battle.Battle;
import Duelyst.Model.Battle.Cell;
import Duelyst.Model.Battle.Player;
import Duelyst.Model.Card;
import Duelyst.Model.Warrior;
import Duelyst.Utility.ImageHolder;
import Duelyst.View.ViewClasses.CardForBattle;
import com.jfoenix.controls.JFXButton;
import javafx.animation.*;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;


import java.util.ArrayList;

import static Duelyst.View.Constants.*;

public class BattleController {


    public ImageView endTurn_img;

    public Label endTurn_lbl;

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
    JFXButton end_turn_btn;

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


    double heightOfPoly_Y;
    double heightOfPoly_X;
    double width;

    ArrayList<CardForBattle> hand = new ArrayList<>();
    ArrayList<CardOnField> cardsOnField = new ArrayList<>();

    Polygon[][] rectangles = new Polygon[BATTLE_ROWS][BATTLE_COLUMNS];

    @FXML
    public void initialize() {
//        BackgroundImage backgroundImage = new BackgroundImage(endTurnBtn , BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT , BackgroundSize.DEFAULT);
//        end_turn_btn.setBackground(new Background( backgroundImage));
    }

    Battle battle;
    Timeline slowTimeline = new Timeline();
    Timeline fastTimeLine = new Timeline();

    public void runTimelines() {
        runSlowTimeline();
        runVeryFastTimeLine();
    }

    public void runSlowTimeline() {
        slowTimeline = new Timeline(new KeyFrame(Duration.ZERO, event -> {
            hand.get(0).getView();
            updateMana();
            emptyCardsOfHand();
        }), new KeyFrame(Duration.seconds(1)));
        slowTimeline.setCycleCount(Animation.INDEFINITE);
        slowTimeline.play();
    }

    public void runVeryFastTimeLine() {
        fastTimeLine = new Timeline(new KeyFrame(Duration.ZERO, event -> {
            rotateCardImageViews();
        }), new KeyFrame(Duration.millis(20)));
        fastTimeLine.setCycleCount(Animation.INDEFINITE);
        fastTimeLine.play();
    }

    public void emptyCardsOfHand() {
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
                rectangles[i][j].getPoints().addAll(new Double[]{
                        upLeftX + width * j + HEIGHT_PADDING_X + i * heightOfPoly_X + WIDTH_PADDING * j + heightOfPoly_X + width, upLeftY + i * HEIGHT_PADDING_Y + i * heightOfPoly_Y + heightOfPoly_Y,
                        upLeftX + width * j + HEIGHT_PADDING_X + i * heightOfPoly_X + width + WIDTH_PADDING * j, upLeftY + i * HEIGHT_PADDING_Y + i * heightOfPoly_Y,
                        upLeftX + width * j + HEIGHT_PADDING_X + i * heightOfPoly_X + WIDTH_PADDING * j, upLeftY + i * HEIGHT_PADDING_Y + i * heightOfPoly_Y,
                        upLeftX + width * j + HEIGHT_PADDING_X + i * heightOfPoly_X + heightOfPoly_X + WIDTH_PADDING * j, upLeftY + i * HEIGHT_PADDING_Y + i * heightOfPoly_Y + heightOfPoly_Y,


                });
                rectangles[i][j].setStyle("-fx-fill: BLACK;-fx-opacity: 0.5");

                anchorPane.getChildren().addAll(rectangles[i][j]);
                rectangles[i][j].setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        rectangleOnMouseClicked(event);

                    }
                });

                rectangles[i][j].setOnMouseEntered(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        rectangleOnMouseEnter(event);
                    }
                });
                rectangles[i][j].setOnMouseExited(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        rectangleOnMouseExited(event);
                    }
                });
            }
        }
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


        if (getBattle().getSelectedCell() != null && getBattle().getSelectedCell().getWarrior() != null) {
            if (getBattle().getSelectedCell() != getBattle().getGrid()[coordinate[0]][coordinate[1]] && getBattle().getGrid()[coordinate[0]][coordinate[1]].getWarrior() == null) {
                //TODO CHECK OF MANHATTAN DISTANCE AND CAN MOVE?
                if (Cell.calculateManhattanDistance(getBattle().getSelectedCell(), getBattle().getGrid()[coordinate[0]][coordinate[1]]) <= 2) {
                    moveAnimationRun(coordinate);
                }
                return;
            } else if (getBattle().getSelectedCell() != getBattle().getGrid()[coordinate[0]][coordinate[1]] && getBattle().getGrid()[coordinate[0]][coordinate[1]].getWarrior() != null) {
                //TODO ADD CHECK OF RANGE AND MELEE OR HYBRID

                if (getBattle().getPlayingPlayer().checkIfCardIsInGame(getBattle().getGrid()[coordinate[0]][coordinate[1]].getWarrior())) {
                    System.out.println("YOUR CARD!!!");
                } else {
                    handleAttackAnimation(coordinate);

                }

                return;
            }
        }
        getBattle().setSelectedCell(getBattle().getGrid()[coordinate[0]][coordinate[1]]);


        if (getBattle().getSelectedCard() != null) {//TODO SOME MORE CHECKS NEEDED
            handleInsertCardClick();
            return;
        }
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
        parallelTransition.play();

    }


    public void handleInsertCardClick() {
        int[] battleCoordinate = getBattle().findCellCoordinate(getBattle().getSelectedCell());
        CardForBattle cardForBattle = CardForBattleController.findCardForBattleWithCard(getHand(), getBattle().getSelectedCard());
        try {
            getBattle().insertSelectedCard(battleCoordinate[0], battleCoordinate[1]);
            Polygon polygon = rectangles[battleCoordinate[0]][battleCoordinate[1]];
            ObservableList<Double> points = polygon.getPoints();


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

        cardOnField.setImageView(new ImageView(ImageHolder.findImageInImageHolders( getBattle().getSelectedCell().getWarrior().getAddressOfRunGif())));

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
        return;
    }

    public void sendIdleImageViewToCenterOfCell(CardOnField cardOnField, Polygon polygon) {
        ObservableList<Double> points = polygon.getPoints();
        cardOnField.setImageView(new ImageView(ImageHolder.findImageInImageHolders( getBattle().getSelectedCard().getAddressOfIdleGif())));
        double x = calculateMidXFromPoint(points);
        double y = calculateMidYFromPoint(points);
        cardOnField.getImageView().relocate(x, y);
        anchorPane.getChildren().add(cardOnField.getImageView());
    }

    public void rectangleOnMouseEnter(MouseEvent event) {
        Polygon p = (Polygon) event.getSource();
        System.out.println(p.getPoints());

        p.setStyle("-fx-fill: YELLOW");
    }

    public void rectangleOnMouseExited(MouseEvent event) {
        Polygon p = (Polygon) event.getSource();
        System.out.println(p.getPoints());

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

    public void endTurnButtonGlow(){
        endTurn_img.setImage(new Image("res/ui/button_end_turn_mine_glow.png"));//TODO Az Image Holder Estefade Nakardam
    }

    public void endTurnButtonGlowDisappear(){
        endTurn_img.setImage(new Image("res/ui/button_end_turn_mine.png"));//TODO Az Image Holder Estefade Nakardam
    }

    public void updateHand() {
        //TODO IT IS THE SAME AS setHandHbox but maybe some of them need more checks. so currently they are two different method.;
        getHand_hBox().getChildren().clear();
        getHand().clear();
        makeHandView();
    }

    public void makeHandView() {
        for (int i = 0; i < SIZE_OF_HAND; i++) {

                System.out.println("gi tot ==================================================== size : "+getBattle().getPlayingPlayer().getHand().size() );


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
}


class CardOnField {
    private ImageView imageView;
    private Card card;


    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public static CardOnField findCardOnFieldFromArrayList(ArrayList<CardOnField> arrayList, Card card) {
        if (card != null && arrayList != null && arrayList.size() > 0) {
            for (int i = 0; i < arrayList.size(); i++) {
                if (arrayList.get(i).getCard().equals(card)) {
                    return arrayList.get(i);
                }
            }
        }
        return null;
    }
}

