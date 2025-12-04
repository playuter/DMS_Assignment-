package com.comp2042.logic.bricks;

import java.util.ArrayList;
import java.util.List;

import com.comp2042.gameLogic.MatrixOperations;

final class OBrick implements Brick {

    private final List<int[][]> brickMatrix = new ArrayList<>();

    public OBrick() {
        // O Piece is invariant, but for consistency we add 4 states
        // Center is usually considered between the blocks
        // . 4 4 .
        // . 4 4 .
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

    @Override
    public List<int[][]> getShapeMatrix() {
        return MatrixOperations.deepCopyList(brickMatrix);
    }

}
