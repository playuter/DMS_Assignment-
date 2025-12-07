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

import com.comp2042.constants.GameConstants;

/**
 * Default implementation of the Board interface.
 * Manages the game state, including the grid matrix, the current falling brick,
 * collision detection, and row clearing logic.
 */
public class SimpleBoard implements Board {

    private final int width;
    private final int height;
    private final BrickGenerator brickGenerator;
    private final BrickRotator brickRotator;
    private int[][] currentGameMatrix;
    private Point currentOffset;
    private final Score score;

    /**
     * Creates a new SimpleBoard with the specified dimensions.
     * 
     * @param width The number of rows in the board.
     * @param height The number of columns in the board.
     */
    public SimpleBoard(int width, int height) {
        this.width = width;
        this.height = height;
        currentGameMatrix = new int[width][height];
        brickGenerator = new RandomBrickGenerator();
        brickRotator = new BrickRotator();
        score = new Score();
    }

    /**
     * {@inheritDoc}
     * Uses MatrixOperations to check for collisions.
     */
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

    /**
     * {@inheritDoc}
     * Calculates the shadow position and moves the brick directly to it.
     */
    @Override
    public void hardDrop() {
        int shadowY = calculateShadowY();
        currentOffset.setLocation(currentOffset.getX(), shadowY);
    }


    /**
     * {@inheritDoc}
     */
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

    /**
     * {@inheritDoc}
     */
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

    /**
     * {@inheritDoc}
     * Implements the Super Rotation System (SRS) wall kicks to allow rotation in tight spaces.
     */
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

    /**
     * {@inheritDoc}
     * Centers the new brick based on the board width.
     */
    @Override
    public boolean createNewBrick() {
        Brick currentBrick = brickGenerator.getBrick();
        brickRotator.setBrick(currentBrick);
        
        // Dynamic spawn position centering
        // Standard board is 10 cols wide.
        // Insane board is 20 cols wide.
        // Note: 'this.height' holds the number of columns (passed as second arg to constructor)
        //       'this.width' holds the number of rows.
        
        int boardWidth = this.height; // Columns
        int centerX;
        if (boardWidth > GameConstants.DEFAULT_BOARD_WIDTH) {
            // Wide board (Insane Mode)
            centerX = (boardWidth / 2) - 2; 
        } else {
            // Standard board (Normal/Extra)
            centerX = (boardWidth / 2) - 1;
        }
        
        currentOffset = new Point(centerX, 2);
        return MatrixOperations.intersect(currentGameMatrix, brickRotator.getCurrentShape(), (int) currentOffset.getX(), (int) currentOffset.getY());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int[][] getBoardMatrix() {
        return currentGameMatrix;
    }

    /**
     * {@inheritDoc}
     */
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

    /**
     * Calculates the Y-coordinate where the current brick would land if dropped instantly.
     * Used for rendering the "ghost" piece.
     * 
     * @return The Y-coordinate for the shadow.
     */
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

    /**
     * {@inheritDoc}
     */
    @Override
    public void mergeBrickToBackground() {
        currentGameMatrix = MatrixOperations.merge(currentGameMatrix, brickRotator.getCurrentShape(), (int) currentOffset.getX(), (int) currentOffset.getY());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ClearRow clearRows() {
        ClearRow clearRow = MatrixOperations.checkRemoving(currentGameMatrix);
        currentGameMatrix = clearRow.getNewMatrix();
        return clearRow;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Score getScore() {
        return score;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void newGame() {
        currentGameMatrix = new int[width][height];
        score.reset();
        createNewBrick();
    }
}
