// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package org.mayheminc.robot2020.autonomousroutines;

import org.mayheminc.robot2020.commands.DriveStraightOnHeading;
import org.mayheminc.robot2020.commands.DriveZeroGyro;
import org.mayheminc.robot2020.commands.TurnToHeading;
import org.mayheminc.robot2020.commands.DriveStraightOnHeading.DistanceUnits;
import org.mayheminc.robot2020.commands.TurnToHeading.Direction;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class DriveBarrelRacing extends SequentialCommandGroup {
  /** Creates a new DriveBarrelRacing. */
  public DriveBarrelRacing() {
    // TODO: make work on actual robot

    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(new DriveZeroGyro(0.0));

    addCommands(new DriveStraightOnHeading(0.4, 102, 0));

    addCommands(new TurnToHeading(12, 0.4, 180, Direction.RIGHT));
    addCommands(new TurnToHeading(12, 0.4, -30, Direction.RIGHT));

    addCommands(new DriveStraightOnHeading(0.4, 94, 0));

    addCommands(new TurnToHeading(12, 0.4, 180, Direction.LEFT));
    addCommands(new TurnToHeading(12, 0.4, 50, Direction.LEFT));

    addCommands(new DriveStraightOnHeading(0.4, 60, 50));

    addCommands(new TurnToHeading(12, 0.4, 180, Direction.LEFT));
    addCommands(new DriveStraightOnHeading(0.4, 20 * 12, 180));
  }
}
