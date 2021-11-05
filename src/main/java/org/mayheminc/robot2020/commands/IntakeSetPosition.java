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
  boolean m_waitForDone;

  /**
   * Creates a new IntakeSetPosition, with "wait" set to false
   */
  public IntakeSetPosition(double position) {
    this (position, false);
  }

  /**
   * Creates a new IntakeSetPosition
   */
  public IntakeSetPosition(double position, boolean wait) {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(RobotContainer.intake);

    m_position = position;
    m_waitForDone = wait;
  }


  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    RobotContainer.intake.setPivot(m_position);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if (m_waitForDone) {
      return RobotContainer.intake.isPivotAtPosition();
    } else {
      return true;
    }
  }
}
