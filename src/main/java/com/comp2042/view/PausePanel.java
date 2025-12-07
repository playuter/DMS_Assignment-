package com.comp2042.view;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

/**
 * Panel displayed when the game is paused.
 * Provides options to resume, restart, change settings, or return to the main menu.
 * Includes a semi-transparent background for a full-screen overlay effect.
 */
public class PausePanel extends BorderPane {

    private final Button resumeButton;
    private final Button restartButton;
    private final Button mainMenuButton;
    private final Button settingsButton;

    /**
     * Creates a new PausePanel.
     * Initializes the layout, label, and navigation buttons.
     */
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
        
        settingsButton = new Button("Settings");
        settingsButton.getStyleClass().add("ipad-dark-grey");

        mainMenuButton = new Button("Main Menu");
        mainMenuButton.getStyleClass().add("ipad-dark-grey");

        content.getChildren().addAll(pauseLabel, resumeButton, restartButton, settingsButton, mainMenuButton);
        setCenter(content);
    }

    /**
     * Gets the Resume button.
     * 
     * @return The resume button.
     */
    public Button getResumeButton() {
        return resumeButton;
    }

    /**
     * Gets the Restart button.
     * 
     * @return The restart button.
     */
    public Button getRestartButton() {
        return restartButton;
    }
    
    /**
     * Gets the Settings button.
     * 
     * @return The settings button.
     */
    public Button getSettingsButton() {
        return settingsButton;
    }

    /**
     * Gets the Main Menu button.
     * 
     * @return The main menu button.
     */
    public Button getMainMenuButton() {
        return mainMenuButton;
    }
}
