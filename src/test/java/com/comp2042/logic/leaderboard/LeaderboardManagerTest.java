package com.comp2042.logic.leaderboard;

import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LeaderboardManagerTest {

    @Test
    void testLeaderboardLogic() {
        LeaderboardManager manager = LeaderboardManager.getInstance();
        
        // Note: This test interacts with the singleton and potential file system.
        // Ideally we would mock the persistence layer, but for this assignment
        // we check the logic of adding and sorting.
        
        // Add a unique score to identify this test run
        String uniqueName = "TestUser_" + System.currentTimeMillis();
        int uniqueScore = 999999;
        
        manager.addScore(uniqueName, uniqueScore);
        
        List<PlayerScore> scores = manager.getScores();
        assertFalse(scores.isEmpty());
        
        // Check if our score is there (it should be at top if it's high enough)
        boolean found = false;
        for (PlayerScore ps : scores) {
            if (ps.getName().equals(uniqueName) && ps.getScore() == uniqueScore) {
                found = true;
                break;
            }
        }
        assertTrue(found, "Added score should be present in leaderboard");
        
        // Check sorting (descending order)
        if (scores.size() > 1) {
            assertTrue(scores.get(0).getScore() >= scores.get(1).getScore(), "Scores should be sorted descending");
        }
    }
}

