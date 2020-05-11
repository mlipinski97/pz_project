package model;

import javafx.animation.TranslateTransition;
import javafx.geometry.Pos;
import javafx.scene.SubScene;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;

import java.util.ArrayList;

public class MinerSubscene extends SubScene {

    public static final String PATH_FONT = "src/res/fonts/goldminer_.ttf";

    public static final int SUBSCENE_HEIGHT = 400;
    public static final int SUBSCENE_WIDTH = 600;

    Boolean isOnTheScreen;

    Label subsceneLabel = new Label();

    /**
     * Constructor creating subscene with given text
     * @param subscenetext subscene text to be displayed
     */
    public MinerSubscene(String subscenetext) {
        super(new AnchorPane(), SUBSCENE_WIDTH, SUBSCENE_HEIGHT);
        prefHeight(SUBSCENE_HEIGHT);
        prefWidth(SUBSCENE_WIDTH);

        AnchorPane root = (AnchorPane) this.getRoot();
        root.setBackground(Background.EMPTY);
        setLayoutX(970);
        setLayoutY(150);
        isOnTheScreen = false;
        root.getChildren().add(subsceneLabel);
        subsceneLabel.setLayoutX(10);
        subsceneLabel.setLayoutY(10);
        subsceneLabel.setText(subscenetext);
        subsceneLabel.setFont(new Font(PATH_FONT, 30));
        subsceneLabel.setAlignment(Pos.CENTER);
        subsceneLabel.setTextFill(Color.BEIGE);

    }

    /**
     * method moving subscene, showing it or hiding it depending on where it is
     */
    public void move(){
        TranslateTransition transition = new TranslateTransition();
        transition.setDuration(Duration.seconds(0.7));
        transition.setNode(this);

        if(isOnTheScreen){
            transition.setToX(610);
            isOnTheScreen = false;
        } else{
            transition.setToX(-610);
            isOnTheScreen = true;
        }
        transition.play();
    }

    /**
     * method setting scoreboard on the subscene
     * @param names names of the players
     * @param points points of the players
     */
    public void setScoreBoard(ArrayList<String> names, ArrayList<Integer> points){
        String leaderBoard = new String();
        for(int i=0; i < names.size(); i++){
            leaderBoard += names.get(i) + " - " + Integer.toString(points.get(i)) + "\n";
        }
        subsceneLabel.setText(leaderBoard);
    }
}
