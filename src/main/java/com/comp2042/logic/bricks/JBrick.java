package com.comp2042.logic.bricks;

import java.util.ArrayList;
import java.util.List;

import com.comp2042.gameLogic.MatrixOperations;

final class JBrick implements Brick {

    private final List<int[][]> brickMatrix = new ArrayList<>();

    public JBrick() {
        // 0 - Up (Head left, tail right-up -> No, standard SRS: J points Up means tail is UP-LEFT relative to center)
        // SRS 0:
        // X . .
        // X X X
        // . . .
        brickMatrix.add(new int[][]{
                {2, 0, 0, 0},
                {2, 2, 2, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0}
        });
        // 1 - Right
        // . 2 2
        // . 2 .
        // . 2 .
        brickMatrix.add(new int[][]{
                {0, 2, 2, 0},
                {0, 2, 0, 0},
                {0, 2, 0, 0},
                {0, 0, 0, 0}
        });
        // 2 - Down
        // . . .
        // 2 2 2
        // . . 2
        brickMatrix.add(new int[][]{
                {0, 0, 0, 0},
                {2, 2, 2, 0},
                {0, 0, 2, 0},
                {0, 0, 0, 0}
        });
        // 3 - Left
        // . 2 .
        // . 2 .
        // 2 2 .
        brickMatrix.add(new int[][]{
                {0, 2, 0, 0},
                {0, 2, 0, 0},
                {2, 2, 0, 0},
                {0, 0, 0, 0}
        });
    }

    @Override
    public List<int[][]> getShapeMatrix() {
        return MatrixOperations.deepCopyList(brickMatrix);
    }
}
