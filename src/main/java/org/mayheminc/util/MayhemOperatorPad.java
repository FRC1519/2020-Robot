package org.mayheminc.util;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.button.Button;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

public class MayhemOperatorPad {

        public final class OPERATOR_PAD_AXIS {
                public static final int OPERATOR_PAD_LEFT_X_AXIS = 0;
                public static final int OPERATOR_PAD_LEFT_Y_AXIS = 1;
                public static final int OPERATOR_PAD_RIGHT_X_AXIS = 2;
                public static final int OPERATOR_PAD_RIGHT_Y_AXIS = 3;
        }

        public final Joystick OPERATOR_PAD = new Joystick(Joysticks.OPERATOR_GAMEPAD);
        public final Button OPERATOR_PAD_BUTTON_ONE = new JoystickButton(OPERATOR_PAD, 1);
        public final Button OPERATOR_PAD_BUTTON_TWO = new JoystickButton(OPERATOR_PAD, 2);
        public final Button OPERATOR_PAD_BUTTON_THREE = new JoystickButton(OPERATOR_PAD, 3);
        public final Button OPERATOR_PAD_BUTTON_FOUR = new JoystickButton(OPERATOR_PAD, 4);
        public final Button OPERATOR_PAD_BUTTON_FIVE = new JoystickButton(OPERATOR_PAD, 5);
        public final Button OPERATOR_PAD_BUTTON_SIX = new JoystickButton(OPERATOR_PAD, 6);
        public final Button OPERATOR_PAD_BUTTON_SEVEN = new JoystickButton(OPERATOR_PAD, 7);
        public final Button OPERATOR_PAD_BUTTON_EIGHT = new JoystickButton(OPERATOR_PAD, 8);
        public final Button OPERATOR_PAD_BUTTON_NINE = new JoystickButton(OPERATOR_PAD, 9);
        public final Button OPERATOR_PAD_BUTTON_TEN = new JoystickButton(OPERATOR_PAD, 10);
        public final Button OPERATOR_PAD_BUTTON_ELEVEN = new JoystickButton(OPERATOR_PAD, 11);
        public final Button OPERATOR_PAD_BUTTON_TWELVE = new JoystickButton(OPERATOR_PAD, 12);
        public final Button FORCE_FIRE_BUTTON = new AndJoystickButton(OPERATOR_PAD, 5, OPERATOR_PAD, 7);

        public final JoystickPOVButton OPERATOR_PAD_D_PAD_LEFT = new JoystickPOVButton(OPERATOR_PAD, 270);
        public final JoystickPOVButton OPERATOR_PAD_D_PAD_RIGHT = new JoystickPOVButton(OPERATOR_PAD, 90);
        public final JoystickPOVButton OPERATOR_PAD_D_PAD_UP = new JoystickPOVButton(OPERATOR_PAD, 0);
        public final JoystickPOVButton OPERATOR_PAD_D_PAD_DOWN = new JoystickPOVButton(OPERATOR_PAD, 180);

        // Operator Control Buttons
        public final JoystickAxisButton OPERATOR_PAD_LEFT_Y_AXIS_UP = new JoystickAxisButton(OPERATOR_PAD,
                        OPERATOR_PAD_AXIS.OPERATOR_PAD_LEFT_Y_AXIS, JoystickAxisButton.NEGATIVE_ONLY);
        public final JoystickAxisButton OPERATOR_PAD_LEFT_Y_AXIS_DOWN = new JoystickAxisButton(OPERATOR_PAD,
                        OPERATOR_PAD_AXIS.OPERATOR_PAD_LEFT_Y_AXIS, JoystickAxisButton.POSITIVE_ONLY);
        public final JoystickAxisButton OPERATOR_PAD_RIGHT_Y_AXIS_UP = new JoystickAxisButton(OPERATOR_PAD,
                        OPERATOR_PAD_AXIS.OPERATOR_PAD_RIGHT_Y_AXIS, JoystickAxisButton.NEGATIVE_ONLY);
        public final JoystickAxisButton OPERATOR_PAD_RIGHT_Y_AXIS_DOWN = new JoystickAxisButton(OPERATOR_PAD,
                        OPERATOR_PAD_AXIS.OPERATOR_PAD_RIGHT_Y_AXIS, JoystickAxisButton.POSITIVE_ONLY);

        public static final int OPERATOR_PAD_LEFT_X_AXIS = 0;
        public static final int OPERATOR_PAD_LEFT_Y_AXIS = 1;
        public static final int OPERATOR_PAD_RIGHT_X_AXIS = 2;
        public static final int OPERATOR_PAD_RIGHT_Y_AXIS = 3;

        private static final double Y_AXIS_DEAD_ZONE_PERCENT = 0.15;

        public double getLeftYAxis() {
                // SteeringX is the "X" axis of the right stick on the Driver Gamepad.
                double value = OPERATOR_PAD.getRawAxis(OPERATOR_PAD_LEFT_Y_AXIS);
                if (Math.abs(value) < Y_AXIS_DEAD_ZONE_PERCENT) {
                        value = 0.0;
                }

                // if the slow button is pressed, cut the steering value in half.
                // if (DRIVER_PAD_RIGHT_LOWER_TRIGGER_BUTTON.get()) {
                // value = value / 2.0;
                // }
                return value;
        }
}
