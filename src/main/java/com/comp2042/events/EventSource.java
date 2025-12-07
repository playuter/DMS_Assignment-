package com.comp2042.events;

/**
 * Enumeration of the possible sources that can trigger a game event.
 * Used to distinguish between user input and automated game logic (e.g., gravity).
 */
public enum EventSource {
    /** Event triggered by user keyboard input. */
    USER, 
    /** Event triggered by the game thread (e.g., automatic falling). */
    THREAD
}
