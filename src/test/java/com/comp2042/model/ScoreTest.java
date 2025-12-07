package com.comp2042.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ScoreTest {

    @Test
    void testScoreOperations() {
        Score score = new Score();
        
        // Initial state
        assertEquals(0, score.scoreProperty().get());
        
        // Add points
        score.add(100);
        assertEquals(100, score.scoreProperty().get());
        
        score.add(50);
        assertEquals(150, score.scoreProperty().get());
        
        // Reset
        score.reset();
        assertEquals(0, score.scoreProperty().get());
    }
}

