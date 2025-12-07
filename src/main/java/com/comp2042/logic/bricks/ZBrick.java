package com.comp2042.logic.bricks;

import java.util.ArrayList;
import java.util.List;

import com.comp2042.gameLogic.MatrixOperations;

final class ZBrick implements Brick {

    private final List<int[][]> brickMatrix = new ArrayList<>();

    public ZBrick() {
        // 0 - Up
        brickMatrix.add(new int[][]{
                {7, 7, 0},
                {0, 7, 7},
                {0, 0, 0}
        });
        // 1 - Right
        brickMatrix.add(new int[][]{
                {0, 0, 7},
                {0, 7, 7},
                {0, 7, 0}
        });
        // 2 - Down
        brickMatrix.add(new int[][]{
                {0, 0, 0},
                {7, 7, 0},
                {0, 7, 7}
        });
        // 3 - Left
        brickMatrix.add(new int[][]{
                {0, 7, 0},
                {7, 7, 0},
                {7, 0, 0}
        });
    }

    @Override
    public List<int[][]> getShapeMatrix() {
        return MatrixOperations.deepCopyList(brickMatrix);
    }
}
