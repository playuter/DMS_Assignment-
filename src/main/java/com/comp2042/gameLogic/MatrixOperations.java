package com.comp2042.gameLogic;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.stream.Collectors;

import com.comp2042.data.ClearRow;

/**
 * Utility class for performing matrix operations on the game board.
 * Handles intersection (collision) checks, merging bricks into the board,
 * copying matrices, and clearing full rows.
 */
public class MatrixOperations {


    //We don't want to instantiate this utility class
    private MatrixOperations(){

    }

    /**
     * Checks if a brick collides with existing blocks in the matrix or goes out of bounds.
     * 
     * @param matrix The game board matrix.
     * @param brick The brick shape matrix.
     * @param x The x-coordinate of the brick (top-left).
     * @param y The y-coordinate of the brick (top-left).
     * @return True if there is a collision or out of bounds, false otherwise.
     */
    public static boolean intersect(final int[][] matrix, final int[][] brick, int x, int y) {
        for (int i = 0; i < brick.length; i++) {
            for (int j = 0; j < brick[i].length; j++) {
                int targetX = x + i;
                int targetY = y + j;
                if (brick[j][i] != 0 && (checkOutOfBound(matrix, targetX, targetY) || matrix[targetY][targetX] != 0)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Helper method to check if a coordinate is within the bounds of the matrix.
     * 
     * @param matrix The matrix to check against.
     * @param targetX The x-coordinate.
     * @param targetY The y-coordinate.
     * @return True if out of bounds, false if within bounds.
     */
    private static boolean checkOutOfBound(int[][] matrix, int targetX, int targetY) {
        boolean returnValue = true;
        if (targetX >= 0 && targetY < matrix.length && targetX < matrix[targetY].length) {
            returnValue = false;
        }
        return returnValue;
    }

    /**
     * Creates a deep copy of a 2D integer array.
     * 
     * @param original The matrix to copy.
     * @return A new 2D array with the same contents.
     */
    public static int[][] copy(int[][] original) {
        int[][] myInt = new int[original.length][];
        for (int i = 0; i < original.length; i++) {
            int[] aMatrix = original[i];
            int aLength = aMatrix.length;
            myInt[i] = new int[aLength];
            System.arraycopy(aMatrix, 0, myInt[i], 0, aLength);
        }
        return myInt;
    }

    /**
     * Merges a brick's shape into the game board matrix.
     * Used when a brick locks into place.
     * 
     * @param filledFields The current game board matrix.
     * @param brick The brick shape matrix.
     * @param x The x-coordinate of the brick.
     * @param y The y-coordinate of the brick.
     * @return A new matrix with the brick merged in.
     */
    public static int[][] merge(int[][] filledFields, int[][] brick, int x, int y) {
        int[][] copy = copy(filledFields);
        for (int i = 0; i < brick.length; i++) {
            for (int j = 0; j < brick[i].length; j++) {
                int targetX = x + i;
                int targetY = y + j;
                if (brick[j][i] != 0) {
                    copy[targetY][targetX] = brick[j][i];
                }
            }
        }
        return copy;
    }

    /**
     * Checks the matrix for full rows and clears them.
     * Shifts rows down to fill the cleared space.
     * 
     * @param matrix The game board matrix to check.
     * @return A ClearRow object containing the new matrix and cleared row indices.
     */
    public static ClearRow checkRemoving(final int[][] matrix) {
        int[][] tmp = new int[matrix.length][matrix[0].length];
        Deque<int[]> newRows = new ArrayDeque<>();
        List<Integer> clearedRows = new ArrayList<>();

        for (int i = 0; i < matrix.length; i++) {
            int[] tmpRow = new int[matrix[i].length];
            boolean rowToClear = true;
            for (int j = 0; j < matrix[0].length; j++) {
                if (matrix[i][j] == 0) {
                    rowToClear = false;
                }
                tmpRow[j] = matrix[i][j];
            }
            if (rowToClear) {
                clearedRows.add(i);
            } else {
                newRows.add(tmpRow);
            }
        }
        for (int i = matrix.length - 1; i >= 0; i--) {
            int[] row = newRows.pollLast();
            if (row != null) {
                tmp[i] = row;
            } else {
                break;
            }
        }
        return new ClearRow(clearedRows.size(), tmp, clearedRows);
    }

    /**
     * Creates a deep copy of a list of 2D integer arrays.
     * 
     * @param list The list to copy.
     * @return A new list containing copies of the arrays.
     */
    public static List<int[][]> deepCopyList(List<int[][]> list){
        return list.stream().map(MatrixOperations::copy).collect(Collectors.toList());
    }

}
