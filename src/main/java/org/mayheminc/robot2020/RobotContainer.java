/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.mayheminc.robot2020;

import org.mayheminc.util.*;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.PrintCommand;
import edu.wpi.first.wpilibj2.command.SelectCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Supplier;

import org.mayheminc.robot2020.autonomousroutines.*;
import org.mayheminc.robot2020.commands.*;
import org.mayheminc.robot2020.commands.DriveStraightOnHeading.DistanceUnits;
import org.mayheminc.robot2020.subsystems.*;

/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a "declarative" paradigm, very little robot logic should
 * actually be handled in the {@link Robot} periodic methods (other than the
 * scheduler calls). Instead, the structure of the robot (including subsystems,
 * commands, and button mappings) should be declared here.
 */
public class RobotContainer {
    public enum GalacticSearchPath {
        PATH_A_RED, PATH_A_BLUE, PATH_B_RED, PATH_B_BLUE, UNKNOWN
    }

    // The robot's subsystems and commands are defined here...

    public static final Climber climber = new Climber();
    public static final Revolver revolver = new Revolver();
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

        // pidtuner = new
        // PidTuner(RobotContainer.DRIVER_STICK.DRIVER_STICK_ENA_BUTTON_SIX,
        // RobotContainer.DRIVER_STICK.DRIVER_STICK_ENA_BUTTON_SEVEN,
        // RobotContainer.DRIVER_STICK.DRIVER_STICK_ENA_BUTTON_ELEVEN,
        // RobotContainer.DRIVER_STICK.DRIVER_STICK_ENA_BUTTON_TEN, intake);

        cameraLights.set(true);
    }

    public static void init() {
        shooterWheel.init();
    }

    public static void safetyInit() {
        hood.setVBus(0.0);
        turret.setVBus(0.0);
        climber.setPistons(false);
    }

    private void configureDefaultCommands() {
        drive.setDefaultCommand(new DriveDefault());
        // intake.setDefaultCommand(new IntakeExtenderVBus());
        revolver.setDefaultCommand(new RevolverDefault());

        // KBS doesn't think the below is the right way to have the compressor be on "by
        // default" because it would require there to always be a command running to
        // keep the compressor off.
        // However, that is a good way to ensure it doesn't get left off by accident.
        // Not quite sure how to handle this;
        // would really rather that other commands which need the compressor off (such
        // as a high-power command which wants all the battery power available) would
        // turn the compressor off when the command starts and off when the command
        // ends.) Then again, maybe the "defaultCommand" is a good way to do this
        // and I just don't understand the style yet.
        // compressor.setDefaultCommand(new AirCompressorDefault());
    }

    private void configureAutonomousPrograms() {
        LinkedList<Command> autonomousPrograms = new LinkedList<Command>();

        // SelectCommand driveGalacticSearch = new SelectCommand(
        // Map.ofEntries(Map.entry(GalacticSearchPath.PATH_A_RED, new PathARed()),
        // Map.entry(GalacticSearchPath.PATH_A_BLUE, new PathABlue()),
        // Map.entry(GalacticSearchPath.PATH_B_RED, new PathBRed()),
        // Map.entry(GalacticSearchPath.PATH_B_BLUE, new PathBBlue()),
        // Map.entry(GalacticSearchPath.UNKNOWN, new PrintCommand("-------- no path
        // found"))),
        // () -> {
        // String path = SmartDashboard.getString("GalacticSearchPath", "unknown");
        // GalacticSearchPath pathValue = GalacticSearchPath.UNKNOWN;

        // switch (path) {
        // case "path a red":
        // pathValue = GalacticSearchPath.PATH_A_RED;
        // break;
        // case "path b red":
        // pathValue = GalacticSearchPath.PATH_B_RED;
        // break;
        // case "path a blue":
        // pathValue = GalacticSearchPath.PATH_A_BLUE;
        // break;
        // case "path b blue":
        // pathValue = GalacticSearchPath.PATH_B_BLUE;
        // break;
        // }

        // return pathValue;
        // });

        // autonomousPrograms.push(/* 22 */ new DriveTest());
        // autonomousPrograms.push(/* 21 */ driveGalacticSearch);
        // autonomousPrograms.push(/* 20 */ driveGalacticSearch);
        // autonomousPrograms.push(/* 19 */ new PathBRed());
        // autonomousPrograms.push(/* 18 */ new PathARed());
        // autonomousPrograms.push(/* 17 */ new PathBBlue());
        // autonomousPrograms.push(/* 16 */ new PathABlue());
        // autonomousPrograms.push(/* 15 */ new DriveBouncePath());
        // autonomousPrograms.push(/* 14 */ new DriveBarrelRacing());
        // autonomousPrograms.push(/* 13 */ new DriveSlalom());
        autonomousPrograms.push(new RiverRageForwardShoot3());
        // autonomousPrograms.push(/* 12 */ new StayStill());
        // autonomousPrograms.push(/* 11 */ new StartBWDriveOnlyToRP());
        // autonomousPrograms.push(/* 10 */ new StartBWDriveOnlyToWall());
        // autonomousPrograms.push(/* 09 */ new StartFWDriveOnlyToRP());
        // autonomousPrograms.push(/* 08 */ new StartFWDriveOnlyToWall());
        // autonomousPrograms.push(/* 07 */ new StartBWShoot3ThenToRP());
        // autonomousPrograms.push(/* 06 */ new StartBWShoot3ThenToWall());
        // autonomousPrograms.push(/* 05 */ new StartFWShoot3ThenToRP());
        // autonomousPrograms.push(/* 04 */ new StartFWShoot3ThenToWall());
        // autonomousPrograms.push(/* 03 */ new StartFWRendezvous());
        // autonomousPrograms.push(/* 02 */ new StartBWOppTrench());
        // autonomousPrograms.push(/* 01 */ new StartBWTrench3());
        // autonomousPrograms.push(/* 00 */ new StartBWTrench5());

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
        // DRIVER_PAD.DRIVER_PAD_D_PAD_LEFT.whenPressed(new TurretSetAbs(-5500));//
        // about -30 degrees
        // DRIVER_PAD.DRIVER_PAD_D_PAD_RIGHT.whenPressed(new TurretSetAbs(+5500));//
        // about +30 degrees
        // DRIVER_PAD.DRIVER_PAD_D_PAD_LEFT.whileHeld(new ShooterSetTurretVBus(-0.2));//
        // about -30 degrees
        // DRIVER_PAD.DRIVER_PAD_D_PAD_RIGHT.whileHeld(new
        // ShooterSetTurretVBus(+0.2));// about +30 degrees
        // DRIVER_PAD.DRIVER_PAD_D_PAD_UP.whenPressed(new
        // HoodSetAbsWhileHeld(Hood.INITIATION_LINE_POSITION));
        // DRIVER_PAD.DRIVER_PAD_D_PAD_DOWN.whenPressed(new
        // HoodSetAbsWhileHeld(Hood.STARTING_POSITION));

        DRIVER_PAD.DRIVER_PAD_D_PAD_LEFT.whenPressed(new TurretSetRel(-200.0));
        DRIVER_PAD.DRIVER_PAD_D_PAD_RIGHT.whenPressed(new TurretSetRel(+200.0));
        DRIVER_PAD.DRIVER_PAD_D_PAD_DOWN.whenPressed(new DriveStraightOnHeading(-0.25, 8));
        // DRIVER_PAD.DRIVER_PAD_D_PAD_UP.whenPressed(new TurretSetAbs(+0.0));
        // DRIVER_PAD.DRIVER_PAD_D_PAD_DOWN.whenPressed(new
        // ShooterSetHoodAbs(Shooter.HOOD_TARGET_ZONE_POSITION));

        // Debug Hood
        // DRIVER_PAD.DRIVER_PAD_D_PAD_UP.whenPressed(new HoodSetRel(+100));
        // DRIVER_PAD.DRIVER_PAD_D_PAD_DOWN.whenPressed(new HoodSetRel(-100));
        // DRIVER_PAD.DRIVER_PAD_D_PAD_UP.whileHeld(new ShooterSetHoodVBus(+1.0));
        // DRIVER_PAD.DRIVER_PAD_D_PAD_DOWN.whileHeld(new ShooterSetHoodVBus(-1.0));

        // Debug shooter pid velocity
        // DRIVER_PAD.DRIVER_PAD_D_PAD_UP.whenHeld(new ParallelCommandGroup(
        // new FeederSet(0.8),
        // new ChimneySet(0.8)
        // ));
        // DRIVER_PAD.DRIVER_PAD_BLUE_BUTTON.whileHeld(new ShooterWheelAdjust(50.0));
        // DRIVER_PAD.DRIVER_PAD_RED_BUTTON.whileHeld(new ShooterWheelAdjust(-50.0));
        // DRIVER_PAD.DRIVER_PAD_YELLOW_BUTTON.whileHeld(new HoodSetRel(50.0));
        // DRIVER_PAD.DRIVER_PAD_GREEN_BUTTON.whileHeld(new HoodSetRel(-50.0));
        // DRIVER_PAD.DRIVER_PAD_RED_BUTTON.whenPressed(new ShooterWheelSetVBus(0.0));

        // DRIVER_PAD.DRIVER_PAD_BLUE_BUTTON.whenPressed(new ChimneySet(-0.7));
        // DRIVER_PAD.DRIVER_PAD_GREEN_BUTTON.whenPressed(new FeederSet(0.8));
        // DRIVER_PAD.DRIVER_PAD_YELLOW_BUTTON.whenPressed(new
        // ShooterWheelSet(ShooterWheel.IDLE_SPEED));
        // DRIVER_PAD.DRIVER_PAD_RED_BUTTON.whenPressed(new ParallelCommandGroup(
        // new ChimneySet(0.0),
        // new FeederSet(0.0),
        // new ShooterWheelSet(0.0)));

        DRIVER_PAD.DRIVER_PAD_LEFT_UPPER_TRIGGER_BUTTON.whenHeld(new ShooterFiringSequence(60.0));
        DRIVER_PAD.DRIVER_PAD_LEFT_UPPER_TRIGGER_BUTTON.whenReleased(new ShooterCeaseFire());

        DRIVER_PAD.DRIVER_PAD_LEFT_LOWER_TRIGGER_BUTTON.whenHeld(new ShooterCloseFiringSequence(60.0));
        DRIVER_PAD.DRIVER_PAD_LEFT_LOWER_TRIGGER_BUTTON.whenReleased(new ShooterCeaseFire());

        DRIVER_PAD.DRIVER_PAD_BACK_BUTTON.whenPressed(new DriveStraightOnHeading(-0.3, DistanceUnits.INCHES, 240, 0));
        DRIVER_PAD.DRIVER_PAD_START_BUTTON.whenPressed(new DriveStraightOnHeading(0.3, DistanceUnits.INCHES, 240, 0));
    }

    private void configureOperatorStickButtons() {
    }

    private void configureOperatorPadButtons() {
        OPERATOR_PAD.OPERATOR_PAD_BUTTON_ONE.whileHeld(new RevolverSetTurntable(0.2));
        OPERATOR_PAD.OPERATOR_PAD_BUTTON_TWO.whenPressed(new IntakeSetPosition(Intake.PIVOT_DOWN));
        OPERATOR_PAD.OPERATOR_PAD_BUTTON_THREE.whileHeld(new RevolverSetTurntable(-0.2));
        OPERATOR_PAD.OPERATOR_PAD_BUTTON_FOUR.whenPressed(new TurretSetAbs(0.0));
        // OPERATOR_PAD.OPERATOR_PAD_BUTTON_FOUR.whenPressed(new
        // IntakeSetPosition(Intake.PIVOT_UP));

        // new ShooterSetWheel(1000));
        OPERATOR_PAD.OPERATOR_PAD_BUTTON_FIVE.whileHeld(new ChimneySet(1.0));
        OPERATOR_PAD.OPERATOR_PAD_BUTTON_SIX.whileHeld(new IntakeSetRollersWhileHeld(-1.0));

        OPERATOR_PAD.OPERATOR_PAD_BUTTON_SEVEN.whenPressed(new ShooterAimToTarget());
        OPERATOR_PAD.OPERATOR_PAD_BUTTON_EIGHT.whileHeld(new IntakeSetRollersWhileHeld(1.0));

        OPERATOR_PAD.OPERATOR_PAD_BUTTON_NINE.whenPressed(new ClimberSetPistons(false));
        OPERATOR_PAD.OPERATOR_PAD_BUTTON_TEN.whenPressed(new ClimberSetPistons(true));

        // OPERATOR_PAD.OPERATOR_PAD_D_PAD_UP.whenPressed(new
        // IntakeSetPosition(RobotContainer.intake.PIVOT_UP));
        // OPERATOR_PAD.OPERATOR_PAD_D_PAD_DOWN.whenPressed(new
        // IntakeSetPosition(RobotContainer.intake.PIVOT_DOWN));
        OPERATOR_PAD.OPERATOR_PAD_D_PAD_LEFT.whileHeld(new TurretSetVBus(-0.4));
        OPERATOR_PAD.OPERATOR_PAD_D_PAD_RIGHT.whileHeld(new TurretSetVBus(+0.4));
        OPERATOR_PAD.OPERATOR_PAD_D_PAD_UP.whileHeld(new HoodAdjust(+1000.0));
        OPERATOR_PAD.OPERATOR_PAD_D_PAD_DOWN.whileHeld(new HoodAdjust(-1000.0));

        OPERATOR_PAD.OPERATOR_PAD_RIGHT_Y_AXIS_UP.whileHeld(new ClimberSetWinchesPower(1.0));
        OPERATOR_PAD.OPERATOR_PAD_RIGHT_Y_AXIS_DOWN.whileHeld(new ClimberSetWinchesPower(-1.0));

        // OPERATOR_PAD.OPERATOR_PAD_LEFT_Y_AXIS_UP.whenPressed(new
        // RevolverSetTurntable());
        OPERATOR_PAD.OPERATOR_PAD_LEFT_Y_AXIS_DOWN.whileHeld(new ChimneyUnjam());
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
