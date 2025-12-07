package com.comp2042.constants;

public class GameConstants {
    
    private GameConstants() {
        // Prevent instantiation
    }

    // Board Dimensions
    public static final int BRICK_SIZE = 20;
    public static final int DEFAULT_BOARD_WIDTH = 10;
    public static final int INSANE_BOARD_WIDTH = 20;
    public static final int DEFAULT_BOARD_HEIGHT = 20;

    // Game Rules & Mechanics
    public static final int LOCK_DELAY_MS = 500;
    public static final int BONUS_BRICK_SCORE_THRESHOLD = 150;
    public static final int BONUS_BRICK_ID = 9;
    public static final int REVIVAL_CLEARS = 2;
    
    // Animation & Speed
    public static final long INITIAL_FALL_SPEED = 400;
    public static final long MAX_INSANE_SPEED_TIME = 120000; // 2 minutes
    public static final long MIN_INSANE_DELAY = 100;
    
    // Scoring
    public static final int SCORE_PER_LINE = 50; // Base score multiplier
    public static final int[] MILESTONES = {100, 250, 500, 1000};
}

