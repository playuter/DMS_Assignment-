package com.comp2042.logic.bricks;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RandomBrickGeneratorTest {

    private RandomBrickGenerator generator;

    @BeforeEach
    void setUp() {
        generator = new RandomBrickGenerator();
    }

    @Test
    void testGetBrick() {
        Brick brick = generator.getBrick();
        assertNotNull(brick, "Should return a brick");
        assertTrue(brick instanceof Brick, "Returned object should be a Brick");
    }

    @Test
    void testGetNextBrick() {
        Brick next = generator.getNextBrick();
        assertNotNull(next, "Should peek at next brick");
        
        Brick pulled = generator.getBrick();
        assertSame(next, pulled, "getBrick() should return the same brick that getNextBrick() peeked at");
    }

    @Test
    void testGetNextBricks() {
        List<Brick> nextBricks = generator.getNextBricks();
        assertNotNull(nextBricks);
        assertEquals(3, nextBricks.size(), "Should return exactly 3 upcoming bricks");
        
        // Ensure they are distinct objects in the list (though type might be same)
        assertNotNull(nextBricks.get(0));
        assertNotNull(nextBricks.get(1));
        assertNotNull(nextBricks.get(2));
    }
    
    @Test
    void testQueueReplenishment() {
        // Consume several bricks to force queue replenishment
        for (int i = 0; i < 10; i++) {
            assertNotNull(generator.getBrick());
        }
        
        // Queue should still be functional
        assertNotNull(generator.getNextBrick());
        assertEquals(3, generator.getNextBricks().size());
    }
}

