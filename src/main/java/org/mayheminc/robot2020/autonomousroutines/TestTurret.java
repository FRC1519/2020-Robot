/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.mayheminc.robot2020.autonomousroutines;

import org.mayheminc.robot2020.RobotContainer;
import org.mayheminc.robot2020.commands.ShooterSetTurretAbs;
import org.mayheminc.robot2020.commands.Wait;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class TestTurret extends SequentialCommandGroup {
  /**
   * Creates a new TestTurret.
   */
  public TestTurret() {
    // Add your commands in the super() call, e.g.
    // super(new FooCommand(), new BarCommand());
    super();

    addRequirements(RobotContainer.shooter);

    addCommands(new ShooterSetTurretAbs(-10));
    addCommands(new Wait(3));
    addCommands(new ShooterSetTurretAbs(0));
    addCommands(new Wait(3));

    addCommands(new ShooterSetTurretAbs(10));
    addCommands(new Wait(3));
    addCommands(new ShooterSetTurretAbs(0));
    addCommands(new Wait(3));

    addCommands(new ShooterSetTurretAbs(-20));
    addCommands(new Wait(3));
    addCommands(new ShooterSetTurretAbs(0));
    addCommands(new Wait(3));

    addCommands(new ShooterSetTurretAbs(20));
    addCommands(new Wait(3));
    addCommands(new ShooterSetTurretAbs(0));
    addCommands(new Wait(3));

    addCommands(new ShooterSetTurretAbs(-45));
    addCommands(new Wait(3));
    addCommands(new ShooterSetTurretAbs(0));
    addCommands(new Wait(3));

    addCommands(new ShooterSetTurretAbs(45));
    addCommands(new Wait(3));
    addCommands(new ShooterSetTurretAbs(0));
    addCommands(new Wait(3));

  }
}
