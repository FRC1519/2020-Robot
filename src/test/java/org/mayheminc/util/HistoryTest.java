package org.mayheminc.util;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class HistoryTest {
    @Test
    public void runHistoryTest() {
        History hist = new History();

        // Returns 0 before any data
        assertEquals(0.0, hist.getAzForTime(1.0));
        
        // Add first "real" data
        hist.add(1.0, 101.0);
        assertEquals(101.0, hist.getAzForTime(1.0));
        assertEquals(101.0, hist.getAzForTime(1.5));
        assertEquals(0.0, hist.getAzForTime(0.5));

        // Add second entry and check interpolation
        hist.add(2.0, 102.0);
        assertEquals(102.0, hist.getAzForTime(2.0));
        assertEquals(101.0, hist.getAzForTime(1.0));
        assertEquals(101.5, hist.getAzForTime(1.5));
        assertEquals(101.8, hist.getAzForTime(1.8));

        // Fill history -- depends on the value of HISTORY_SIZE
        for (int i=3; i <= 50; i++) {
            hist.add(i, 100 + i);
        }
        assertEquals(150.0, hist.getAzForTime(50));
        assertEquals(150.0, hist.getAzForTime(51));
        assertEquals(150.0, hist.getAzForTime(100));
        assertEquals(101.0, hist.getAzForTime(1.0));

        // Overflow history
        hist.add(51, 151);
        assertEquals(151.0, hist.getAzForTime(51));
        assertEquals(151.0, hist.getAzForTime(100));
        assertEquals(102.0, hist.getAzForTime(0.1));
        assertEquals(150.7, hist.getAzForTime(50.7));
    }
} 