/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.mayheminc.robot2020;

import org.mayheminc.util.*;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.button.Button;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

import java.util.LinkedList;

import org.mayheminc.robot2020.autonomousroutines.*;
import org.mayheminc.robot2020.commands.DriveStraightOnHeading;
import org.mayheminc.robot2020.subsystems.*;

/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a "declarative" paradigm, very little robot logic should
 * actually be handled in the {@link Robot} periodic methods (other than the
 * scheduler calls). Instead, the structure of the robot (including subsystems,
 * commands, and button mappings) should be declared here.
 */
public class RobotContainer {
        // The robot's subsystems and commands are defined here...
        // private final ExampleSubsystem m_exampleSubsystem = new ExampleSubsystem();

        // private final ExampleCommand m_autoCommand = new
        // ExampleCommand(m_exampleSubsystem);

        private final Climber m_Climber = new Climber();
        private final Magazine m_Magazine = new Magazine();
        private final Shooter m_shooter = new Shooter();
        private final Drive m_drive = new Drive();
        LinkedList<CommandBase> m_autonomousPrograms = new LinkedList<CommandBase>();
        private final Autonomous m_autonomous = new Autonomous();

        private final Joystick DRIVER_PAD = new Joystick(Constants.Joysticks.DRIVER_GAMEPAD);

        @SuppressWarnings("unused")
        private final Button DRIVER_PAD_BUTTON_FIVE = new JoystickButton(DRIVER_PAD, 5); // Left Top Trigger
        @SuppressWarnings("unused")
        private final Button DRIVER_PAD_BUTTON_SIX = new JoystickButton(DRIVER_PAD, 6); // Right Top Trigger
        @SuppressWarnings("unused")
        private final Button DRIVER_PAD_LEFT_STICK_BUTTON = new JoystickButton(DRIVER_PAD,
                        Constants.GAMEPAD_BUTTION.GAMEPAD_F310_LEFT_STICK_BUTTON); // Left Stick Trigger
        @SuppressWarnings("unused")
        private final Button DRIVER_PAD_RIGHT_STICK_BUTTON = new JoystickButton(DRIVER_PAD,
                        Constants.GAMEPAD_BUTTION.GAMEPAD_F310_RIGHT_STICK_BUTTON); // Right Stick Trigger

        @SuppressWarnings("unused")
        private final JoystickPOVButton DRIVER_PAD_D_PAD_UP = new JoystickPOVButton(DRIVER_PAD, 0);
        @SuppressWarnings("unused")
        private final JoystickPOVButton DRIVER_PAD_D_PAD_RIGHT = new JoystickPOVButton(DRIVER_PAD, 90);
        @SuppressWarnings("unused")
        private final JoystickPOVButton DRIVER_PAD_D_PAD_DOWN = new JoystickPOVButton(DRIVER_PAD, 180);
        @SuppressWarnings("unused")
        private final JoystickPOVButton DRIVER_PAD_D_PAD_LEFT = new JoystickPOVButton(DRIVER_PAD, 270);

        private final JoystickPOVButton OPERATOR_PAD_D_PAD_LEFT = new JoystickPOVButton(OPERATOR_PAD, 270);
        private final JoystickPOVButton OPERATOR_PAD_D_PAD_RIGHT = new JoystickPOVButton(OPERATOR_PAD, 90);
        private final JoystickPOVButton OPERATOR_PAD_D_PAD_UP = new JoystickPOVButton(OPERATOR_PAD, 0);
        private final JoystickPOVButton OPERATOR_PAD_D_PAD_DOWN = new JoystickPOVButton(OPERATOR_PAD, 180);

        private final Button CLIMB_L3_BUTTON_PAIR = new AndJoystickButton(DRIVER_PAD,
                        Constants.GAMEPAD_BUTTION.GAMEPAD_F310_Y_BUTTON, DRIVER_PAD,
                        Constants.GAMEPAD_BUTTION.GAMEPAD_F310_LEFT_BUTTON); // Y=Yellow,
                                                                             // plus
                                                                             // left
                                                                             // top
                                                                             // trigger
        private final Button CLIMB_L2_BUTTON_PAIR = new AndJoystickButton(DRIVER_PAD,
                        Constants.GAMEPAD_BUTTION.GAMEPAD_F310_A_BUTTON, DRIVER_PAD,
                        Constants.GAMEPAD_BUTTION.GAMEPAD_F310_LEFT_BUTTON); // A=Green,
                                                                             // plus
                                                                             // left
                                                                             // top
                                                                             // trigger

        // Driver Control Modes
        @SuppressWarnings("unused")
        private final Button TOGGLE_CLOSED_LOOP_MODE_BUTTON = new DisabledOnlyJoystickButton(DRIVER_PAD, 7);
        @SuppressWarnings("unused")
        private final Button TOGGLE_FOD_BUTTON = new DisabledOnlyJoystickButton(DRIVER_PAD, 8);

        // NOTE: DRIVER_PAD_RIGHT_UPPER_TRIGGER_BUTTON is "QUICKTURN" in Drive.java - DO
        // NOT USE HERE!!!
        private final Button DRIVER_PAD_LEFT_UPPER_TRIGGER_BUTTON = new EnabledOnlyJoystickButton(DRIVER_PAD,
                        Constants.GAMEPAD_BUTTION.GAMEPAD_F310_LEFT_BUTTON);
        private final JoystickAxisButton DRIVER_PAD_LEFT_LOWER_TRIGGER_BUTTON = new JoystickAxisButton(DRIVER_PAD,
                        Constants.GAMEPAD_AXIS.GAMEPAD_F310_LEFT_TRIGGER, JoystickAxisButton.POSITIVE_ONLY);
        private final JoystickAxisButton DRIVER_PAD_RIGHT_LOWER_TRIGGER_BUTTON = new JoystickAxisButton(DRIVER_PAD,
                        Constants.GAMEPAD_AXIS.GAMEPAD_F310_RIGHT_TRIGGER, JoystickAxisButton.POSITIVE_ONLY);

        @SuppressWarnings("unused")
        private final Button DRIVER_PAD_BACK_BUTTON = new JoystickButton(DRIVER_PAD,
                        Constants.GAMEPAD_BUTTION.GAMEPAD_F310_BACK_BUTTON);
        private final Button DRIVER_PAD_START_BUTTON = new JoystickButton(DRIVER_PAD,
                        Constants.GAMEPAD_BUTTION.GAMEPAD_F310_START_BUTTON);
        private final Button DRIVER_PAD_GREEN_BUTTON = new JoystickButton(DRIVER_PAD, 1); // Green "A" button
        private final Button DRIVER_PAD_RED_BUTTON = new JoystickButton(DRIVER_PAD, 2); // RED 'B" button
        private final Button DRIVER_PAD_BLUE_BUTTON = new JoystickButton(DRIVER_PAD, 3); // BLUE 'X' button
        private final Button DRIVER_PAD_YELLOW_BUTTON = new JoystickButton(DRIVER_PAD, 4); // YELLOW 'Y' button

        private final Joystick DRIVER_STICK = new Joystick(Constants.Joysticks.DRIVER_JOYSTICK);

        private final Button DRIVER_STICK_BUTTON_ONE_DISABLED = new DisabledOnlyJoystickButton(DRIVER_STICK, 1);
        private final Button DRIVER_STICK_BUTTON_ONE_ENABLED = new EnabledOnlyJoystickButton(DRIVER_STICK, 1);
        private final Button DRIVER_STICK_BUTTON_TWO = new DisabledOnlyJoystickButton(DRIVER_STICK, 2);
        private final Button DRIVER_STICK_BUTTON_THREE = new DisabledOnlyJoystickButton(DRIVER_STICK, 3);
        private final Button DRIVER_STICK_BUTTON_FOUR = new DisabledOnlyJoystickButton(DRIVER_STICK, 4);
        private final Button DRIVER_STICK_BUTTON_FIVE = new DisabledOnlyJoystickButton(DRIVER_STICK, 5);
        private final Button DRIVER_STICK_BUTTON_SIX = new DisabledOnlyJoystickButton(DRIVER_STICK, 6);
        private final Button DRIVER_STICK_BUTTON_SEVEN = new DisabledOnlyJoystickButton(DRIVER_STICK, 7);
        private final Button DRIVER_STICK_BUTTON_EIGHT = new DisabledOnlyJoystickButton(DRIVER_STICK, 8);
        private final Button DRIVER_STICK_BUTTON_NINE = new JoystickButton(DRIVER_STICK, 9);
        private final Button DRIVER_STICK_BUTTON_TEN = new DisabledOnlyJoystickButton(DRIVER_STICK, 10);
        private final Button DRIVER_STICK_BUTTON_ELEVEN = new DisabledOnlyJoystickButton(DRIVER_STICK, 11);
        public final int DRIVER_STICK_X_AXIS = 0;
        public final int DRIVER_STICK_Y_AXIS = 1;

        public static final Joystick OPERATOR_PAD = new Joystick(Constants.Joysticks.OPERATOR_GAMEPAD);
        private static final Button OPERATOR_PAD_BUTTON_ONE = new JoystickButton(OPERATOR_PAD, 1);
        private static final Button OPERATOR_PAD_BUTTON_TWO = new JoystickButton(OPERATOR_PAD, 2);
        private static final Button OPERATOR_PAD_BUTTON_THREE = new JoystickButton(OPERATOR_PAD, 3);
        private static final Button OPERATOR_PAD_BUTTON_FOUR = new JoystickButton(OPERATOR_PAD, 4);
        private static final Button OPERATOR_PAD_BUTTON_FIVE = new JoystickButton(OPERATOR_PAD, 5);
        private static final Button OPERATOR_PAD_BUTTON_SIX = new JoystickButton(OPERATOR_PAD, 6);
        private static final Button OPERATOR_PAD_BUTTON_SEVEN = new JoystickButton(OPERATOR_PAD, 7);
        private static final Button OPERATOR_PAD_BUTTON_EIGHT = new JoystickButton(OPERATOR_PAD, 8);
        private static final Button OPERATOR_PAD_BUTTON_NINE = new JoystickButton(OPERATOR_PAD, 9);
        private static final Button OPERATOR_PAD_BUTTON_TEN = new JoystickButton(OPERATOR_PAD, 10);
        @SuppressWarnings("unused")
        private static final Button OPERATOR_PAD_BUTTON_ELEVEN = new JoystickButton(OPERATOR_PAD, 11);
        private static final Button OPERATOR_PAD_BUTTON_TWELVE = new JoystickButton(OPERATOR_PAD, 12);
        @SuppressWarnings("unused")
        private static final Button FORCE_FIRE_BUTTON = new AndJoystickButton(OPERATOR_PAD, 5, OPERATOR_PAD, 7);

        // Operator Control Buttons
        @SuppressWarnings("unused")
        private static final JoystickAxisButton OPERATOR_PAD_LEFT_Y_AXIS_UP = new JoystickAxisButton(OPERATOR_PAD,
                        Constants.OPERATOR_PAD_AXIS.OPERATOR_PAD_LEFT_Y_AXIS, JoystickAxisButton.NEGATIVE_ONLY);
        @SuppressWarnings("unused")
        private static final JoystickAxisButton OPERATOR_PAD_LEFT_Y_AXIS_DOWN = new JoystickAxisButton(OPERATOR_PAD,
                        Constants.OPERATOR_PAD_AXIS.OPERATOR_PAD_LEFT_Y_AXIS, JoystickAxisButton.POSITIVE_ONLY);
        @SuppressWarnings("unused")
        private static final JoystickAxisButton OPERATOR_PAD_RIGHT_Y_AXIS_UP = new JoystickAxisButton(OPERATOR_PAD,
                        Constants.OPERATOR_PAD_AXIS.OPERATOR_PAD_RIGHT_Y_AXIS, JoystickAxisButton.NEGATIVE_ONLY);
        @SuppressWarnings("unused")
        private static final JoystickAxisButton OPERATOR_PAD_RIGHT_Y_AXIS_DOWN = new JoystickAxisButton(OPERATOR_PAD,
                        Constants.OPERATOR_PAD_AXIS.OPERATOR_PAD_RIGHT_Y_AXIS, JoystickAxisButton.POSITIVE_ONLY);

        /**
         * The container for the robot. Contains subsystems, OI devices, and commands.
         */
        public RobotContainer() {
                // Configure the button bindings
                configureButtonBindings();

                m_autonomousPrograms.push(new StayStill(this.m_drive));
                m_autonomousPrograms.push(new DriveStraight(this.m_drive));

                m_autonomous.setAutonomousPrograms(this.m_autonomousPrograms);
        }

        /**
         * Use this method to define your button->command mappings. Buttons can be
         * created by instantiating a {@link GenericHID} or one of its subclasses
         * ({@link edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then
         * passing it to a {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
         */
        private void configureButtonBindings() {
                confiugreDriverStickButtons();
                confiugreDriverPadButtons();
                confiugreOperatorStickButtons();
                confiugreOperatorPadButtons();

        }

        private void confiugreDriverStickButtons() {

                // DRIVER_STICK_BUTTON_ONE_DISABLED.whenPressed(new SystemZeroIncludingGyro());
                // DRIVER_STICK_BUTTON_ONE_ENABLED.whenPressed(new SystemZeroWithoutGyro());

                // // adjust auto parameters
                // DRIVER_STICK_BUTTON_THREE.whenPressed(new SelectAutonomousProgram(1));
                // DRIVER_STICK_BUTTON_TWO.whenPressed(new SelectAutonomousProgram(-1));
                // DRIVER_STICK_BUTTON_FOUR.whenPressed(new SelectAutonomousDelay(-1));
                // DRIVER_STICK_BUTTON_FIVE.whenPressed(new SelectAutonomousDelay(1));

                // // NOTE: buttons SIX, SEVEN, TEN, ELEVEN are reserved for PidTuner
                // DRIVER_STICK_BUTTON_SEVEN.whenPressed(new TestSound());

                // // zero elements that require zeroing
                // DRIVER_STICK_BUTTON_EIGHT.whenPressed(new DriveZeroGyro());
                // DRIVER_STICK_BUTTON_NINE.whenPressed(new Wait(0));

        }

        private void confiugreDriverPadButtons() {
        }

        private void confiugreOperatorStickButtons() {
        }

        private void confiugreOperatorPadButtons() {
        }

        /**
         * Use this to pass the autonomous command to the main {@link Robot} class.
         *
         * @return the command to run in autonomous
         */
        public Command getAutonomousCommand() {
                // An ExampleCommand will run in autonomous
                return null;
        }
}
