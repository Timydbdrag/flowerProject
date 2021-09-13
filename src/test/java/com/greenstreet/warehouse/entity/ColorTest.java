package com.greenstreet.warehouse.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ColorTest {

    @Test
    void testEquals() {
        Color colorTest1 = getColorTest();
        Color colorTest2 = getColorTest();
        assertEquals(colorTest2, colorTest1);
        assertEquals(colorTest1, colorTest2);
        assertEquals(colorTest1, colorTest1);
    }

    @Test
    void notEquals(){
        Color color = new Color();
        Color color2 = getColorTest();
        assertNotEquals(color, color2);
    }

    @Test
    void canEqual() {
        assertTrue(getColorTest().getClass().isInstance(getColorTest()));
    }

    @Test
    void testHashCode() {
        Color colorTest1 = getColorTest();
        Color colorTest2 = getColorTest();
        assertEquals(colorTest1.hashCode(), colorTest2.hashCode());
    }

    private Color getColorTest(){
        return new Color(1, "Green");
    }
}