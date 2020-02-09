/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.mayheminc.robot2020.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;

import org.mayheminc.robot2020.Constants;
import org.mayheminc.util.MayhemTalonSRX;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Climber extends SubsystemBase {
  private final MayhemTalonSRX winchLeft = new MayhemTalonSRX(Constants.Talon.CLIMBER_WINCH_LEFT);
  private final MayhemTalonSRX winchRight = new MayhemTalonSRX(Constants.Talon.CLIMBER_WINCH_RIGHT);
  private final MayhemTalonSRX walkerLeft = new MayhemTalonSRX(Constants.Talon.CLIMBER_WALKER_LEFT);
  private final MayhemTalonSRX walkerRight = new MayhemTalonSRX(Constants.Talon.CLIMBER_WALKER_RIGHT);

  // private final Solenoid pistons = new
  // Solenoid(Constants.Solenoid.CLIMBER_PISTONS);

  /**
   * Creates a new Climber.
   */
  public Climber() {
    winchLeft.setNeutralMode(NeutralMode.Coast);
    winchLeft.configNominalOutputVoltage(+0.0f, -0.0f);
    winchLeft.configPeakOutputVoltage(+12.0, -12.0);

    winchRight.setNeutralMode(NeutralMode.Coast);
    winchRight.configNominalOutputVoltage(+0.0f, -0.0f);
    winchRight.configPeakOutputVoltage(+12.0, -12.0);

    walkerRight.setNeutralMode(NeutralMode.Coast);
    walkerRight.configNominalOutputVoltage(+0.0f, -0.0f);
    walkerRight.configPeakOutputVoltage(+12.0, -12.0);

    walkerLeft.setNeutralMode(NeutralMode.Coast);
    walkerLeft.configNominalOutputVoltage(+0.0f, -0.0f);
    walkerLeft.configPeakOutputVoltage(+12.0, -12.0);

  }

  public void zero() {
    winchLeft.setSelectedSensorPosition(0);
    winchRight.setSelectedSensorPosition(0);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    // SmartDashboard.putNumber("Climber Winch Left", winchLeft.getPosition());
    // SmartDashboard.putNumber("Climber Winch Right", winchRight.getPosition());
    // SmartDashboard.putNumber("Climber Walker Left", walkerLeft.getPosition());
    // SmartDashboard.putNumber("Climber Walker Right", walkerRight.getPosition());
    // SmartDashboard.putBoolean("Climber Pistons", pistons.get());
  }

  public void setWinchLeftSpeed(double power) {
    winchLeft.set(ControlMode.PercentOutput, power);
  }

  public void setWinchRightSpeed(double power) {
    winchRight.set(ControlMode.PercentOutput, power);
  }

  public void setWalkerLeftSpeed(double power) {
    walkerLeft.set(ControlMode.Velocity, power);
  }

  public void setWalkerRightSpeed(double power) {
    walkerRight.set(ControlMode.Velocity, power);
  }

  public void setPistons(boolean b) {
    // pistons.set(b);
  }

}