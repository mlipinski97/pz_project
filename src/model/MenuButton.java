package model;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class MenuButton extends Button {

    public static final String PATH_FONT = "src/res/fonts/goldmihner_.ttf";
    private final String BUTTON_PRESSED_STYLE = "-fx-background-color: transparent; -fx-background-image: url('/res/button_pressed.png')";
    private final String BUTTON_FREE_STYLE = "-fx-background-color: transparent; -fx-background-image: url('/res/button_free.png')";

    /**
     * MenuButton constructor creating custom button with given text
     * @param text
     */
    public MenuButton(String text) {
        setText(text);
        setButtonFont();
        setPrefHeight(48);
        setPrefWidth(200);
        setStyle(BUTTON_FREE_STYLE);
        initializeButtonListeners();
    }

    private void setButtonFont() {
        try {
            setFont(Font.loadFont(new FileInputStream(PATH_FONT), 20));
            setTextFill(Color.BROWN);
        } catch (FileNotFoundException e) {
            setFont(Font.font("Times New Roman", 20));
            setTextFill(Color.BROWN);
        }
    }

    private void setButtonPressedStyle() {
        setStyle(BUTTON_PRESSED_STYLE);
        setPrefHeight(44);
        setLayoutY(getLayoutY() + 4);
    }

    private void setButtonReleasedStyle() {
        setStyle(BUTTON_FREE_STYLE);
        setPrefHeight(48);
        setLayoutY(getLayoutY() - 4);
    }


    private void initializeButtonListeners() {
        setOnMousePressed(event -> {
            if(event.getButton().equals(MouseButton.PRIMARY)) {
                setButtonPressedStyle();
            }
        });

        setOnMouseReleased(event -> {
            if(event.getButton().equals(MouseButton.PRIMARY)) {
                setButtonReleasedStyle();
            }
        });
        setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                setEffect(new DropShadow());
            }
        });
        setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                setEffect(null);
            }
        });
    }
}
