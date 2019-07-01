package Duelyst.Controllers;

import Duelyst.Exceptions.CreateCardFieldNotCompleteException;
import Duelyst.Exceptions.MyException;
import Duelyst.Model.*;
import Duelyst.Model.Buffs.HolyBuff;
import Duelyst.Model.Buffs.WeaknessBuff;
import Duelyst.Utility.ImageHolder;
import Duelyst.View.Constants;
import com.jfoenix.controls.*;
import javafx.fxml.FXML;
import javafx.scene.control.TextFormatter;
import javafx.scene.image.Image;
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

    public final String MELEE = "Melee", HYBRID = "Hybrid", RANGED = "Ranged",
            HOLY_BUFF = "Holy Buff", POISON_BUFF = "Poison Buff", POWER_BUFF = "Power Buff", STUN_BUFF = "Stun Buff",
            DISARM_BUFF = "Disarm Buff", FLAME_BUFF = "Flame Buff", WEAKNESS_BUFF = "Weakness Buff",
            FRIENDLY = "Friendly", ENEMY = "Enemy";
    public ImageView selectImage_img;
    public ImageView selectIdleGif_img;
    public ImageView selectRunGif_img;
    public ImageView selectAttackGif_img;
    public ImageView selectDeathGif_img;
    public ImageView selectHitGif_img;

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
    VBox buff_vb;

    @FXML
    JFXButton back_btn;

    @FXML
    JFXComboBox<String> heroAttackType_cb;
    @FXML
    JFXComboBox<String> minionAttackType_cb;
    @FXML
    JFXComboBox<String> friendOrEnemy_cb;
    @FXML
    JFXComboBox<String> buffType_cb;

    @FXML
    JFXTextField heroCardName_tf, heroAP_tf, heroHP_tf, heroCost_tf, heroRange_tf, heroManaCost_tf, heroCooldown_tf;
    @FXML
    JFXTextField minionCardName_tf, minionAP_tf, minionHP_tf, minionCost_tf, minionRange_tf, minionManaCost_tf;
    @FXML
    JFXTextField spellManaCost_tf, spellCardName_tf, spellCost_tf;
    @FXML
    JFXTextField buffName_tf, effectValue_tf, delay_tf, buffLast_tf;

    @FXML
    JFXCheckBox buff_checkbox;

    @FXML
    JFXTextArea heroDesc_ta, minionDesc_ta;
    @FXML
    ImageView cardImage_iv, attackGif_iv, deathGif_iv, hitGif_iv, idleGif_iv, runGif_iv;


    private String imageAddress = NO_ADDRESS, idleGifAddress = NO_ADDRESS, attackGifAddress = NO_ADDRESS, deathGifAddress = NO_ADDRESS, hitGifAddress = NO_ADDRESS, runGifAddress = NO_ADDRESS;


    @FXML
    public void initialize() {
        getHero_rb().setSelected(true);
        getBuff_checkbox().setSelected(false);
        handleHeroRb();
        handleBuffCheckBox();
        initAttackTypeComboBox(heroAttackType_cb);
        initAttackTypeComboBox(minionAttackType_cb);
        initBuffTypeComboBox();
        initFriendlyOrEnemyComboBox();
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
        getHeroAP_tf().setTextFormatter(Container.getOnlyNumberFormatter());
        getHeroHP_tf().setTextFormatter(Container.getOnlyNumberFormatter());
        getHeroCost_tf().setTextFormatter(Container.getOnlyNumberFormatter());
        getHeroRange_tf().setTextFormatter(Container.getOnlyNumberFormatter());
        getHeroCooldown_tf().setTextFormatter(Container.getOnlyNumberFormatter());
        getHeroManaCost_tf().setTextFormatter(Container.getOnlyNumberFormatter());

        getMinionAP_tf().setTextFormatter(Container.getOnlyNumberFormatter());
        getMinionHP_tf().setTextFormatter(Container.getOnlyNumberFormatter());
        getMinionCost_tf().setTextFormatter(Container.getOnlyNumberFormatter());
        getMinionRange_tf().setTextFormatter(Container.getOnlyNumberFormatter());
        getMinionManaCost_tf().setTextFormatter(Container.getOnlyNumberFormatter());

        getSpellManaCost_tf().setTextFormatter(Container.getOnlyNumberFormatter());
        getSpellCost_tf().setTextFormatter(Container.getOnlyNumberFormatter());


        getEffectValue_tf().setTextFormatter(Container.getOnlyNumberFormatter());
        getDelay_tf().setTextFormatter(Container.getOnlyNumberFormatter());
        getBuffLast_tf().setTextFormatter(Container.getOnlyNumberFormatter());


    }

    public void handleBuffCheckBox() {
        if (getBuff_checkbox().isSelected()) {
            makeVbEnable(getBuff_vb());
        } else {
            makeVbDisable(getBuff_vb());
        }
    }


    private void initAttackTypeComboBox(JFXComboBox<String> comboBox) {
        comboBox.getItems().removeAll(comboBox.getItems());
        comboBox.getItems().addAll(MELEE, RANGED, HYBRID);
        comboBox.getSelectionModel().select(MELEE);
    }

    private void initBuffTypeComboBox() {
        JFXComboBox<String> comboBox = getBuffType_cb();

        comboBox.getItems().removeAll(comboBox.getItems());
        comboBox.getItems().addAll(DISARM_BUFF, FLAME_BUFF, HOLY_BUFF, POISON_BUFF, POWER_BUFF, STUN_BUFF, WEAKNESS_BUFF);
        comboBox.getSelectionModel().select(HOLY_BUFF);

    }

    private void initFriendlyOrEnemyComboBox() {
        JFXComboBox<String> comboBox = getFriendOrEnemy_cb();

        comboBox.getItems().removeAll(comboBox.getItems());
        comboBox.getItems().addAll(FRIENDLY, ENEMY);
        comboBox.getSelectionModel().select(FRIENDLY);

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
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image files", "*.png"));

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
            String name = getHeroCardName_tf().getText();
            String desc = getHeroDesc_ta().getText();
            if (name == null || name.length() <= 0) {
                return;
            }
            int ap = Integer.parseInt(getHeroAP_tf().getText());
            int hp = Integer.parseInt(getHeroHP_tf().getText());
            int cost = Integer.parseInt(getHeroCost_tf().getText());
            int range = Integer.parseInt(getHeroRange_tf().getText());
            int cooldown = Integer.parseInt(getHeroCooldown_tf().getText());
            int manaCost = Integer.parseInt(getHeroManaCost_tf().getText());

            AttackKind attackKind = AttackKind.getFromString(getHeroAttackType_cb().getValue());
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
            String name = getMinionCardName_tf().getText();
            String desc = getMinionDesc_ta().getText();
            if (name == null || name.length() <= 0) {
                return;
            }
            int ap = Integer.parseInt(getMinionAP_tf().getText());
            int hp = Integer.parseInt(getMinionHP_tf().getText());
            int cost = Integer.parseInt(getMinionCost_tf().getText());
            int range = Integer.parseInt(getMinionRange_tf().getText());
            int manaCost = Integer.parseInt(getMinionManaCost_tf().getText());
            AttackKind attackKind = AttackKind.getFromString(getMinionAttackType_cb().getValue());
            ArrayList<String> imagesAddress = createImagesInFolderAndGiveAddress(name);
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
            result.add("./res/Accounts/" + Account.getLoggedAccount().getUsername() + "/Cards" + "/" + nameOfCard + "_image.png");
        } else {
            result.add("./res/Characters/generals/general_f1.png");
        }

        if (!getIdleGifAddress().equals(NO_ADDRESS)) {
            result.add("./res/Accounts/" + Account.getLoggedAccount().getUsername() + "/Cards" + "/" + nameOfCard + "_idle.gif");
        } else {
            result.add("./res/gifs/f1_altgeneral/idle_t.gif");
        }

        if (!getRunGifAddress().equals(NO_ADDRESS)) {
            result.add("./res/Accounts/" + Account.getLoggedAccount().getUsername() + "/Cards" + "/" + nameOfCard + "_run.gif");
        } else {
            result.add("./res/gifs/f1_altgeneral/run_t.gif");
        }

        if (!getAttackGifAddress().equals(NO_ADDRESS)) {
            result.add("./res/Accounts/" + Account.getLoggedAccount().getUsername() + "/Cards" + "/" + nameOfCard + "_attack.gif");
        } else {
            result.add("./res/gifs/f1_altgeneral/attack_t.gif");
        }
        if (!getHitGifAddress().equals(NO_ADDRESS)) {
            result.add("./res/Accounts/" + Account.getLoggedAccount().getUsername() + "/Cards" + "/" + nameOfCard + "_hit.gif");
        } else {
            result.add("./res/gifs/f1_altgeneral/hit_t.gif");
        }

        if (!getDeathGifAddress().equals(NO_ADDRESS)) {
            result.add("./res/Accounts/" + Account.getLoggedAccount().getUsername() + "/Cards" + "/" + nameOfCard + "_death.gif");
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
        makeVbEnable(getSpell_vb());
        makeVbDisable(getMinion_vb());
        makeVbDisable(getHero_vb());

    }

    public void handleMinionRb() {
        System.out.println("MINION");

        makeVbEnable(getMinion_vb());
        makeVbDisable(getSpell_vb());
        makeVbDisable(getHero_vb());

    }

    public void handleHeroRb() {
        System.out.println("HERO");

        makeVbEnable(getHero_vb());
        makeVbDisable(getMinion_vb());
        makeVbDisable(getSpell_vb());

    }

    public void handleBackBtn() {
        Container.handleBack();
    }

    public void selectImageGlow() {
        selectImage_img.setImage(new Image("res/ui/button_primary_glow.png"));
    }

    public void selectImageGlowDisappear() {
        selectImage_img.setImage(new Image("res/ui/button_primary.png"));
    }

    public void selectIdleGifGlow() {
        selectIdleGif_img.setImage(new Image("res/ui/button_primary_glow.png"));
    }

    public void selectIdleGifGlowDisappear() {
        selectIdleGif_img.setImage(new Image("res/ui/button_primary.png"));
    }

    public void selectRunGifGlow() {
        selectRunGif_img.setImage(new Image("res/ui/button_primary_glow.png"));

    }

    public void selectRunGifGlowDisappear() {
        selectRunGif_img.setImage(new Image("res/ui/button_primary.png"));

    }

    public void selectAttackGifGlow() {
        selectAttackGif_img.setImage(new Image("res/ui/button_primary_glow.png"));
    }

    public void selectAttackGifGlowDisappear() {
        selectAttackGif_img.setImage(new Image("res/ui/button_primary.png"));
    }

    public void selectDeathGifGlow() {
        selectDeathGif_img.setImage(new Image("res/ui/button_primary_glow.png"));
    }

    public void selectDeathGifGlowDisappear() {
        selectDeathGif_img.setImage(new Image("res/ui/button_primary.png"));
    }

    public void selectHitGifGlow() {
        selectHitGif_img.setImage(new Image("res/ui/button_primary_glow.png"));
    }

    public void selectHitGifGlowDisappear() {
        selectHitGif_img.setImage(new Image("res/ui/button_primary.png"));
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


    public StackPane getStackPane() {
        return stackPane;
    }

    public JFXRadioButton getHero_rb() {
        return hero_rb;
    }

    public JFXRadioButton getMinion_rb() {
        return minion_rb;
    }

    public JFXRadioButton getSpell_rb() {
        return spell_rb;
    }

    public VBox getHero_vb() {
        return hero_vb;
    }

    public VBox getMinion_vb() {
        return minion_vb;
    }

    public VBox getSpell_vb() {
        return spell_vb;
    }

    public VBox getBuff_vb() {
        return buff_vb;
    }

    public JFXButton getBack_btn() {
        return back_btn;
    }

    public JFXComboBox<String> getHeroAttackType_cb() {
        return heroAttackType_cb;
    }

    public JFXComboBox<String> getMinionAttackType_cb() {
        return minionAttackType_cb;
    }

    public JFXComboBox<String> getFriendOrEnemy_cb() {
        return friendOrEnemy_cb;
    }

    public JFXComboBox<String> getBuffType_cb() {
        return buffType_cb;
    }

    public JFXTextField getHeroCardName_tf() {
        return heroCardName_tf;
    }

    public JFXTextField getHeroAP_tf() {
        return heroAP_tf;
    }

    public JFXTextField getHeroHP_tf() {
        return heroHP_tf;
    }

    public JFXTextField getHeroCost_tf() {
        return heroCost_tf;
    }

    public JFXTextField getHeroRange_tf() {
        return heroRange_tf;
    }

    public JFXTextField getHeroManaCost_tf() {
        return heroManaCost_tf;
    }

    public JFXTextField getHeroCooldown_tf() {
        return heroCooldown_tf;
    }

    public JFXTextField getMinionCardName_tf() {
        return minionCardName_tf;
    }

    public JFXTextField getMinionAP_tf() {
        return minionAP_tf;
    }

    public JFXTextField getMinionHP_tf() {
        return minionHP_tf;
    }

    public JFXTextField getMinionCost_tf() {
        return minionCost_tf;
    }

    public JFXTextField getMinionRange_tf() {
        return minionRange_tf;
    }

    public JFXTextField getMinionManaCost_tf() {
        return minionManaCost_tf;
    }

    public JFXTextField getSpellManaCost_tf() {
        return spellManaCost_tf;
    }

    public JFXTextField getSpellCardName_tf() {
        return spellCardName_tf;
    }

    public JFXTextField getSpellCost_tf() {
        return spellCost_tf;
    }

    public JFXTextField getBuffName_tf() {
        return buffName_tf;
    }

    public JFXTextField getEffectValue_tf() {
        return effectValue_tf;
    }

    public JFXTextField getDelay_tf() {
        return delay_tf;
    }

    public JFXTextField getBuffLast_tf() {
        return buffLast_tf;
    }

    public JFXTextArea getHeroDesc_ta() {
        return heroDesc_ta;
    }

    public JFXTextArea getMinionDesc_ta() {
        return minionDesc_ta;
    }

    public JFXCheckBox getBuff_checkbox() {
        return buff_checkbox;
    }
}
