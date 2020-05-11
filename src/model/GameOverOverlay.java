package model;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import utils.DbConnect;
import utils.MinerStateListener;

import java.util.ArrayList;

import static utils.Sound.stopBackground;
import static view.GameViewManager.gameInterface;
import static view.GameViewManager.miner_one;

public class GameOverOverlay implements MinerStateListener {

    public static final int MENU_BUTTONS_STARTING_X = 380;
    public static final int MENU_BUTTONS_STARTING_Y = 500;

    private Rectangle shadowOverlay;
    private boolean isVisible;
    private Text pauseLabel = new Text();
    private ArrayList<MenuButton> buttonList = new ArrayList<>();
    private AnchorPane gamePane;
    private Stage gameStage;
    private Stage menuStage;
    private DbConnect databaseConntector;
    private TextField playerNameText = new TextField();

    /**
     * GameOverOverlay constructor creating overlay and setting it hidden
     * @param menuStage stage to be shown after submit button is pressed
     * @param gameStage stage to be hidden after submit button is pressed
     * @param gamePane Pane on which gammeover screen will be shown
     * @param width width of the game over screen
     * @param height height of the game over screen
     */
    public GameOverOverlay(Stage menuStage, Stage gameStage, AnchorPane gamePane, int width, int height){
        this.gamePane = gamePane;
        this.gameStage = gameStage;
        this.menuStage = menuStage;
        createShadowOverlay(width, height);
        createGameOverTexts();
        createButtons();
        databaseConntector = new DbConnect();
        gameInterface.addListener(this);
    }

    private void showGameOverOverlay(){
        miner_one.setAbleToMove(false);
        shadowOverlay.setVisible(true);
        shadowOverlay.toFront();
        for(MenuButton b : buttonList){
            b.setVisible(true);
            b.toFront();
        }
        pauseLabel.setVisible(true);
        pauseLabel.toFront();
        playerNameText.setVisible(true);
        playerNameText.toFront();
    }

    private void createShadowOverlay(int width, int height){
        shadowOverlay = new Rectangle(0, 0, width, height);
        shadowOverlay.setOpacity(0.6);
        shadowOverlay.setVisible(false);
        gamePane.getChildren().add(shadowOverlay);
    }

    private void createGameOverTexts(){
        pauseLabel.setText("GAME OVER!");
        pauseLabel.setLayoutX(360);
        pauseLabel.setLayoutY(250);
        pauseLabel.setFont(Font.font(40));
        pauseLabel.setFill(Color.WHITE);
        pauseLabel.setVisible(false);

        playerNameText.setLayoutX(250);
        playerNameText.setLayoutY(350);
        playerNameText.setFont(Font.font(40));
        playerNameText.setVisible(false);

        gamePane.getChildren().addAll(pauseLabel, playerNameText);
    }


    private void addMenuButton(MenuButton button){
        button.setLayoutX(MENU_BUTTONS_STARTING_X + buttonList.size() * 250);
        button.setLayoutY(MENU_BUTTONS_STARTING_Y);
        buttonList.add(button);
        button.setVisible(false);
        gamePane.getChildren().add(button);
    }
    private void createButtons(){
        createSubmitButton();
    }


    private void createSubmitButton(){
        MenuButton buttonSubmit = new MenuButton("Submit!");
        addMenuButton(buttonSubmit);

        buttonSubmit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(!playerNameText.getText().isEmpty()){
                    try{
                        databaseConntector.insertPlayerResult(playerNameText.getText(), gameInterface.getGold());
                    } catch (Exception e){
                        System.out.println("Cannot connect to the database!!!");
                    }
                    menuStage.show();
                    stopBackground();
                    gameStage.close();
                }
            }
        });
    }

    /**
     * MinerStateListener interface method. Overrided to handle GameOver event
     */
    @Override
    public void onStateChange() {
        this.showGameOverOverlay();
    }
}
