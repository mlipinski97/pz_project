package view;

import Entity.Miner;
import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.GameInterface;
import model.GameOverOverlay;
import model.Map;
import model.PauseOverlay;


import static utils.Sound.playBackground;

public class GameViewManager {

    public static final String PATH_BACKGROUND_IMAGE = "res/cavern_background.jpg";
    public static final int WINDOW_HEIGHT = 840;
    public static final int WINDOW_WIDTH = 960;


    private AnchorPane minerPane;
    private Scene minerScene;
    private Stage minerStage;
    private Stage menuStage;
    private AnimationTimer gameTimer;
    public static Miner miner_one;
    public static Map gameMap;
    public static GameInterface gameInterface;
    private PauseOverlay pauseOverlay;
    private GameOverOverlay gameOverOverlay;

    /**
     * GameViewManager constructor initializing stage for a new game and creating new key listeners
     */
    public GameViewManager(){
        initStage();
        createKeyListeners();
    }



    private void createKeyListeners() {
        minerScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if(event.getCode() == KeyCode.W || event.getCode() == KeyCode.UP){
                    miner_one.setUpKeyPressed(true);
                    if(miner_one.getUpKeyPressed() && !miner_one.getRightKeyPressed() && !miner_one.getLeftKeyPressed() && !miner_one.getDownKeyPressed()){
                        miner_one.move(Miner.MinerDirectionEnum.UP);
                        System.out.println("Movment up");
                    }
                } else if(event.getCode() == KeyCode.S || event.getCode() == KeyCode.DOWN){
                    miner_one.setDownKeyPressed(true);
                    if(miner_one.getDownKeyPressed() && !miner_one.getRightKeyPressed() && !miner_one.getUpKeyPressed() && !miner_one.getLeftKeyPressed()){
                        miner_one.move(Miner.MinerDirectionEnum.DOWN);
                        System.out.println("Movment down");
                    }
                } else if(event.getCode() == KeyCode.A || event.getCode() == KeyCode.LEFT){
                    miner_one.setLeftKeyPressed(true);
                    if(miner_one.getLeftKeyPressed() && !miner_one.getRightKeyPressed() && !miner_one.getUpKeyPressed() && !miner_one.getDownKeyPressed()){
                        miner_one.move(Miner.MinerDirectionEnum.LEFT);
                        System.out.println("Movment left");
                    }
                } else if(event.getCode() == KeyCode.D || event.getCode() == KeyCode.RIGHT){
                    miner_one.setRightKeyPressed(true);
                    if(miner_one.getRightKeyPressed() && !miner_one.getLeftKeyPressed() && !miner_one.getUpKeyPressed() && !miner_one.getDownKeyPressed()){
                        miner_one.move(Miner.MinerDirectionEnum.RIGHT);
                        System.out.println("Movment right");
                    }
                } else if(event.getCode() == KeyCode.E){
                    miner_one.mine();
                    miner_one.setAbleToMove(false);
                } else if(event.getCode() == KeyCode.ESCAPE){
                    pauseOverlay.showPauseOverlay();
                }
            }
        });

        minerScene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if(event.getCode() == KeyCode.W || event.getCode() == KeyCode.UP){
                    miner_one.setUpKeyPressed(false);
                } else if(event.getCode() == KeyCode.S || event.getCode() == KeyCode.DOWN){
                    miner_one.setDownKeyPressed(false);
                } else if(event.getCode() == KeyCode.A || event.getCode() == KeyCode.LEFT){
                    miner_one.setLeftKeyPressed(false);
                } else if(event.getCode() == KeyCode.D || event.getCode() == KeyCode.RIGHT){
                    miner_one.setRightKeyPressed(false);
                } else if(event.getCode() == KeyCode.E){
                }
            }
        });
    }

    private void initStage() {
        minerPane = new AnchorPane();
        minerScene = new Scene(minerPane, WINDOW_WIDTH, WINDOW_HEIGHT);
        minerStage = new Stage();
        createBackground();
        minerStage.setResizable(false);
        minerStage.initStyle(StageStyle.UNDECORATED);
        createGameLoop();
        minerStage.setScene(minerScene);
        gameMap = new Map(minerPane);
        playBackground();
    }

    private void createBackground(){
        Image backgroundImage = new Image(PATH_BACKGROUND_IMAGE, WINDOW_WIDTH, WINDOW_HEIGHT, false, false);
        BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, null);
        minerPane.setBackground(new Background(background));
    }

    /**
     * sets up new game window on given stage
     * @param mStage stage on which game will be set
     */
    public void createNewGame(Stage mStage){
        this.menuStage = mStage;
        menuStage.hide();
        miner_one = new Miner(minerPane,60,60);
        gameInterface = new GameInterface(minerPane);
        minerStage.show();
        pauseOverlay = new PauseOverlay(menuStage, minerStage, minerPane, WINDOW_WIDTH, WINDOW_HEIGHT);
        gameOverOverlay = new GameOverOverlay(menuStage, minerStage, minerPane, WINDOW_WIDTH, WINDOW_HEIGHT);
    }

    private void createGameLoop(){
        gameTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if(gameInterface.getStatue() > 2 && miner_one.getCurrentX() == 0 && miner_one.getCurrentY() == 60){
                    gameInterface.setStatue(gameInterface.getStatue()-3);
                    miner_one.setCurrentX(60);
                    miner_one.setCurrentY(60);
                    gameMap.clearMap();
                    gameMap = new Map(minerPane);
                    gameInterface.setPickaxeDurability(gameInterface.getPickaxeDurability()+10);
                    miner_one.updateShadow();
                }
            }
        };
        gameTimer.start();
    }
}
