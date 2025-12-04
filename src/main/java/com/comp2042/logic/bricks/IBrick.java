package com.comp2042.logic.bricks;

import java.util.ArrayList;
import java.util.List;

import com.comp2042.gameLogic.MatrixOperations;

final class IBrick implements Brick {

    private final List<int[][]> brickMatrix = new ArrayList<>();

    public IBrick() {
        // 0 - Up (Flat)
        // . . . .
        // 1 1 1 1
        // . . . .
        // . . . .
        brickMatrix.add(new int[][]{
                {0, 0, 0, 0},
                {1, 1, 1, 1},
                {0, 0, 0, 0},
                {0, 0, 0, 0}
        });
        // 1 - Right (Vertical)
        // . . 1 .
        // . . 1 .
        // . . 1 .
        // . . 1 .
        brickMatrix.add(new int[][]{
                {0, 0, 1, 0},
                {0, 0, 1, 0},
                {0, 0, 1, 0},
                {0, 0, 1, 0}
        });
        // 2 - Down (Flat)
        // . . . .
        // . . . .
        // 1 1 1 1
        // . . . .
        brickMatrix.add(new int[][]{
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {1, 1, 1, 1},
                {0, 0, 0, 0}
        });
        // 3 - Left (Vertical)
        // . 1 . .
        // . 1 . .
        // . 1 . .
        // . 1 . .
        brickMatrix.add(new int[][]{
                {0, 1, 0, 0},
                {0, 1, 0, 0},
                {0, 1, 0, 0},
                {0, 1, 0, 0}
        });
    }

    @Override
    public List<int[][]> getShapeMatrix() {
        return MatrixOperations.deepCopyList(brickMatrix);
    }

}
