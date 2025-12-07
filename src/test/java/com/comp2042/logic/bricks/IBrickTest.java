package com.comp2042.logic.bricks;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class IBrickTest {

    @Test
    void testIBrickStructure() {
        IBrick iBrick = new IBrick();
        List<int[][]> shapes = iBrick.getShapeMatrix();
        
        assertNotNull(shapes);
        assertEquals(4, shapes.size(), "IBrick should have 4 rotation states");
        
        // Verify dimensions (4x4 for I-piece)
        for (int[][] matrix : shapes) {
            assertEquals(4, matrix.length);
            assertEquals(4, matrix[0].length);
        }
        
        // Verify state 0 (Horizontal/Flat) contains 1s
        // Row 1 should be all 1s: {1, 1, 1, 1}
        int[][] state0 = shapes.get(0);
        int sum = 0;
        for(int x : state0[1]) sum += x;
        assertEquals(4, sum, "Second row of state 0 should sum to 4 (all 1s)");
    }
    
    @Test
    void testDeepCopy() {
        IBrick iBrick = new IBrick();
        List<int[][]> shapes1 = iBrick.getShapeMatrix();
        List<int[][]> shapes2 = iBrick.getShapeMatrix();
        
        assertNotSame(shapes1, shapes2, "Should return a new list structure");
        assertNotSame(shapes1.get(0), shapes2.get(0), "Should return deep copies of arrays");
        
        // Modify one
        shapes1.get(0)[0][0] = 99;
        assertNotEquals(99, shapes2.get(0)[0][0], "Modification should not affect other copies");
    }
}

