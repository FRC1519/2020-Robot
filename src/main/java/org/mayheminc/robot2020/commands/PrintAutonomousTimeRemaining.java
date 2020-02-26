package org.mayheminc.robot2020.commands;

// import org.mayheminc.robot2020.Robot;

import edu.wpi.first.wpilibj2.command.CommandBase;

/**
 *
 */
public class PrintAutonomousTimeRemaining extends CommandBase {
	String Message = ""; 
    public PrintAutonomousTimeRemaining(String msg) {
        this.Message = msg;
    }
    
    // Called just before this Command runs the first time
    @Override
    public void initialize() {
		// DriverStation.reportError(Message + " At: " + Robot.autonomousTimeRemaining() + "\n", false);
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    public boolean isFinished() {
        return true;
    }
}
