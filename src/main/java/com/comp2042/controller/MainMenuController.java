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

public class MainMenuController implements Initializable {

    @FXML
    private TextField nameInput;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
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
    public void exitGame(ActionEvent event) {
        System.exit(0);
    }
}

