package com.comp2042.constants;

/**
 * Holds all the constant values used throughout the game.
 * This centralized configuration makes it easier to tweak game balance
 * and visual settings without modifying the logic code.
 */
public class GameConstants {
    
    private GameConstants() {
        // Prevent instantiation
    }

    // Board Dimensions
    /** The size of each square brick in pixels. */
    public static final int BRICK_SIZE = 20;
    /** Default width of the game board (in blocks). */
    public static final int DEFAULT_BOARD_WIDTH = 10;
    /** Width of the game board in Insane Mode (in blocks). */
    public static final int INSANE_BOARD_WIDTH = 20;
    /** Default height of the game board (in blocks). */
    public static final int DEFAULT_BOARD_HEIGHT = 20;

    // Game Rules & Mechanics
    /** The delay in milliseconds before a piece locks in place after touching the ground. */
    public static final int LOCK_DELAY_MS = 500;
    
    // Animation & Speed
    /** The starting fall speed in milliseconds (lower is faster). */
    public static final long INITIAL_FALL_SPEED = 400;
    /** The duration over which the insane mode speed increases to maximum. */
    public static final long MAX_INSANE_SPEED_TIME = 120000; // 2 minutes
    /** The fastest fall speed possible in Insane Mode. */
    public static final long MIN_INSANE_DELAY = 100;
    
    // Scoring
    /** The base score multiplier for clearing a line. */
    public static final int SCORE_PER_LINE = 50; // Base score multiplier
    /** Score thresholds that trigger special animations or sounds. */
    public static final int[] MILESTONES = {100, 250, 500, 1000};
}
