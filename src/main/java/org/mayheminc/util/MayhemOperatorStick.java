package org.mayheminc.util;

import edu.wpi.first.wpilibj.Joystick;

public class MayhemOperatorStick {

    public Joystick OPERATOR_STICK;

    public MayhemOperatorStick(int port) {
        OPERATOR_STICK = new Joystick(port);
    }
}