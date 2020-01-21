/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mayheminc.robot2020.subsystems;

import java.util.ArrayList;
import java.util.LinkedList;

import org.mayheminc.robot2020.autonomousroutines.*;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

/**
 *
 * @author Team1519
 */
public class Autonomous extends SubsystemBase {

	private LinkedList<Command> autonomousPrograms = new LinkedList<Command>();

	private int programNumber = 0; // 0 = Do nothing
	private int delay = 0;

	public Autonomous() {
	}

	public void setAutonomousPrograms(LinkedList<Command> programs) {
		autonomousPrograms = programs;
	}

	public Command getSelectedProgram() {
		return autonomousPrograms.get(programNumber);
	}

	public int getDelay() {
		return delay;
	}

	public void adjustProgramNumber(final int delta) {
		programNumber += delta;
		if (programNumber < 0) {
			programNumber = autonomousPrograms.size() - 1;
		} else if (programNumber >= autonomousPrograms.size()) {
			programNumber = 0;
		}
		updateSmartDashboard();
	}

	private final int MAX_DELAY = 9;

	public void adjustDelay(final int delta) {
		delay += delta;
		if (delay < 0) {
			delay = 0;
		} else if (delay > MAX_DELAY) {
			delay = MAX_DELAY;
		}
		updateSmartDashboard();
	}

	private StringBuffer sb = new StringBuffer();

	public void updateSmartDashboard() {
		sb.setLength(0);
		sb.append(programNumber + " " + autonomousPrograms.get(programNumber).getName());
		sb.append("         ");
		// SmartDashboard.putString("Auto Prog", sb.toString());
		// SmartDashboard.putNumber("Auto Delay", delay);
	}

	public String toString() {
		return autonomousPrograms.get(programNumber).getName();
	}
}
