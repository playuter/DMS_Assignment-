package com.comp2042.logic.bricks;

import java.util.List;

/**
 * Interface representing a Tetris brick (tetromino).
 * Defines the structure of a brick across its different rotation states.
 */
public interface Brick {

    /**
     * Gets the list of matrices representing the brick's shape in all rotation orientations.
     * Index 0: Default orientation (Up)
     * Index 1: 90 degrees clockwise (Right)
     * Index 2: 180 degrees (Down)
     * Index 3: 270 degrees clockwise (Left)
     * 
     * @return A list of 2D integer arrays representing the brick shapes.
     */
    List<int[][]> getShapeMatrix();
}
