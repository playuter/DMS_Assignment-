package com.comp2042.logic.leaderboard;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PlayerScoreTest {

    @Test
    void testCompareTo() {
        PlayerScore score1 = new PlayerScore("Alice", 100);
        PlayerScore score2 = new PlayerScore("Bob", 200);
        PlayerScore score3 = new PlayerScore("Charlie", 100);

        // score2 > score1, so compareTo should be positive (if sorting descending means higher score comes first, compare logic usually implies this)
        // PlayerScore compareTo: return Integer.compare(other.score, this.score);
        // if other (200) > this (100), result is > 0. 
        // So score1.compareTo(score2) should be positive (meaning score1 comes "after" score2).
        assertTrue(score1.compareTo(score2) > 0);
        
        // score2.compareTo(score1) should be negative (score2 comes "before" score1).
        assertTrue(score2.compareTo(score1) < 0);
        
        // Equal scores
        assertEquals(0, score1.compareTo(score3));
    }
}

