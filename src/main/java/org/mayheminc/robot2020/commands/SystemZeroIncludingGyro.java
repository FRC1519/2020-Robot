/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.mayheminc.robot2020.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class SystemZeroIncludingGyro extends SequentialCommandGroup {
  /**
   * Creates a new SystemZeroIncludingGyro.
   */
  public SystemZeroIncludingGyro() {
    // Use addRequirements() here to declare subsystem dependencies.

    addCommands(new IntakeZero());
    addCommands(new ClimberZero());
    addCommands(new ShooterZero());
    addCommands(new DriveZeroGyro());
  }

  @Override
  public boolean runsWhenDisabled() {
    // TODO Auto-generated method stub
    return true;
  }

}
