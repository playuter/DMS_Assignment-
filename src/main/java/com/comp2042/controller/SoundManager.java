package com.comp2042.controller;

import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class SoundManager {
    private static final Map<String, AudioClip> sounds = new HashMap<>();
    private static final Map<String, Media> musicMedia = new HashMap<>();
    
    private static MediaPlayer currentMediaPlayer = null;
    private static String currentBackgroundMusic = null;
    private static double globalVolume = 1.0;

    public static void loadSounds() {
        loadSound("gameover", "/sounds/03_Game Over.mp3");
        loadSound("bop", "/sounds/bop.mp3");
        loadSound("clear", "/sounds/doubleLineClear.mp3");
        loadSound("highscore", "/sounds/highScore.mp3");
        
        // Load music as Media for MediaPlayer support
        loadMusic("insane", "/sounds/insaneLevel.mp3");
        loadMusic("menu", "/sounds/mainMenu.mp3");
        loadMusic("normal", "/sounds/normalLeve.mp3");
        loadMusic("pause", "/sounds/Pause.mp3");
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

    private static void loadMusic(String key, String path) {
        URL resource = SoundManager.class.getResource(path);
        if (resource != null) {
            Media media = new Media(resource.toExternalForm());
            musicMedia.put(key, media);
        } else {
            System.err.println("Music not found: " + path);
        }
    }

    public static void play(String key) {
        AudioClip clip = sounds.get(key);
        if (clip != null) {
            // AudioClip play(volume) - uses 0.0 to 1.0
            clip.play(globalVolume);
        }
    }

    public static void setGlobalVolume(double volume) {
        globalVolume = volume;
        if (currentMediaPlayer != null) {
            currentMediaPlayer.setVolume(volume);
        }
    }

    public static double getGlobalVolume() {
        return globalVolume;
    }

    public static void playBackgroundMusic(String key) {
        if (currentBackgroundMusic != null && currentBackgroundMusic.equals(key)) {
             if (currentMediaPlayer != null && currentMediaPlayer.getStatus() != MediaPlayer.Status.PLAYING) {
                 currentMediaPlayer.play();
             }
            return; 
        }
        
        stopBackgroundMusic();
        
        Media media = musicMedia.get(key);
        if (media != null) {
            currentMediaPlayer = new MediaPlayer(media);
            currentMediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            currentMediaPlayer.setVolume(globalVolume);
            currentMediaPlayer.play();
            currentBackgroundMusic = key;
        }
    }

    public static void stopBackgroundMusic() {
        if (currentMediaPlayer != null) {
            currentMediaPlayer.stop();
            currentMediaPlayer.dispose();
            currentMediaPlayer = null;
        }
        currentBackgroundMusic = null;
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
