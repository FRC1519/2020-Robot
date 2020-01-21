package org.mayheminc.robot2020.commands;

import org.mayheminc.robot2020.subsystems.Drive;

// import org.mayheminc.robot2019.Robot;

// import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveZeroGyro extends RobotDisabledCommand {
    Drive m_drive;

    public DriveZeroGyro(Drive drive) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        addRequirements(drive);
        m_drive = drive;
    }

    // Called just before this Command runs the first time
    public void initialize() {
        m_drive.zeroHeadingGyro(0.0);
    }

    // Called repeatedly when this Command is scheduled to run
    public void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    public boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
