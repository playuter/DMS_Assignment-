package com.comp2042.logic.leaderboard;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Singleton manager for the game's leaderboard.
 * Handles loading, saving, and updating player scores persistently.
 */
public class LeaderboardManager {
    private static final String FILE_PATH = "leaderboard.ser";
    private static final int MAX_SCORES = 50;
    private static LeaderboardManager instance;
    private List<PlayerScore> scores;

    private LeaderboardManager() {
        scores = new ArrayList<>();
        loadScores();
    }

    /**
     * Gets the singleton instance of the LeaderboardManager.
     * 
     * @return The LeaderboardManager instance.
     */
    public static synchronized LeaderboardManager getInstance() {
        if (instance == null) {
            instance = new LeaderboardManager();
        }
        return instance;
    }

    /**
     * Adds a new score to the leaderboard.
     * The list is automatically sorted and trimmed to the top 50 scores.
     * 
     * @param name The player's name.
     * @param score The player's score.
     */
    public void addScore(String name, int score) {
        scores.add(new PlayerScore(name, score));
        Collections.sort(scores);
        
        if (scores.size() > MAX_SCORES) {
            scores = new ArrayList<>(scores.subList(0, MAX_SCORES));
        }
        saveScores();
    }

    /**
     * Gets the list of top scores.
     * 
     * @return An unmodifiable list of PlayerScore objects.
     */
    public List<PlayerScore> getScores() {
        return Collections.unmodifiableList(scores);
    }

    /**
     * Gets the highest score currently on the leaderboard.
     * 
     * @return The highest score, or 0 if the leaderboard is empty.
     */
    public int getHighestScore() {
        if (scores.isEmpty()) {
            return 0;
        }
        return scores.get(0).getScore();
    }

    /**
     * Loads scores from the persistent file.
     */
    @SuppressWarnings("unchecked")
    private void loadScores() {
        File file = new File(FILE_PATH);
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                scores = (List<PlayerScore>) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("Error loading leaderboard: " + e.getMessage());
                scores = new ArrayList<>();
            }
        }
    }

    /**
     * Saves the current scores to the persistent file.
     */
    private void saveScores() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            oos.writeObject(scores);
        } catch (IOException e) {
            System.err.println("Error saving leaderboard: " + e.getMessage());
        }
    }
}
