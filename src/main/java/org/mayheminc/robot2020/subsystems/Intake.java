/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.mayheminc.robot2020.subsystems;

import org.mayheminc.robot2020.Constants;

import org.mayheminc.util.MayhemTalonSRX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Intake extends SubsystemBase {

  private final MayhemTalonSRX rollerTalon = new MayhemTalonSRX(Constants.Talon.INTAKE_ROLLERS);
  private final MayhemTalonSRX extenderTalon = new MayhemTalonSRX(Constants.Talon.INTAKE_EXTENDER);

  /**
   * Creates a new Intake.
   */
  public Intake() {

  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
