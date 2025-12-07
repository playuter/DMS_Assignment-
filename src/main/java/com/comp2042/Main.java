package com.comp2042;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * The entry point of the Tetris application.
 * Extends JavaFX Application to set up the primary stage and load the initial scene.
 */
public class Main extends Application {

    /**
     * Starts the JavaFX application.
     * Loads the Main Menu FXML, sets up the scene, and initializes sound resources.
     * 
     * @param primaryStage The primary stage for this application, onto which
     *                     the application scene can be set.
     * @throws Exception if there is an issue loading the FXML file.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {

        // Initialize sounds
        com.comp2042.controller.SoundManager.loadSounds();

        URL location = getClass().getClassLoader().getResource("MainMenu.fxml");
        ResourceBundle resources = null;
        FXMLLoader fxmlLoader = new FXMLLoader(location, resources);
        Parent root = fxmlLoader.load();

        primaryStage.setTitle("TetrisJFX");
        Scene scene = new Scene(root, 300, 510);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be launched
     * through deployment artifacts, e.g., in IDEs with limited FX support.
     * 
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
