package org.mayheminc.util;

import edu.wpi.first.wpilibj2.command.button.Button;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;

public class MayhemDriverStick {

    public Joystick Joystick;

    public Button DRIVER_STICK_BUTTON_ONE_DISABLED;
    public Button DRIVER_STICK_BUTTON_ONE_ENABLED;
    public Button DRIVER_STICK_BUTTON_TWO;
    public Button DRIVER_STICK_BUTTON_THREE;
    public Button DRIVER_STICK_BUTTON_FOUR;
    public Button DRIVER_STICK_BUTTON_FIVE;
    public Button DRIVER_STICK_BUTTON_SIX;
    public Button DRIVER_STICK_BUTTON_SEVEN;
    public Button DRIVER_STICK_BUTTON_EIGHT;
    public Button DRIVER_STICK_BUTTON_NINE;
    public Button DRIVER_STICK_BUTTON_TEN;
    public Button DRIVER_STICK_BUTTON_ELEVEN;

    public int DRIVER_STICK_X_AXIS = 0;
    public int DRIVER_STICK_Y_AXIS = 1;

    public MayhemDriverStick(int port) {
        this.Joystick = new Joystick(port);

        DRIVER_STICK_BUTTON_ONE_DISABLED = new DisabledOnlyJoystickButton(Joystick, 1);
        DRIVER_STICK_BUTTON_ONE_ENABLED = new EnabledOnlyJoystickButton(Joystick, 1);
        DRIVER_STICK_BUTTON_TWO = new DisabledOnlyJoystickButton(Joystick, 2);
        DRIVER_STICK_BUTTON_THREE = new DisabledOnlyJoystickButton(Joystick, 3);
        DRIVER_STICK_BUTTON_FOUR = new DisabledOnlyJoystickButton(Joystick, 4);
        DRIVER_STICK_BUTTON_FIVE = new DisabledOnlyJoystickButton(Joystick, 5);
        DRIVER_STICK_BUTTON_SIX = new DisabledOnlyJoystickButton(Joystick, 6);
        DRIVER_STICK_BUTTON_SEVEN = new DisabledOnlyJoystickButton(Joystick, 7);
        DRIVER_STICK_BUTTON_EIGHT = new DisabledOnlyJoystickButton(Joystick, 8);
        DRIVER_STICK_BUTTON_NINE = new JoystickButton(Joystick, 9);
        DRIVER_STICK_BUTTON_TEN = new DisabledOnlyJoystickButton(Joystick, 10);
        DRIVER_STICK_BUTTON_ELEVEN = new DisabledOnlyJoystickButton(Joystick, 11);
    }
}