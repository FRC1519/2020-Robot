/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.mayheminc.robot2020.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;

import org.mayheminc.robot2020.Constants;
import org.mayheminc.util.MayhemTalonSRX;
import org.mayheminc.util.MayhemTalonSRX.CurrentLimit;

public class Revolver extends SubsystemBase {
  private final MayhemTalonSRX revolverTalon = new MayhemTalonSRX(Constants.Talon.REVOLVER_TURNTABLE,
      CurrentLimit.LOW_CURRENT);

  /**
   * Creates a new Revolver.
   */
  public Revolver() {
    ConfigureTalon(revolverTalon);
    revolverTalon.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);
  }

  private void ConfigureTalon(MayhemTalonSRX talon) {
    talon.setNeutralMode(NeutralMode.Coast);
    talon.configNominalOutputVoltage(+0.0f, -0.0f);
    talon.configPeakOutputVoltage(+12.0, -12.0);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    updateSmartDashboard();
  }

  void updateSmartDashboard() {
    SmartDashboard.putNumber("Revolver Speed", revolverTalon.getSpeed());
  }

  public void setRevolverSpeed(double speed) {
    revolverTalon.set(ControlMode.PercentOutput, speed);
  }

}
