package Duelyst.Controllers;

import Duelyst.Exceptions.CreateCardFieldNotCompleteException;
import Duelyst.Exceptions.MyException;
import Duelyst.Model.*;
import Duelyst.Utility.ImageHolder;
import Duelyst.View.Constants;
import com.jfoenix.controls.*;
import javafx.fxml.FXML;
import javafx.scene.control.TextFormatter;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

import java.io.*;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.function.UnaryOperator;

import static Duelyst.View.Constants.*;

public class CardCreatorController {

    public final String MELEE = "Melee", HYBRID = "Hybrid", RANGED = "RANGED";

    @FXML
    StackPane stackPane;
    @FXML
    JFXRadioButton hero_rb;
    @FXML
    JFXRadioButton minion_rb;
    @FXML
    JFXRadioButton spell_rb;


    @FXML
    VBox hero_vb;
    @FXML
    VBox minion_vb;
    @FXML
    VBox spell_vb;

    @FXML
    JFXButton back_btn;

    @FXML
    JFXComboBox<String> heroAttackType_cb;
    @FXML
    JFXComboBox<String> minionAttackType_cb;

    @FXML
    JFXTextField heroCardName_tf, heroAP_tf, heroHP_tf, heroCost_tf, heroRange_tf, heroManaCost_tf, heroCooldown_tf;
    @FXML
    JFXTextField minionCardName_tf, minionAP_tf, minionHP_tf, minionCost_tf, minionRange_tf, minionManaCost_tf;
    @FXML
    JFXTextArea heroDesc_ta, minionDesc_ta;
    @FXML
    ImageView cardImage_iv, attackGif_iv, deathGif_iv, hitGif_iv, idleGif_iv, runGif_iv;


    private String imageAddress = NO_ADDRESS, idleGifAddress = NO_ADDRESS, attackGifAddress = NO_ADDRESS, deathGifAddress = NO_ADDRESS, hitGifAddress = NO_ADDRESS, runGifAddress = NO_ADDRESS;


    @FXML
    public void initialize() {
        hero_rb.setSelected(true);
        handleHeroRb();
        initAttackTypeComboBox(heroAttackType_cb);
        initAttackTypeComboBox(minionAttackType_cb);
        setDigitTextLimitaions();
        setAddressToNoAdddress();


    }

    public void setAddressToNoAdddress() {
        setImageAddress(NO_ADDRESS);
        setIdleGifAddress(NO_ADDRESS);
        setAttackGifAddress(NO_ADDRESS);
        setDeathGifAddress(NO_ADDRESS);
        setHitGifAddress(NO_ADDRESS);
        setRunGifAddress(NO_ADDRESS);

    }

    public void setDigitTextLimitaions() {
        heroAP_tf.setTextFormatter(Container.getOnlyNumberFormatter());
        heroHP_tf.setTextFormatter(Container.getOnlyNumberFormatter());
        heroCost_tf.setTextFormatter(Container.getOnlyNumberFormatter());
        heroRange_tf.setTextFormatter(Container.getOnlyNumberFormatter());
        heroCooldown_tf.setTextFormatter(Container.getOnlyNumberFormatter());
        heroManaCost_tf.setTextFormatter(Container.getOnlyNumberFormatter());

        minionAP_tf.setTextFormatter(Container.getOnlyNumberFormatter());
        minionHP_tf.setTextFormatter(Container.getOnlyNumberFormatter());
        minionCost_tf.setTextFormatter(Container.getOnlyNumberFormatter());
        minionRange_tf.setTextFormatter(Container.getOnlyNumberFormatter());
        minionManaCost_tf.setTextFormatter(Container.getOnlyNumberFormatter());


    }


    public void initAttackTypeComboBox(JFXComboBox<String> comboBox) {
        comboBox.getItems().removeAll(comboBox.getItems());
        comboBox.getItems().addAll(MELEE, RANGED, HYBRID);
        comboBox.getSelectionModel().select(MELEE);
    }

    public void makeVbEnable(VBox vBox) {
        vBox.setDisable(false);
        vBox.setOpacity(0.8);

    }

    public void makeVbDisable(VBox vBox) {
        vBox.setDisable(true);
        vBox.setOpacity(0.3);
    }

    public void handleCreateCardBtn() {
        if (hero_rb.isSelected()) {
            createHero();
        }
        if (minion_rb.isSelected()) {
            createMinion();
        }
        if (spell_rb.isSelected()) {
            createSpell();
        }
    }

    public void handleSelectImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Image");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image files",  "*.png"));

        File file = fileChooser.showOpenDialog(null);

        if (file != null) {
            System.out.println(file.getAbsolutePath());
            setImageAddress(file.getAbsolutePath());
            changeImageOfImageView(getCardImage_iv(), file);

        } else {
            System.out.println("NO FIlE");
        }
    }

    public void handleSelectIdleGif() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Idle Gif");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Gif files", "*.gif"));

        File file = fileChooser.showOpenDialog(null);

        if (file != null) {
            System.out.println(file.getAbsolutePath());
            setIdleGifAddress(file.getAbsolutePath());
            changeImageOfImageView(getIdleGif_iv(), file);

        } else {
            System.out.println("NO FIlE");
        }
    }

    public void handleSelectRunGif() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Run Gif");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Gif files", "*.gif"));

        File file = fileChooser.showOpenDialog(null);

        if (file != null) {
            System.out.println(file.getAbsolutePath());
            setRunGifAddress(file.getAbsolutePath());
            changeImageOfImageView(getRunGif_iv(), file);

        } else {
            System.out.println("NO FIlE");
        }
    }

    public void handleSelectAttackGif() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Attack Gif");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Gif files", "*.gif"));

        File file = fileChooser.showOpenDialog(null);

        if (file != null) {
            System.out.println(file.getAbsolutePath());
            setAttackGifAddress(file.getAbsolutePath());
            changeImageOfImageView(getAttackGif_iv(), file);

        } else {
            System.out.println("NO FIlE");
        }
    }

    public void handleSelectDeathGif() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Death Gif");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Gif files", "*.gif"));

        File file = fileChooser.showOpenDialog(null);

        if (file != null) {
            System.out.println(file.getAbsolutePath());
            setDeathGifAddress(file.getAbsolutePath());
            changeImageOfImageView(getDeathGif_iv(), file);

        } else {
            System.out.println("NO FIlE");
        }
    }

    public void handleSelectHitGif() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Hit Gif");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Gif files", "*.gif"));

        File file = fileChooser.showOpenDialog(null);

        if (file != null) {
            System.out.println(file.getAbsolutePath());
            setHitGifAddress(file.getAbsolutePath());
            changeImageOfImageView(getHitGif_iv(), file);

        } else {
            System.out.println("NO FIlE");
        }
    }


    public void changeImageOfImageView(ImageView imageView, File file) {
        try {
            imageView.setImage(ImageHolder.findImageInImageHolders(file.toURI().toURL().toExternalForm()));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }


    public void createHero() {
        try {
            System.out.println("Create Hero");
            String name = heroCardName_tf.getText();
            if (name == null || name.length() <= 0) {
                throw new CreateCardFieldNotCompleteException();
            }
            int ap = Integer.parseInt(heroAP_tf.getText());
            int hp = Integer.parseInt(heroHP_tf.getText());
            int cost = Integer.parseInt(heroCost_tf.getText());
            int range = Integer.parseInt(heroRange_tf.getText());
            int cooldown = Integer.parseInt(heroCooldown_tf.getText());
            int manaCost = Integer.parseInt(heroManaCost_tf.getText());
            String desc = heroDesc_ta.getText();
            AttackKind attackKind = AttackKind.getFromString(heroAttackType_cb.getValue());
            ArrayList<String> imagesAddress = createImagesInFolderAndGiveAddress(name);

            //TODO IMAGES MUST BE OKD
            Hero hero = new Hero(name, desc, manaCost, cost, hp, ap, range, attackKind, 0, imagesAddress.get(0),
                    imagesAddress.get(1),
                    imagesAddress.get(2),
                    imagesAddress.get(3),
                    imagesAddress.get(4),
                    imagesAddress.get(5),
                    cooldown);
            System.out.println(attackKind);

            Account.getLoggedAccount().getCardCollection().getCustomCards().add(hero);
            Account.getLoggedAccount().getCardCollection().addCard(hero);
            Shop.getInstance().getCards().add(hero);

        } catch (Exception e) {
            e.printStackTrace();
            Container.exceptionGenerator(new CreateCardFieldNotCompleteException(), stackPane);
        }


        //   ./res/gifs/f1_altgeneral/idle_t.gif

    }

    public void createMinion() {
        try {
            System.out.println("Create Minion");
            String name = minionCardName_tf.getText();
            String desc = minionDesc_ta.getText();
            if (name == null || name.length() <= 0) {
                throw new CreateCardFieldNotCompleteException();
            }
            int ap = Integer.parseInt(minionAP_tf.getText());
            int hp = Integer.parseInt(minionHP_tf.getText());
            int cost = Integer.parseInt(minionCost_tf.getText());
            int range = Integer.parseInt(minionRange_tf.getText());
            int manaCost = Integer.parseInt(minionManaCost_tf.getText());
            AttackKind attackKind = AttackKind.getFromString(minionAttackType_cb.getValue());
            ArrayList<String> imagesAddress = createImagesInFolderAndGiveAddress(name);
            //TODO IMAGES MUST BE OKD
            Minion minion = new Minion(name, desc, manaCost, cost, hp, ap, range, attackKind, 0, imagesAddress.get(0),
                    imagesAddress.get(1),
                    imagesAddress.get(2),
                    imagesAddress.get(3),
                    imagesAddress.get(4),
                    imagesAddress.get(5)
            );
            System.out.println(attackKind);
            Account.getLoggedAccount().getCardCollection().getCustomCards().add(minion);
            Account.getLoggedAccount().getCardCollection().addCard(minion);
            Shop.getInstance().getCards().add(minion);
        } catch (Exception e) {
            Container.exceptionGenerator(new CreateCardFieldNotCompleteException(), stackPane);
        }
    }

    public ArrayList<String> createImagesInFolderAndGiveAddress(String nameOfCard) {
        ArrayList<String> result = new ArrayList<>();

        String address = "./src/res/Accounts/" + Account.getLoggedAccount().getUsername() + "/Cards";

        new File(address).mkdirs();
        createImageInGivenAddress(address + "/" + nameOfCard + "_image.png", getImageAddress());

        createImageInGivenAddress(address + "/" + nameOfCard + "_idle.gif", getIdleGifAddress());
        createImageInGivenAddress(address + "/" + nameOfCard + "_run.gif", getRunGifAddress());
        createImageInGivenAddress(address + "/" + nameOfCard + "_attack.gif", getAttackGifAddress());
        createImageInGivenAddress(address + "/" + nameOfCard + "_death.gif", getDeathGifAddress());
        createImageInGivenAddress(address + "/" + nameOfCard + "_hit.gif", getHitGifAddress());

        if (!getImageAddress().equals(NO_ADDRESS)) {
            result.add("./res/Accounts/"+Account.getLoggedAccount().getUsername()+"/Cards" + "/" + nameOfCard + "_image.png");
        } else {
            result.add("./res/Characters/generals/general_f1.png");
        }

        if (!getIdleGifAddress().equals(NO_ADDRESS)) {
            result.add("./res/Accounts/"+Account.getLoggedAccount().getUsername()+"/Cards" + "/" + nameOfCard+ "_idle.gif");
        } else {
            result.add("./res/gifs/f1_altgeneral/idle_t.gif");
        }

        if (!getRunGifAddress().equals(NO_ADDRESS)) {
            result.add("./res/Accounts/"+Account.getLoggedAccount().getUsername()+"/Cards" + "/" + nameOfCard + "_run.gif");
        } else {
            result.add("./res/gifs/f1_altgeneral/run_t.gif");
        }

        if (!getAttackGifAddress().equals(NO_ADDRESS)) {
            result.add("./res/Accounts/"+Account.getLoggedAccount().getUsername()+"/Cards" + "/" + nameOfCard+ "_attack.gif");
        } else {
            result.add("./res/gifs/f1_altgeneral/attack_t.gif");
        }
        if (!getHitGifAddress().equals(NO_ADDRESS)) {
            result.add("./res/Accounts/"+Account.getLoggedAccount().getUsername()+"/Cards" + "/" + nameOfCard + "_hit.gif");
        } else {
            result.add("./res/gifs/f1_altgeneral/hit_t.gif");
        }

        if (!getDeathGifAddress().equals(NO_ADDRESS)) {
            result.add("./res/Accounts/"+Account.getLoggedAccount().getUsername()+"/Cards" + "/" + nameOfCard+ "_death.gif");
        } else {
            result.add("./res/gifs/f1_altgeneral/death_t.gif");
        }




        return result;
    }

    public void createImageInGivenAddress(String destAddress, String srcAddress) {
        if (srcAddress != NO_ADDRESS) {
            try (InputStream in = new BufferedInputStream(new FileInputStream(srcAddress));
                 OutputStream out = new BufferedOutputStream(new FileOutputStream(destAddress));) {
                byte[] bytes = new byte[1024];
                int lengthRead = 0;
                while (-1 != (lengthRead = in.read(bytes))) {
                    out.write(bytes, 0, lengthRead);
                    out.flush();
                    out.flush();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void createSpell() {
        System.out.println("Create Spell");

    }


    public void handleSpellRb() {
        System.out.println("SPELL");
        makeVbEnable(spell_vb);
        makeVbDisable(minion_vb);
        makeVbDisable(hero_vb);

    }

    public void handleMinionRb() {
        System.out.println("MINION");

        makeVbEnable(minion_vb);
        makeVbDisable(spell_vb);
        makeVbDisable(hero_vb);

    }

    public void handleHeroRb() {
        System.out.println("HERO");

        makeVbEnable(hero_vb);
        makeVbDisable(minion_vb);
        makeVbDisable(spell_vb);

    }

    public void handleBackBtn() {
        Container.handleBack();
    }

    public String getImageAddress() {
        return imageAddress;
    }

    public void setImageAddress(String imageAddress) {
        this.imageAddress = imageAddress;
    }

    public String getIdleGifAddress() {
        return idleGifAddress;
    }

    public void setIdleGifAddress(String idleGifAddress) {
        this.idleGifAddress = idleGifAddress;
    }

    public String getAttackGifAddress() {
        return attackGifAddress;
    }

    public void setAttackGifAddress(String attackGifAddress) {
        this.attackGifAddress = attackGifAddress;
    }

    public String getDeathGifAddress() {
        return deathGifAddress;
    }

    public void setDeathGifAddress(String deathGifAddress) {
        this.deathGifAddress = deathGifAddress;
    }

    public String getHitGifAddress() {
        return hitGifAddress;
    }

    public void setHitGifAddress(String hitGifAddress) {
        this.hitGifAddress = hitGifAddress;
    }

    public String getRunGifAddress() {
        return runGifAddress;
    }

    public void setRunGifAddress(String runGifAddress) {
        this.runGifAddress = runGifAddress;
    }

    public ImageView getCardImage_iv() {
        return cardImage_iv;
    }

    public ImageView getAttackGif_iv() {
        return attackGif_iv;
    }

    public ImageView getDeathGif_iv() {
        return deathGif_iv;
    }

    public ImageView getHitGif_iv() {
        return hitGif_iv;
    }

    public ImageView getIdleGif_iv() {
        return idleGif_iv;
    }

    public ImageView getRunGif_iv() {
        return runGif_iv;
    }
}
