/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.mayheminc.robot2020.commands;

import org.mayheminc.robot2020.RobotContainer;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class ShooterWheelSetToTargetContinuously extends CommandBase {
    double m_rpm;
    // boolean m_waitForDone;

    /**
     * Creates a new ShooterWheelSet
     */
    public ShooterWheelSetToTargetContinuously() {
        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(RobotContainer.shooterWheel);

        m_rpm = 3000; // default setting
    }

    // Called when the command is initially scheduled.
    @Override
    public void execute() {
        m_rpm = RobotContainer.targeting.getDesiredWheelSpeed();
        RobotContainer.shooterWheel.setSpeed(m_rpm);
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;
    }
}
