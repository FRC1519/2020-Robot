/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.mayheminc.robot2020.commands;

import org.mayheminc.robot2020.RobotContainer;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class IntakeSetRollersWhileHeld extends CommandBase {
  double m_speed;

  /**
   * Creates a new IntakeSetRollers.
   */
  public IntakeSetRollersWhileHeld(double speed) {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(RobotContainer.intake);
    m_speed = speed;

  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    RobotContainer.intake.setRollers(m_speed);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    RobotContainer.intake.setRollers(0.0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
