/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.mayheminc.robot2020.commands;

import org.mayheminc.robot2020.subsystems.Shooter;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class ShooterAdjustWheel extends CommandBase {
  Shooter m_shooter;
  double m_adjust;

  /**
   * Creates a new ShooterAdjustWheel.
   */
  public ShooterAdjustWheel(Shooter shooter, double adjust) {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(shooter);

    m_shooter = shooter;
    m_adjust = adjust;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_shooter.setShooterWheelSpeed(m_shooter.getShooterWheelSpeed() + m_adjust);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return true;
  }
}
