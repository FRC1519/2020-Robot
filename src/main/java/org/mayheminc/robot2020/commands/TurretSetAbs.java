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
  boolean m_waitForDone;

  /**
   * Creates a new TurretSetAbs.
   */
  public TurretSetAbs(double setPoint) {
    this(setPoint, false);
  }

  public TurretSetAbs(double setPoint, boolean wait) {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(RobotContainer.turret);

    m_setPoint = setPoint;
    m_waitForDone = wait;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    RobotContainer.turret.setPositionAbs(m_setPoint);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if (m_waitForDone) {
      return RobotContainer.turret.isAtDesiredPosition();
    } else {
      return true;
    }
  }
}
