// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package org.mayheminc.robot2020.autonomousroutines;

import org.mayheminc.robot2020.commands.DriveStraightOnHeading;
import org.mayheminc.robot2020.commands.DriveZeroGyro;
import org.mayheminc.robot2020.commands.ShooterCloseFiringSequence;
import org.mayheminc.robot2020.commands.ShooterWheelSet;
import org.mayheminc.robot2020.subsystems.ShooterWheel;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class RiverRageForwardShoot3 extends SequentialCommandGroup {
  /** Creates a new RiverRageForwardShoot3. */
  public RiverRageForwardShoot3() {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(new DriveZeroGyro(0));

    addCommands(new DriveStraightOnHeading(0.4, 6 * 14, 0));

    addCommands(new ShooterWheelSet(ShooterWheel.CLOSE_SHOOTING_SPEED, true));

    addCommands(new ShooterCloseFiringSequence(15));
  }
}
