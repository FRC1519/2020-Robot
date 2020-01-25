/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.mayheminc.robot2020.commands;

import org.mayheminc.robot2020.RobotContainer;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class MagazineSetTurntable extends CommandBase {
  double m_speed;

  /**
   * Creates a new MagazineSetTurntable.
   */
  public MagazineSetTurntable(double d) {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(RobotContainer.magazine);
    m_speed = d;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    RobotContainer.magazine.setTurntableSpeed(m_speed);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    RobotContainer.magazine.setTurntableSpeed(0.0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
