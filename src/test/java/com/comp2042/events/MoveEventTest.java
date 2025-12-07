package com.comp2042.events;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MoveEventTest {

    @Test
    void testMoveEventCreationAndAccess() {
        EventType type = EventType.DOWN;
        EventSource source = EventSource.USER;
        
        MoveEvent event = new MoveEvent(type, source);
        
        assertEquals(EventType.DOWN, event.getEventType());
        assertEquals(EventSource.USER, event.getEventSource());
        
        // Verify immutability (fields are final, so setting via constructor is enough)
    }
}

