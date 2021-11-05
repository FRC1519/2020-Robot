// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package org.mayheminc.robot2020.commands;

import org.mayheminc.robot2020.RobotContainer;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.*;

public class ChimneyUnjam extends SequentialCommandGroup {
  /** Creates a new ChimneyUnjam. */
  public ChimneyUnjam() {
    super(new ParallelCommandGroup(new RevolverSetTurntable(-0.5), new ChimneySet(-0.5)));
  }

}
