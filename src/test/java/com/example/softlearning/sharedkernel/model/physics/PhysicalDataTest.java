package com.example.softlearning.sharedkernel.model.physics;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class PhysicalDataTest {

    @Test
    void shouldCreatePhysicalDataAndExposeValues() {
        PhysicalData data = new PhysicalData(2.0, 3.0, 4.0, 1.5, true);

        assertEquals(2.0, data.getHeigth());
        assertEquals(3.0, data.getWidth());
        assertEquals(4.0, data.getDepth());
        assertEquals(1.5, data.getWeigth());
        assertTrue(data.getFragile());
        assertEquals("heigth:2.0;width:3.0;depth:4.0", data.getSize());
        assertEquals(24.0, data.getVolum());
    }

    @Test
    void shouldUpdatePhysicalDataValues() {
        PhysicalData data = new PhysicalData(1.0, 1.0, 1.0, 1.0, false);

        data.setHeigth(5.0);
        data.setWidth(6.0);
        data.setDepth(7.0);
        data.setWeigth(8.0);
        data.setFragile(true);

        assertEquals(5.0, data.getHeigth());
        assertEquals(6.0, data.getWidth());
        assertEquals(7.0, data.getDepth());
        assertEquals(8.0, data.getWeigth());
        assertTrue(data.getFragile());
        assertFalse(new PhysicalData(1.0, 1.0, 1.0, 1.0, false).getFragile());
    }
}
