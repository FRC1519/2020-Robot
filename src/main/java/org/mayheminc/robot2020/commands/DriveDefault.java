/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.mayheminc.robot2020.commands;

import org.mayheminc.robot2020.RobotContainer;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class DriveDefault extends CommandBase {
  /**
   * Creates a new DriveDefault.
   */
  public DriveDefault() {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(RobotContainer.drive);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double throttle = RobotContainer.DRIVER_PAD.driveThrottle();
    double steeringX = RobotContainer.DRIVER_PAD.steeringX();
    boolean quickTurn = RobotContainer.DRIVER_PAD.quickTurn();
    RobotContainer.drive.speedRacerDrive(throttle, steeringX, quickTurn);
    // RobotContainer.drive.speedRacerDrive(0, 0, false);

  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
