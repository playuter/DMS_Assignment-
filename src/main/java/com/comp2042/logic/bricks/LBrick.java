package com.comp2042.logic.bricks;

import java.util.ArrayList;
import java.util.List;

import com.comp2042.gameLogic.MatrixOperations;

final class LBrick implements Brick {

    private final List<int[][]> brickMatrix = new ArrayList<>();

    public LBrick() {
        // 0 - Up
        // . . 3
        // 3 3 3
        // . . .
        brickMatrix.add(new int[][]{
                {0, 0, 3},
                {3, 3, 3},
                {0, 0, 0}
        });
        // 1 - Right
        brickMatrix.add(new int[][]{
                {0, 3, 0},
                {0, 3, 0},
                {0, 3, 3}
        });
        // 2 - Down
        brickMatrix.add(new int[][]{
                {0, 0, 0},
                {3, 3, 3},
                {3, 0, 0}
        });
        // 3 - Left
        brickMatrix.add(new int[][]{
                {3, 3, 0},
                {0, 3, 0},
                {0, 3, 0}
        });
    }

    @Override
    public List<int[][]> getShapeMatrix() {
        return MatrixOperations.deepCopyList(brickMatrix);
    }
}
