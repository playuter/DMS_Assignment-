package com.comp2042.view;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class PausePanel extends BorderPane {

    private final Button resumeButton;
    private final Button restartButton;
    private final Button mainMenuButton;

    public PausePanel() {
        VBox content = new VBox(20);
        content.setAlignment(Pos.CENTER);
        content.setStyle("-fx-background-color: rgba(0, 0, 0, 0.8); -fx-padding: 20;");

        final Label pauseLabel = new Label("PAUSED");
        pauseLabel.getStyleClass().add("gameOverStyle"); 
        
        resumeButton = new Button("Resume");
        resumeButton.getStyleClass().add("ipad-dark-grey");
        
        restartButton = new Button("Restart");
        restartButton.getStyleClass().add("ipad-dark-grey");
        
        mainMenuButton = new Button("Main Menu");
        mainMenuButton.getStyleClass().add("ipad-dark-grey");

        content.getChildren().addAll(pauseLabel, resumeButton, restartButton, mainMenuButton);
        setCenter(content);
    }

    public Button getResumeButton() {
        return resumeButton;
    }

    public Button getRestartButton() {
        return restartButton;
    }

    public Button getMainMenuButton() {
        return mainMenuButton;
    }
}

