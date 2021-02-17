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

public class DriveTest extends SequentialCommandGroup {
        /**
         * Add your docs here.
         */
        public DriveTest() {
                addCommands(new DriveZeroGyro(0.0));

                // addCommands(new DriveStraightOnHeading(0.25, DistanceUnits.INCHES, 36, 0));
                // addCommands(new TurnToHeading(25, 0.25, 45, TurnToHeading.Direction.RIGHT));

                // addCommands(new DriveStraightOnHeading(0.25, DistanceUnits.INCHES, 36, 45));
                // addCommands(new TurnToHeading(25, 0.25, 135, TurnToHeading.Direction.RIGHT));

                // addCommands(new DriveStraightOnHeading(0.25, DistanceUnits.INCHES, 36, 135));
                // addCommands(new TurnToHeading(25, 0.25, -135,
                // TurnToHeading.Direction.RIGHT));

                // addCommands(new DriveStraightOnHeading(0.25, DistanceUnits.INCHES, 36,
                // -135));
                // addCommands(new TurnToHeading(25, 0.25, -45, TurnToHeading.Direction.RIGHT));

                // addCommands(new DriveStraightOnHeading(0.25, DistanceUnits.INCHES, 36, -45));

                addCommands(new DriveStraightOnHeading(0.25, DistanceUnits.INCHES, 36, 0));
                addCommands(new TurnToHeading(25, 0.25, -45, TurnToHeading.Direction.LEFT));

                addCommands(new DriveStraightOnHeading(0.25, DistanceUnits.INCHES, 36, -45));
                addCommands(new TurnToHeading(25, 0.25, -135, TurnToHeading.Direction.LEFT));

                addCommands(new DriveStraightOnHeading(0.25, DistanceUnits.INCHES, 36, -135));
                addCommands(new TurnToHeading(25, 0.25, 135, TurnToHeading.Direction.LEFT));

                addCommands(new DriveStraightOnHeading(0.25, DistanceUnits.INCHES, 36, 135));
                addCommands(new TurnToHeading(25, 0.25, 45, TurnToHeading.Direction.LEFT));

                addCommands(new DriveStraightOnHeading(0.25, DistanceUnits.INCHES, 36, 45));
        }
}
