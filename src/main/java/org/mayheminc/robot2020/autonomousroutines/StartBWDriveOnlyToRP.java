/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.mayheminc.robot2020.autonomousroutines;

import org.mayheminc.robot2020.commands.*;
import org.mayheminc.robot2020.commands.DriveStraightOnHeading.DistanceUnits;
import org.mayheminc.robot2020.subsystems.Intake;

import edu.wpi.first.wpilibj2.command.*;

public class StartBWDriveOnlyToRP extends SequentialCommandGroup {
        /**
         * Add your docs here.
         */
        public StartBWDriveOnlyToRP() {

                // start backwards
                addCommands(new DriveZeroGyro(180.0));

                // lower the intake
                addCommands(new IntakeSetPosition(Intake.PIVOT_DOWN));

                // then, drive to the rendezvous point
                addCommands(new DriveStraightOnHeading(0.3, DistanceUnits.INCHES, 12, 180));
        }
}
