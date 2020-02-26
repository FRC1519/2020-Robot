/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.mayheminc.robot2020.commands;

import org.mayheminc.robot2020.RobotContainer;
// import org.mayheminc.robot2020.subsystems.Targeting;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class ShooterFireWhenReady extends CommandBase {
  /**
   * Creates a new ShooterFireWhenReady.
   */
  public ShooterFireWhenReady() {
    // Use addRequirements() here to declare subsystem dependencies.
    // addRequirements(RobotContainer.shooter);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    // boolean wheelsGood =
    // Math.abs(Targeting.convertRangeToWheelSpeed(RobotContainer.targeting.getRangeToTarget())
    // - RobotContainer.shooter.getShooterWheelSpeed()) < 100;

    // RobotContainer.shooter.setFeederSpeed((wheelsGood) ? 0.5 : 0.0);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    RobotContainer.feeder.setSpeed(0);
    RobotContainer.shooterWheel.setSpeed(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
