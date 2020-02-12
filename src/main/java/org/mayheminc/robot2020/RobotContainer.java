/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.mayheminc.robot2020;

import org.mayheminc.util.*;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.Button;

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
    // public static final Compressor compressor = new Compressor();
    public static PidTuner pidtuner;
    public static final Chimney chimney = new Chimney();

    // Operator Inputs
    public static final MayhemDriverStick DRIVER_STICK = new MayhemDriverStick();
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

        pidtuner = new PidTuner(RobotContainer.DRIVER_STICK.DRIVER_STICK_BUTTON_SIX,
                RobotContainer.DRIVER_STICK.DRIVER_STICK_BUTTON_SEVEN,
                RobotContainer.DRIVER_STICK.DRIVER_STICK_BUTTON_ELEVEN,
                RobotContainer.DRIVER_STICK.DRIVER_STICK_BUTTON_TEN, shooter);
    }

    public static void init() {
        shooter.init();
    }

    private void configureDefaultCommands() {
        drive.setDefaultCommand(new DriveDefault());
        // intake.setDefaultCommand(new IntakeExtenderVBus());
        magazine.setDefaultCommand(new MagazineDefault());
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

        DRIVER_STICK.DRIVER_STICK_BUTTON_ONE_DISABLED.whenPressed(new SystemZeroIncludingGyro());
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

        // Debug shooter vbus
        // DRIVER_PAD.DRIVER_PAD_BLUE_BUTTON.whenPressed(new
        // ShooterAdjustWheelVBus(0.1));
        // DRIVER_PAD.DRIVER_PAD_GREEN_BUTTON.whenPressed(new
        // ShooterAdjustWheelVBus(-0.1));
        // DRIVER_PAD.DRIVER_PAD_RED_BUTTON.whenPressed(new ShooterSetWheelVBus(0.0));

        // Debug shooter pid velocity
        DRIVER_PAD.DRIVER_PAD_BLUE_BUTTON.whenPressed(new ShooterAdjustWheel(100.0));
        DRIVER_PAD.DRIVER_PAD_GREEN_BUTTON.whenPressed(new ShooterAdjustWheel(-100.0));
        DRIVER_PAD.DRIVER_PAD_RED_BUTTON.whenPressed(new ShooterSetWheel(0.0));

        DRIVER_PAD.DRIVER_PAD_LEFT_UPPER_TRIGGER_BUTTON.whileHeld(new ShooterSetFeeder(0.5));

        // debug climber pistons
        // DRIVER_PAD.DRIVER_PAD_D_PAD_UP.whenPressed(new ClimberSetPistons(true));
        // DRIVER_PAD.DRIVER_PAD_D_PAD_UP.whenPressed(new ClimberSetPistons(false));

    }

    private void configureOperatorStickButtons() {
    }

    private void configureOperatorPadButtons() {
        // OPERATOR_PAD.OPERATOR_PAD_BUTTON_ONE.whenPressed(new IntakeZero());
        // new ShooterAdjustWheel(-100));
        OPERATOR_PAD.OPERATOR_PAD_BUTTON_TWO.whenPressed(new IntakeSetPosition(RobotContainer.intake.PIVOT_DOWN));
        // new ShooterSetWheel(0));
        // OPERATOR_PAD.OPERATOR_PAD_BUTTON_THREE.whenPressed(new
        // IntakeSetPosition(RobotContainer.intake.PIVOT_DOWN));
        // new ShooterAdjustWheel(+100));
        OPERATOR_PAD.OPERATOR_PAD_BUTTON_FOUR.whenPressed(new IntakeSetPosition(RobotContainer.intake.PIVOT_UP));
        // new ShooterSetWheel(1000));
        OPERATOR_PAD.OPERATOR_PAD_BUTTON_FIVE.whileHeld(new ChimneySetChimney(1.0));
        OPERATOR_PAD.OPERATOR_PAD_BUTTON_SIX.whileHeld(new IntakeSetRollers(-1.0));

        OPERATOR_PAD.OPERATOR_PAD_BUTTON_EIGHT.whileHeld(new IntakeSetRollers(1.0));
        OPERATOR_PAD.OPERATOR_PAD_D_PAD_UP.whenPressed(new IntakeSetPosition(RobotContainer.intake.PIVOT_UP));
        OPERATOR_PAD.OPERATOR_PAD_D_PAD_DOWN.whenPressed(new IntakeSetPosition(RobotContainer.intake.PIVOT_DOWN));
        OPERATOR_PAD.OPERATOR_PAD_D_PAD_LEFT.whenPressed(new ShooterSetHood(-0.2));
        OPERATOR_PAD.OPERATOR_PAD_D_PAD_RIGHT.whenPressed(new ShooterSetHood(-0.2));

        OPERATOR_PAD.OPERATOR_PAD_RIGHT_Y_AXIS_UP.whileHeld(new ClimberSetWinchesPower(0.7));
        OPERATOR_PAD.OPERATOR_PAD_RIGHT_Y_AXIS_DOWN.whileHeld(new ClimberSetWinchesPower(-0.7));
        OPERATOR_PAD.OPERATOR_PAD_LEFT_Y_AXIS_UP.whenPressed(new ClimberSetPistons(true));
        OPERATOR_PAD.OPERATOR_PAD_LEFT_Y_AXIS_DOWN.whenPressed(new ClimberSetPistons(false));
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
