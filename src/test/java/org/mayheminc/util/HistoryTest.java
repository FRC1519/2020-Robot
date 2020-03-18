package org.mayheminc.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.junit.jupiter.api.Assertions.*;

import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class HistoryTest {
    @Test
    public void runHistoryTest() {
        History hist = spy(History.class);

        // Stub out the error reporting
        doNothing().when(hist).reportError(anyString());

        // Returns 0 by default before any data
        assertEquals(0.0, hist.getAzForTime(1.1));
        assertEquals(0.0, hist.getAzForTime(0.1));
        verify(hist, never()).reportError(anyString());

        // Add first "real" data
        hist.add(1.0, 101.0);
        assertEquals(101.0, hist.getAzForTime(1.0));
        assertEquals(101.0, hist.getAzForTime(2.0));
        assertEquals(0.0, hist.getAzForTime(0.1));
        verify(hist, never()).reportError(anyString());

        // Add second entry and check interpolation
        hist.add(2.0, 102.0);
        assertEquals(102.0, hist.getAzForTime(2.0));
        assertEquals(101.0, hist.getAzForTime(1.0));
        assertEquals(101.5, hist.getAzForTime(1.5));
        assertEquals(101.8, hist.getAzForTime(1.8));
        verify(hist, never()).reportError(anyString());

        // Fill history -- depends on the value of HISTORY_SIZE
        for (int i=3; i <= 50; i++) {
            hist.add(i, 100 + i);
        }
        assertEquals(150.0, hist.getAzForTime(50));
        assertEquals(150.0, hist.getAzForTime(51));
        assertEquals(150.0, hist.getAzForTime(100));
        assertEquals(101.0, hist.getAzForTime(0.1));
        verify(hist, times(1)).reportError(anyString());
        assertEquals(101.0, hist.getAzForTime(1.0));
        assertEquals(102.0, hist.getAzForTime(2.0));
        verify(hist, times(1)).reportError(anyString());

        // Overflow history
        hist.add(51, 151);
        assertEquals(151.0, hist.getAzForTime(51));
        assertEquals(151.0, hist.getAzForTime(100));
        assertEquals(102.0, hist.getAzForTime(0.1));
        verify(hist, times(2)).reportError(anyString());
        assertEquals(102.0, hist.getAzForTime(1.0));
        verify(hist, times(3)).reportError(anyString());
        assertEquals(102.0, hist.getAzForTime(2.0));
        assertEquals(150.7, hist.getAzForTime(50.7));
        verify(hist, times(3)).reportError(anyString());
    }
}