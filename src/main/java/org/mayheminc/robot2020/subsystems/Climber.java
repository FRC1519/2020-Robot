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

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Climber extends SubsystemBase {
  private final MayhemTalonSRX winchLeft = new MayhemTalonSRX(Constants.Talon.CLIMBER_WINCH_LEFT);
  private final MayhemTalonSRX winchRight = new MayhemTalonSRX(Constants.Talon.CLIMBER_WINCH_RIGHT);
  private final MayhemTalonSRX walkerLeft = new MayhemTalonSRX(Constants.Talon.CLIMBER_WALKER_LEFT);
  private final MayhemTalonSRX walkerRight = new MayhemTalonSRX(Constants.Talon.CLIMBER_WALKER_RIGHT);

  /**
   * Creates a new Climber.
   */
  public Climber() {
    winchLeft.setNeutralMode(NeutralMode.Brake);
    winchLeft.configNominalOutputVoltage(+0.0f, -0.0f);
    winchLeft.configPeakOutputVoltage(+12.0, -12.0);

    winchRight.setNeutralMode(NeutralMode.Brake);
    winchRight.configNominalOutputVoltage(+0.0f, -0.0f);
    winchRight.configPeakOutputVoltage(+12.0, -12.0);

    walkerRight.setNeutralMode(NeutralMode.Brake);
    walkerRight.configNominalOutputVoltage(+0.0f, -0.0f);
    walkerRight.configPeakOutputVoltage(+12.0, -12.0);

    walkerLeft.setNeutralMode(NeutralMode.Brake);
    walkerLeft.configNominalOutputVoltage(+0.0f, -0.0f);
    walkerLeft.configPeakOutputVoltage(+12.0, -12.0);

  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void setWinchLeftSpeed(double power) {
    winchLeft.set(ControlMode.Velocity, power);
  }

  public void setWinchRightSpeed(double power) {
    winchRight.set(ControlMode.Velocity, power);
  }

  public void setWalkerLeftSpeed(double power) {
    walkerLeft.set(ControlMode.Velocity, power);
  }

  public void setWalkerRightSpeed(double power) {
    walkerRight.set(ControlMode.Velocity, power);
  }

}