/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.mayheminc.robot2020.commands;

import org.mayheminc.robot2020.RobotContainer;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class ShooterWheelSet extends CommandBase {
  double m_rpm;
  boolean m_waitForSpeed;

  public ShooterWheelSet(double rpm) {
    this(rpm, false);
  }

  /**
   * Creates a new ShooterWheelSet
   */
  public ShooterWheelSet(double rpm, boolean wait) {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(RobotContainer.shooterWheel);

    m_rpm = rpm;
    m_waitForSpeed = wait;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    RobotContainer.shooterWheel.setSpeed(m_rpm);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if( m_waitForSpeed)
    {
      return  (Math.abs( m_rpm - RobotContainer.shooterWheel.getSpeed() ) < 100);
    }
    else
    {
      return true;
    }
  }
}
