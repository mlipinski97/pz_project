package model;

import com.sun.istack.internal.NotNull;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import utils.MinerStateListener;

import java.util.ArrayList;
import java.util.List;

public class GameInterface {

    private static final String PATH_PICKAXE_ICON_SPRITE = "res/icon_pickaxe.png";
    private static final String PATH_GOLD_ICON_SPRITE = "res/icon_gold.png";
    private static final String PATH_STATUE_ICON_SPRITE = "res/icon_statue.png";
    private static final Integer STARTING_PICKAXE_NUMBER = 50;

    private int gold;
    private int pickaxeDurability;
    private int statue;
    private Text goldValueText = new Text();
    private Text pickaxeDurabilityText = new Text();
    private Text statueText = new Text();
    private ImageView pickaxeSprite = new ImageView();
    private ImageView goldSprite = new ImageView();
    private ImageView statueSprite = new ImageView();
    private AnchorPane gamePane;

    private final List<MinerStateListener> listeners = new ArrayList<>();

    public void addListener(@NotNull MinerStateListener listener){
        listeners.add(listener);
    }

    public  int getPickaxeDurability() {
        return pickaxeDurability;
    }

    public void setPickaxeDurability(int pickaxeDurability) {
        this.pickaxeDurability = pickaxeDurability;
        updatePickAxeDurability();
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
        updateGoldValue();
    }

    private void updateGoldValue() {
        goldValueText.setText(Integer.toString(gold));
    }

    private void updatePickAxeDurability(){
        pickaxeDurabilityText.setText(Integer.toString(pickaxeDurability));
        if(pickaxeDurability <= 0){
            fireGameOverEvent();
        }
    }

    private void fireGameOverEvent() {
        for(MinerStateListener l : listeners){
            l.onStateChange();
        }
    }

    private void updateStatueValue(){
        statueText.setText(Integer.toString(statue));
    }

    /**
     * GameInterface constructor initializing interface with set values
     * @param gamePane Anchor pane on which interface will be displayed
     */
    public GameInterface(AnchorPane gamePane){
        this.gamePane = gamePane;
        initInterface();
    }

    private void initInterface(){
        goldValueText.setTranslateX(865);
        goldValueText.setTranslateY(65);
        goldValueText.setFill(Color.WHITE);
        goldValueText.setFont(Font.font(60));
        pickaxeDurabilityText.setTranslateX(715);
        pickaxeDurabilityText.setTranslateY(65);
        pickaxeDurabilityText.setFill(Color.WHITE);
        pickaxeDurabilityText.setFont(Font.font(60));
        statueText.setTranslateX(565);
        statueText.setTranslateY(65);
        statueText.setFill(Color.WHITE);
        statueText.setFont(Font.font(60));
        setGold(0);
        setPickaxeDurability(STARTING_PICKAXE_NUMBER);
        setStatue(0);


        goldSprite.setImage(new Image(PATH_GOLD_ICON_SPRITE));
        goldSprite.setTranslateY(5);
        goldSprite.setTranslateX(800);
        pickaxeSprite.setImage(new Image(PATH_PICKAXE_ICON_SPRITE));
        pickaxeSprite.setTranslateY(5);
        pickaxeSprite.setTranslateX(650);
        statueSprite.setImage(new Image(PATH_STATUE_ICON_SPRITE));
        statueSprite.setTranslateY(5);
        statueSprite.setTranslateX(500);
        this.gamePane.getChildren().addAll(goldSprite, pickaxeSprite, goldValueText, pickaxeDurabilityText, statueText, statueSprite);
    }

    public int getStatue() {
        return statue;
    }

    public void setStatue(int statue) {
        this.statue = statue;
        updateStatueValue();
    }

    /**
     * method setting game interface to the top of the screen making it always visible
     */
    public void toFront(){
        goldValueText.toFront();
        pickaxeDurabilityText.toFront();
        statueText.toFront();
        goldSprite.toFront();
        statueSprite.toFront();
        pickaxeSprite.toFront();
    }
}
