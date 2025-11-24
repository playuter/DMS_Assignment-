package com.comp2042.view;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

/**
 * Maps integer values to colors for Tetris blocks.
 * This utility class provides a centralized way to convert block type values
 * (0-7) into their corresponding JavaFX colors.
 * 
 * Color mapping:
 * 0 = TRANSPARENT (empty cell)
 * 1 = AQUA
 * 2 = BLUEVIOLET
 * 3 = DARKGREEN
 * 4 = YELLOW
 * 5 = RED
 * 6 = BEIGE
 * 7 = BURLYWOOD
 * default = WHITE
 */
public class ColorMapper {
    
    /**
     * Private constructor to prevent instantiation.
     * This is a utility class with only static methods.
     */
    private ColorMapper() {
        throw new UnsupportedOperationException("ColorMapper is a utility class and cannot be instantiated");
    }
    
    /**
     * Returns the color corresponding to the given integer value.
     * 
     * @param value The integer value representing a block type (0-7)
     * @return The Paint color corresponding to the value
     */
    public static Paint getColorForValue(int value) {
        return switch (value) {
            case 0 -> Color.TRANSPARENT;
            case 1 -> Color.AQUA;
            case 2 -> Color.BLUEVIOLET;
            case 3 -> Color.DARKGREEN;
            case 4 -> Color.YELLOW;
            case 5 -> Color.RED;
            case 6 -> Color.BEIGE;
            case 7 -> Color.BURLYWOOD;
            default -> Color.WHITE;
        };
    }
    
    /**
     * Returns the transparent color (used for empty cells).
     * 
     * @return Color.TRANSPARENT
     */
    public static Paint getTransparentColor() {
        return Color.TRANSPARENT;
    }
}

