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
import frc.robot.commands.ExampleCommand;
import frc.robot.subsystems.ExampleSubsystem;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.Button;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a "declarative" paradigm, very little robot logic should
 * actually be handled in the {@link Robot} periodic methods (other than the
 * scheduler calls). Instead, the structure of the robot (including subsystems,
 * commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final ExampleSubsystem m_exampleSubsystem = new ExampleSubsystem();

  private final ExampleCommand m_autoCommand = new ExampleCommand(m_exampleSubsystem);

  private final Joystick DRIVER_PAD = new Joystick(Constants.Joysticks.DRIVER_GAMEPAD);
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

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();
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
    return m_autoCommand;
  }
}
