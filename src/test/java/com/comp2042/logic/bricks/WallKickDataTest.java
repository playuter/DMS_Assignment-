package com.comp2042.logic.bricks;

import org.junit.jupiter.api.Test;
import java.awt.Point;
import static org.junit.jupiter.api.Assertions.*;

class WallKickDataTest {

    @Test
    void testGetKicks_OBrick() {
        Brick oBrick = new OBrick();
        // O Brick should always return (0,0) as it doesn't rotate/kick
        Point[] kicks = WallKickData.getKicks(oBrick, 0, 1);
        assertEquals(1, kicks.length);
        assertEquals(0, kicks[0].x);
        assertEquals(0, kicks[0].y);
    }

    @Test
    void testGetKicks_IBrick() {
        Brick iBrick = new IBrick();
        // Test a known transition 0->1
        Point[] kicks = WallKickData.getKicks(iBrick, 0, 1);
        
        // Expect 5 test points for SRS I-piece
        assertEquals(5, kicks.length);
        // First kick is always (0,0)
        assertEquals(0, kicks[0].x);
        assertEquals(0, kicks[0].y);
        
        // Verify second kick for 0->1: (-2, 0)
        assertEquals(-2, kicks[1].x);
        assertEquals(0, kicks[1].y);
    }

    @Test
    void testGetKicks_JLSTZ() {
        Brick tBrick = new TBrick();
        // Test a known transition 0->1
        Point[] kicks = WallKickData.getKicks(tBrick, 0, 1);
        
        // Expect 5 test points
        assertEquals(5, kicks.length);
        // First kick is always (0,0)
        assertEquals(0, kicks[0].x);
        assertEquals(0, kicks[0].y);
        
        // Verify second kick for 0->1: (-1, 0)
        assertEquals(-1, kicks[1].x);
        assertEquals(0, kicks[1].y);
    }
}

