/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.mayheminc.robot2020.commands;

import org.mayheminc.robot2020.RobotContainer;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class HoodAdjust extends CommandBase {

  double m_adjust;

  /**
   * Creates a new ShooterAdjustHood.
   */
  public HoodAdjust(double adjust) {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(RobotContainer.hood);

    m_adjust = adjust;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    RobotContainer.hood.setPosition(RobotContainer.hood.getPosition() + m_adjust);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return true;
  }
}
