/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.mayheminc.robot2020.subsystems;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class AirCompressor extends SubsystemBase {
  Compressor compressor = new Compressor();

  /**
   * Creates a new compressor.
   */
  public AirCompressor() {
    // turn on the compressor in the constructor
    setCompressor(true);
  }

  public void setCompressor(boolean b) {
    // b = false;
    if (b) {
      compressor.start();
    } else {
      compressor.stop();
    }
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
