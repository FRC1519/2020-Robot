/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.mayheminc.robot2020.commands;

import org.mayheminc.robot2020.RobotContainer;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class TurretSetAbs extends CommandBase {
  double m_setPoint;

  /**
   * Creates a new TurretSetAbs.
   */
  public TurretSetAbs(double setPoint) {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(RobotContainer.turret);

    m_setPoint = setPoint;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    RobotContainer.turret.setPositionAbs(m_setPoint);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return true;
  }
}
