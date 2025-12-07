package com.comp2042.data;

import com.comp2042.view.ViewData;

/**
 * Data class wrapping the result of a "move down" operation.
 * Contains information about any rows cleared during the move and the updated view data.
 */
public final class DownData {
    private final ClearRow clearRow;
    private final ViewData viewData;

    /**
     * Creates a new DownData object.
     * 
     * @param clearRow The result of any row clearing (can be null if no rows cleared).
     * @param viewData The updated view data after the move.
     */
    public DownData(ClearRow clearRow, ViewData viewData) {
        this.clearRow = clearRow;
        this.viewData = viewData;
    }

    /**
     * Gets the ClearRow data.
     * 
     * @return The ClearRow object, or null if no lines were cleared.
     */
    public ClearRow getClearRow() {
        return clearRow;
    }

    /**
     * Gets the updated view data.
     * 
     * @return The ViewData object.
     */
    public ViewData getViewData() {
        return viewData;
    }
}
