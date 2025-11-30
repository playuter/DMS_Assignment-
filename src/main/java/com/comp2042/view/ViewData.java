package com.comp2042.view;

import com.comp2042.gameLogic.MatrixOperations;

public final class ViewData {

    private final int[][] brickData;
    private final int xPosition;
    private final int yPosition;
    private final int[][] nextBrickData;
    private final int shadowY;

    public ViewData(int[][] brickData, int xPosition, int yPosition, int[][] nextBrickData, int shadowY) {
        this.brickData = brickData;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.nextBrickData = nextBrickData;
        this.shadowY = shadowY;
    }
    
    public ViewData(int[][] brickData, int xPosition, int yPosition, int[][] nextBrickData) {
        this(brickData, xPosition, yPosition, nextBrickData, yPosition);
    }

    public int[][] getBrickData() {
        return MatrixOperations.copy(brickData);
    }

    public int getxPosition() {
        return xPosition;
    }

    public int getyPosition() {
        return yPosition;
    }

    public int[][] getNextBrickData() {
        return MatrixOperations.copy(nextBrickData);
    }
    
    public int getShadowY() {
        return shadowY;
    }
}
