package com.comp2042.events;

import com.comp2042.data.DownData;
import com.comp2042.view.ViewData;

/**
 * Interface for handling input events that affect the game state.
 * Controllers implement this to respond to movement and rotation commands.
 */
public interface InputEventListener {

    /**
     * Handles a downward movement event (gravity or soft drop).
     * 
     * @param event The move event details.
     * @return Data containing the result of the move, including any cleared rows.
     */
    DownData onDownEvent(MoveEvent event);

    /**
     * Handles a left movement event.
     * 
     * @param event The move event details.
     * @return Updated view data reflecting the new position.
     */
    ViewData onLeftEvent(MoveEvent event);

    /**
     * Handles a right movement event.
     * 
     * @param event The move event details.
     * @return Updated view data reflecting the new position.
     */
    ViewData onRightEvent(MoveEvent event);

    /**
     * Handles a rotation event.
     * 
     * @param event The move event details.
     * @return Updated view data reflecting the new orientation.
     */
    ViewData onRotateEvent(MoveEvent event);

    /**
     * Resets the game state to start a new game.
     */
    void createNewGame();
}
