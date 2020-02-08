/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.mayheminc.robot2020.commands;

import org.mayheminc.robot2020.RobotContainer;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class ClimberSetWinchesPower extends CommandBase {
  double power;

  /**
   * Creates a new ClimberSetWinchesPower.
   */
  public ClimberSetWinchesPower(double d) {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(RobotContainer.climber);
    power = d;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    RobotContainer.climber.setWinchLeftSpeed(power);
    RobotContainer.climber.setWinchRightSpeed(power);
  }

  @Override
  public void end(boolean interrupted) {

    RobotContainer.climber.setWinchLeftSpeed(0.0);
    RobotContainer.climber.setWinchRightSpeed(0.0);
  }

}
