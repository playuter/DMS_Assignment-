package com.comp2042.controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

/**
 * Manages the game's automatic falling animation timeline.
 * This class encapsulates the Timeline logic for making Tetris pieces fall automatically.
 * Separates animation control from display logic.
 */
public class AnimationController {
    private Timeline timeline;
    private Runnable onTick;
    private long fallSpeedMillis;
    
    /**
     * Creates a new AnimationController with the default fall speed of 400ms.
     * 
     * @param onTick The callback to execute on each animation tick (when piece should fall)
     */
    public AnimationController(Runnable onTick) {
        this(onTick, 400);
    }
    
    /**
     * Creates a new AnimationController with a custom fall speed.
     * 
     * @param onTick The callback to execute on each animation tick (when piece should fall)
     * @param fallSpeedMillis The time in milliseconds between each automatic fall
     */
    public AnimationController(Runnable onTick, long fallSpeedMillis) {
        this.onTick = onTick;
        this.fallSpeedMillis = fallSpeedMillis;
        initializeTimeline();
    }
    
    /**
     * Initializes the Timeline with the configured fall speed and callback.
     */
    private void initializeTimeline() {
        timeline = new Timeline(new KeyFrame(
            Duration.millis(fallSpeedMillis),
            ae -> {
                if (onTick != null) {
                    onTick.run();
                }
            }
        ));
        timeline.setCycleCount(Timeline.INDEFINITE);
    }
    
    /**
     * Starts the animation. The callback will be executed repeatedly at the configured interval.
     */
    public void start() {
        if (timeline != null) {
            timeline.play();
        }
    }
    
    /**
     * Stops the animation. The callback will no longer be executed.
     */
    public void stop() {
        if (timeline != null) {
            timeline.stop();
        }
    }
    
    /**
     * Checks if the animation is currently running.
     * 
     * @return true if the animation is playing, false otherwise
     */
    public boolean isRunning() {
        return timeline != null && timeline.getStatus() == javafx.animation.Animation.Status.RUNNING;
    }
    
    /**
     * Changes the fall speed of the animation.
     * The animation must be stopped before changing speed, then restarted.
     * 
     * @param fallSpeedMillis The new fall speed in milliseconds
     */
    public void setFallSpeed(long fallSpeedMillis) {
        boolean wasRunning = isRunning();
        stop();
        this.fallSpeedMillis = fallSpeedMillis;
        initializeTimeline();
        if (wasRunning) {
            start();
        }
    }
    
    /**
     * Gets the current fall speed.
     * 
     * @return The fall speed in milliseconds
     */
    public long getFallSpeed() {
        return fallSpeedMillis;
    }
}
