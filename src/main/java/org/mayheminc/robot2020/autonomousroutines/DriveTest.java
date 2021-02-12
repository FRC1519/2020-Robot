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
                // wut
                addCommands(new DriveZeroGyro(0.0));

                addCommands(new DriveStraightOnHeading(0.4, DistanceUnits.INCHES, 36, 0));
                // addCommands(new Wait(0.02));
                // test stuff
                addCommands(new TurnToHeading(25, 0.4, 70, TurnToHeading.Direction.RIGHT));
                // turn
                // addCommands(new DriveStraightOnHeading(0.4, DistanceUnits.INCHES, 36, 90));
                // // addCommands(new Wait(0.03));

                // // test stuff
                // addCommands(new TurnToHeading(25, 0.4, 160, TurnToHeading.Direction.RIGHT));
                // // turn
                // addCommands(new DriveStraightOnHeading(0.4, DistanceUnits.INCHES, 36, 180));
                // // addCommands(new Wait(0.03));
                // // test stuff
                // addCommands(new TurnToHeading(25, 0.4, -110, TurnToHeading.Direction.RIGHT));
                // // turn
                // addCommands(new DriveStraightOnHeading(0.4, DistanceUnits.INCHES, 36, -90));
                // // addCommands(new Wait(0.03));
                // // test stuff
                // addCommands(new TurnToHeading(25, 0.4, -20, TurnToHeading.Direction.RIGHT));
                // // turn
                // addCommands(new DriveStraightOnHeading(0.4, DistanceUnits.INCHES, 36, 0));
                // // addCommands(new Wait(0.03));
        }
}
