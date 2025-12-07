package com.comp2042.logic.bricks;

import java.util.ArrayList;
import java.util.List;

import com.comp2042.gameLogic.MatrixOperations;

/**
 * Represents the 'I' shaped Tetris brick.
 * This brick is a 4x1 line (cyan color).
 * It uses a 4x4 matrix for rotation.
 */
final class IBrick implements Brick {

    private final List<int[][]> brickMatrix = new ArrayList<>();

    /**
     * Creates a new IBrick.
     * Initializes the 4 rotation states (0, 90, 180, 270 degrees).
     * State 0: Horizontal (Flat)
     * State 1: Vertical
     * State 2: Horizontal (Flat)
     * State 3: Vertical
     */
    public IBrick() {
        // 0 - Up (Flat)
        brickMatrix.add(new int[][]{
                {0, 0, 0, 0},
                {1, 1, 1, 1},
                {0, 0, 0, 0},
                {0, 0, 0, 0}
        });
        // 1 - Right (Vertical)
        brickMatrix.add(new int[][]{
                {0, 0, 1, 0},
                {0, 0, 1, 0},
                {0, 0, 1, 0},
                {0, 0, 1, 0}
        });
        // 2 - Down (Flat)
        brickMatrix.add(new int[][]{
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {1, 1, 1, 1},
                {0, 0, 0, 0}
        });
        // 3 - Left (Vertical)
        brickMatrix.add(new int[][]{
                {0, 1, 0, 0},
                {0, 1, 0, 0},
                {0, 1, 0, 0},
                {0, 1, 0, 0}
        });
    }

    /**
     * Gets the shape matrices for this brick.
     * 
     * @return A list of 4x4 int arrays representing the brick's rotations.
     */
    @Override
    public List<int[][]> getShapeMatrix() {
        return MatrixOperations.deepCopyList(brickMatrix);
    }

}
