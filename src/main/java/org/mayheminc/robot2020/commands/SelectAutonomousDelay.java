
package org.mayheminc.robot2020.commands;

import org.mayheminc.robot2020.subsystems.Autonomous;

/**
 *
 * @author Team1519
 */
public class SelectAutonomousDelay extends RobotDisabledCommand {

    private int delta;
    private Autonomous auto;

    public SelectAutonomousDelay(Autonomous auto, int delta) {
        addRequirements(auto);
        this.auto = auto;
        this.delta = delta;
    }

    // Called just before this Command runs the first time
    public void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    public void execute() {
        auto.adjustDelay(delta);
    }

    // Make this return true when this Command no longer needs to run execute()
    public boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    public void end(boolean interrupted) {
    }

}
