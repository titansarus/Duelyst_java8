package Duelyst.Model.CommandClasses;

import Duelyst.Model.Card;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

public class CustomCardCommand extends CommandClass {

    private Card card;
    private byte[] image;
    private byte[] idleGif;
    private byte[] runGif;
    private byte[] attackGif;
    private byte[] hitGif;
    private byte[] deathGif;

    public CustomCardCommand(Card card, File image, File idleGif, File runGif, File attackGif, File hitGif, File deathGif) {
        super(CommandKind.CUSTOM_CARD);
        this.card = card;
        this.image = makeByteArrayFromImages(image);
        this.idleGif = makeByteArrayFromGif(idleGif);
        this.runGif = makeByteArrayFromGif(runGif);
        this.attackGif = makeByteArrayFromGif(attackGif);
        this.hitGif = makeByteArrayFromGif(hitGif);
        this.deathGif = makeByteArrayFromGif(deathGif);
    }

    private byte[] makeByteArrayFromImages(File file) {
        if (file == null)
            return null;
        try {
            byte[] temp = new byte[1000000];
            FileInputStream fileInputStream = new FileInputStream(file);
            fileInputStream.read(temp);
            return temp;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private byte[] makeByteArrayFromGif(File file) {
        if (file == null)
            return null;
        try {
            byte[] temp = new byte[1000000];
            FileInputStream fileInputStream = new FileInputStream(file);
            fileInputStream.read(temp);
            return temp;
        } catch (IOException e) {
            return null;
        }

    }


    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public byte[] getIdleGif() {
        return idleGif;
    }

    public void setIdleGif(byte[] idleGif) {
        this.idleGif = idleGif;
    }

    public byte[] getRunGif() {
        return runGif;
    }

    public void setRunGif(byte[] runGif) {
        this.runGif = runGif;
    }

    public byte[] getAttackGif() {
        return attackGif;
    }

    public void setAttackGif(byte[] attackGif) {
        this.attackGif = attackGif;
    }

    public byte[] getHitGif() {
        return hitGif;
    }

    public void setHitGif(byte[] hitGif) {
        this.hitGif = hitGif;
    }

    public byte[] getDeathGif() {
        return deathGif;
    }

    public void setDeathGif(byte[] deathGif) {
        this.deathGif = deathGif;
    }

}
