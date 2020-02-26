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
import org.mayheminc.robot2020.subsystems.Hood;

import edu.wpi.first.wpilibj2.command.*;

public class TrenchAuto extends SequentialCommandGroup {
    /**
     * Add your docs here.
     */
    public TrenchAuto() {

        addCommands(new DriveZeroGyro(180.0));
        // first, shoot the balls that were pre-loaded

        addCommands(new IntakeSetPositionWithoutWaiting(RobotContainer.intake.PIVOT_DOWN));
        addCommands(new TurretSetAbs(+23000.0));
        addCommands(new ShooterWheelSet(3000.0, false));
        addCommands(new Wait(2.0));
        addCommands(new ShooterReadyAimFire(2.5));

        // then, drive down the trench, jogging left a little
        addCommands(new DriveStraightOnHeading(0.3, DistanceUnits.INCHES, 36, 150));

        // pick up balls while heading down the trench.
        addCommands(new ParallelRaceGroup(
                // intake while driving down the trench
                new IntakeSetRollers(-1.0),
                new SequentialCommandGroup(new DriveStraightOnHeading(0.3, DistanceUnits.INCHES, 180, 180),
                        new Wait(0.5), new DriveStraightOnHeading(-0.3, DistanceUnits.INCHES, 12, 180))));

        // after getting all three balls, go back to shooting position
        addCommands(new DriveStraightOnHeading(-0.3, DistanceUnits.INCHES, 48, 180));
        addCommands(new DriveStraightOnHeading(-0.3, DistanceUnits.INCHES, 96, 160));
        addCommands(new DriveStraightOnHeading(-0.3, DistanceUnits.INCHES, 24, 180));

        addCommands(new ShooterReadyAimFire(6.0));

        // turn the wheel off now that the shooting is all done
        addCommands(new ShooterWheelSet(0.0));

        // turn the wheel off now that the shooting is all done
        addCommands(new HoodSetAbs(Hood.HOOD_TARGET_ZONE_POSITION));
    }
}
