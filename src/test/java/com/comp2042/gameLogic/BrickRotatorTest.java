package com.comp2042.gameLogic;

import com.comp2042.data.NextShapeInfo;
import com.comp2042.logic.bricks.Brick;
// IBrick is package-private, so we cannot access it here.
// Instead, we can create a simple anonymous subclass of Brick for testing purposes.
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BrickRotatorTest {

    private BrickRotator rotator;
    private Brick mockBrick;

    // Helper method to create a dummy 3x3 matrix
    private int[][] createMatrix(int val) {
        return new int[][]{{val, val, val}, {val, val, val}, {val, val, val}};
    }

    @BeforeEach
    void setUp() {
        rotator = new BrickRotator();
        
        // Create a mock brick with 4 rotation states
        List<int[][]> shapeMatrix = new ArrayList<>();
        shapeMatrix.add(createMatrix(1)); // State 0
        shapeMatrix.add(createMatrix(2)); // State 1
        shapeMatrix.add(createMatrix(3)); // State 2
        shapeMatrix.add(createMatrix(4)); // State 3

        mockBrick = new Brick() {
            @Override
            public List<int[][]> getShapeMatrix() {
                return shapeMatrix;
            }
        };
        rotator.setBrick(mockBrick);
    }

    @Test
    void testInitialState() {
        assertEquals(0, rotator.getCurrentShapeIndex());
        assertNotNull(rotator.getCurrentShape());
    }

    @Test
    void testGetNextShape() {
        NextShapeInfo nextInfo = rotator.getNextShape();
        assertEquals(1, nextInfo.getPosition());
        
        // Current state should not have changed
        assertEquals(0, rotator.getCurrentShapeIndex());
    }

    @Test
    void testSetCurrentShape() {
        rotator.setCurrentShape(2);
        assertEquals(2, rotator.getCurrentShapeIndex());
        
        // Next shape from 2 should be 3
        assertEquals(3, rotator.getNextShape().getPosition());
    }
    
    @Test
    void testCycleRotation() {
        // IBrick has 4 states (size 4 list of matrices)
        rotator.setCurrentShape(3);
        assertEquals(0, rotator.getNextShape().getPosition(), "Should cycle back to 0 after last state");
    }
}

