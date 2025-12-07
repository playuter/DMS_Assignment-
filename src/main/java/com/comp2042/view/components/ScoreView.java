package com.comp2042.view.components;

import com.comp2042.constants.GameConstants;
import com.comp2042.controller.SoundManager;
import com.comp2042.logic.leaderboard.LeaderboardManager;
import javafx.animation.ScaleTransition;
import javafx.beans.property.IntegerProperty;
import javafx.scene.control.Label;
import javafx.scene.effect.Glow;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class ScoreView {

    private final Text scoreValue;
    private final Text highScoreValue;
    private final VBox container;

    public ScoreView(Text scoreValue, Text highScoreValue, VBox container) {
        this.scoreValue = scoreValue;
        this.highScoreValue = highScoreValue;
        this.container = container;
        initializeHighScore();
    }

    private void initializeHighScore() {
        int currentHighScore = LeaderboardManager.getInstance().getHighestScore();
        if (highScoreValue != null) {
            highScoreValue.setText(String.valueOf(currentHighScore));
        }
    }

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

    private boolean isMilestone(int score) {
        if (score == 0) return false;
        for (int milestone : GameConstants.MILESTONES) {
            if (score % milestone == 0) return true;
        }
        return false;
    }

    public void animateScore() {
        animateScore(scoreValue);
    }

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
    
    public int getCurrentScore() {
        try {
            return Integer.parseInt(scoreValue.getText());
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}

