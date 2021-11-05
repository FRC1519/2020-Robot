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

public class StartFWShoot3ThenToRP extends SequentialCommandGroup {
        /**
         * Add your docs here.
         */
        public StartFWShoot3ThenToRP() {

                // start backwards and shoot the first three balls
                // addCommands(new StartFWShoot3());
                addCommands(new Wait(2.0));

                addCommands(new ShooterFiringSequence(4.0));

                // then, drive to the rendezvous point
                addCommands(new DriveStraightOnHeading(-0.3, DistanceUnits.INCHES, 12, 0));
        }
}
