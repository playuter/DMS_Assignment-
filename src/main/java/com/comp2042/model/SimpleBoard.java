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
             // We need to modify the shape matrix of the current brick instance or create a copy
             // Since Brick objects might be shared, let's just modify the current matrix after loading it
             // Actually, best to do it after checking collision, but we need to see it falling.
             // Let's modify the values in brickRotator's current shape.
             
             // Note: This modifies the int[][] array in place.
             // We must be careful not to affect the shared static definitions if they are static.
             // But getBrick() usually returns a new instance or we can copy it.
             // The brickRotator.setCurrentShape takes an int[][], let's modify that.
             
             // To be safe, we should ensure we are working with a mutable copy in BrickRotator
             // For now, let's assume we can modify the values in the rotator's current shape matrix.
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
        // We need to check the original matrix rows that correspond to cleared rows
        // But clearRow.getClearedRows() gives indices.
        // We need to check BEFORE the matrix is updated in checkRemoving, but checkRemoving does it all.
        // So we should probably modify checkRemoving or check here if we had access to old matrix.
        // Alternative: Check the rows *before* calling checkRemoving.
        
        // Let's re-implement checking logic here briefly or just iterate the cleared rows indices on the *old* matrix
        // But we don't have the old matrix unless we copy it.
        // Wait, 'currentGameMatrix' is updated to 'clearRow.getNewMatrix()' *after* this check in the original code?
        // No, 'MatrixOperations.checkRemoving(currentGameMatrix)' returns a ClearRow which contains the NEW matrix.
        // So 'currentGameMatrix' is still the OLD one until the line below.
        
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
            // Similar to MatrixOperations.checkRemoving but for a specific row
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
            // Remove column by shifting? No, usually columns don't shift in Tetris.
            // Just clear the column cells.
            for (int i = 0; i < currentGameMatrix.length; i++) {
                currentGameMatrix[i][col] = 0;
            }
            
            // Or should it shift left/right? 
            // "Clear any two rows or columns" usually implies they disappear.
            // For columns, gravity usually doesn't apply sideways. 
            // Let's just empty the cells in that column.
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
