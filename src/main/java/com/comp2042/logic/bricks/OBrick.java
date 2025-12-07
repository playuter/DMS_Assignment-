package com.comp2042.logic.bricks;

import java.util.ArrayList;
import java.util.List;

import com.comp2042.gameLogic.MatrixOperations;

/**
 * Represents the 'O' shaped Tetris brick (Square).
 * This brick is a 2x2 square (yellow color).
 * It does not change shape upon rotation, but effectively rotates in place.
 */
final class OBrick implements Brick {

    private final List<int[][]> brickMatrix = new ArrayList<>();

    /**
     * Creates a new OBrick.
     * Initializes the 4 rotation states (which are identical).
     */
    public OBrick() {
        int[][] oShape = {
                {0, 4, 4, 0},
                {0, 4, 4, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0}
        };
        
        brickMatrix.add(oShape);
        brickMatrix.add(oShape);
        brickMatrix.add(oShape);
        brickMatrix.add(oShape);
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
