package com.comp2042.controller;

import com.comp2042.data.ClearRow;
import com.comp2042.data.DownData;
import com.comp2042.events.InputEventListener;
import com.comp2042.events.MoveEvent;
import com.comp2042.model.Board;
import com.comp2042.model.SimpleBoard;
import com.comp2042.view.ViewData;

public class GameController implements InputEventListener {

    private Board board;

    private final GuiController viewGuiController;

    private javafx.animation.Timeline lockTimer;
    private boolean isLocking = false;
    private static final int LOCK_DELAY_MS = 500;

    public GameController(GuiController c, int rows, int cols) {
        viewGuiController = c;
        this.board = new SimpleBoard(rows, cols);
        board.createNewBrick();
        
        lockTimer = new javafx.animation.Timeline(new javafx.animation.KeyFrame(javafx.util.Duration.millis(LOCK_DELAY_MS), e -> lockBrick()));
        lockTimer.setCycleCount(1);
        
        viewGuiController.setEventListener(this);
        viewGuiController.initGameView(board.getBoardMatrix(), board.getViewData());
        viewGuiController.bindScore(board.getScore().scoreProperty());
    }

    private void startLockTimer() {
        if (!isLocking) {
            isLocking = true;
            lockTimer.playFromStart();
        }
    }

    private void cancelLockTimer() {
        if (isLocking) {
            isLocking = false;
            lockTimer.stop();
        }
    }

    private void resetLockTimer() {
        if (isLocking) {
            lockTimer.stop();
            lockTimer.playFromStart();
        }
    }

    private void lockBrick() {
        cancelLockTimer();
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
            board.getScore().add(clearRow.getScoreBonus());
            
            // Trigger score animation if more than 1 line cleared
            if (clearRow.getLinesRemoved() > 1) {
                viewGuiController.animateScore();
            }
            
            viewGuiController.animateClearRows(clearRow.getClearedRows(), afterLockAction);
        } else {
            afterLockAction.run();
        }
    }

    @Override
    public DownData onDownEvent(MoveEvent event) {
        if (event.getEventType() == com.comp2042.events.EventType.HARD_DROP) {
            board.hardDrop();
            lockBrick(); // Immediate lock on hard drop
            return new DownData(null, board.getViewData());
        }

        boolean moved = board.moveBrickDown();
        if (moved) {
            cancelLockTimer();
        } else {
            startLockTimer();
        }
        
        return new DownData(null, board.getViewData());
    }

    @Override
    public ViewData onLeftEvent(MoveEvent event) {
        if (board.moveBrickLeft()) {
            resetLockTimer();
        }
        return board.getViewData();
    }

    @Override
    public ViewData onRightEvent(MoveEvent event) {
        if (board.moveBrickRight()) {
            resetLockTimer();
        }
        return board.getViewData();
    }

    @Override
    public ViewData onRotateEvent(MoveEvent event) {
        if (board.rotateLeftBrick()) {
            resetLockTimer();
        }
        return board.getViewData();
    }


    @Override
    public void createNewGame() {
        board.newGame();
        viewGuiController.refreshGameBackground(board.getBoardMatrix());
    }
}
