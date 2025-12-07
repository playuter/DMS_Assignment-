package com.comp2042.model;

import com.comp2042.data.ClearRow;
import com.comp2042.data.NextShapeInfo;
import com.comp2042.gameLogic.BrickRotator;
import com.comp2042.gameLogic.MatrixOperations;
import com.comp2042.logic.bricks.Brick;
import com.comp2042.logic.bricks.BrickGenerator;
import com.comp2042.logic.bricks.RandomBrickGenerator;
import com.comp2042.logic.bricks.WallKickData;
import com.comp2042.view.ViewData;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class SimpleBoard implements Board {

    private final int width;
    private final int height;
    private final BrickGenerator brickGenerator;
    private final BrickRotator brickRotator;
    private int[][] currentGameMatrix;
    private Point currentOffset;
    private final Score score;

    private boolean isInsaneMode = false;
    private boolean heartSpawned = false;
    private boolean heartCollected = false;

    public SimpleBoard(int width, int height) {
        this.width = width;
        this.height = height;
        currentGameMatrix = new int[width][height];
        brickGenerator = new RandomBrickGenerator();
        brickRotator = new BrickRotator();
        score = new Score();
    }

    @Override
    public boolean moveBrickDown() {
        int[][] currentMatrix = MatrixOperations.copy(currentGameMatrix);
        Point p = new Point(currentOffset);
        p.translate(0, 1);
        boolean conflict = MatrixOperations.intersect(currentMatrix, brickRotator.getCurrentShape(), (int) p.getX(), (int) p.getY());
        if (conflict) {
            return false;
        } else {
            currentOffset = p;
            return true;
        }
    }

    @Override
    public void hardDrop() {
        int shadowY = calculateShadowY();
        currentOffset.setLocation(currentOffset.getX(), shadowY);
    }


    @Override
    public boolean moveBrickLeft() {
        int[][] currentMatrix = MatrixOperations.copy(currentGameMatrix);
        Point p = new Point(currentOffset);
        p.translate(-1, 0);
        boolean conflict = MatrixOperations.intersect(currentMatrix, brickRotator.getCurrentShape(), (int) p.getX(), (int) p.getY());
        if (conflict) {
            return false;
        } else {
            currentOffset = p;
            return true;
        }
    }

    @Override
    public boolean moveBrickRight() {
        int[][] currentMatrix = MatrixOperations.copy(currentGameMatrix);
        Point p = new Point(currentOffset);
        p.translate(1, 0);
        boolean conflict = MatrixOperations.intersect(currentMatrix, brickRotator.getCurrentShape(), (int) p.getX(), (int) p.getY());
        if (conflict) {
            return false;
        } else {
            currentOffset = p;
            return true;
        }
    }

    @Override
    public boolean rotateLeftBrick() {
        int[][] currentMatrix = MatrixOperations.copy(currentGameMatrix);
        NextShapeInfo nextShape = brickRotator.getNextShape();
        int currentRot = brickRotator.getCurrentShapeIndex();
        int nextRot = nextShape.getPosition();
        Point[] kicks = WallKickData.getKicks(brickRotator.getBrick(), currentRot, nextRot);

        for (Point kick : kicks) {
            int testX = (int) currentOffset.getX() + kick.x;
            int testY = (int) currentOffset.getY() + kick.y;

            boolean conflict = MatrixOperations.intersect(currentMatrix, nextShape.getShape(), testX, testY);
            if (!conflict) {
                currentOffset.translate(kick.x, kick.y);
                brickRotator.setCurrentShape(nextRot);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean createNewBrick() {
        Brick currentBrick = brickGenerator.getBrick();
        brickRotator.setBrick(currentBrick);
        
        // Insane Mode Heart/Bonus Brick Logic
        if (isInsaneMode && !heartSpawned && score.scoreProperty().get() >= 150) {
             // Force spawn on next brick
             heartSpawned = true;
             // Override brick color/value to 9 (Bonus Brick)
             int[][] shape = brickRotator.getCurrentShape();
             for (int i=0; i<shape.length; i++) {
                 for (int j=0; j<shape[i].length; j++) {
                     if (shape[i][j] != 0) {
                         shape[i][j] = 9; // Set to Bonus Brick Value
                     }
                 }
             }
        }
        
        // Dynamic spawn position centering
        // Standard board is 10 cols wide.
        // Insane board is 20 cols wide.
        // Note: 'this.height' holds the number of columns (passed as second arg to constructor)
        //       'this.width' holds the number of rows.
        
        int boardWidth = this.height; // Columns
        int centerX;
        if (boardWidth > 10) {
            // Wide board (Insane Mode)
            centerX = (boardWidth / 2) - 2; // 20/2 - 2 = 8
        } else {
            // Standard board (Normal/Extra)
            centerX = (boardWidth / 2) - 1; // 10/2 - 1 = 4
        }
        
        currentOffset = new Point(centerX, 2);
        return MatrixOperations.intersect(currentGameMatrix, brickRotator.getCurrentShape(), (int) currentOffset.getX(), (int) currentOffset.getY());
    }

    @Override
    public void setInsaneMode(boolean insaneMode) {
        this.isInsaneMode = insaneMode;
    }
    
    @Override
    public boolean isHeartCollectedInLastClear() {
        boolean collected = heartCollected;
        heartCollected = false; // Reset after reading
        return collected;
    }

    @Override
    public int[][] getBoardMatrix() {
        return currentGameMatrix;
    }

    @Override
    public ViewData getViewData() {
        int shadowY = calculateShadowY();
        List<int[][]> nextBricksData = new ArrayList<>();
        for (Brick brick : brickGenerator.getNextBricks()) {
            nextBricksData.add(brick.getShapeMatrix().get(0));
        }
        return new ViewData(brickRotator.getCurrentShape(), 
                          (int) currentOffset.getX(), 
                          (int) currentOffset.getY(), 
                          brickGenerator.getNextBrick().getShapeMatrix().get(0), 
                          nextBricksData, 
                          shadowY);
    }

    private int calculateShadowY() {
        int[][] currentMatrix = MatrixOperations.copy(currentGameMatrix);
        int[][] shape = brickRotator.getCurrentShape();
        int x = (int) currentOffset.getX();
        int y = (int) currentOffset.getY();
        
        while (true) {
            y++;
            if (MatrixOperations.intersect(currentMatrix, shape, x, y)) {
                return y - 1;
            }
        }
    }

    @Override
    public void mergeBrickToBackground() {
        currentGameMatrix = MatrixOperations.merge(currentGameMatrix, brickRotator.getCurrentShape(), (int) currentOffset.getX(), (int) currentOffset.getY());
    }

    @Override
    public ClearRow clearRows() {
        ClearRow clearRow = MatrixOperations.checkRemoving(currentGameMatrix);
        
        // Check for Bonus Brick collection (Value 9) in cleared rows
        for (Integer rowIndex : clearRow.getClearedRows()) {
             if (rowIndex < currentGameMatrix.length) { // Safety check
                 for (int val : currentGameMatrix[rowIndex]) {
                     if (val == 9) {
                         heartCollected = true;
                         break;
                     }
                 }
             }
        }
        
        currentGameMatrix = clearRow.getNewMatrix();
        return clearRow;

    }

    @Override
    public void removeRow(int row) {
        if (row >= 0 && row < currentGameMatrix.length) {
            // Remove row by shifting everything above it down
             for (int i = row; i > 0; i--) {
                System.arraycopy(currentGameMatrix[i - 1], 0, currentGameMatrix[i], 0, currentGameMatrix[0].length);
            }
            // Clear top row
            for (int j = 0; j < currentGameMatrix[0].length; j++) {
                currentGameMatrix[0][j] = 0;
            }
        }
    }

    @Override
    public void removeCol(int col) {
        if (col >= 0 && col < currentGameMatrix[0].length) {
            // Clear the column cells.
            for (int i = 0; i < currentGameMatrix.length; i++) {
                currentGameMatrix[i][col] = 0;
            }
        }
    }

    @Override
    public Score getScore() {
        return score;
    }


    @Override
    public void newGame() {
        currentGameMatrix = new int[width][height];
        score.reset();
        heartSpawned = false;
        heartCollected = false;
        createNewBrick();
    }
}
