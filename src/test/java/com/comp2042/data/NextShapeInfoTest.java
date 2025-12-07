package com.comp2042.data;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class NextShapeInfoTest {

    @Test
    void testNextShapeInfo() {
        int[][] shape = {{1, 0}, {0, 1}};
        int position = 2;
        
        NextShapeInfo info = new NextShapeInfo(shape, position);
        
        assertEquals(position, info.getPosition());
        
        // Check if getShape returns a copy
        int[][] retrievedShape = info.getShape();
        assertNotSame(shape, retrievedShape);
        assertArrayEquals(shape[0], retrievedShape[0]);
    }
}

