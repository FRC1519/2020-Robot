/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.mayheminc.robot2020.autonomousroutines;

import org.mayheminc.robot2020.commands.*;
import org.mayheminc.robot2020.RobotContainer;
import org.mayheminc.robot2020.commands.DriveStraightOnHeading.DistanceUnits;
import edu.wpi.first.wpilibj2.command.*;

public class StartFWDriveOnlyToWall extends SequentialCommandGroup {
        /**
         * Add your docs here.
         */
        public StartFWDriveOnlyToWall() {

                // start forwards
                addCommands(new DriveZeroGyro(0.0));

                // lower the intake
                addCommands(new IntakeSetPosition(RobotContainer.intake.PIVOT_DOWN));

                // then, drive to the RP
                addCommands(new DriveStraightOnHeading(0.3, DistanceUnits.INCHES, 40, 0));
        }
}
