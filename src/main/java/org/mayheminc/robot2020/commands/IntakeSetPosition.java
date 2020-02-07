/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.mayheminc.robot2020.commands;

import org.mayheminc.robot2020.RobotContainer;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class IntakeSetPosition extends CommandBase {
  double m_position;

  /**
   * Creates a new IntakeSetPosition.
   */
  public IntakeSetPosition(Double position) {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(RobotContainer.intake);
    m_position = position;

  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    RobotContainer.intake.setExtender(m_position);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return RobotContainer.intake.isExtenderAtPosition();
  }
}
