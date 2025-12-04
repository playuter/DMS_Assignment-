package com.comp2042.controller;

import javafx.scene.media.AudioClip;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class SoundManager {
    private static final Map<String, AudioClip> sounds = new HashMap<>();
    private static String currentBackgroundMusic = null;

    public static void loadSounds() {
        loadSound("gameover", "/sounds/03_Game Over.mp3");
        loadSound("bop", "/sounds/bop.mp3");
        loadSound("clear", "/sounds/doubleLineClear.mp3");
        loadSound("highscore", "/sounds/highScore.mp3");
        loadSound("insane", "/sounds/insaneLevel.mp3");
        loadSound("menu", "/sounds/mainMenu.mp3");
        loadSound("normal", "/sounds/normalLeve.mp3");
        loadSound("pause", "/sounds/Pause.mp3");
    }

    private static void loadSound(String key, String path) {
        URL resource = SoundManager.class.getResource(path);
        if (resource != null) {
            AudioClip clip = new AudioClip(resource.toExternalForm());
            sounds.put(key, clip);
        } else {
            System.err.println("Sound not found: " + path);
        }
    }

    public static void play(String key) {
        AudioClip clip = sounds.get(key);
        if (clip != null) {
            if ("bop".equals(key)) {
                clip.play(1.0); // Play bop at max volume
            } else {
                clip.play();
            }
        }
    }

    public static void playBackgroundMusic(String key) {
        if (currentBackgroundMusic != null && currentBackgroundMusic.equals(key)) {
            if (!sounds.get(key).isPlaying()) {
                 sounds.get(key).play();
            }
            return; 
        }
        
        stopBackgroundMusic();
        
        AudioClip clip = sounds.get(key);
        if (clip != null) {
            clip.setCycleCount(AudioClip.INDEFINITE);
            clip.play();
            currentBackgroundMusic = key;
        }
    }

    public static void stopBackgroundMusic() {
        if (currentBackgroundMusic != null) {
            AudioClip clip = sounds.get(currentBackgroundMusic);
            if (clip != null) {
                clip.stop();
            }
            currentBackgroundMusic = null;
        }
    }

    public static String getCurrentTrack() {
        return currentBackgroundMusic;
    }
    
    public static void stop(String key) {
        AudioClip clip = sounds.get(key);
        if (clip != null) {
            clip.stop();
        }
    }
}
