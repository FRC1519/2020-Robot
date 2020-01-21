package org.mayheminc.util;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.button.Button;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

public class MayhemOperatorPad {

    public Joystick OPERATOR_PAD;
    public Button OPERATOR_PAD_BUTTON_ONE;
    public Button OPERATOR_PAD_BUTTON_TWO;
    public Button OPERATOR_PAD_BUTTON_THREE;
    public Button OPERATOR_PAD_BUTTON_FOUR;
    public Button OPERATOR_PAD_BUTTON_FIVE;
    public Button OPERATOR_PAD_BUTTON_SIX;
    public Button OPERATOR_PAD_BUTTON_SEVEN;
    public Button OPERATOR_PAD_BUTTON_EIGHT;
    public Button OPERATOR_PAD_BUTTON_NINE;
    public Button OPERATOR_PAD_BUTTON_TEN;
    public Button OPERATOR_PAD_BUTTON_ELEVEN;
    public Button OPERATOR_PAD_BUTTON_TWELVE;
    public Button FORCE_FIRE_BUTTON;

    public JoystickPOVButton OPERATOR_PAD_D_PAD_LEFT;
    public JoystickPOVButton OPERATOR_PAD_D_PAD_RIGHT;
    public JoystickPOVButton OPERATOR_PAD_D_PAD_UP;
    public JoystickPOVButton OPERATOR_PAD_D_PAD_DOWN;

    // Operator Control Buttons
    public JoystickAxisButton OPERATOR_PAD_LEFT_Y_AXIS_UP;
    public JoystickAxisButton OPERATOR_PAD_LEFT_Y_AXIS_DOWN;
    public JoystickAxisButton OPERATOR_PAD_RIGHT_Y_AXIS_UP;
    public JoystickAxisButton OPERATOR_PAD_RIGHT_Y_AXIS_DOWN;

    public final class OPERATOR_PAD_AXIS {
        public static final int OPERATOR_PAD_LEFT_X_AXIS = 0;
        public static final int OPERATOR_PAD_LEFT_Y_AXIS = 1;
        public static final int OPERATOR_PAD_RIGHT_X_AXIS = 2;
        public static final int OPERATOR_PAD_RIGHT_Y_AXIS = 3;
    }

    public MayhemOperatorPad(int port) {

        OPERATOR_PAD = new Joystick(port);
        OPERATOR_PAD_BUTTON_ONE = new JoystickButton(OPERATOR_PAD, 1);
        OPERATOR_PAD_BUTTON_TWO = new JoystickButton(OPERATOR_PAD, 2);
        OPERATOR_PAD_BUTTON_THREE = new JoystickButton(OPERATOR_PAD, 3);
        OPERATOR_PAD_BUTTON_FOUR = new JoystickButton(OPERATOR_PAD, 4);
        OPERATOR_PAD_BUTTON_FIVE = new JoystickButton(OPERATOR_PAD, 5);
        OPERATOR_PAD_BUTTON_SIX = new JoystickButton(OPERATOR_PAD, 6);
        OPERATOR_PAD_BUTTON_SEVEN = new JoystickButton(OPERATOR_PAD, 7);
        OPERATOR_PAD_BUTTON_EIGHT = new JoystickButton(OPERATOR_PAD, 8);
        OPERATOR_PAD_BUTTON_NINE = new JoystickButton(OPERATOR_PAD, 9);
        OPERATOR_PAD_BUTTON_TEN = new JoystickButton(OPERATOR_PAD, 10);
        OPERATOR_PAD_BUTTON_ELEVEN = new JoystickButton(OPERATOR_PAD, 11);
        OPERATOR_PAD_BUTTON_TWELVE = new JoystickButton(OPERATOR_PAD, 12);
        FORCE_FIRE_BUTTON = new AndJoystickButton(OPERATOR_PAD, 5, OPERATOR_PAD, 7);

        OPERATOR_PAD_D_PAD_LEFT = new JoystickPOVButton(OPERATOR_PAD, 270);
        OPERATOR_PAD_D_PAD_RIGHT = new JoystickPOVButton(OPERATOR_PAD, 90);
        OPERATOR_PAD_D_PAD_UP = new JoystickPOVButton(OPERATOR_PAD, 0);
        OPERATOR_PAD_D_PAD_DOWN = new JoystickPOVButton(OPERATOR_PAD, 180);

        // Operator Control Buttons
        OPERATOR_PAD_LEFT_Y_AXIS_UP = new JoystickAxisButton(OPERATOR_PAD, OPERATOR_PAD_AXIS.OPERATOR_PAD_LEFT_Y_AXIS,
                JoystickAxisButton.NEGATIVE_ONLY);
        OPERATOR_PAD_LEFT_Y_AXIS_DOWN = new JoystickAxisButton(OPERATOR_PAD, OPERATOR_PAD_AXIS.OPERATOR_PAD_LEFT_Y_AXIS,
                JoystickAxisButton.POSITIVE_ONLY);
        OPERATOR_PAD_RIGHT_Y_AXIS_UP = new JoystickAxisButton(OPERATOR_PAD, OPERATOR_PAD_AXIS.OPERATOR_PAD_RIGHT_Y_AXIS,
                JoystickAxisButton.NEGATIVE_ONLY);
        OPERATOR_PAD_RIGHT_Y_AXIS_DOWN = new JoystickAxisButton(OPERATOR_PAD,
                OPERATOR_PAD_AXIS.OPERATOR_PAD_RIGHT_Y_AXIS, JoystickAxisButton.POSITIVE_ONLY);

    }
}
