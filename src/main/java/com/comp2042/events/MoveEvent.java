package com.comp2042.events;

/**
 * Represents a movement event in the game.
 * Encapsulates the type of movement and its source.
 */
public final class MoveEvent {
    private final EventType eventType;
    private final EventSource eventSource;

    /**
     * Creates a new MoveEvent.
     * 
     * @param eventType The type of movement (e.g., DOWN, LEFT).
     * @param eventSource The source of the event (USER or THREAD).
     */
    public MoveEvent(EventType eventType, EventSource eventSource) {
        this.eventType = eventType;
        this.eventSource = eventSource;
    }

    /**
     * Gets the type of this event.
     * 
     * @return The EventType.
     */
    public EventType getEventType() {
        return eventType;
    }

    /**
     * Gets the source of this event.
     * 
     * @return The EventSource.
     */
    public EventSource getEventSource() {
        return eventSource;
    }
}
