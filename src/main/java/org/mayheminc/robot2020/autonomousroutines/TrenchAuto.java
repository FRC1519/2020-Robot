/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.mayheminc.robot2020.autonomousroutines;

import org.mayheminc.robot2020.RobotContainer;
import org.mayheminc.robot2020.commands.*;
import org.mayheminc.robot2020.commands.DriveStraightOnHeading.DistanceUnits;
import org.mayheminc.robot2020.subsystems.Shooter;

import edu.wpi.first.wpilibj2.command.*;

public class TrenchAuto extends SequentialCommandGroup {
    /**
     * Add your docs here.
     */
    public TrenchAuto() {

        addCommands(new DriveZeroGyro());
        // first, shoot the balls that were pre-loaded

        addCommands(new IntakeSetPositionWithoutWaiting(RobotContainer.intake.PIVOT_DOWN));
        addCommands(new ShooterReadyAimFire());

        // then, perform a 3-point turn
        addCommands(new DriveStraightOnHeading(0.3, DistanceUnits.INCHES, 48, 160));

        // pick up balls while heading down the trench.
        addCommands(new ParallelRaceGroup(
                // intake while driving down the trench
                new IntakeSetRollers(-1.0),
                new SequentialCommandGroup(new DriveStraightOnHeading(0.2, DistanceUnits.INCHES, 132, 180),
                        new Wait(0.5), new DriveStraightOnHeading(-0.2, DistanceUnits.INCHES, 12, 180))));

        // after getting all three balls, go back to shooting position
        addCommands(new DriveStraightOnHeading(-0.2, DistanceUnits.INCHES, 24, 30));
        addCommands(new DriveStraightOnHeading(0.3, DistanceUnits.INCHES, 120, 0));
        addCommands(new DriveStraightOnHeading(-0.1, DistanceUnits.INCHES, 6, 0));

        addCommands(new ShooterReadyAimFire());

        // turn the wheel off now that the shooting is all done
        addCommands( new ShooterSetWheel(0.0));

        addCommands(new ShooterSetHoodAbs(Shooter.HOOD_TARGET_ZONE_POSITION));
    }
}
