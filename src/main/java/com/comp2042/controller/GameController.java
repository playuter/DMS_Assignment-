package com.comp2042.controller;

import com.comp2042.constants.GameConstants;
import com.comp2042.data.ClearRow;
import com.comp2042.data.DownData;
import com.comp2042.events.InputEventListener;
import com.comp2042.events.MoveEvent;
import com.comp2042.gameLogic.LockDelayManager;
import com.comp2042.model.Board;
import com.comp2042.model.SimpleBoard;
import com.comp2042.view.ViewData;

/**
 * Manages the core game logic and coordinates interactions between the model (Board) and the view (GuiController).
 * This class implements InputEventListener to respond to user actions and game events.
 * It handles game state updates, brick locking, line clearing, and scoring.
 */
public class GameController implements InputEventListener {

    private Board board;

    private final GuiController viewGuiController;

    private final LockDelayManager lockDelayManager;

    /**
     * Creates a new GameController.
     * 
     * @param c The GuiController instance for view updates.
     * @param rows The number of rows in the game board.
     * @param cols The number of columns in the game board.
     */
    public GameController(GuiController c, int rows, int cols) {
        viewGuiController = c;
        this.board = new SimpleBoard(rows, cols);
        board.createNewBrick();
        
        // Initialize lock delay manager with the action to perform on lock
        lockDelayManager = new LockDelayManager(this::lockBrick);
        
        viewGuiController.setEventListener(this);
        viewGuiController.initGameView(board.getBoardMatrix(), board.getViewData());
        viewGuiController.bindScore(board.getScore().scoreProperty());
    }

    /**
     * Locks the current brick into the board, plays sound, checks for cleared rows,
     * updates the score, and triggers animations or game over if necessary.
     */
    private void lockBrick() {
        lockDelayManager.cancelLockTimer();
        SoundManager.play("bop");
        board.mergeBrickToBackground();
        ClearRow clearRow = board.clearRows();
        
        Runnable afterLockAction = () -> {
            if (board.createNewBrick()) {
                viewGuiController.gameOver();
            }
            viewGuiController.refreshGameBackground(board.getBoardMatrix());
            viewGuiController.refreshBrick(board.getViewData());
        };

        if (clearRow.getLinesRemoved() > 0) {
            int scoreBonus = GameConstants.SCORE_PER_LINE * clearRow.getLinesRemoved() * clearRow.getLinesRemoved();
            board.getScore().add(scoreBonus);
            
            // Trigger score animation if more than 1 line cleared
            if (clearRow.getLinesRemoved() > 1) {
                viewGuiController.animateScore();
            }
            
            viewGuiController.animateClearRows(clearRow.getClearedRows(), afterLockAction);
        } else {
            afterLockAction.run();
        }
    }

    /**
     * Handles the DOWN move event.
     * Moves the brick down or performs a hard drop.
     * 
     * @param event The move event containing event type and source.
     * @return The updated DownData containing view information.
     */
    @Override
    public DownData onDownEvent(MoveEvent event) {
        if (event.getEventType() == com.comp2042.events.EventType.HARD_DROP) {
            board.hardDrop();
            lockBrick(); // Immediate lock on hard drop
            return new DownData(null, board.getViewData());
        }

        boolean moved = board.moveBrickDown();
        if (moved) {
            lockDelayManager.cancelLockTimer();
        } else {
            lockDelayManager.startLockTimer();
        }
        
        return new DownData(null, board.getViewData());
    }

    /**
     * Handles the LEFT move event.
     * Moves the brick to the left if possible.
     * 
     * @param event The move event.
     * @return The updated ViewData.
     */
    @Override
    public ViewData onLeftEvent(MoveEvent event) {
        if (board.moveBrickLeft()) {
            lockDelayManager.resetLockTimer();
        }
        return board.getViewData();
    }

    /**
     * Handles the RIGHT move event.
     * Moves the brick to the right if possible.
     * 
     * @param event The move event.
     * @return The updated ViewData.
     */
    @Override
    public ViewData onRightEvent(MoveEvent event) {
        if (board.moveBrickRight()) {
            lockDelayManager.resetLockTimer();
        }
        return board.getViewData();
    }

    /**
     * Handles the ROTATE move event.
     * Rotates the brick if possible.
     * 
     * @param event The move event.
     * @return The updated ViewData.
     */
    @Override
    public ViewData onRotateEvent(MoveEvent event) {
        if (board.rotateLeftBrick()) {
            lockDelayManager.resetLockTimer();
        }
        return board.getViewData();
    }


    /**
     * Starts a new game by resetting the board and view.
     */
    @Override
    public void createNewGame() {
        board.newGame();
        viewGuiController.refreshGameBackground(board.getBoardMatrix());
    }
}
