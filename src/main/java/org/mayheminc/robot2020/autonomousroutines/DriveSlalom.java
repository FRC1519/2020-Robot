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

public class DriveSlalom extends SequentialCommandGroup {
        /**
         * Add your docs here.
         */
        public DriveSlalom() {
                addCommands(new DriveZeroGyro(0.0));

                // // drive forward
                // addCommands(new DriveStraightOnHeading(0.3, DistanceUnits.INCHES, 11, 0));
                // // turn
                // addCommands(new DriveStraightOnHeading(0.3, DistanceUnits.INCHES, 78, 295));
                // // drive forward some more
                // addCommands(new DriveStraightOnHeading(0.3, DistanceUnits.INCHES, 12 * 11,
                // 0));
                // // turn again
                // addCommands(new DriveStraightOnHeading(0.3, DistanceUnits.INCHES, 78, 65));
                // // forward
                // addCommands(new DriveStraightOnHeading(0.3, DistanceUnits.INCHES, 48, 0));
                // // turn again again
                // addCommands(new DriveStraightOnHeading(0.3, DistanceUnits.INCHES, 54, 270));
                // addCommands(new DriveStraightOnHeading(0.3, DistanceUnits.INCHES, 54, 185));
                // addCommands(new DriveStraightOnHeading(0.3, DistanceUnits.INCHES, 60, 115));

                // // lotta driving
                // addCommands(new DriveStraightOnHeading(0.3, DistanceUnits.INCHES, 12 * 12,
                // 180));
                // // turn again again 2 electic boogalo
                // addCommands(new DriveStraightOnHeading(0.3, DistanceUnits.INCHES, 78, 245));
                // addCommands(new DriveStraightOnHeading(0.3, DistanceUnits.INCHES, 36, 180));

                addCommands(new TurnToHeading(20, 0.3, -45, TurnToHeading.Direction.LEFT));
                addCommands(new DriveStraightOnHeading(0.3, DistanceUnits.INCHES, 15, 295));
                addCommands(new TurnToHeading(20, 0.4, 0, TurnToHeading.Direction.RIGHT));
                addCommands(new DriveStraightOnHeading(0.3, DistanceUnits.INCHES, 66, 0));
                addCommands(new TurnToHeading(20, 0.3, 50, TurnToHeading.Direction.RIGHT));
                addCommands(new DriveStraightOnHeading(0.3, DistanceUnits.INCHES, 20, 50));
                // COOL TURN 
                addCommands(new TurnToHeading(1, 0.42, -45, TurnToHeading.Direction.LEFT));
                addCommands(new TurnToHeading(1, 0.42, -135, TurnToHeading.Direction.LEFT));
                addCommands(new TurnToHeading(1, 0.42, 100, TurnToHeading.Direction.LEFT));
                // COOL TURN 
                addCommands(new DriveStraightOnHeading(0.3, DistanceUnits.INCHES, 30, 135));
                addCommands(new TurnToHeading(1, 0.3, -180, TurnToHeading.Direction.RIGHT));
                addCommands(new DriveStraightOnHeading(0.3, DistanceUnits.INCHES, 88, 180));
                addCommands(new TurnToHeading(1, 0.3, -135, TurnToHeading.Direction.RIGHT));
                addCommands(new DriveStraightOnHeading(0.3, DistanceUnits.INCHES, 56, -135));
                addCommands(new DriveStraightOnHeading(0.3, DistanceUnits.INCHES, 15, -180));
        }
}
