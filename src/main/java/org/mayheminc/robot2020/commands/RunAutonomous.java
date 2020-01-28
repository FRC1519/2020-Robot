/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.mayheminc.robot2020.commands;

import org.mayheminc.robot2020.RobotContainer;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

/**
 * Delay the set amount of time then schedule the selected command to run.
 */
public class RunAutonomous extends CommandBase {
  private long startTime;
  Command command;
  boolean commandIsRunning;

  /**
   * Creates a new RunAutonomous.
   */
  public RunAutonomous() {
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called just before this Command runs the first time
  public void initialize() {
    startTime = System.currentTimeMillis() + RobotContainer.autonomous.getDelay() * 1000;
    command = RobotContainer.autonomous.getSelectedProgram();
  }

  // Called repeatedly when this Command is scheduled to run
  public void execute() {
    if (System.currentTimeMillis() >= startTime) {
      CommandScheduler.getInstance().schedule(command);
      commandIsRunning = true;
    }
  }

  // Make this return true when this Command no longer needs to run execute()
  public boolean isFinished() {
    return commandIsRunning;
  }

}