/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.mayheminc.robot2020.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Targeting extends SubsystemBase {
  /**
   * Creates a new Targeting.
   */
  public Targeting() {

  }

  public boolean isOnTarget() {
    return true;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public double getBearingToTarget() {
    return 0;
  }

  public double getRangeToTarget() {
    return 0;
  }

  public static double convertRangeToWheelSpeed(double rangeToTarget) {
    return 1000;
  }
}
