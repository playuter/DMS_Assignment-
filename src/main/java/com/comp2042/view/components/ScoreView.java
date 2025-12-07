package com.comp2042.view.components;

import com.comp2042.constants.GameConstants;
import com.comp2042.controller.SoundManager;
import com.comp2042.logic.leaderboard.LeaderboardManager;
import javafx.animation.ScaleTransition;
import javafx.beans.property.IntegerProperty;
import javafx.scene.effect.Glow;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Duration;

/**
 * View component responsible for displaying the score and high score.
 * Handles score updates, high score tracking, and visual animations for score milestones.
 */
public class ScoreView {

    private final Text scoreValue;
    private final Text highScoreValue;
    private final VBox container;

    /**
     * Creates a new ScoreView.
     * 
     * @param scoreValue The Text node for displaying the current score.
     * @param highScoreValue The Text node for displaying the high score.
     * @param container The parent VBox container (optional, can be null).
     */
    public ScoreView(Text scoreValue, Text highScoreValue, VBox container) {
        this.scoreValue = scoreValue;
        this.highScoreValue = highScoreValue;
        this.container = container;
        initializeHighScore();
    }

    /**
     * Loads and displays the current highest score from the leaderboard manager.
     */
    private void initializeHighScore() {
        int currentHighScore = LeaderboardManager.getInstance().getHighestScore();
        if (highScoreValue != null) {
            highScoreValue.setText(String.valueOf(currentHighScore));
        }
    }

    /**
     * Binds the score display to the game's score property.
     * Sets up listeners to update high score and trigger animations/sounds on milestones.
     * 
     * @param scoreProperty The score property to bind to.
     */
    public void bindScore(IntegerProperty scoreProperty) {
        scoreValue.textProperty().bind(scoreProperty.asString());
        
        scoreProperty.addListener((obs, oldVal, newVal) -> {
            int currentScore = newVal.intValue();
            int highScore = Integer.parseInt(highScoreValue.getText());
            
            // High Score Logic
            if (currentScore > highScore) {
                if (currentScore > 0 && oldVal.intValue() <= highScore) {
                    SoundManager.play("highscore");
                }
                highScoreValue.setText(String.valueOf(currentScore));
                animateScore(highScoreValue);
            }
            
            // Milestone Logic
            if (isMilestone(currentScore)) {
                SoundManager.play("highscore");
                animateScore(scoreValue);
            }
        });
    }

    /**
     * Checks if the current score has reached a defined milestone.
     * 
     * @param score The current score.
     * @return True if the score is a milestone, false otherwise.
     */
    private boolean isMilestone(int score) {
        if (score == 0) return false;
        for (int milestone : GameConstants.MILESTONES) {
            if (score % milestone == 0) return true;
        }
        return false;
    }

    /**
     * Triggers the score animation on the main score text.
     */
    public void animateScore() {
        animateScore(scoreValue);
    }

    /**
     * Helper method to apply a scale and glow animation to a text node.
     * 
     * @param target The Text node to animate.
     */
    private void animateScore(Text target) {
        ScaleTransition st = new ScaleTransition(Duration.millis(200), target);
        st.setFromX(1.0);
        st.setFromY(1.0);
        st.setToX(1.5);
        st.setToY(1.5);
        st.setCycleCount(2);
        st.setAutoReverse(true);
        st.play();
        
        Glow glow = new Glow(0.8);
        target.setEffect(glow);
        st.setOnFinished(e -> target.setEffect(null));
    }
    
    /**
     * Gets the current score as an integer.
     * 
     * @return The current score, or 0 if parsing fails.
     */
    public int getCurrentScore() {
        try {
            return Integer.parseInt(scoreValue.getText());
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}
