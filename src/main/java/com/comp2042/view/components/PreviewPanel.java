package com.comp2042.view.components;

import com.comp2042.view.ColorMapper;
import com.comp2042.view.ViewData;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import java.util.List;

public class PreviewPanel {

    private final GridPane nextBrickPanel;

    public PreviewPanel(GridPane nextBrickPanel) {
        this.nextBrickPanel = nextBrickPanel;
    }

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

