/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.mayheminc.robot2020.commands;

import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class ShooterFiringSequence extends SequentialCommandGroup {
  /**
   * Creates a new ShooterReadyAimFire.
   */
  public ShooterFiringSequence(double waitDuration) {
    // Add your commands in the super() call, e.g.
    // super(new FooCommand(), new BarCommand());
    super();

    // Turn off compressor while actively shooting.
    addCommands(new AirCompressorPause());

    // one last aim at the target before starting shooting.
    addCommands(new ShooterAimToTarget());

    addCommands(new ParallelRaceGroup(new ShooterWheelSetToTarget(true), new TurretAimToTargetContinuously()));

    // turn on the feeder, wait 0.1, turn on the Chimney, wait 0.1, turn on the
    // revolver turntable, shoot for specified duration
    // TODO: should really shoot until no balls detected any more
    addCommands(new ParallelRaceGroup( //
        new TurretAimToTargetContinuously(), // continue aiming while shooting
        new FeederSet(1.0), new SequentialCommandGroup(new Wait(0.1), new ChimneySet(1.0)),
        new SequentialCommandGroup(new Wait(0.2), new RevolverSetTurntable(0.3)), new Wait(waitDuration)));

    // turn off the feeder, chimney, and revolver, ending after 0.1 seconds
    addCommands(new ShooterCeaseFire());
  }
}
