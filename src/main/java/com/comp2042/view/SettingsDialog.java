package com.comp2042.view;

import com.comp2042.controller.SoundManager;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;

/**
 * Utility class for displaying the Settings dialog.
 * Allows the user to adjust game settings like volume.
 */
public class SettingsDialog {

    /**
     * Shows the Settings dialog.
     * Creates an Alert dialog containing a volume slider.
     */
    public static void show() {
        Alert alert = new Alert(Alert.AlertType.NONE);
        alert.setTitle("Settings");
        alert.setHeaderText("Volume Settings");
        
        // Create slider with black styling
        Slider volumeSlider = new Slider(0, 1.0, SoundManager.getGlobalVolume());
        volumeSlider.setShowTickLabels(false); 
        volumeSlider.setShowTickMarks(false);
        volumeSlider.getStyleClass().add("black-slider");
        
        // Create label
        Label volumeLabel = new Label("Volume: " + (int)(volumeSlider.getValue() * 100) + "%");
        volumeLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
        
        // Listener to update SoundManager and label
        volumeSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            // Update the global volume setting
            SoundManager.setGlobalVolume(newVal.doubleValue());
            volumeLabel.setText("Volume: " + (int)(newVal.doubleValue() * 100) + "%");
        });
        
        VBox content = new VBox(15, volumeLabel, volumeSlider);
        content.setStyle("-fx-padding: 20; -fx-alignment: center;");
        
        alert.getDialogPane().setContent(content);
        alert.getDialogPane().setMinWidth(300);
        
        try {
            String css = SettingsDialog.class.getResource("/window_style.css").toExternalForm();
            alert.getDialogPane().getStylesheets().add(css);
        } catch (Exception e) {
            System.err.println("Could not load CSS for SettingsDialog: " + e.getMessage());
        }

        alert.getButtonTypes().add(ButtonType.OK);
        alert.showAndWait();
    }
}
