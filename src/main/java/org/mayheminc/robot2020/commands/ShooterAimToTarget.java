/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.mayheminc.robot2020.commands;

import org.mayheminc.robot2020.RobotContainer;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class ShooterAimToTarget extends CommandBase {
  /**
   * Creates a new TurretAimToTarget.
   */
  public ShooterAimToTarget() {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(RobotContainer.hood);
    addRequirements(RobotContainer.turret);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    // aim the hood and turret
    RobotContainer.hood.setPosition(RobotContainer.targeting.getDesiredHood());
    RobotContainer.turret.setPositionAbs(RobotContainer.targeting.getDesiredAzimuth());
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    // aim the hood and turret
    RobotContainer.hood.setPosition(RobotContainer.targeting.getDesiredHood());
    RobotContainer.turret.setPositionAbs(RobotContainer.targeting.getDesiredAzimuth());
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    if (!interrupted) {
      // RobotContainer.shooterWheel.setSpeed(RobotContainer.targeting.getDesiredWheelSpeed());

      // want to also schedule a command here that does the shooting!
    }
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return (RobotContainer.hood.isAtPosition() && RobotContainer.turret.isAtDesiredPosition());
  }
}
