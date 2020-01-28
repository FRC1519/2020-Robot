/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.mayheminc.robot2020.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;

import org.mayheminc.robot2020.Constants;
import org.mayheminc.util.MayhemFakeTalonSRX;
import org.mayheminc.util.MayhemTalonSRX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Intake extends SubsystemBase {

  private final MayhemFakeTalonSRX rollerTalon = new MayhemFakeTalonSRX(Constants.Talon.INTAKE_ROLLERS);
  private final MayhemTalonSRX extenderTalon = new MayhemTalonSRX(Constants.Talon.INTAKE_EXTENDER);

  /**
   * Creates a new Intake.
   */
  public Intake() {
    rollerTalon.setNeutralMode(NeutralMode.Coast);
    rollerTalon.configNominalOutputVoltage(+0.0f, -0.0f);
    rollerTalon.configPeakOutputVoltage(+12.0, -12.0);

    extenderTalon.configNominalOutputVoltage(+0.0f, -0.0f);
    extenderTalon.configPeakOutputVoltage(+12.0, -12.0);
    extenderTalon.setFeedbackDevice(FeedbackDevice.QuadEncoder);
    extenderTalon.config_kP(0, 1.0, 0);
    extenderTalon.config_kI(0, 0.0, 0);
    extenderTalon.config_kD(0, 0.0, 0);
  }

  public void setRollers(double power) {
    rollerTalon.set(ControlMode.PercentOutput, power);

  }

  public void setExtender(boolean b) {
    if (b) {
      extenderTalon.set(ControlMode.Position, 90);

    } else {
      extenderTalon.set(ControlMode.Position, 10);
    }
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putNumber("Intake Position", extenderTalon.get());
    SmartDashboard.putNumber("Intake Rollers", rollerTalon.get());
  }
}
