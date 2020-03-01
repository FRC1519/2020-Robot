/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.mayheminc.robot2020.autonomousroutines;

import org.mayheminc.robot2020.commands.*;
import org.mayheminc.robot2020.commands.DriveStraightOnHeading.DistanceUnits;
import org.mayheminc.robot2020.subsystems.Hood;
import org.mayheminc.robot2020.subsystems.Intake;
import org.mayheminc.robot2020.subsystems.ShooterWheel;
import org.mayheminc.robot2020.subsystems.Turret;

import edu.wpi.first.wpilibj2.command.*;

public class StartBWOppTrench extends SequentialCommandGroup {
        /**
         * Add your docs here.
         */
        public StartBWOppTrench() {

                addCommands(new DriveZeroGyro(180.0));

                // lower the intake and wait for it to be on before turning on rollers or
                // driving forwards
                addCommands(new IntakeSetPosition(Intake.PIVOT_DOWN));
                addCommands(new Wait(2.5));
                addCommands(new IntakeSetRollers(-1.0));

                // make sure the hood is down
                addCommands(new HoodSetAbsWhileHeld(Hood.TARGET_ZONE_POSITION));

                // drive to get balls from opponent's trench
                addCommands(new DriveStraightOnHeading(0.2, DistanceUnits.INCHES, 108, 180));

                // now driven to get the balls from opposing trench
                addCommands(new Wait(0.8), // wait for last two balls to get into robot
                                // new IntakeSetRollers(0), // slow down the intake
                                new DriveStraightOnHeading(-0.4, DistanceUnits.INCHES, 36, 180)); // start backing up
                                                                                                  // slowly

                // backup about halfway across the field
                addCommands(new DriveStraightOnHeading(-0.4, DistanceUnits.INCHES, 180, 270));

                // in shooting position, prepare everything for shooting
                addCommands(new ParallelCommandGroup( // run the following commands in parallel:
                                new ShooterWheelSet(ShooterWheel.IDLE_SPEED),
                                new HoodSetAbsWhileHeld(Hood.INITIATION_LINE_POSITION + 3000.0),
                                new TurretSetAbs((105.0 * Turret.TICKS_PER_DEGREE), Turret.WAIT_FOR_DONE)));

                // turn on the intake gently while shooting to help balls settle
                addCommands(new IntakeSetRollers(-0.2));

                // wait 2/10 of a second to get some camera data after turning turret
                addCommands(new Wait(0.2));

                // use the "one button" firing sequence
                addCommands(new ShooterFiringSequence(6.0));

                // turn the shooter wheel and intake off now that the shooting is all done
                addCommands(new ShooterWheelSet(ShooterWheel.IDLE_SPEED));
                addCommands(new IntakeSetRollers(0.0));

                // turn the wheel off now that the shooting is all done
                addCommands(new HoodSetAbsWhileHeld(Hood.STARTING_POSITION));
        }
}
