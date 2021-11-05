/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.mayheminc.robot2020.commands;

import org.mayheminc.robot2020.subsystems.Hood;
import org.mayheminc.robot2020.subsystems.Intake;
import org.mayheminc.robot2020.subsystems.ShooterWheel;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class ShooterCloseFiringSequence extends SequentialCommandGroup {
    /**
     * Creates a new ShooterReadyAimFire.
     */
    public ShooterCloseFiringSequence(double waitDuration) {
        // Add your commands in the super() call, e.g.
        // super(new FooCommand(), new BarCommand());
        super();

        // Prepare for shooting.
        addCommands(new IntakeSetPosition(Intake.PIVOT_SHOOTING)); // move intake to "shooting position"

        addCommands( //
                // new ParallelCommandGroup( // prepare for shooting,
                new AirCompressorPause() // turn off compressor while actively shooting,
        // , new ShooterAimToTarget() // and aim at the target (azimuth and elevation).
        // )
        );

        // no aiming when up close; just turn on the shooter wheels and raise the hood
        addCommands(new ShooterWheelSet(ShooterWheel.CLOSE_SHOOTING_SPEED, true), // shooter wheel manual speed
                new HoodSetAbs(Hood.CLOSE_SHOOTING_POSITION));

        // turn on the feeder, wait 0.1, turn on the Chimney, wait 0.1, turn on the
        // revolver turntable, shoot for specified duration.
        // TODO: should really shoot until no balls detected any more
        addCommands(new ParallelRaceGroup( //
                new FeederSet(1.0), //
                new SequentialCommandGroup(new Wait(0.1), new ChimneySet(1.0)), //
                new SequentialCommandGroup(new Wait(0.2), new RevolverSetTurntable(1.0)), new Wait(waitDuration)));

        // turn off the feeder, chimney, and revolver, ending after 0.1 seconds
        addCommands(new ShooterCeaseFire());
    }
}
