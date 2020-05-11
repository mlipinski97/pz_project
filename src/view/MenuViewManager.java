package view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import utils.DbConnect;
import model.MenuButton;
import model.MinerSubscene;

import java.util.ArrayList;

public class MenuViewManager {

    public static final int WINDOW_HEIGHT = 840;
    public static final int WINDOW_WIDTH = 960;

    public static final int MENU_BUTTONS_STARTING_X = 100;
    public static final int MENU_BUTTONS_STARTING_Y = 150;
    ArrayList<MenuButton> buttonList = new ArrayList<>();

    public static final String PATH_BACKGROUND_IMAGE = "res/cavern_background.jpg";

    private AnchorPane mainPane;
    private Scene mainScene;
    private Stage mainStage;
    private MinerSubscene helpSubscene, creditsSubscene, leaderBoardsSubscene;
    private MinerSubscene subsceneToHide;
    private DbConnect databaseConnector;

    /**
     *Class constructor creating Menu window
     */
    public MenuViewManager(){
        mainPane = new AnchorPane();
        mainScene = new Scene(mainPane, WINDOW_WIDTH,WINDOW_HEIGHT);
        mainStage = new Stage();
        mainStage.setScene(mainScene);
        mainStage.setResizable(false);
        mainStage.setTitle("Miner - programowanie zdarzeniowe MM");
        mainStage.initStyle(StageStyle.UNDECORATED);
        databaseConnector = new DbConnect();
        createButtons();
        createBackground();
        createSubscene();
    }

    /**
     * getter for Main Stage
     * @return mainStage parameter
     */
    public Stage getMainStage() {
        return mainStage;
    }

    private void addMenuButton(MenuButton button){
        button.setLayoutX(MENU_BUTTONS_STARTING_X);
        button.setLayoutY(MENU_BUTTONS_STARTING_Y + buttonList.size() * 100);
        buttonList.add(button);
        mainPane.getChildren().add(button);
    }


    private void createStartButton(){
        MenuButton buttonStart = new MenuButton("Let's dig!");
        buttonStart.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                GameViewManager gameManager = new GameViewManager();
                gameManager.createNewGame(mainStage);
            }
        });
        addMenuButton(buttonStart);
    }

    private void createLeaderBoardButton(){
        MenuButton buttonLB = new MenuButton("Leaderboards" +
                "");
        addMenuButton(buttonLB);

        buttonLB.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                databaseConnector.queryTopPlayerResults();
                leaderBoardsSubscene.setScoreBoard(databaseConnector.getNameResults(), databaseConnector.getPointsResults());
                rollSubscene(leaderBoardsSubscene);
            }
        });
    }

    private void createExitButton(){
        MenuButton buttonExit = new MenuButton("Exit mines");
        addMenuButton(buttonExit);

        buttonExit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                mainStage.close();
            }
        });
    }

    private void createHelpButton(){
        MenuButton buttonHelp = new MenuButton("HELP! HELP!");
        addMenuButton(buttonHelp);

        buttonHelp.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                rollSubscene(helpSubscene);
            }
        });
    }

    private void createCreditsButton(){
        MenuButton buttonCredits = new MenuButton("About the Author");
        addMenuButton(buttonCredits);

        buttonCredits.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                rollSubscene(creditsSubscene);
            }
        });
    }

    private void createButtons(){
        createStartButton();
        createLeaderBoardButton();
        createHelpButton();
        createCreditsButton();
        createExitButton();
    }

    private void createBackground(){
        Image backgroundImage = new Image(PATH_BACKGROUND_IMAGE, WINDOW_WIDTH, WINDOW_HEIGHT, false, false);
        BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, null);
        mainPane.setBackground(new Background(background));
    }

    private void createSubscene(){
        helpSubscene = new MinerSubscene("W -> ruch w gore\nS - ruch w dol\nA -> ruch w lewo\nD -> ruch w prawo\nE -> kopanie\nESC -> pauza");
        creditsSubscene = new MinerSubscene("Wykonal: Maciej Lipinski\nGrupa: I7B3S1\nPrzedmiot: Programowanie Zdarzeniowe\nProwadzacy: mgr inż. Tomasz Derski");
        leaderBoardsSubscene = new MinerSubscene("");
        mainPane.getChildren().addAll(helpSubscene, creditsSubscene, leaderBoardsSubscene);
    }

    private void rollSubscene(MinerSubscene subsceneToMove){
        if(subsceneToHide != null){
            subsceneToHide.move();
        }
        subsceneToMove.move();
        subsceneToHide = subsceneToMove;
    }
}
