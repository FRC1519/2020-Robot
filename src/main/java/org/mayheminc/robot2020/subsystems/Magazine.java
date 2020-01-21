/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.mayheminc.robot2020.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import org.mayheminc.robot2020.Constants;
import org.mayheminc.util.MayhemTalonSRX;

public class Magazine extends SubsystemBase {
  private final MayhemTalonSRX turntableTalon = new MayhemTalonSRX(Constants.Talon.MAGAZINE_TURNTABLE);

  /**
   * Creates a new Magazine.
   */
  public Magazine() {
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void setTurntableSpeed(boolean b) {

    if (b) {
      turntableTalon.set(1);
    } else {
      turntableTalon.set(0);
    }

  };

}
