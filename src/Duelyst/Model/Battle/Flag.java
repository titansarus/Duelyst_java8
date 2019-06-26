package Duelyst.Model.Battle;

import Duelyst.Model.Warrior;
import javafx.scene.image.ImageView;


public class Flag {
    private Warrior warrior;
    private KindOfFlag kindOfFlag;
    private int numberOfTurn = 0;
    private int x;
    private int y;
    private static String image = "res/Items/Flag/CollectFlag.png";
    private ImageView imageView;

    public Flag(KindOfFlag kindOfFlag, int x, int y) {
        this.kindOfFlag = kindOfFlag;
        this.x = x;
        this.y = y;
    }

    public static String getImage() {
        return image;
    }

    public Warrior getWarrior() {
        return warrior;
    }

    public void setWarrior(Warrior warrior) {
        this.warrior = warrior;
    }

    public KindOfFlag getKindOfFlag() {
        return kindOfFlag;
    }

    public void setKindOfFlag(KindOfFlag kindOfFlag) {
        this.kindOfFlag = kindOfFlag;
    }

    public int getNumberOfTurn() {
        return numberOfTurn;
    }

    public void setNumberOfTurn(int numberOfTurn) {
        this.numberOfTurn = numberOfTurn;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }
}
