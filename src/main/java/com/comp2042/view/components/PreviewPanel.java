package com.comp2042.view.components;

import com.comp2042.view.ColorMapper;
import com.comp2042.view.ViewData;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import java.util.List;

/**
 * View component responsible for displaying the "Next Pieces" preview.
 * Renders the upcoming bricks in a side panel.
 */
public class PreviewPanel {

    private final GridPane nextBrickPanel;

    /**
     * Creates a new PreviewPanel.
     * 
     * @param nextBrickPanel The GridPane container where next bricks will be rendered.
     */
    public PreviewPanel(GridPane nextBrickPanel) {
        this.nextBrickPanel = nextBrickPanel;
    }

    /**
     * Refreshes the preview display with the latest list of upcoming bricks.
     * Clears the previous display and re-renders the new bricks.
     * 
     * @param brick The ViewData object containing the list of next bricks.
     */
    public void refreshNextBricks(ViewData brick) {
        if (nextBrickPanel == null) return;
        nextBrickPanel.getChildren().clear();
        
        List<int[][]> nextBricks = brick.getNextBricksData();
        if (nextBricks == null) return;
        
        for (int k = 0; k < nextBricks.size(); k++) {
            int[][] nextBrickData = nextBricks.get(k);
            GridPane singleBrickContainer = new GridPane();
            singleBrickContainer.setHgap(1);
            singleBrickContainer.setVgap(1);
            
            for (int i = 0; i < nextBrickData.length; i++) {
                for (int j = 0; j < nextBrickData[i].length; j++) {
                    if (nextBrickData[i][j] != 0) {
                        Rectangle rectangle = new Rectangle(15, 15); // Smaller size for preview
                        rectangle.setFill(ColorMapper.getColorForValue(nextBrickData[i][j]));
                        rectangle.setArcWidth(5);
                        rectangle.setArcHeight(5);
                        singleBrickContainer.add(rectangle, j, i);
                    }
                }
            }
            nextBrickPanel.add(singleBrickContainer, 0, k);
        }
    }
}
