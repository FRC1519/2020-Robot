// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package org.mayheminc.robot2020.autonomousroutines;

import org.mayheminc.robot2020.commands.DriveStraightOnHeading;
import org.mayheminc.robot2020.commands.DriveZeroGyro;
import org.mayheminc.robot2020.commands.TurnToHeading;

import org.mayheminc.robot2020.commands.TurnToHeading.Direction;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class DriveBouncePath extends SequentialCommandGroup {
  /** Creates a new DriveBouncePath. */
  public DriveBouncePath() {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(new DriveZeroGyro(0.0));
    addCommands(new DriveStraightOnHeading(0.4, 18, 0));
    addCommands(new TurnToHeading(12, 0.4, -90, Direction.LEFT));
    addCommands(new DriveStraightOnHeading(0.4, 6, -90));
    addCommands(new DriveStraightOnHeading(-0.4, 6.5 * 12, -114));
    addCommands(new TurnToHeading(12, -0.4, 100, Direction.LEFT));
    addCommands(new DriveStraightOnHeading(-0.4, 5 * 12, 90));
    addCommands(new DriveStraightOnHeading(0.4, 5.5 * 12, 90));
    addCommands(new TurnToHeading(12, 0.4, 0, Direction.LEFT));
    addCommands(new DriveStraightOnHeading(0.4, 1.5 * 12, 0));
    addCommands(new TurnToHeading(12, 0.4, -80, Direction.LEFT));
    addCommands(new DriveStraightOnHeading(0.4, 5 * 12, -90));
    addCommands(new DriveStraightOnHeading(-0.4, 12, -90));
    addCommands(new TurnToHeading(12, -0.4, -180, Direction.LEFT));
    addCommands(new DriveStraightOnHeading(-0.4, 12, -180));
  }
}
