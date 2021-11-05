/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.mayheminc.robot2020.commands;

import org.mayheminc.robot2020.RobotContainer;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class TurretSetVBus extends CommandBase {
  double m_power;

  /**
   * Creates a new TurretSetVBus.
   */
  public TurretSetVBus(double power) {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(RobotContainer.turret);
    m_power = power;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    RobotContainer.turret.setVBus(m_power);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    RobotContainer.turret.setVBus(0.0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
