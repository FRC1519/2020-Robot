package org.mayheminc.robot2020.commands;

import org.mayheminc.robot2020.RobotContainer;

// import org.mayheminc.robot2019.Robot;

// import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveZeroGyro extends RobotDisabledCommand {

    private double m_headingOffset = 0.0;

    public DriveZeroGyro(double headingOffset) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        addRequirements(RobotContainer.drive);

        m_headingOffset = headingOffset;
    }

    // Called just before this Command runs the first time
    public void initialize() {
        RobotContainer.drive.zeroHeadingGyro(m_headingOffset);
    }

    // Make this return true when this Command no longer needs to run execute()
    public boolean isFinished() {
        return true;
    }

}
