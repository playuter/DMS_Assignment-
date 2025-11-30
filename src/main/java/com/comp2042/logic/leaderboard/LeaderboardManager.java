package com.comp2042.logic.leaderboard;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LeaderboardManager {
    private static final String FILE_PATH = "leaderboard.ser";
    private static final int MAX_SCORES = 50;
    private static LeaderboardManager instance;
    private List<PlayerScore> scores;

    private LeaderboardManager() {
        scores = new ArrayList<>();
        loadScores();
    }

    public static synchronized LeaderboardManager getInstance() {
        if (instance == null) {
            instance = new LeaderboardManager();
        }
        return instance;
    }

    public void addScore(String name, int score) {
        scores.add(new PlayerScore(name, score));
        Collections.sort(scores);
        
        if (scores.size() > MAX_SCORES) {
            scores = new ArrayList<>(scores.subList(0, MAX_SCORES));
        }
        saveScores();
    }

    public List<PlayerScore> getScores() {
        return Collections.unmodifiableList(scores);
    }

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

    private void saveScores() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            oos.writeObject(scores);
        } catch (IOException e) {
            System.err.println("Error saving leaderboard: " + e.getMessage());
        }
    }
}

