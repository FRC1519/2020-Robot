/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.mayheminc.robot2020.commands;

import org.mayheminc.robot2020.RobotContainer;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class ShooterWheelSetToTarget extends CommandBase {
    double m_rpm;
    boolean m_waitForDone;

    public ShooterWheelSetToTarget() {
        this(false);
    }

    /**
     * Creates a new ShooterWheelSet
     */
    public ShooterWheelSetToTarget(boolean wait) {
        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(RobotContainer.shooterWheel);

        m_rpm = 3000; // default setting
        m_waitForDone = wait;
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        m_rpm = RobotContainer.targeting.getDesiredWheelSpeed();
        RobotContainer.shooterWheel.setSpeed(m_rpm);
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        if (m_waitForDone) {
            return (Math.abs(m_rpm - RobotContainer.shooterWheel.getSpeed()) < 100);
        } else {
            return true;
        }
    }
}
