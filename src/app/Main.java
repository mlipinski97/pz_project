package app;

import javafx.application.Application;
import javafx.stage.Stage;
import view.MenuViewManager;

public class Main extends Application {

    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        MenuViewManager manager = new MenuViewManager();
        primaryStage = manager.getMainStage();
        primaryStage.show();
    }
}
