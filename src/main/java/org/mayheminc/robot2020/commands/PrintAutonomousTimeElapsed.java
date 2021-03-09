package org.mayheminc.robot2020.commands;

import edu.wpi.first.wpilibj.DriverStation;

import frc.robot.Robot;

import edu.wpi.first.wpilibj2.command.CommandBase;

/**
 *
 */
public class PrintAutonomousTimeElapsed extends CommandBase {
    String Message = "";

    public PrintAutonomousTimeElapsed(String msg) {
        this.Message = msg;
    }

    // Called just before this Command runs the first time
    @Override
    public void initialize() {
        DriverStation.reportError(Message + " At: " + Robot.autonomousTimeElapsed() + "\n", false);
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    public boolean isFinished() {
        return true;
    }
}
