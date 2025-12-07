package com.comp2042.gameLogic;

import com.comp2042.data.ClearRow;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MatrixOperationsTest {

    @Test
    void testIntersect_NoCollision() {
        int[][] matrix = new int[10][20];
        int[][] brick = {
            {0, 0, 0},
            {0, 1, 0}, // Simple single block shape
            {0, 0, 0}
        };
        // Place in middle of empty board
        boolean collision = MatrixOperations.intersect(matrix, brick, 5, 5);
        assertFalse(collision, "Should not collide in empty space");
    }

    @Test
    void testIntersect_CollisionWithWall() {
        int[][] matrix = new int[10][20];
        int[][] brick = {
            {1} 
        };
        // Place out of bounds (left)
        boolean collisionLeft = MatrixOperations.intersect(matrix, brick, -1, 5);
        assertTrue(collisionLeft, "Should collide with left wall");

        // Place out of bounds (right)
        // x = 20 (index 20 is out of bounds for size 20)
        boolean collisionRight = MatrixOperations.intersect(matrix, brick, 20, 5); 
        assertTrue(collisionRight, "Should collide with right wall");
    }

    @Test
    void testMerge() {
        int[][] matrix = new int[5][5];
        int[][] brick = {{1}}; // 1x1 brick
        
        // Merge at 2,2
        int[][] newMatrix = MatrixOperations.merge(matrix, brick, 2, 2);
        
        // Expect 1 at [2][2] (Wait, merge uses targetY = y + j, targetX = x + i)
        // merge(..., x=2, y=2). j=0, i=0. targetY=2, targetX=2.
        // matrix[2][2] should be 1.
        assertEquals(1, newMatrix[2][2]);
    }

    @Test
    void testCheckRemoving() {
        // Create a 4x4 matrix
        int[][] matrix = new int[4][4];
        
        // Fill the bottom row (index 3) completely
        for (int j = 0; j < 4; j++) {
            matrix[3][j] = 1;
        }
        // Fill the row above partially
        matrix[2][0] = 1;

        ClearRow result = MatrixOperations.checkRemoving(matrix);
        
        assertEquals(1, result.getLinesRemoved());
        
        // The bottom row should now be empty (or shifted down logic)
        // Actually, logic is: remove full rows, shift others down.
        // Row 3 was full. Row 2 had one block.
        // Result: Row 3 should take content of Row 2. Row 2 content of Row 1...
        // So new matrix[3][0] should be 1 (from old matrix[2][0]).
        assertEquals(1, result.getNewMatrix()[3][0]);
        // And the rest of row 3 should be 0
        assertEquals(0, result.getNewMatrix()[3][1]);
    }
}

