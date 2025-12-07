package com.comp2042.logic.bricks;

import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

/**
 * Data provider for the Super Rotation System (SRS) wall kicks.
 * Stores the offset data required to rotate pieces when they are blocked by walls or other blocks.
 * See https://tetris.wiki/Super_Rotation_System for details.
 */
public class WallKickData {

    private static final Map<String, Point[]> KICKS_JLSTZ = new HashMap<>();
    private static final Map<String, Point[]> KICKS_I = new HashMap<>();

    static {
        // J, L, S, T, Z Wall Kicks (Y is UP because in grid (0,0) is top-left but kicks are relative cartesian)
        // Wait, standard Grid: Y increases downwards.
        // SRS Definition: (+x is Right, +y is Up).
        // OUR Grid: (+x is Right, +y is Down).
        // So SRS (+1, +2) means Right 1, Up 2.
        // In our grid (+1, +2) means Right 1, DOWN 2.
        // WE MUST INVERT Y COORDINATES FROM SRS DATA TO MATCH OUR GRID.

        // SRS Data for J, L, S, T, Z
        // 0->1 (0->R): (0,0), (-1,0), (-1,+1), (0,-2), (-1,-2)
        // Inverted Y: (0,0), (-1,0), (-1,-1), (0,2), (-1,2)
        KICKS_JLSTZ.put("0-1", new Point[]{new Point(0,0), new Point(-1,0), new Point(-1,-1), new Point(0,2), new Point(-1,2)});

        // 1->0 (R->0): (0,0), (+1,0), (+1,-1), (0,+2), (+1,+2)
        // Inverted Y: (0,0), (+1,0), (+1,1), (0,-2), (+1,-2)
        KICKS_JLSTZ.put("1-0", new Point[]{new Point(0,0), new Point(1,0), new Point(1,1), new Point(0,-2), new Point(1,-2)});

        // 1->2 (R->2): (0,0), (+1,0), (+1,-1), (0,+2), (+1,+2)
        // Inverted Y: (0,0), (+1,0), (+1,1), (0,-2), (+1,-2)
        KICKS_JLSTZ.put("1-2", new Point[]{new Point(0,0), new Point(1,0), new Point(1,1), new Point(0,-2), new Point(1,-2)});

        // 2->1 (2->R): (0,0), (-1,0), (-1,+1), (0,-2), (-1,-2)
        // Inverted Y: (0,0), (-1,0), (-1,-1), (0,2), (-1,2)
        KICKS_JLSTZ.put("2-1", new Point[]{new Point(0,0), new Point(-1,0), new Point(-1,-1), new Point(0,2), new Point(-1,2)});

        // 2->3 (2->L): (0,0), (+1,0), (+1,+1), (0,-2), (+1,-2)
        // Inverted Y: (0,0), (+1,0), (+1,-1), (0,2), (+1,2)
        KICKS_JLSTZ.put("2-3", new Point[]{new Point(0,0), new Point(1,0), new Point(1,-1), new Point(0,2), new Point(1,2)});

        // 3->2 (L->2): (0,0), (-1,0), (-1,-1), (0,+2), (-1,+2)
        // Inverted Y: (0,0), (-1,0), (-1,1), (0,-2), (-1,-2)
        KICKS_JLSTZ.put("3-2", new Point[]{new Point(0,0), new Point(-1,0), new Point(-1,1), new Point(0,-2), new Point(-1,-2)});

        // 3->0 (L->0): (0,0), (-1,0), (-1,-1), (0,+2), (-1,+2)
        // Inverted Y: (0,0), (-1,0), (-1,1), (0,-2), (-1,-2)
        KICKS_JLSTZ.put("3-0", new Point[]{new Point(0,0), new Point(-1,0), new Point(-1,1), new Point(0,-2), new Point(-1,-2)});

        // 0->3 (0->L): (0,0), (+1,0), (+1,+1), (0,-2), (+1,-2)
        // Inverted Y: (0,0), (+1,0), (+1,-1), (0,2), (+1,2)
        KICKS_JLSTZ.put("0-3", new Point[]{new Point(0,0), new Point(1,0), new Point(1,-1), new Point(0,2), new Point(1,2)});

        // SRS Data for I
        // 0->1 (0->R): (0,0), (-2,0), (+1,0), (-2,-1), (+1,+2)
        // Inverted Y: (0,0), (-2,0), (1,0), (-2,1), (1,-2)
        KICKS_I.put("0-1", new Point[]{new Point(0,0), new Point(-2,0), new Point(1,0), new Point(-2,1), new Point(1,-2)});

        // 1->0 (R->0): (0,0), (+2,0), (-1,0), (+2,+1), (-1,-2)
        // Inverted Y: (0,0), (2,0), (-1,0), (2,-1), (-1,2)
        KICKS_I.put("1-0", new Point[]{new Point(0,0), new Point(2,0), new Point(-1,0), new Point(2,-1), new Point(-1,2)});

        // 1->2 (R->2): (0,0), (-1,0), (+2,0), (-1,+2), (+2,-1)
        // Inverted Y: (0,0), (-1,0), (2,0), (-1,-2), (2,1)
        KICKS_I.put("1-2", new Point[]{new Point(0,0), new Point(-1,0), new Point(2,0), new Point(-1,-2), new Point(2,1)});

        // 2->1 (2->R): (0,0), (+1,0), (-2,0), (+1,-2), (-2,+1)
        // Inverted Y: (0,0), (1,0), (-2,0), (1,2), (-2,-1)
        KICKS_I.put("2-1", new Point[]{new Point(0,0), new Point(1,0), new Point(-2,0), new Point(1,2), new Point(-2,-1)});

        // 2->3 (2->L): (0,0), (+2,0), (-1,0), (+2,+1), (-1,-2)
        // Inverted Y: (0,0), (2,0), (-1,0), (2,-1), (-1,2)
        KICKS_I.put("2-3", new Point[]{new Point(0,0), new Point(2,0), new Point(-1,0), new Point(2,-1), new Point(-1,2)});

        // 3->2 (L->2): (0,0), (-2,0), (+1,0), (-2,-1), (+1,+2)
        // Inverted Y: (0,0), (-2,0), (1,0), (-2,1), (1,-2)
        KICKS_I.put("3-2", new Point[]{new Point(0,0), new Point(-2,0), new Point(1,0), new Point(-2,1), new Point(1,-2)});

        // 3->0 (L->0): (0,0), (+1,0), (-2,0), (+1,-2), (-2,+1)
        // Inverted Y: (0,0), (1,0), (-2,0), (1,2), (-2,-1)
        KICKS_I.put("3-0", new Point[]{new Point(0,0), new Point(1,0), new Point(-2,0), new Point(1,2), new Point(-2,-1)});

        // 0->3 (0->L): (0,0), (-1,0), (+2,0), (-1,+2), (+2,-1)
        // Inverted Y: (0,0), (-1,0), (2,0), (-1,-2), (2,1)
        KICKS_I.put("0-3", new Point[]{new Point(0,0), new Point(-1,0), new Point(2,0), new Point(-1,-2), new Point(2,1)});
    }

    /**
     * Retrieves the wall kick offsets for a specific rotation transition.
     * 
     * @param brick The brick being rotated (different rules for I vs others).
     * @param currentRot The current rotation state (0=Up, 1=Right, 2=Down, 3=Left).
     * @param nextRot The target rotation state.
     * @return An array of Point objects representing the test offsets (kicks) to try.
     */
    public static Point[] getKicks(Brick brick, int currentRot, int nextRot) {
        if (brick instanceof OBrick) {
            return new Point[]{new Point(0,0)};
        }

        String key = currentRot + "-" + nextRot;
        if (brick instanceof IBrick) {
            return KICKS_I.getOrDefault(key, new Point[]{new Point(0,0)});
        } else {
            return KICKS_JLSTZ.getOrDefault(key, new Point[]{new Point(0,0)});
        }
    }
}
