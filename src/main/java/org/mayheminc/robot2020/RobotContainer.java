/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.mayheminc.robot2020;

import org.mayheminc.util.*;

// import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.Command;
// import edu.wpi.first.wpilibj2.command.button.Button;

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
    public static final ShooterWheel shooterWheel = new ShooterWheel();
    public static final Hood hood = new Hood();
    public static final Turret turret = new Turret();
    public static final Feeder feeder = new Feeder();
    public static final Drive drive = new Drive();
    public static final Intake intake = new Intake();
    public static final Autonomous autonomous = new Autonomous();
    public static final Targeting targeting = new Targeting();
    public static final AirCompressor compressor = new AirCompressor();
    public static PidTuner pidtuner;
    public static final Chimney chimney = new Chimney();

    public static final Solenoid cameraLights = new Solenoid(Constants.Solenoid.CAMERA_LIGHTS);

    // Operator Inputs
    public static final MayhemDriverStick DRIVER_STICK = new MayhemDriverStick();
    public static final MayhemDriverPad DRIVER_PAD = new MayhemDriverPad();
    public static final MayhemOperatorPad OPERATOR_PAD = new MayhemOperatorPad();
    // private final MayhemOperatorStick OPERATOR_STICK = new MayhemOperatorStick();

    /**
     * The container for the robot. Contains subsystems, OI devices, and commands.
     */
    public RobotContainer() {
        // Configure the button bindings
        configureButtonBindings();
        configureAutonomousPrograms();
        configureDefaultCommands();

        pidtuner = new PidTuner(RobotContainer.DRIVER_STICK.DRIVER_STICK_ENA_BUTTON_SIX,
                RobotContainer.DRIVER_STICK.DRIVER_STICK_ENA_BUTTON_SEVEN,
                RobotContainer.DRIVER_STICK.DRIVER_STICK_ENA_BUTTON_ELEVEN,
                RobotContainer.DRIVER_STICK.DRIVER_STICK_ENA_BUTTON_TEN, drive);

        cameraLights.set(true);
    }

    public static void init() {
        shooterWheel.init();
    }

    private void configureDefaultCommands() {
        drive.setDefaultCommand(new DriveDefault());
        // intake.setDefaultCommand(new IntakeExtenderVBus());
        magazine.setDefaultCommand(new MagazineDefault());

        // TODO:  Figure out if the current approach of "AirCompressorDefault()" is the way to go for compressor control.
        // KBS doesn't think the below is the right way to have the compressor be on "by default" because
        // it would require there to always be a command running to keep the compressor off.  However, that
        // is a good way to ensure it doesn't get left off by accident.  Not quite sure how to handle this;
        // would really rather that other commands which need the compressor off (such as a high-power command
        // which wants all the battery power available) would turn the compressor off when the command starts
        // and off when the command ends.)  Then again, maybe the "defaultCommand" is a good way to do this
        // and I just don't understand the style yet.
        // compressor.setDefaultCommand(new AirCompressorDefault());
    }

    private void configureAutonomousPrograms() {
        LinkedList<Command> autonomousPrograms = new LinkedList<Command>();
        // TODO:  fix "wierdness" with auto program selection - sometimes doesn't seem to work
        // TODO:  fix so that auto program is shown not just when changed (as shows old setting sometimes)
        
        // autonomousPrograms.push(/* 01 */ new StayStill());
        autonomousPrograms.push(/* 01 */ new DriveStraightOnly());
        autonomousPrograms.push(/* 00 */ new TrenchAuto());

        // autonomousPrograms.push( new ShooterReadyAimFire());
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
        // DRIVER_STICK.DRIVER_STICK_BUTTON_ONE_ENABLED.whenPressed(new
        // SystemZeroIncludingGyro());
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

        // Debug Turret
        // DRIVER_PAD.DRIVER_PAD_D_PAD_LEFT.whenPressed(new
        // ShooterSetTurretAbs(-5500));// about -30 degrees
        // DRIVER_PAD.DRIVER_PAD_D_PAD_RIGHT.whenPressed(new
        // ShooterSetTurretAbs(+5500));// about +30 degrees
        // DRIVER_PAD.DRIVER_PAD_D_PAD_LEFT.whileHeld(new ShooterSetTurretVBus(-0.2));//
        // about -30 degrees
        // DRIVER_PAD.DRIVER_PAD_D_PAD_RIGHT.whileHeld(new
        // ShooterSetTurretVBus(+0.2));// about +30 degrees
        DRIVER_PAD.DRIVER_PAD_D_PAD_UP.whenPressed(new HoodSetAbs(Hood.HOOD_INITIATION_LINE_POSITION));
        DRIVER_PAD.DRIVER_PAD_D_PAD_DOWN.whenPressed(new HoodSetAbs(Hood.HOOD_TARGET_ZONE_POSITION));

        // DRIVER_PAD.DRIVER_PAD_D_PAD_LEFT.whenPressed(new
        // ShooterSetTurretRel(-200.0));
        // DRIVER_PAD.DRIVER_PAD_D_PAD_RIGHT.whenPressed(new
        // ShooterSetTurretRel(+200.0));
        // DRIVER_PAD.DRIVER_PAD_D_PAD_UP.whenPressed(new ShooterSetTurretAbs(+0.0));
        // DRIVER_PAD.DRIVER_PAD_D_PAD_DOWN.whenPressed(new
        // ShooterSetHoodAbs(Shooter.HOOD_TARGET_ZONE_POSITION));

        // Debug Hood
        // DRIVER_PAD.DRIVER_PAD_D_PAD_UP.whenPressed(new ShooterSetHoodRel(+1000));
        // DRIVER_PAD.DRIVER_PAD_D_PAD_DOWN.whenPressed(new ShooterSetHoodRel(-1000));
        // DRIVER_PAD.DRIVER_PAD_D_PAD_UP.whileHeld(new ShooterSetHoodVBus(+1.0));
        // DRIVER_PAD.DRIVER_PAD_D_PAD_DOWN.whileHeld(new ShooterSetHoodVBus(-1.0));

        // Debug shooter pid velocity
        DRIVER_PAD.DRIVER_PAD_BLUE_BUTTON.whenPressed(new ShooterWheelAdjust(100.0));
        DRIVER_PAD.DRIVER_PAD_GREEN_BUTTON.whenPressed(new ShooterWheelAdjust(-100.0));
        DRIVER_PAD.DRIVER_PAD_RED_BUTTON.whenPressed(new ShooterWheelSetVBus(0.0));
        DRIVER_PAD.DRIVER_PAD_YELLOW_BUTTON.whenPressed(new ShooterWheelSet(3000));

        DRIVER_PAD.DRIVER_PAD_BACK_BUTTON.whileHeld(new DriveStraight(0.1));
        // TODO:  above hard-coded constant (3000) should be a named constant from Shooter.java

        DRIVER_PAD.DRIVER_PAD_LEFT_UPPER_TRIGGER_BUTTON.whileHeld(new FeederSet(1.0));

    }

    private void configureOperatorStickButtons() {
    }

    private void configureOperatorPadButtons() {
        OPERATOR_PAD.OPERATOR_PAD_BUTTON_ONE.whileHeld(new MagazineSetTurntable(0.2));
        OPERATOR_PAD.OPERATOR_PAD_BUTTON_TWO.whenPressed(new IntakeSetPosition(RobotContainer.intake.PIVOT_DOWN));
        OPERATOR_PAD.OPERATOR_PAD_BUTTON_THREE.whileHeld(new MagazineSetTurntable(0.5));
        OPERATOR_PAD.OPERATOR_PAD_BUTTON_FOUR.whenPressed(new IntakeSetPosition(RobotContainer.intake.PIVOT_UP));

        // new ShooterSetWheel(1000));
        OPERATOR_PAD.OPERATOR_PAD_BUTTON_FIVE.whileHeld(new ChimneySetChimney(1.0));
        OPERATOR_PAD.OPERATOR_PAD_BUTTON_SIX.whileHeld(new IntakeSetRollers(-1.0));

        OPERATOR_PAD.OPERATOR_PAD_BUTTON_SEVEN.whileHeld(new TurretAimToTarget());
        OPERATOR_PAD.OPERATOR_PAD_BUTTON_EIGHT.whileHeld(new IntakeSetRollers(1.0));
        
        OPERATOR_PAD.OPERATOR_PAD_BUTTON_NINE.whenPressed(new ClimberSetPistons(true));
        OPERATOR_PAD.OPERATOR_PAD_BUTTON_TEN.whenPressed(new ClimberSetPistons(false));


        // OPERATOR_PAD.OPERATOR_PAD_D_PAD_UP.whenPressed(new
        // IntakeSetPosition(RobotContainer.intake.PIVOT_UP));
        // OPERATOR_PAD.OPERATOR_PAD_D_PAD_DOWN.whenPressed(new
        // IntakeSetPosition(RobotContainer.intake.PIVOT_DOWN));
        OPERATOR_PAD.OPERATOR_PAD_D_PAD_LEFT.whileHeld(new TurretSetVBus(-0.2));
        OPERATOR_PAD.OPERATOR_PAD_D_PAD_RIGHT.whileHeld(new TurretSetVBus(+0.2));
        OPERATOR_PAD.OPERATOR_PAD_D_PAD_UP.whileHeld(new HoodAdjust(+1000.0));
        OPERATOR_PAD.OPERATOR_PAD_D_PAD_DOWN.whileHeld(new HoodAdjust(-1000.0));

        OPERATOR_PAD.OPERATOR_PAD_RIGHT_Y_AXIS_UP.whileHeld(new ClimberSetWinchesPower(1.0));
        OPERATOR_PAD.OPERATOR_PAD_RIGHT_Y_AXIS_DOWN.whileHeld(new ClimberSetWinchesPower(-1.0));

        // OPERATOR_PAD.OPERATOR_PAD_LEFT_Y_AXIS_UP.whenPressed(new
        // MagazineSetTurntable());
        OPERATOR_PAD.OPERATOR_PAD_LEFT_Y_AXIS_DOWN.whileHeld(new ChimneySetChimney(-1.0));
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
