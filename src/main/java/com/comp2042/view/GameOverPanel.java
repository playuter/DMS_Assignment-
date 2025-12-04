package com.comp2042.view;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class GameOverPanel extends BorderPane {

    private final Button restartButton;
    private final Button mainMenuButton;
    private final Button leaderboardButton;
    private final Button quitButton;

    public GameOverPanel() {
        this.setStyle("-fx-background-color: rgba(0, 0, 0, 0.8);"); // Apply background to the entire panel

        VBox content = new VBox(20);
        content.setAlignment(Pos.CENTER);
        content.setStyle("-fx-padding: 20;");

        final Label gameOverLabel = new Label("GAME OVER");
        gameOverLabel.getStyleClass().add("gameOverStyle");
        
        restartButton = new Button("Restart");
        restartButton.getStyleClass().add("ipad-dark-grey");

        mainMenuButton = new Button("Main Menu");
        mainMenuButton.getStyleClass().add("ipad-dark-grey");
        
        leaderboardButton = new Button("Leaderboard");
        leaderboardButton.getStyleClass().add("ipad-dark-grey");
        
        quitButton = new Button("Save & Quit");
        quitButton.getStyleClass().add("ipad-dark-grey");

        content.getChildren().addAll(gameOverLabel, restartButton, mainMenuButton, leaderboardButton, quitButton);
        setCenter(content);
    }

    public Button getRestartButton() {
        return restartButton;
    }

    public Button getMainMenuButton() {
        return mainMenuButton;
    }
    
    public Button getLeaderboardButton() {
        return leaderboardButton;
    }
    
    public Button getQuitButton() {
        return quitButton;
    }
}
