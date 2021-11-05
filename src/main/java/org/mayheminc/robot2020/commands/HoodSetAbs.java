/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.mayheminc.robot2020.commands;

import org.mayheminc.robot2020.RobotContainer;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class HoodSetAbs extends CommandBase {
  double m_set;

  /**
   * Creates a new HoodSetAbs.
   */
  public HoodSetAbs(double set) {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(RobotContainer.hood);

    m_set = set;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    RobotContainer.hood.setPosition(m_set);
  }

  @Override
  public boolean isFinished() {
    return (RobotContainer.hood.isAtPosition());
  }
}
