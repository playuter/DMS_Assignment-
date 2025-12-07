package com.comp2042.data;

import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ClearRowTest {

    @Test
    void testClearRowAccessorsAndImmutability() {
        int linesRemoved = 2;
        int[][] matrix = {{1, 1}, {2, 2}};
        List<Integer> clearedIndices = Arrays.asList(0, 3);
        
        ClearRow clearRow = new ClearRow(linesRemoved, matrix, clearedIndices);
        
        // Basic access
        assertEquals(linesRemoved, clearRow.getLinesRemoved());
        assertEquals(clearedIndices, clearRow.getClearedRows());
        
        // Matrix copy check (ClearRow usually returns a copy to be safe, checking implementation)
        int[][] retrievedMatrix = clearRow.getNewMatrix();
        assertNotSame(matrix, retrievedMatrix);
        assertArrayEquals(matrix[0], retrievedMatrix[0]);
        
        // Ensure modifying retrieved matrix doesn't affect internal state (if deep copy logic exists)
        retrievedMatrix[0][0] = 99;
        assertNotEquals(99, matrix[0][0]); 
    }
}

