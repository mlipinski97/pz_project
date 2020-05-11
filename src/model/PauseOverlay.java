package model;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import utils.MinerStateListener;

import java.util.ArrayList;

import static utils.Sound.stopBackground;
import static view.GameViewManager.gameInterface;
import static view.GameViewManager.miner_one;

public class PauseOverlay implements MinerStateListener {

    public static final int MENU_BUTTONS_STARTING_X = 250;
    public static final int MENU_BUTTONS_STARTING_Y = 450;

    private Rectangle shadowOverlay;
    private boolean isVisible;
    private Text pauseLabel = new Text();
    private ArrayList<MenuButton> buttonList = new ArrayList<>();
    private AnchorPane gamePane;
    private Stage gameStage;
    private Stage menuStage;

    /**
     * pauseOverlay constructor creating overplay pausing the game with given height and width
     * @param menuStage Stage to be shown after player exits the game
     * @param gameStage stage to be hidden after player exits the game
     * @param gamePane pane on which overlay will be added
     * @param width width of the overlay
     * @param height height of the overlay
     */
    public PauseOverlay(Stage menuStage, Stage gameStage, AnchorPane gamePane, int width, int height) {
        this.gamePane = gamePane;
        this.menuStage = menuStage;
        this.gameStage = gameStage;
        createShadowOverlay(width, height);
        createPauseText();
        createButtons();
    }

    /**
     * method showing pause overlay and pausing the game
     */
    public void showPauseOverlay(){
        miner_one.setAbleToMove(false);
        shadowOverlay.setVisible(true);
        shadowOverlay.toFront();
        for(MenuButton b : buttonList){
            b.setVisible(true);
            b.toFront();
        }
        pauseLabel.setVisible(true);
        pauseLabel.toFront();
    }

    private void hidePauseOverlay(){
        shadowOverlay.setVisible(false);
        for(MenuButton b : buttonList){
            b.setVisible(false);
        }
        pauseLabel.setVisible(false);
        miner_one.setAbleToMove(true);
    }

    private void createShadowOverlay(int width, int height){
        shadowOverlay = new Rectangle(0, 0, width, height);
        shadowOverlay.setOpacity(0.6);
        shadowOverlay.setVisible(false);
        gamePane.getChildren().add(shadowOverlay);
    }

    private void createPauseText(){
        pauseLabel.setText("GAME PAUSED!");
        pauseLabel.setLayoutX(350);
        pauseLabel.setLayoutY(350);
        pauseLabel.setFont(Font.font(40));
        pauseLabel.setFill(Color.WHITE);
        pauseLabel.setVisible(false);
        gamePane.getChildren().add(pauseLabel);
    }


    private void addMenuButton(MenuButton button){
        button.setLayoutX(MENU_BUTTONS_STARTING_X + buttonList.size() * 250);
        button.setLayoutY(MENU_BUTTONS_STARTING_Y);
        buttonList.add(button);
        button.setVisible(false);
        gamePane.getChildren().add(button);
    }
    private void createButtons(){
        createResumeButton();
        createExitButton();
    }

    private void createResumeButton() {
        MenuButton buttonExit = new MenuButton("Resume Game!");
        addMenuButton(buttonExit);

        buttonExit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                hidePauseOverlay();
            }
        });
    }

    private void createExitButton(){
        MenuButton buttonExit = new MenuButton("Exit game!");
        addMenuButton(buttonExit);

        buttonExit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                menuStage.show();
                stopBackground();
                gameStage.close();
            }
        });
    }

    @Override
    public void onStateChange() {
        showPauseOverlay();
    }
}
