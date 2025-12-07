package com.comp2042.logic.bricks;

import java.util.ArrayList;
import java.util.List;

import com.comp2042.gameLogic.MatrixOperations;

/**
 * Represents the 'S' shaped Tetris brick.
 * This brick is shaped like an S (green color).
 */
final class SBrick implements Brick {

    private final List<int[][]> brickMatrix = new ArrayList<>();

    /**
     * Creates a new SBrick.
     * Initializes the 4 rotation states.
     */
    public SBrick() {
        // 0 - Up
        brickMatrix.add(new int[][]{
                {0, 5, 5, 0},
                {5, 5, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0}
        });
        // 1 - Right
        brickMatrix.add(new int[][]{
                {0, 5, 0, 0},
                {0, 5, 5, 0},
                {0, 0, 5, 0},
                {0, 0, 0, 0}
        });
        // 2 - Down
        brickMatrix.add(new int[][]{
                {0, 0, 0, 0},
                {0, 5, 5, 0},
                {5, 5, 0, 0},
                {0, 0, 0, 0}
        });
        // 3 - Left
        brickMatrix.add(new int[][]{
                {5, 0, 0, 0},
                {5, 5, 0, 0},
                {0, 5, 0, 0},
                {0, 0, 0, 0}
        });
    }

    /**
     * Gets the shape matrices for this brick.
     * 
     * @return A list of int arrays representing the brick's rotations.
     */
    @Override
    public List<int[][]> getShapeMatrix() {
        return MatrixOperations.deepCopyList(brickMatrix);
    }
}
