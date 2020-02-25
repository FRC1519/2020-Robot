/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.mayheminc.robot2020.commands;

import org.mayheminc.robot2020.RobotContainer;

import edu.wpi.first.wpilibj2.command.InstantCommand;

public class AirCompressorPause extends InstantCommand {
  /**
   * Creates a new AirCompressorSet.
   */
  public AirCompressorPause() {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(RobotContainer.compressor);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    RobotContainer.compressor.setCompressor(false);
  }

}
