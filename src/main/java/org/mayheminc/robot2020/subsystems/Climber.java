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

public class Climber extends SubsystemBase {
  private final MayhemTalonSRX winchLeft = new MayhemTalonSRX(Constants.Talon.CLIMBER_WINCH_LEFT);
  private final MayhemTalonSRX winchRight = new MayhemTalonSRX(Constants.Talon.CLIMBER_WINCH_RIGHT);
  private final MayhemTalonSRX walkerLeft = new MayhemTalonSRX(Constants.Talon.CLIMBER_WALKER_LEFT);
  private final MayhemTalonSRX walkerRight = new MayhemTalonSRX(Constants.Talon.CLIMBER_WALKER_RIGHT);

  /**
   * Creates a new Climber.
   */
  public Climber() {

  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
