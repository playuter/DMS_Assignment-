package com.comp2042.view;

import com.comp2042.gameLogic.MatrixOperations;

import java.util.ArrayList;
import java.util.List;

/**
 * Immutable data class containing all information required to render the game state.
 * This decouples the internal model representation from the view rendering logic.
 */
public final class ViewData {

    private final int[][] brickData;
    private final int xPosition;
    private final int yPosition;
    private final int[][] nextBrickData;
    private final List<int[][]> nextBricksData;
    private final int shadowY;

    /**
     * Creates a new ViewData object with detailed next bricks information.
     * 
     * @param brickData The matrix of the current falling brick.
     * @param xPosition The x-coordinate of the brick.
     * @param yPosition The y-coordinate of the brick.
     * @param nextBrickData The matrix of the immediate next brick.
     * @param nextBricksData A list of matrices for subsequent upcoming bricks.
     * @param shadowY The y-coordinate of the brick's shadow (ghost piece).
     */
    public ViewData(int[][] brickData, int xPosition, int yPosition, int[][] nextBrickData, List<int[][]> nextBricksData, int shadowY) {
        this.brickData = brickData;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.nextBrickData = nextBrickData;
        this.nextBricksData = nextBricksData;
        this.shadowY = shadowY;
    }
    
    /**
     * Creates a new ViewData object with only the immediate next brick.
     * 
     * @param brickData The matrix of the current falling brick.
     * @param xPosition The x-coordinate of the brick.
     * @param yPosition The y-coordinate of the brick.
     * @param nextBrickData The matrix of the immediate next brick.
     * @param shadowY The y-coordinate of the brick's shadow.
     */
    public ViewData(int[][] brickData, int xPosition, int yPosition, int[][] nextBrickData, int shadowY) {
        this(brickData, xPosition, yPosition, nextBrickData, new ArrayList<>(), shadowY);
    }

    /**
     * Gets the current brick's matrix.
     * 
     * @return A copy of the brick data array.
     */
    public int[][] getBrickData() {
        return MatrixOperations.copy(brickData);
    }

    /**
     * Gets the x-coordinate of the current brick.
     * 
     * @return The x position.
     */
    public int getxPosition() {
        return xPosition;
    }

    /**
     * Gets the y-coordinate of the current brick.
     * 
     * @return The y position.
     */
    public int getyPosition() {
        return yPosition;
    }

    /**
     * Gets the matrix of the immediate next brick.
     * 
     * @return A copy of the next brick data array.
     */
    public int[][] getNextBrickData() {
        return MatrixOperations.copy(nextBrickData);
    }

    /**
     * Gets the list of matrices for upcoming bricks.
     * 
     * @return The list of next bricks data.
     */
    public List<int[][]> getNextBricksData() {
        return nextBricksData;
    }
    
    /**
     * Gets the y-coordinate of the shadow (ghost piece).
     * 
     * @return The shadow y position.
     */
    public int getShadowY() {
        return shadowY;
    }
}
