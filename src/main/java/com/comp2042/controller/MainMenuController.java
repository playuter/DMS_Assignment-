package com.comp2042.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainMenuController implements Initializable {

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
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("gameLayout.fxml"));
            Parent root = loader.load();
            
            // Get the controller and set up the game
            GuiController c = loader.getController();
            new GameController(c); // Initialize game controller which sets up the game logic

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 300, 510);
            stage.setScene(scene);
            stage.show();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void exitGame(ActionEvent event) {
        System.exit(0);
    }
}

