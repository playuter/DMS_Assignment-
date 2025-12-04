package com.comp2042.logic.bricks;

import java.util.ArrayList;
import java.util.List;

import com.comp2042.gameLogic.MatrixOperations;

final class SBrick implements Brick {

    private final List<int[][]> brickMatrix = new ArrayList<>();

    public SBrick() {
        // 0 - Up
        // . 5 5
        // 5 5 .
        brickMatrix.add(new int[][]{
                {0, 5, 5, 0},
                {5, 5, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0}
        });
        // 1 - Right
        // . 5 .
        // . 5 5
        // . . 5
        brickMatrix.add(new int[][]{
                {0, 5, 0, 0},
                {0, 5, 5, 0},
                {0, 0, 5, 0},
                {0, 0, 0, 0}
        });
        // 2 - Down
        // . . .
        // . 5 5
        // 5 5 .
        brickMatrix.add(new int[][]{
                {0, 0, 0, 0},
                {0, 5, 5, 0},
                {5, 5, 0, 0},
                {0, 0, 0, 0}
        });
        // 3 - Left
        // 5 . .
        // 5 5 .
        // . 5 .
        brickMatrix.add(new int[][]{
                {5, 0, 0, 0},
                {5, 5, 0, 0},
                {0, 5, 0, 0},
                {0, 0, 0, 0}
        });
    }

    @Override
    public List<int[][]> getShapeMatrix() {
        return MatrixOperations.deepCopyList(brickMatrix);
    }
}
