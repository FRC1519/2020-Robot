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

public class Magazine extends SubsystemBase {
  private final MayhemTalonSRX turntableTalon = new MayhemTalonSRX(Constants.Talon.MAGAZINE_TURNTABLE);

  /**
   * Creates a new Magazine.
   */
  public Magazine() {
    ConfigureTalon(turntableTalon);
    turntableTalon.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);
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
    monitorTurntableMovement();
  }

  void updateSmartDashboard() {
    SmartDashboard.putNumber("Magazine Speed", turntableTalon.getSpeed());
    // SmartDashboard.putNumber("Magazine Speed", turntableTalon.getSpeed());
  }

  void monitorTurntableMovement() {

  }

  public void setTurntableSpeed(double speed) {
    turntableTalon.set(ControlMode.PercentOutput, speed);
  }

}
