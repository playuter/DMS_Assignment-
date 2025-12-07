package com.comp2042.view;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ColorMapperTest {

    @Test
    void testGetColorForValue() {
        // Test known mappings
        assertEquals(Color.TRANSPARENT, ColorMapper.getColorForValue(0));
        assertEquals(Color.AQUA, ColorMapper.getColorForValue(1));
        assertEquals(Color.BLUEVIOLET, ColorMapper.getColorForValue(2));
        assertEquals(Color.DARKGREEN, ColorMapper.getColorForValue(3));
        assertEquals(Color.YELLOW, ColorMapper.getColorForValue(4));
        assertEquals(Color.RED, ColorMapper.getColorForValue(5));
        assertEquals(Color.BEIGE, ColorMapper.getColorForValue(6));
        assertEquals(Color.BURLYWOOD, ColorMapper.getColorForValue(7));
        
        // Test default
        assertEquals(Color.WHITE, ColorMapper.getColorForValue(99));
        assertEquals(Color.WHITE, ColorMapper.getColorForValue(-1));
    }
    
    @Test
    void testGetTransparentColor() {
        assertEquals(Color.TRANSPARENT, ColorMapper.getTransparentColor());
    }
}

