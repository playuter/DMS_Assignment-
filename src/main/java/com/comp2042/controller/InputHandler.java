package com.comp2042.controller;

import javafx.beans.property.BooleanProperty;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import com.comp2042.events.EventSource;
import com.comp2042.events.EventType;
import com.comp2042.events.InputEventListener;
import com.comp2042.events.MoveEvent;
import com.comp2042.view.ViewData;

/**
  Handles keyboard input for the Tetris game.
  Separates input handling logic from the GUI controller.
 */
public class InputHandler {
    private InputEventListener eventListener;
    private BooleanProperty isPause;
    private BooleanProperty isGameOver;
    private BrickDisplayUpdater displayUpdater;
    
    /**
      Interface for updating the brick display after input events.
      This allows InputHandler to notify GuiController to refresh the display
      without creating a circular dependency.
     */
    public interface BrickDisplayUpdater {
        void refreshBrick(ViewData brick);
        void moveDown(MoveEvent event);
        void newGame();
    }
    
    /**
      Creates a new InputHandler.
      
      @param listener The event listener to handle game logic events
      @param isPause Property indicating if game is paused
      @param isGameOver Property indicating if game is over
      @param displayUpdater Callback to update the display after input
     */
    public InputHandler(InputEventListener listener, 
                       BooleanProperty isPause, 
                       BooleanProperty isGameOver,
                       BrickDisplayUpdater displayUpdater) {
        this.eventListener = listener;
        this.isPause = isPause;
        this.isGameOver = isGameOver;
        this.displayUpdater = displayUpdater;
    }
    
    /**
      Handles a key press event.
      Processes movement keys (LEFT, RIGHT, UP, DOWN) and game control keys (N for new game).
      
      @param keyEvent The key event to process
     */
    public void handleKeyPress(KeyEvent keyEvent) {
        // Only process input if game is not paused and not over
        if (isPause.getValue() == Boolean.FALSE && 
            isGameOver.getValue() == Boolean.FALSE) {
            
            // Handle movement keys
            if (keyEvent.getCode() == KeyCode.LEFT || 
                keyEvent.getCode() == KeyCode.A) {
                ViewData result = eventListener.onLeftEvent(
                    new MoveEvent(EventType.LEFT, EventSource.USER));
                displayUpdater.refreshBrick(result);
                keyEvent.consume();
            }
            
            if (keyEvent.getCode() == KeyCode.RIGHT || 
                keyEvent.getCode() == KeyCode.D) {
                ViewData result = eventListener.onRightEvent(
                    new MoveEvent(EventType.RIGHT, EventSource.USER));
                displayUpdater.refreshBrick(result);
                keyEvent.consume();
            }
            
            if (keyEvent.getCode() == KeyCode.UP || 
                keyEvent.getCode() == KeyCode.W) {
                ViewData result = eventListener.onRotateEvent(
                    new MoveEvent(EventType.ROTATE, EventSource.USER));
                displayUpdater.refreshBrick(result);
                keyEvent.consume();
            }
            
            if (keyEvent.getCode() == KeyCode.DOWN || 
                keyEvent.getCode() == KeyCode.S) {
                displayUpdater.moveDown(
                    new MoveEvent(EventType.DOWN, EventSource.USER));
                keyEvent.consume();
            }
        }
        
        // Handle new game key (works even when paused/game over)
        if (keyEvent.getCode() == KeyCode.N) {
            displayUpdater.newGame();
        }
    }
}