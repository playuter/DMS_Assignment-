package com.comp2042.data;

import com.comp2042.gameLogic.MatrixOperations;

/**
 * Data class representing the result of a row clearing operation.
 * Contains information about which lines were cleared and the new state of the board matrix.
 */
public final class ClearRow {

    private final int linesRemoved;
    private final int[][] newMatrix;
    private final java.util.List<Integer> clearedRows;

    /**
     * Creates a new ClearRow object.
     * 
     * @param linesRemoved The number of lines that were cleared.
     * @param newMatrix The new board matrix after lines are removed and shifted.
     * @param clearedRows A list of the indices of the rows that were cleared.
     */
    public ClearRow(int linesRemoved, int[][] newMatrix, java.util.List<Integer> clearedRows) {
        this.linesRemoved = linesRemoved;
        this.newMatrix = newMatrix;
        this.clearedRows = clearedRows;
    }

    /**
     * Gets the number of lines removed.
     * 
     * @return The count of removed lines.
     */
    public int getLinesRemoved() {
        return linesRemoved;
    }

    /**
     * Gets the indices of the rows that were cleared.
     * 
     * @return A list of row indices.
     */
    public java.util.List<Integer> getClearedRows() {
        return clearedRows;
    }

    /**
     * Gets the new board matrix.
     * 
     * @return A copy of the 2D integer array representing the board.
     */
    public int[][] getNewMatrix() {
        return MatrixOperations.copy(newMatrix);
    }
}
