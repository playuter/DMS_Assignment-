package com.comp2042.data;

import com.comp2042.gameLogic.MatrixOperations;

public final class ClearRow {

    private final int linesRemoved;
    private final int[][] newMatrix;
    private final java.util.List<Integer> clearedRows;

    public ClearRow(int linesRemoved, int[][] newMatrix, java.util.List<Integer> clearedRows) {
        this.linesRemoved = linesRemoved;
        this.newMatrix = newMatrix;
        this.clearedRows = clearedRows;
    }

    public int getLinesRemoved() {
        return linesRemoved;
    }

    public java.util.List<Integer> getClearedRows() {
        return clearedRows;
    }

    public int[][] getNewMatrix() {
        return MatrixOperations.copy(newMatrix);
    }
}
