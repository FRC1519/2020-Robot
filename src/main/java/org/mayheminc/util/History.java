package org.mayheminc.util;

import edu.wpi.first.wpilibj.DriverStation;

public class History {
    private static final int HISTORY_SIZE = 50;

    private double time[] = new double[HISTORY_SIZE];
    private double azimuth[] = new double[HISTORY_SIZE];
    private int index = 0;

    public History() {
        // ensure there is at least one element in the history
        add(-1.0, 0.0); // make a fictitious element at t=-1 seconds, with heading of 0.0 degrees
    }

    public void add(double t, double az) {
        time[index] = t;
        azimuth[index] = az;
        index++;
        index %= HISTORY_SIZE;
    }

    protected void reportError(String message) {
        DriverStation.reportError("Looking too far back", false);
    }

    public double getAzForTime(double t) {
        int i = index; // Start just after last entry

        do {
            // Move to one entry earlier
            i = (i + HISTORY_SIZE - 1) % HISTORY_SIZE;
            
            // Check if the time entry is old enough
            if (time[i] <= t) {
                int prev = (i + 1) % HISTORY_SIZE;
                if (prev != index && time[i] >= 0.0) {
                    // Interpolate between the two closest entries
                    assert(time[i] < time[prev]);
                    double factor = (t - time[i]) / (time[prev] - time[i]);
                    return azimuth[i] + factor * (azimuth[prev] - azimuth[i]);
                } else {
                    // Only one (real) entry; no interpolation possible
                    return azimuth[i];
                }
            }
        } while (i != index);

        // Oldest entry is still newer than the requested timestamp
        // Return the oldest entry in the history 
        reportError("Looking too far back");
        return azimuth[index];
    }
}