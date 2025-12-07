package com.comp2042.events;

/**
 * Enumeration of the types of move events that can occur in the game.
 */
public enum EventType {
    /** Move the piece down. */
    DOWN,
    /** Move the piece left. */
    LEFT,
    /** Move the piece right. */
    RIGHT,
    /** Rotate the piece. */
    ROTATE,
    /** Hard drop the piece instantly to the bottom. */
    HARD_DROP
}
