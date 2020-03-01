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
import org.mayheminc.robot2020.subsystems.ShooterWheel;
import org.mayheminc.robot2020.subsystems.Turret;

import edu.wpi.first.wpilibj2.command.*;

public class StartBWTrench extends SequentialCommandGroup {
        /**
         * Add your docs here.
         */
        public StartBWTrench(double extraDistance) {
                // Note: extra distance planned to be 40 inches

                // start backwards and shoot the first three balls
                addCommands(new StartBWShoot3());

                // then, drive to the trench entrance (jog left a little to get there)
                addCommands(new DriveStraightOnHeading(0.4, DistanceUnits.INCHES, 46, 140));

                // pick up balls while heading down the trench.
                addCommands(new ParallelCommandGroup(
                                // intake while driving down the trench
                                new IntakeSetRollers(-1.0),
                                // ensure the hood is down
                                new HoodSetAbsWhileHeld(Hood.STARTING_POSITION),
                                // drive the path under the control panel to the end
                                new DriveStraightOnHeading(0.3, DistanceUnits.INCHES, 140 + extraDistance, 180)));

                // now driven to the balls at far end of trench
                addCommands(new Wait(0.8), // wait for last two balls to get into robot
                                new DriveStraightOnHeading(-0.5, DistanceUnits.INCHES, 12, 180)); // start backing up
                                                                                                  // slowly

                // after getting all three balls, go back to shooting position
                // first, make sure we drive straight out from under the control panel
                addCommands(new DriveStraightOnHeading(-0.6, DistanceUnits.INCHES, 8 + extraDistance, 180));
                addCommands(new IntakeSetRollers(0.0)); // turn off the intake in case it has been stalled for a while

                // drive diagonally over towards the shooting position, while turning on shooter
                // wheels, raising the hood, and re-aiming the turret
                addCommands(new ParallelCommandGroup(new ShooterWheelSet(ShooterWheel.IDLE_SPEED),
                                new HoodSetAbsWhileHeld(Hood.INITIATION_LINE_POSITION),
                                new TurretSetAbs((168.0 * Turret.TICKS_PER_DEGREE)),
                                new DriveStraightOnHeading(-0.5, DistanceUnits.INCHES, 96, 160)));

                // straighten out again to enable turret to aim to the target
                addCommands(new DriveStraightOnHeading(-0.3, DistanceUnits.INCHES, 24, 180));

                // turn on the intake gently while shooting to help balls settle
                addCommands(new IntakeSetRollers(-0.2));

                // start the shooting sequence
                addCommands(new ShooterFiringSequence(6.0));

                // turn the shooter wheel and intake off now that the shooting is all done
                addCommands(new ShooterWheelSet(ShooterWheel.IDLE_SPEED));
                addCommands(new IntakeSetRollers(0.0));

                // put the hood down now that the shooting is all done
                addCommands(new HoodSetAbsWhileHeld(Hood.STARTING_POSITION));
        }
}
