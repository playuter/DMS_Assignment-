package com.comp2042.gameLogic;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

import com.comp2042.constants.GameConstants;

/**
 * Manages the lock delay timer for the Tetris game.
 * The lock delay allows the player a short window of time to move or rotate
 * a piece after it has landed before it locks into place.
 */
public class LockDelayManager {

    private final Timeline lockTimer;
    private boolean isLocking = false;
    private final Runnable onLockAction;

    /**
     * Creates a new LockDelayManager.
     * 
     * @param onLockAction The action to execute when the lock timer expires.
     */
    public LockDelayManager(Runnable onLockAction) {
        this.onLockAction = onLockAction;
        this.lockTimer = new Timeline(new KeyFrame(Duration.millis(GameConstants.LOCK_DELAY_MS), e -> performLock()));
        this.lockTimer.setCycleCount(1);
    }

    /**
     * Starts the lock timer if it is not already running.
     */
    public void startLockTimer() {
        if (!isLocking) {
            isLocking = true;
            lockTimer.playFromStart();
        }
    }

    /**
     * Cancels the lock timer if it is running.
     */
    public void cancelLockTimer() {
        if (isLocking) {
            isLocking = false;
            lockTimer.stop();
        }
    }

    /**
     * Resets the lock timer (stops and restarts it) if it is currently running.
     * This is used when the player performs a successful move or rotation while the piece is landing.
     */
    public void resetLockTimer() {
        if (isLocking) {
            lockTimer.stop();
            lockTimer.playFromStart();
        }
    }

    /**
     * Helper to execute the lock action and clean up state.
     */
    private void performLock() {
        // Stop timer first
        if (isLocking) {
            isLocking = false;
            lockTimer.stop();
        }
        // Execute the callback
        if (onLockAction != null) {
            onLockAction.run();
        }
    }
}

