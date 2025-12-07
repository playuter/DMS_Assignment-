package com.comp2042.logic.bricks;

import java.util.ArrayList;
import java.util.List;

import com.comp2042.gameLogic.MatrixOperations;

/**
 * Represents the 'J' shaped Tetris brick.
 * This brick is shaped like a J (blue color).
 * It uses a 3x4 matrix (effectively 3x3 within 4x4 grid) for rotation.
 */
final class JBrick implements Brick {

    private final List<int[][]> brickMatrix = new ArrayList<>();

    /**
     * Creates a new JBrick.
     * Initializes the 4 rotation states.
     */
    public JBrick() {
        // 0 - Up (Head left, tail right-up)
        brickMatrix.add(new int[][]{
                {2, 0, 0, 0},
                {2, 2, 2, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0}
        });
        // 1 - Right
        brickMatrix.add(new int[][]{
                {0, 2, 2, 0},
                {0, 2, 0, 0},
                {0, 2, 0, 0},
                {0, 0, 0, 0}
        });
        // 2 - Down
        brickMatrix.add(new int[][]{
                {0, 0, 0, 0},
                {2, 2, 2, 0},
                {0, 0, 2, 0},
                {0, 0, 0, 0}
        });
        // 3 - Left
        brickMatrix.add(new int[][]{
                {0, 2, 0, 0},
                {0, 2, 0, 0},
                {2, 2, 0, 0},
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
