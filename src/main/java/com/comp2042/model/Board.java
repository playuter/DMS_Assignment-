package com.comp2042.model;

import com.comp2042.data.ClearRow;
import com.comp2042.view.ViewData;

/**
 * Interface defining the core operations of a Tetris game board.
 * It handles brick movement, rotation, locking, and row clearing.
 */
public interface Board {

    /**
     * Attempts to move the current brick down by one row.
     * 
     * @return True if the move was successful, false if it was blocked.
     */
    boolean moveBrickDown();

    /**
     * Instantly drops the current brick to the lowest possible position.
     */
    void hardDrop();

    /**
     * Attempts to move the current brick one column to the left.
     * 
     * @return True if the move was successful, false if it was blocked.
     */
    boolean moveBrickLeft();

    /**
     * Attempts to move the current brick one column to the right.
     * 
     * @return True if the move was successful, false if it was blocked.
     */
    boolean moveBrickRight();

    /**
     * Attempts to rotate the current brick to the left (counter-clockwise).
     * 
     * @return True if the rotation was successful, false if it was blocked.
     */
    boolean rotateLeftBrick();

    /**
     * Spawns a new brick at the top of the board.
     * 
     * @return True if the new brick can be placed, false if it collides immediately (Game Over).
     */
    boolean createNewBrick();

    /**
     * Gets the current state of the board as a 2D integer array.
     * 
     * @return The board matrix where values represent block colors.
     */
    int[][] getBoardMatrix();

    /**
     * Gets the view data for the current brick, including its position, shape, and shadow.
     * 
     * @return The ViewData object containing brick render information.
     */
    ViewData getViewData();

    /**
     * Merges the current falling brick into the board's static background matrix.
     * This is called when a brick locks into place.
     */
    void mergeBrickToBackground();

    /**
     * Checks for and clears any completed rows in the board.
     * 
     * @return A ClearRow object containing information about cleared lines and the new board state.
     */
    ClearRow clearRows();

    /**
     * Gets the score object associated with the board.
     * 
     * @return The Score object.
     */
    Score getScore();

    /**
     * Resets the board for a new game.
     * Clears the matrix and resets the score.
     */
    void newGame();
}
