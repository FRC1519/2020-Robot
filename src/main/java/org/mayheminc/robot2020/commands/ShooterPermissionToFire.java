/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.mayheminc.robot2020.commands;

import org.mayheminc.robot2020.RobotContainer;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class ShooterPermissionToFire extends CommandBase {

  /**
   * Creates a new ShooterPositionToFire This command is intended to shoot while
   * the driver holds the "Permission To Fire" button
   */
  public ShooterPermissionToFire() {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(RobotContainer.chimney);
    addRequirements(RobotContainer.feeder);
    addRequirements(RobotContainer.hood);
    addRequirements(RobotContainer.revolver);
    addRequirements(RobotContainer.shooterWheel);
    addRequirements(RobotContainer.turret);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    // do the following: command the shooterWheel to turn at speed, aimTurret,
    // aimHood
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    // if shooterWheels at speed, turret aimed, and hood aimed, then fire!
    // firing actions as follows:
    // feed roller after 0.0 seconds since start of firing
    // chimney after 0.1 seconds since start of firing
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }
}
