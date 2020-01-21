
package org.mayheminc.robot2020.commands;

import org.mayheminc.robot2020.subsystems.Autonomous;

/**
 *
 * @author Team1519
 */
public class SelectAutonomousProgram extends RobotDisabledCommand {

    private int m_delta;
    private Autonomous auto;

    public SelectAutonomousProgram(Autonomous auto, int delta) {
        addRequirements(auto);
        this.auto = auto;
        m_delta = delta;
    }

    // Make this return true when this Command no longer needs to run execute()
    public boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    public void end(boolean interrupted) {
        this.auto.adjustProgramNumber(m_delta);
    }
}
