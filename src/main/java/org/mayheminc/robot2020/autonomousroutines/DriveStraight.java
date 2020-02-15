/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.mayheminc.robot2020.autonomousroutines;

import org.mayheminc.robot2020.commands.*;
import org.mayheminc.robot2020.commands.DriveStraightOnHeading.DistanceUnits;

import edu.wpi.first.wpilibj2.command.*;

public class DriveStraight extends SequentialCommandGroup {
  /**
   * Add your docs here.
   */
  public DriveStraight() {

    addCommands(new DriveZeroGyro());
    // addCommands(new DriveStraightOnHeading(0.2, DistanceUnits.INCHES, 100, 0));
    addCommands(new DriveStraightOnHeading(0.3, DistanceUnits.INCHES, 100, 0));
    // addCommands(new DriveStraightOnHeading(0.4, DistanceUnits.INCHES, 100, 0));

    // addCommands(new ParallelCommandGroup(new IntakeSetPosition(true), new
    // MagazineSetTurntable(0.0)));
  }
}
