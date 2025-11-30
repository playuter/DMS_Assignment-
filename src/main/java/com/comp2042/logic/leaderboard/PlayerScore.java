package com.comp2042.logic.leaderboard;

import java.io.Serializable;

public class PlayerScore implements Serializable, Comparable<PlayerScore> {
    private static final long serialVersionUID = 1L;
    private String name;
    private int score;

    public PlayerScore(String name, int score) {
        this.name = name;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    @Override
    public int compareTo(PlayerScore other) {
        // Descending order (higher score first)
        return Integer.compare(other.score, this.score);
    }
    
    @Override
    public String toString() {
        return name + ": " + score;
    }
}

