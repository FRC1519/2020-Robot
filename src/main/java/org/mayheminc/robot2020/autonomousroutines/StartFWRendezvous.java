/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.mayheminc.robot2020.autonomousroutines;

import org.mayheminc.robot2020.RobotContainer;
import org.mayheminc.robot2020.commands.*;
import org.mayheminc.robot2020.commands.DriveStraightOnHeading.DistanceUnits;
import org.mayheminc.robot2020.subsystems.Hood;
import org.mayheminc.robot2020.subsystems.ShooterWheel;
import org.mayheminc.robot2020.subsystems.Turret;

import edu.wpi.first.wpilibj2.command.*;

public class StartFWRendezvous extends SequentialCommandGroup {
        /**
         * Add your docs here.
         */
        public StartFWRendezvous() {

                // start out facing in the normal direction
                addCommands(new DriveZeroGyro(0.0));

                // shoot the 3 balls we started with
                // first, lower the intake, start the shooter wheel, and wait until the turret
                // is turned towards the target
                addCommands(new ParallelCommandGroup( // run the following commands in parallel:
                                new IntakeSetPosition(RobotContainer.intake.PIVOT_DOWN),
                                new ShooterWheelSet(ShooterWheel.SHOOTER_WHEEL_INITIATION_LINE_SPEED),
                                new HoodSetAbs(Hood.INITIATION_LINE_POSITION),
                                new TurretSetAbs((10.0 * Turret.TICKS_PER_DEGREE), Turret.WAIT_FOR_DONE)));

                addCommands(new ShooterReadyAimFire(1.0));

                // drive a little bit out to get away from other robots.
                addCommands(new DriveStraightOnHeading(-0.2, DistanceUnits.INCHES, 36, 30));

                // now that we are clear of other robots, lower the intake while backing up
                // further
                addCommands(new IntakeSetPosition(RobotContainer.intake.PIVOT_DOWN));

                // rais the hood a little to shoot from this increased distance
                addCommands(new HoodSetAbsWhileHeld((Hood.INITIATION_LINE_POSITION + Hood.TRENCH_MID_POSITION) / 2.0));
                addCommands(new DriveStraightOnHeading(-0.3, DistanceUnits.INCHES, 144, 50));

                // turn on the intake to pick up balls
                addCommands(new IntakeSetRollers(-1.0));
                addCommands(new DriveStraightOnHeading(0.3, DistanceUnits.INCHES, 84, 30));

                // now driven to get the balls, stay here and shoot them
                addCommands(new Wait(0.8), // wait for the balls to get into robot
                                new IntakeSetRollers(0)); // turn off the intake

                // in shooting position, prepare everything for shooting
                addCommands(new ParallelCommandGroup( // run the following commands in parallel:
                                new ShooterWheelSet(ShooterWheel.SHOOTER_WHEEL_TRENCH_FRONT_SPEED),
                                new TurretSetAbs((-40.0 * Turret.TICKS_PER_DEGREE), Turret.WAIT_FOR_DONE)));

                // turn on the intake gently while shooting to help balls settle
                addCommands(new IntakeSetRollers(-0.2));
                addCommands(new ShooterReadyAimFire(5.0));

                // turn the shooter wheel and intake off now that the shooting is all done
                addCommands(new ShooterWheelSet(0.0));
                addCommands(new IntakeSetRollers(0.0));

                // turn the wheel off now that the shooting is all done
                addCommands(new HoodSetAbsWhileHeld(Hood.TARGET_ZONE_POSITION));
        }
}
