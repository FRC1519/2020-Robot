/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.mayheminc.robot2020.autonomousroutines;

import org.mayheminc.robot2020.commands.*;
import org.mayheminc.robot2020.subsystems.Hood;
import org.mayheminc.robot2020.subsystems.Intake;
import org.mayheminc.robot2020.subsystems.ShooterWheel;
import org.mayheminc.robot2020.subsystems.Turret;

import edu.wpi.first.wpilibj2.command.*;

public class StartBWShoot3 extends SequentialCommandGroup {
        /**
         * Add your docs here.
         */
        public StartBWShoot3() {

                addCommands(new DriveZeroGyro(180.0));

                // first, lower the intake, start the shooter wheel, and wait until the turret
                // is turned towards the target
                addCommands(new ParallelCommandGroup( // run the following commands in parallel:
                                new IntakeSetPosition(Intake.PIVOT_DOWN), // intake down
                                new ShooterWheelSet(ShooterWheel.IDLE_SPEED), // spin up the shooter
                                new HoodSetAbsWhileHeld(Hood.INITIATION_LINE_POSITION), // set the hood to a good
                                                                                        // starting position
                                new TurretSetAbs((180.0 * Turret.TICKS_PER_DEGREE), Turret.WAIT_FOR_DONE))); // turn
                                                                                                             // turret
                                                                                                             // around

                addCommands(new ShooterFiringSequence(1.5));

                // note that the above turns everything off again when it is done.
        }
}
