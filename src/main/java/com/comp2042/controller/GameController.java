package com.comp2042.controller;

import com.comp2042.data.ClearRow;
import com.comp2042.data.DownData;
import com.comp2042.events.InputEventListener;
import com.comp2042.events.MoveEvent;
import com.comp2042.model.Board;
import com.comp2042.model.SimpleBoard;
import com.comp2042.view.ViewData;

public class GameController implements InputEventListener {

    private Board board = new SimpleBoard(25, 10);

    private final GuiController viewGuiController;

    public GameController(GuiController c) {
        viewGuiController = c;
        board.createNewBrick();
        viewGuiController.setEventListener(this);
        viewGuiController.initGameView(board.getBoardMatrix(), board.getViewData());
        viewGuiController.bindScore(board.getScore().scoreProperty());
    }

    @Override
    public DownData onDownEvent(MoveEvent event) {
        boolean canMove;
        
        if (event.getEventType() == com.comp2042.events.EventType.HARD_DROP) {
            board.hardDrop();
            canMove = false; // Force merge
        } else {
            canMove = board.moveBrickDown();
        }
        
        ClearRow clearRow = null;
        if (!canMove) {
            board.mergeBrickToBackground();
            clearRow = board.clearRows();
            if (clearRow.getLinesRemoved() > 0) {
                board.getScore().add(clearRow.getScoreBonus());
                
                // Animate clearing then continue game loop
                final ClearRow finalClearRow = clearRow;
                viewGuiController.animateClearRows(finalClearRow.getClearedRows(), () -> {
                    if (board.createNewBrick()) {
                        viewGuiController.gameOver();
                    }
                    viewGuiController.refreshGameBackground(board.getBoardMatrix());
                });
            } else {
                // No lines cleared, just continue
                if (board.createNewBrick()) {
                    viewGuiController.gameOver();
                }
                viewGuiController.refreshGameBackground(board.getBoardMatrix());
            }
            
        }
        return new DownData(clearRow, board.getViewData());
    }

    @Override
    public ViewData onLeftEvent(MoveEvent event) {
        board.moveBrickLeft();
        return board.getViewData();
    }

    @Override
    public ViewData onRightEvent(MoveEvent event) {
        board.moveBrickRight();
        return board.getViewData();
    }

    @Override
    public ViewData onRotateEvent(MoveEvent event) {
        board.rotateLeftBrick();
        return board.getViewData();
    }


    @Override
    public void createNewGame() {
        board.newGame();
        viewGuiController.refreshGameBackground(board.getBoardMatrix());
    }
}
