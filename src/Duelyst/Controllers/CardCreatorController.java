package Duelyst.Controllers;

import Duelyst.Model.AttackKind;
import Duelyst.Model.Hero;
import Duelyst.Model.Minion;
import Duelyst.View.Constants;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.VBox;

import java.util.function.UnaryOperator;

public class CardCreatorController {

    public final String MELEE = "Melee", HYBRID = "Hybrid", RANGED = "RANGED";

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
    JFXComboBox<String> heroAttackType_cb;
    @FXML
    JFXComboBox<String> minionAttackType_cb;

    @FXML
    JFXTextField heroCardName_tf, heroAP_tf, heroHP_tf, heroCost_tf, heroRange_tf, heroManaCost_tf, heroCooldown_tf;
    @FXML
    JFXTextField minionCardName_tf, minionAP_tf, minionHP_tf, minionCost_tf, minionRange_tf, minionManaCost_tf;
    @FXML
    JFXTextArea heroDesc_ta , minionDesc_ta;


    @FXML
    public void initialize() {
        hero_rb.setSelected(true);
        handleHeroRb();
        initAttackTypeComboBox(heroAttackType_cb);
        initAttackTypeComboBox(minionAttackType_cb);
        setDigitTextLimitaions();


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

    public void createHero() {
        System.out.println("Create Hero");
        String name = heroCardName_tf.getText();
        int ap = Integer.parseInt(heroAP_tf.getText());
        int hp = Integer.parseInt(heroHP_tf.getText());
        int cost = Integer.parseInt(heroCost_tf.getText());
        int range = Integer.parseInt(heroRange_tf.getText());
        int cooldown = Integer.parseInt(heroCooldown_tf.getText());
        int manaCost = Integer.parseInt(heroManaCost_tf.getText());
        String desc = heroDesc_ta.getText();
        AttackKind attackKind = AttackKind.getFromString(heroAttackType_cb.getValue());

        //TODO IMAGES MUST BE OKD
        Hero hero = new Hero(name, desc, manaCost, cost, hp, ap, range, attackKind, 0, "./res/gifs/f1_altgeneral/idle_t.gif",
                "./res/gifs/f1_altgeneral/idle_t.gif",
                "./res/gifs/f1_altgeneral/idle_t.gif",
                "./res/gifs/f1_altgeneral/idle_t.gif",
                "./res/gifs/f1_altgeneral/idle_t.gif",
                "./res/gifs/f1_altgeneral/idle_t.gif",
                cooldown);
        System.out.println(attackKind);


        //   ./res/gifs/f1_altgeneral/idle_t.gif

    }

    public void createMinion() {
        System.out.println("Create Minion");
        String desc = minionDesc_ta.getText();
        String name = heroCardName_tf.getText();
        int ap = Integer.parseInt(minionAP_tf.getText());
        int hp = Integer.parseInt(minionHP_tf.getText());
        int cost = Integer.parseInt(minionCost_tf.getText());
        int range = Integer.parseInt(minionRange_tf.getText());
        int manaCost = Integer.parseInt(minionManaCost_tf.getText());
        AttackKind attackKind = AttackKind.getFromString(minionAttackType_cb.getValue());

        //TODO IMAGES MUST BE OKD
        Minion minion = new Minion(name, desc, manaCost, cost, hp, ap, range, attackKind, 0, "./res/gifs/f1_altgeneral/idle_t.gif",
                "./res/gifs/f1_altgeneral/idle_t.gif",
                "./res/gifs/f1_altgeneral/idle_t.gif",
                "./res/gifs/f1_altgeneral/idle_t.gif",
                "./res/gifs/f1_altgeneral/idle_t.gif",
                "./res/gifs/f1_altgeneral/idle_t.gif"
        );
        System.out.println(attackKind);
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

}
