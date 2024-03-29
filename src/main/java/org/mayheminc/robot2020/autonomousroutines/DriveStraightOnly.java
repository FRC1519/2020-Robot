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

public class DriveStraightOnly extends SequentialCommandGroup {
  /**
   * Add your docs here.
   */
  public DriveStraightOnly() {

    addCommands(new DriveZeroGyro(0.0));

    addCommands(new DriveStraightOnHeading(0.3, DistanceUnits.INCHES, 50, 0));

    addCommands(new DriveStraightOnHeading(0.3, DistanceUnits.INCHES, 100, 270));

    // addCommands(new ShooterSetHoodAbs(Shooter.HOOD_INITIATION_LINE_POSITION));
    // addCommands(new DriveStraightOnHeading(0.4, DistanceUnits.INCHES, 100, 0));

  }
}
