package org.mayheminc.robot2020.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;

/**
 *
 */
public class Wait extends CommandBase {
    Timer m_Timer = new Timer();
    double m_endTime;

    public Wait() {
        this(0);
    }

    public Wait(double endTime) {
        super();
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        m_endTime = endTime;
    }

    // Called just before this Command runs the first time
    public void initialize() {
        m_Timer.start();
    }

    // Make this return true when this Command no longer needs to run execute()
    public boolean isFinished() {
        return m_Timer.hasPeriodPassed(m_endTime);
    }

}
