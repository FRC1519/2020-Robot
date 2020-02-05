/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.mayheminc.robot2020;

import org.mayheminc.util.*;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj2.command.Command;

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

        public static final Climber climber = new Climber();
        public static final Magazine magazine = new Magazine();
        public static final Shooter shooter = new Shooter();
        public static final Drive drive = new Drive();
        public static final Intake intake = new Intake();
        public static final Autonomous autonomous = new Autonomous();
        public static final Targeting targeting = new Targeting();

        private static final MayhemDriverStick DRIVER_STICK = new MayhemDriverStick();
        public static final MayhemDriverPad DRIVER_PAD = new MayhemDriverPad();

        public static final MayhemOperatorPad OPERATOR_PAD = new MayhemOperatorPad();
        private final MayhemOperatorStick OPERATOR_STICK = new MayhemOperatorStick();

        /**
         * The container for the robot. Contains subsystems, OI devices, and commands.
         */
        public RobotContainer() {
                // Configure the button bindings
                configureButtonBindings();
                configureAutonomousPrograms();
                configureDefaultCommands();
        }

        private void configureDefaultCommands() {
                drive.setDefaultCommand(new DriveDefault());
                intake.setDefaultCommand(new IntakeExtenderVBus());
        }

        private void configureAutonomousPrograms() {
                LinkedList<Command> autonomousPrograms = new LinkedList<Command>();

                // autonomousPrograms.push(new StayStill());
                autonomousPrograms.push(new DriveStraight());
                // autonomousPrograms.push(new TestTurret());

                autonomous.setAutonomousPrograms(autonomousPrograms);

        }

        /**
         * Use this method to define your button->command mappings. Buttons can be
         * created by instantiating a {@link GenericHID} or one of its subclasses
         * ({@link edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then
         * passing it to a {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
         */
        private void configureButtonBindings() {
                configureDriverStickButtons();
                configureDriverPadButtons();
                configureOperatorStickButtons();
                configureOperatorPadButtons();

        }

        private void configureDriverStickButtons() {

                // DRIVER_STICK_BUTTON_ONE_DISABLED.whenPressed(new SystemZeroIncludingGyro());
                // DRIVER_STICK_BUTTON_ONE_ENABLED.whenPressed(new SystemZeroWithoutGyro());

                // // adjust auto parameters
                DRIVER_STICK.DRIVER_STICK_BUTTON_THREE.whenPressed(new SelectAutonomousProgram(autonomous, 1));
                DRIVER_STICK.DRIVER_STICK_BUTTON_TWO.whenPressed(new SelectAutonomousProgram(autonomous, -1));
                DRIVER_STICK.DRIVER_STICK_BUTTON_FOUR.whenPressed(new SelectAutonomousDelay(autonomous, -1));
                DRIVER_STICK.DRIVER_STICK_BUTTON_FIVE.whenPressed(new SelectAutonomousDelay(autonomous, 1));

                // // NOTE: buttons SIX, SEVEN, TEN, ELEVEN are reserved for PidTuner
                // DRIVER_STICK_BUTTON_SEVEN.whenPressed(new TestSound());

                // // zero elements that require zeroing
                // DRIVER_STICK_BUTTON_EIGHT.whenPressed(new DriveZeroGyro());
                // DRIVER_STICK_BUTTON_NINE.whenPressed(new Wait(0));

        }

        private void configureDriverPadButtons() {
        }

        private void configureOperatorStickButtons() {
        }

        private void configureOperatorPadButtons() {
                OPERATOR_PAD.OPERATOR_PAD_BUTTON_ONE.whenPressed(new ShooterAdjustWheel(-100));
                OPERATOR_PAD.OPERATOR_PAD_BUTTON_TWO.whenPressed(new ShooterSetWheel(0));
                OPERATOR_PAD.OPERATOR_PAD_BUTTON_THREE.whenPressed(new ShooterAdjustWheel(+100));
                OPERATOR_PAD.OPERATOR_PAD_BUTTON_FOUR.whenPressed(new ShooterSetWheel(1000));
                OPERATOR_PAD.OPERATOR_PAD_BUTTON_FIVE.whileHeld(new MagazineSetTurntable(0.3));
                OPERATOR_PAD.OPERATOR_PAD_BUTTON_SIX.whileHeld(new IntakeSetRollers(-1.0));

                OPERATOR_PAD.OPERATOR_PAD_BUTTON_EIGHT.whileHeld(new IntakeSetRollers(1.0));
                OPERATOR_PAD.OPERATOR_PAD_D_PAD_UP.whenPressed(new IntakeSetPosition(RobotContainer.intake.PIVOT_UP));
                OPERATOR_PAD.OPERATOR_PAD_D_PAD_DOWN
                                .whenPressed(new IntakeSetPosition(RobotContainer.intake.PIVOT_DOWN));
                OPERATOR_PAD.OPERATOR_PAD_D_PAD_LEFT.whenPressed(new ShooterSetHood(-0.2));
                OPERATOR_PAD.OPERATOR_PAD_D_PAD_RIGHT.whenPressed(new ShooterSetHood(-0.2));

                // OPERATOR_PAD.OPERATOR_PAD_RIGHT_Y_AXIS_UP.whenPressed(new
                // IntakeSetPosition(0.0));
                // OPERATOR_PAD.OPERATOR_PAD_RIGHT_Y_AXIS_DOWN.whenPressed(new
                // IntakeSetPosition(90.0));
        }

        /**
         * Use this to pass the autonomous command to the main {@link Robot} class.
         *
         * @return the command to run in autonomous
         */
        public Command getAutonomousCommand() {
                return new RunAutonomous();
        }
}
