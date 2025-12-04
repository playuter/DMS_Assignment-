package com.comp2042.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.ButtonType;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.List;

import com.comp2042.logic.leaderboard.LeaderboardManager;
import com.comp2042.logic.leaderboard.PlayerScore;

import javafx.scene.control.ChoiceDialog;
import java.util.ArrayList;
import java.util.Optional;

public class MainMenuController implements Initializable {

    @FXML
    private TextField nameInput;
    
    private long selectedDelay = 400; // Default 400ms (Extra)

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        SoundManager.playBackgroundMusic("menu");
        // Load the font to ensure it's available for the menu
        try {
            Font.loadFont(getClass().getClassLoader().getResource("digital.ttf").toExternalForm(), 38);
        } catch (Exception e) {
            System.err.println("Could not load font: " + e.getMessage());
        }
    }

    @FXML
    public void startGame(ActionEvent event) {
        String playerName = nameInput.getText().trim();
        if (playerName.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Please enter your name!", ButtonType.OK);
            alert.showAndWait();
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("gameLayout.fxml"));
            Parent root = loader.load();
            
            // Get the controller and set up the game
            GuiController c = loader.getController();
            c.setPlayerName(playerName);
            c.setInitialFallSpeed(selectedDelay);
            new GameController(c); // Initialize game controller which sets up the game logic

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 550, 600); // Adjusted size for side panel
            stage.setScene(scene);
            stage.show();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void showHowToPlay(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("How to Play Tetris");
        alert.setHeaderText("Game Instructions & Controls");
        
        String instructions = 
            "Objective:\n" +
            "Arrange falling blocks to form complete horizontal lines.\n" +
            "Completed lines disappear and earn you points.\n" +
            "Don't let the blocks stack up to the top!\n\n" +
            "Controls:\n" +
            "← / → : Move block Left / Right\n" +
            "↑ : Rotate block\n" +
            "↓ : Soft Drop (Accelerate fall)\n" +
            "SPACE : Hard Drop (Instant fall)\n" +
            "P : Pause / Resume Game\n\n" +
            "Scoring:\n" +
            "Clear lines to increase your score.\n" +
            "Try to beat the High Score!";
            
        alert.setContentText(instructions);
        alert.getDialogPane().setMinWidth(400);
        alert.showAndWait();
    }

    @FXML
    public void showLevels(ActionEvent event) {
        List<String> choices = new ArrayList<>();
        choices.add("Normal");
        choices.add("Extra");
        choices.add("Insane");

        ChoiceDialog<String> dialog = new ChoiceDialog<>("Extra", choices);
        dialog.setTitle("Select Level");
        dialog.setHeaderText("Choose your difficulty level:");
        dialog.setContentText("Level:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(level -> {
            switch (level) {
                case "Normal": 
                    selectedDelay = 600; 
                    SoundManager.playBackgroundMusic("normal");
                    break;
                case "Extra": 
                    selectedDelay = 400; 
                    // Extra might use default or another track
                    break;
                case "Insane": 
                    selectedDelay = 200; 
                    SoundManager.playBackgroundMusic("insane");
                    break;
            }
        });
    }

    @FXML
    public void showLeaderboard(ActionEvent event) {
        List<PlayerScore> scores = LeaderboardManager.getInstance().getScores();
        StringBuilder sb = new StringBuilder();
        if (scores.isEmpty()) {
            sb.append("No scores yet!");
        } else {
            for (int i = 0; i < scores.size(); i++) {
                PlayerScore ps = scores.get(i);
                sb.append(String.format("%d. %s: %d\n", i + 1, ps.getName(), ps.getScore()));
            }
        }
        
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Leaderboard");
        alert.setHeaderText("Top 50 Scores");
        alert.setContentText(sb.toString());
        alert.showAndWait();
    }

    @FXML
    public void showSettings(ActionEvent event) {
        com.comp2042.view.SettingsDialog.show();
    }

    @FXML
    public void exitGame(ActionEvent event) {
        System.exit(0);
    }
}

