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
        this.setStyle("-fx-background-color: rgba(0, 0, 0, 0.8);"); // Apply background to the entire panel

        VBox content = new VBox(20);
        content.setAlignment(Pos.CENTER);
        content.setStyle("-fx-padding: 20;"); // Keep padding on the content container

        final Label pauseLabel = new Label("PAUSED");
        pauseLabel.getStyleClass().add("gameOverStyle"); 
        
        resumeButton = new Button("Resume");
        resumeButton.getStyleClass().add("ipad-dark-grey");
        
        restartButton = new Button("Restart");
        restartButton.getStyleClass().add("ipad-dark-grey");
        
        Button settingsButton = new Button("Settings");
        settingsButton.getStyleClass().add("ipad-dark-grey");
        // We can assign an ID or expose it via getter, or handle action via callback.
        // Since GuiController handles actions, we need to expose it.
        this.settingsButton = settingsButton;

        mainMenuButton = new Button("Main Menu");
        mainMenuButton.getStyleClass().add("ipad-dark-grey");

        content.getChildren().addAll(pauseLabel, resumeButton, restartButton, settingsButton, mainMenuButton);
        setCenter(content);
    }

    private final Button settingsButton;

    public Button getResumeButton() {
        return resumeButton;
    }

    public Button getRestartButton() {
        return restartButton;
    }
    
    public Button getSettingsButton() {
        return settingsButton;
    }

    public Button getMainMenuButton() {
        return mainMenuButton;
    }
}

