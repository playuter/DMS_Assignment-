package com.comp2042.view;

import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;


public class GameOverPanel extends BorderPane {

    public GameOverPanel() {
        this.setStyle("-fx-background-color: rgba(0, 0, 0, 0.8);"); // Apply background to the entire panel
        final Label gameOverLabel = new Label("GAME OVER");
        gameOverLabel.getStyleClass().add("gameOverStyle");
        setCenter(gameOverLabel);
    }

}
