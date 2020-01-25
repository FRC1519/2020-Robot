/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.mayheminc.robot2020;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide
 * numerical or boolean constants. This class should not be used for any other
 * purpose. All constants should be declared globally (i.e. public static). Do
 * not put anything functional in this class.
 *
 * <p>
 * It is advised to statically import this class (or one of its inner classes)
 * wherever the constants are needed, to reduce verbosity.
 */
public final class Constants {

    public final class Talon {
        public static final int DRIVE_RIGHT_A = 3; // high current
        public static final int DRIVE_RIGHT_B = 4; // high current
        public static final int DRIVE_LEFT_A = 1; // high current
        public static final int DRIVE_LEFT_B = 2; // high current

        public static final int SHOOTER_WHEEL = 5; // high current
        public static final int SHOOTER_FEEDER = 6;
        public static final int SHOOTER_TURRET = 7;
        public static final int SHOOTER_HOOD = 8;

        public static final int INTAKE_ROLLERS = 9;
        public static final int INTAKE_EXTENDER = 10;

        public static final int MAGAZINE_TURNTABLE = 11;
        public static final int MAGAZINE_CHIMNEY = 12;

        public static final int CLIMBER_WINCH_LEFT = 13; // high current
        public static final int CLIMBER_WINCH_RIGHT = 14; // high current
        public static final int CLIMBER_WALKER_LEFT = 15;
        public static final int CLIMBER_WALKER_RIGHT = 16;

        public static final int CONTROL_PANEL_ROLLER = 17; // WON"T FIT!!!
    }

    public final class PDP {
        public static final int DRIVE_RIGHT_A = 1;
        public static final int DRIVE_RIGHT_B = 2;
        public static final int DRIVE_LEFT_A = 3;
        public static final int DRIVE_LEFT_B = 4;
    }

}
