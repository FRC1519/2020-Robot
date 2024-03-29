/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.mayheminc.robot2020.commands;

import org.mayheminc.robot2020.RobotContainer;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class TurretSetRel extends CommandBase {
  double m_setPoint;

  /**
   * Creates a new TurretSetRel.
   */
  public TurretSetRel(double setPoint) {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(RobotContainer.turret);

    m_setPoint = setPoint;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    RobotContainer.turret.setPositionRel(m_setPoint);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return true;
  }
}
