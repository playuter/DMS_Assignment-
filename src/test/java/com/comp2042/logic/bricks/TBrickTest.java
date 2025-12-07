package com.comp2042.logic.bricks;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class TBrickTest {

    @Test
    void testTBrickStructure() {
        TBrick tBrick = new TBrick();
        List<int[][]> shapes = tBrick.getShapeMatrix();
        
        assertNotNull(shapes);
        assertEquals(4, shapes.size(), "TBrick should have 4 rotation states");
        
        // Verify dimensions (3x3 for T-piece)
        for (int[][] matrix : shapes) {
            assertEquals(3, matrix.length);
            assertEquals(3, matrix[0].length);
        }
        
        // Verify a T shape property in State 0 (Up)
        // [0, 1, 0]
        // [1, 1, 1]
        // [0, 0, 0]
        int[][] state0 = shapes.get(0);
        assertEquals(0, state0[0][0]);
        assertEquals(1, state0[0][1]);
        assertEquals(0, state0[0][2]);
        
        assertEquals(1, state0[1][0]);
        assertEquals(1, state0[1][1]);
        assertEquals(1, state0[1][2]);
    }
}

