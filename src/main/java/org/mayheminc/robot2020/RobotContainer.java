/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.mayheminc.robot2020;

import org.mayheminc.util.*;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;

import java.util.LinkedList;

import org.mayheminc.robot2020.autonomousroutines.*;
import org.mayheminc.robot2020.commands.*;
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
        private final Autonomous m_autonomous = new Autonomous();

        private final MayhemDriverStick DRIVER_STICK = new MayhemDriverStick(Constants.Joysticks.DRIVER_JOYSTICK);
        private final MayhemDriverPad DRIVER_PAD = new MayhemDriverPad(Constants.Joysticks.DRIVER_GAMEPAD);

        private final MayhemOperatorPad OPERATOR_PAD = new MayhemOperatorPad(Constants.Joysticks.OPERATOR_GAMEPAD);
        private final MayhemOperatorStick OPERATOR_STICK = new MayhemOperatorStick(
                        Constants.Joysticks.OPERATOR_JOYSTICK);

        /**
         * The container for the robot. Contains subsystems, OI devices, and commands.
         */
        public RobotContainer() {
                // Configure the button bindings
                configureButtonBindings();
                configureAutonomousPrograms();
        }

        private void configureAutonomousPrograms() {
                LinkedList<Command> autonomousPrograms = new LinkedList<Command>();

                autonomousPrograms.push(new StayStill(this.m_drive));
                autonomousPrograms.push(new DriveStraight(this.m_drive));

                m_autonomous.setAutonomousPrograms(autonomousPrograms);

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
                DRIVER_STICK.DRIVER_STICK_BUTTON_THREE.whenPressed(new SelectAutonomousProgram(m_autonomous, 1));
                DRIVER_STICK.DRIVER_STICK_BUTTON_TWO.whenPressed(new SelectAutonomousProgram(m_autonomous, -1));
                DRIVER_STICK.DRIVER_STICK_BUTTON_FOUR.whenPressed(new SelectAutonomousDelay(m_autonomous, -1));
                DRIVER_STICK.DRIVER_STICK_BUTTON_FIVE.whenPressed(new SelectAutonomousDelay(m_autonomous, 1));

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
                OPERATOR_PAD.OPERATOR_PAD_BUTTON_ONE.whenPressed(new ShooterAdjustWheel(m_shooter, 100));
                OPERATOR_PAD.OPERATOR_PAD_BUTTON_TWO.whenPressed(new ShooterAdjustWheel(m_shooter, -100));
                OPERATOR_PAD.OPERATOR_PAD_BUTTON_THREE.whenPressed(new ShooterSetWheel(m_shooter, 1000));
        }

        /**
         * Use this to pass the autonomous command to the main {@link Robot} class.
         *
         * @return the command to run in autonomous
         */
        public Command getAutonomousCommand() {
                // An ExampleCommand will run in autonomous
                return m_autonomous.getCurrentCommand();
        }
}
