/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.mayheminc.robot2020.autonomousroutines;

import org.mayheminc.robot2020.commands.DriveStraightOnHeading;
import org.mayheminc.robot2020.commands.DriveZeroGyro;
import org.mayheminc.robot2020.commands.IntakeSetPosition;
import org.mayheminc.robot2020.commands.IntakeSetRollers;
import org.mayheminc.robot2020.commands.PrintAutonomousTimeElapsed;
import org.mayheminc.robot2020.commands.DriveStraightOnHeading.DistanceUnits;
import org.mayheminc.robot2020.subsystems.Intake;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.PrintCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class PathABlue extends SequentialCommandGroup {
  /**
   * Creates a new PathABlue.
   * 
   * Starts pointing straight from the left of the field
   */
  public PathABlue() {
    // Add your commands in the super() call, e.g.
    // super(new FooCommand(), new BarCommand());

    addCommands(new DriveZeroGyro(0.0));

    addCommands(new IntakeSetRollers(-1));

    addCommands(new ParallelCommandGroup(new IntakeSetPosition(Intake.PIVOT_DOWN),
        new DriveStraightOnHeading(0.4, DistanceUnits.INCHES, 10 * 12, 0)));

    addCommands(new DriveStraightOnHeading(0.4, DistanceUnits.INCHES, 9.5 * 12, -85));
    addCommands(new DriveStraightOnHeading(0.4, DistanceUnits.INCHES, 10 * 12, 50));
    addCommands(new DriveStraightOnHeading(0.4, DistanceUnits.INCHES, 3 * 12, 0));

    addCommands(new IntakeSetRollers(0));
    addCommands(new PrintAutonomousTimeElapsed("Path A Blue"));
  }
}
