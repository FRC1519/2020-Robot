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

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Chimney extends SubsystemBase {
  private final MayhemTalonSRX chimneyTalon = new MayhemTalonSRX(Constants.Talon.MAGAZINE_CHIMNEY);

  /**
   * Creates a new Chimney.
   */
  public Chimney() {
    chimneyTalon.setNeutralMode(NeutralMode.Coast);
    chimneyTalon.configNominalOutputVoltage(+0.0f, -0.0f);
    chimneyTalon.configPeakOutputVoltage(+12.0, -12.0);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    updateSmartDashboard();
    monitorTurntableMovement();
  }

  void updateSmartDashboard() {
    SmartDashboard.putNumber("Chimney Turntable", chimneyTalon.getSpeed());
  }

  void monitorTurntableMovement() {
  }

  public void setChimneySpeed(double speed) {
    chimneyTalon.set(ControlMode.PercentOutput, speed);
  }
}
