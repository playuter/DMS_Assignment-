package com.comp2042.model;

import com.comp2042.constants.GameConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SimpleBoardTest {

    private SimpleBoard board;
    private static final int WIDTH = 10;
    private static final int HEIGHT = 20;

    @BeforeEach
    void setUp() {
        board = new SimpleBoard(WIDTH, HEIGHT);
        board.createNewBrick();
    }

    @Test
    void testBoardInitialization() {
        int[][] matrix = board.getBoardMatrix();
        assertNotNull(matrix);
        assertEquals(WIDTH, matrix.length);
        assertEquals(HEIGHT, matrix[0].length);
    }

    @Test
    void testCreateNewBrick() {
        // Just verify no exception and brick exists (indirectly via matrix check if we could access brick, 
        // but brick is private. We can check if view data is not null)
        assertNotNull(board.getViewData());
    }

    @Test
    void testMoveBrickDown() {
        // Initially it should be able to move down from top
        assertTrue(board.moveBrickDown());
    }

    @Test
    void testScoreInitialization() {
        assertNotNull(board.getScore());
        assertEquals(0, board.getScore().scoreProperty().get());
    }
}

