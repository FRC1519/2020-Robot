/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.mayheminc.robot2020.commands;

import org.mayheminc.robot2020.subsystems.Hood;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class ShooterCeaseFire extends SequentialCommandGroup {
  /**
   * Creates a new ShooterReadyAimFire.
   */
  public ShooterCeaseFire() {
    // Add your commands in the super() call, e.g.
    // super(new FooCommand(), new BarCommand());
    super();

    // turn off the subsystems that were used in shooting
    // (feed roller, chimney, revolver, and shooter wheels)

    addCommands(new ParallelRaceGroup(new FeederSet(0.0), new ChimneySet(0.0), new RevolverSetTurntable(0.0),
        new ShooterWheelSetVBus(0.0), new Wait(0.1)));

    // Lower the hood now that we're done shooting
    addCommands(new HoodSetAbsWhileHeld(Hood.STARTING_POSITION));
  }
}
