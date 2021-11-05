/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.mayheminc.robot2020.commands;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;

public class SystemZeroIncludingGyro extends ParallelCommandGroup {
  /**
   * Creates a new SystemZeroIncludingGyro.
   */
  public SystemZeroIncludingGyro() {
    // Use addRequirements() here to declare subsystem dependencies.

    addCommands(new IntakeZero());
    addCommands(new ClimberZero());
    addCommands(new HoodZero());
    addCommands(new TurretZero());
    addCommands(new DriveZeroGyro(0.0));
  }

  @Override
  public boolean runsWhenDisabled() {
    return true;
  }

}
