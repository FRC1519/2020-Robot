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
    public final class GAMEPAD_AXIS {
        public static final int GAMEPAD_F310_LEFT_X_AXIS = 0;
        public static final int GAMEPAD_F310_LEFT_Y_AXIS = 1;
        public static final int GAMEPAD_F310_LEFT_TRIGGER = 2;
        public static final int GAMEPAD_F310_RIGHT_TRIGGER = 3;
        public static final int GAMEPAD_F310_RIGHT_X_AXIS = 4;
        public static final int GAMEPAD_F310_RIGHT_Y_AXIS = 5;
    }

    public final class GAMEPAD_BUTTION {
        public static final int GAMEPAD_F310_A_BUTTON = 1;
        public static final int GAMEPAD_F310_B_BUTTON = 2;
        public static final int GAMEPAD_F310_X_BUTTON = 3;
        public static final int GAMEPAD_F310_Y_BUTTON = 4;
        public static final int GAMEPAD_F310_LEFT_BUTTON = 5;
        public static final int GAMEPAD_F310_RIGHT_BUTTON = 6;
        public static final int GAMEPAD_F310_BACK_BUTTON = 7;
        public static final int GAMEPAD_F310_START_BUTTON = 8;
        public static final int GAMEPAD_F310_LEFT_STICK_BUTTON = 9;
        public static final int GAMEPAD_F310_RIGHT_STICK_BUTTON = 10;
    }

    public final class OPERATOR_PAD_AXIS {
        public static final int OPERATOR_PAD_LEFT_X_AXIS = 0;
        public static final int OPERATOR_PAD_LEFT_Y_AXIS = 1;
        public static final int OPERATOR_PAD_RIGHT_X_AXIS = 2;
        public static final int OPERATOR_PAD_RIGHT_Y_AXIS = 3;
    }

    public final class Joysticks {
        public static final int DRIVER_GAMEPAD = 0;
        public static final int DRIVER_JOYSTICK = 1;
        public static final int OPERATOR_GAMEPAD = 2;
        public static final int OPERATOR_JOYSTICK = 3;
    }
}