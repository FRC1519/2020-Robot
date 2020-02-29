/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.mayheminc.robot2020.commands;

import org.mayheminc.robot2020.RobotContainer;

// import org.mayheminc.robot2020.RobotContainer;
// import org.mayheminc.robot2020.subsystems.Targeting;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class TurretIsOnTarget extends CommandBase {
  /**
   * Creates a new TargetingIsOnTarget.
   */
  public TurretIsOnTarget() {
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    double targetPos = RobotContainer.targeting.getDesiredAzimuth();
    double turretPos = RobotContainer.turret.getPosition();

    return (Math.abs(targetPos - turretPos) < 50);
  }
}
