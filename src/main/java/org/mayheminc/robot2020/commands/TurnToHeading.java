/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.mayheminc.robot2020.commands;

import org.mayheminc.robot2020.Constants;
import org.mayheminc.robot2020.RobotContainer;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class TurnToHeading extends CommandBase {
  double distanceFromPoint;
  double speed;
  double exitHeading;
  double initialHeading;
  Direction direction;
  int turnPhase;

  double ratio;

  public enum Direction {
    LEFT, RIGHT
  }

  /**
   * Creates a new TurnToHeading.
   */
  public TurnToHeading(double distanceFromPoint, double speed, double exitHeading, Direction direction) {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(RobotContainer.drive);

    this.turnPhase = 0;
    this.distanceFromPoint = distanceFromPoint;
    this.speed = speed;
    this.exitHeading = exitHeading;
    this.direction = direction;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    this.initialHeading = RobotContainer.drive.getHeading();
    this.turnPhase = 0;

    this.ratio = getRatio();

    if ((speed > 0 && direction == Direction.LEFT) || (speed < 0 && direction == Direction.RIGHT)) {
      RobotContainer.drive.tankDrive(speed * ratio, speed);
    } else if ((speed > 0 && direction == Direction.RIGHT) || (speed < 0 && direction == Direction.LEFT)) {
      RobotContainer.drive.tankDrive(speed, speed * ratio);
    }
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
  }

  public double getRatio() {
    return distanceFromPoint / (distanceFromPoint + Constants.Drive.DRIVEBASE_WIDTH)
        / Constants.Drive.TURNING_CORRECTION;
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    RobotContainer.drive.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if (direction == Direction.LEFT) {
      if (initialHeading < exitHeading) {
        if (turnPhase == 0) {
          if (RobotContainer.drive.getHeading() > (initialHeading + 25)) {
            turnPhase = 1;
          }
          return false;
        } else { // turnPhase == 1
          return (RobotContainer.drive.getHeading() < exitHeading);
        }
      } else {
        return (RobotContainer.drive.getHeading() < exitHeading
            || RobotContainer.drive.getHeading() > (initialHeading + 25));
      }
    } else {// direction == right
      if (initialHeading > exitHeading) {
        if (turnPhase == 0) {
          if (RobotContainer.drive.getHeading() < (initialHeading - 25)) {
            turnPhase = 1;
          }
          return false;
        } else { // turnPhase == 1
          return (RobotContainer.drive.getHeading() > exitHeading);
        }
      } else {
        return (RobotContainer.drive.getHeading() > exitHeading
            || RobotContainer.drive.getHeading() < (initialHeading - 25));
      }
    }
  }
}
