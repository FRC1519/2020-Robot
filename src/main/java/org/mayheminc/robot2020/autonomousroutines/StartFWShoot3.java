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

public class StartFWShoot3 extends SequentialCommandGroup {
        /**
         * Add your docs here.
         */
        public StartFWShoot3() {

                addCommands(new DriveZeroGyro(0.0));

                // first, lower the intake, start the shooter wheel, and wait until the turret
                // is turned towards the target
                // addCommands(new ParallelCommandGroup( // run the following commands in
                // parallel:
                // new IntakeSetPosition(Intake.PIVOT_DOWN),
                // // new ShooterWheelSet(ShooterWheel.INITIATION_LINE_SPEED, true),
                // // new HoodSetAbs(Hood.INITIATION_LINE_POSITION),
                // new TurretSetAbs((0.0 * Turret.TICKS_PER_DEGREE), Turret.WAIT_FOR_DONE)));

                addCommands(new ShooterFiringSequence(4.0));

                // turn the shooter wheel and intake off now that the shooting is all done
                addCommands(new ParallelCommandGroup( // below commands in parallel
                                new ShooterWheelSet(ShooterWheel.IDLE_SPEED), //
                                new IntakeSetRollers(0.0), // turn off the rollers
                                new HoodSetAbs(Hood.STARTING_POSITION)));
        }
}
