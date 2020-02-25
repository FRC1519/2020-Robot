/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.mayheminc.robot2020.commands;

import org.mayheminc.robot2020.subsystems.Shooter;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class ShooterReadyAimFire extends SequentialCommandGroup {
  /**
   * Creates a new ShooterReadyAimFire.
   */
  public ShooterReadyAimFire(double waitDuration) {
    // Add your commands in the super() call, e.g.
    // super(new FooCommand(), new BarCommand());
    super();

    addCommands(new ShooterSetHoodAbs(Shooter.HOOD_INITIATION_LINE_POSITION));
    // addCommands(new ShooterSetWheel(3000.0, true));
    
    // addCommands(new Wait(3.0));

    // TODO:  Add compressor control so that compressor is turned off while actively shooting.
    
    // aim to the target until we are on target.
    addCommands(
      new ParallelRaceGroup( 
          new ParallelCommandGroup(new TargetingIsOnTarget(), new ShooterSetWheel(3000.0, true)), 
          new TurretAimToTarget()));

    // turn on the feeder, wiat 0.1, turn on the Chimney, wait 0.1, turn on the magazine, shoot for about 4 seconds
    addCommands(new ParallelRaceGroup(
                    new ShooterSetFeeder(1.0),
                    new SequentialCommandGroup(new Wait(0.1), new ChimneySetChimney(0.5)),
                    new SequentialCommandGroup(new Wait(0.2), new MagazineSetTurntable(0.3)),
                    new Wait(waitDuration)));

    addCommands(new ParallelRaceGroup(
      new MagazineSetTurntable(0.0),
      new ChimneySetChimney(0.0),
      new ShooterSetFeeder(0.0),
      new Wait(0.1)));
  }
}
