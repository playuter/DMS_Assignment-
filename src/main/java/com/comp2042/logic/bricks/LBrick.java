package com.comp2042.logic.bricks;

import java.util.ArrayList;
import java.util.List;

import com.comp2042.gameLogic.MatrixOperations;

final class LBrick implements Brick {

    private final List<int[][]> brickMatrix = new ArrayList<>();

    public LBrick() {
        // 0 - Up
        // . . X
        // X X X
        brickMatrix.add(new int[][]{
                {0, 0, 3, 0},
                {3, 3, 3, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0}
        });
        // 1 - Right
        // . 3 .
        // . 3 .
        // . 3 3
        brickMatrix.add(new int[][]{
                {0, 3, 0, 0},
                {0, 3, 0, 0},
                {0, 3, 3, 0},
                {0, 0, 0, 0}
        });
        // 2 - Down
        // . . .
        // 3 3 3
        // 3 . .
        brickMatrix.add(new int[][]{
                {0, 0, 0, 0},
                {3, 3, 3, 0},
                {3, 0, 0, 0},
                {0, 0, 0, 0}
        });
        // 3 - Left
        // 3 3 .
        // . 3 .
        // . 3 .
        brickMatrix.add(new int[][]{
                {3, 3, 0, 0},
                {0, 3, 0, 0},
                {0, 3, 0, 0},
                {0, 0, 0, 0}
        });
    }

    @Override
    public List<int[][]> getShapeMatrix() {
        return MatrixOperations.deepCopyList(brickMatrix);
    }
}
