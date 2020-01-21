/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.mayheminc.robot2020.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class RobotDisabledCommand extends CommandBase {
  /**
   * Creates a new RobotDisabledCommand.
   */
  public RobotDisabledCommand() {
    // Use addRequirements() here to declare subsystem dependencies.
  }

  public boolean runsWhenDisabled() {
    return true;
  }
}
