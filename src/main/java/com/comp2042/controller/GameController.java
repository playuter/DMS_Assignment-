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
    private boolean hasHeart = false;
    private boolean heartUsed = false;
    private boolean isRevivalMode = false;
    private int revivalClearsRemaining = 0;

    public GameController(GuiController c, int rows, int cols) {
        viewGuiController = c;
        viewGuiController.setGameController(this);
        this.board = new SimpleBoard(rows, cols);
        board.createNewBrick();
        
        lockTimer = new javafx.animation.Timeline(new javafx.animation.KeyFrame(javafx.util.Duration.millis(LOCK_DELAY_MS), e -> lockBrick()));
        lockTimer.setCycleCount(1);
        
        viewGuiController.setEventListener(this);
        viewGuiController.initGameView(board.getBoardMatrix(), board.getViewData());
        viewGuiController.bindScore(board.getScore().scoreProperty());
    }

    public void setInsaneMode(boolean insaneMode) {
        board.setInsaneMode(insaneMode);
    }
    
    public boolean isRevivalMode() {
        return isRevivalMode;
    }
    
    public void handleRevivalSelection(int row, int col, boolean isRowSelection) {
        if (!isRevivalMode || revivalClearsRemaining <= 0) return;
        
        if (isRowSelection) {
            board.removeRow(row);
        } else {
            board.removeCol(col);
        }
        
        revivalClearsRemaining--;
        viewGuiController.refreshGameBackground(board.getBoardMatrix());
        
        if (revivalClearsRemaining <= 0) {
            endRevivalMode();
        }
    }
    
    private void startRevivalMode() {
        isRevivalMode = true;
        heartUsed = true;
        hasHeart = false;
        revivalClearsRemaining = 2;
        viewGuiController.setHasHeart(false);
        viewGuiController.showRevivalOverlay(true);
    }
    
    private void endRevivalMode() {
        isRevivalMode = false;
        viewGuiController.showRevivalOverlay(false);
        if (board.createNewBrick()) {
             viewGuiController.gameOver();
        }
        viewGuiController.refreshGameBackground(board.getBoardMatrix());
        viewGuiController.refreshBrick(board.getViewData());
    }

    private void startLockTimer() {
        if (!isLocking && !isRevivalMode) {
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

    public void pauseGame() {
        if (isLocking) {
            lockTimer.pause();
        }
    }

    public void resumeGame() {
        if (isLocking) {
            lockTimer.play();
        }
    }

    private void lockBrick() {
        cancelLockTimer();
        SoundManager.play("bop");
        board.mergeBrickToBackground();
        ClearRow clearRow = board.clearRows();
        
        if (board.isHeartCollectedInLastClear()) {
            if (!heartUsed) {
                hasHeart = true;
                viewGuiController.setHasHeart(true);
                SoundManager.play("highscore");
            }
        }
        
        Runnable afterLockAction = () -> {
            if (board.createNewBrick()) {
                if (hasHeart && !heartUsed) {
                    startRevivalMode();
                } else {
                    viewGuiController.gameOver();
                }
            }
            viewGuiController.refreshGameBackground(board.getBoardMatrix());
            viewGuiController.refreshBrick(board.getViewData());
        };

        if (clearRow.getLinesRemoved() > 0) {
            board.getScore().add(clearRow.getScoreBonus());
            
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
        if (isRevivalMode) return new DownData(null, board.getViewData());

        if (event.getEventType() == com.comp2042.events.EventType.HARD_DROP) {
            board.hardDrop();
            lockBrick();
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
        if (isRevivalMode) return board.getViewData();
        if (board.moveBrickLeft()) {
            resetLockTimer();
        }
        return board.getViewData();
    }

    @Override
    public ViewData onRightEvent(MoveEvent event) {
        if (isRevivalMode) return board.getViewData();
        if (board.moveBrickRight()) {
            resetLockTimer();
        }
        return board.getViewData();
    }

    @Override
    public ViewData onRotateEvent(MoveEvent event) {
        if (isRevivalMode) return board.getViewData();
        if (board.rotateLeftBrick()) {
            resetLockTimer();
        }
        return board.getViewData();
    }

    @Override
    public void createNewGame() {
        cancelLockTimer();
        board.newGame();
        hasHeart = false;
        heartUsed = false;
        isRevivalMode = false;
        viewGuiController.setHasHeart(false);
        viewGuiController.showRevivalOverlay(false);
        viewGuiController.refreshGameBackground(board.getBoardMatrix());
    }
}
