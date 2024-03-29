/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.mayheminc.robot2020.subsystems;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class AirCompressor extends SubsystemBase {
  Compressor compressor = new Compressor();
  Timer m_Timer = new Timer();

  /**
   * Creates a new compressor.
   */
  public AirCompressor() {
    // turn on the compressor in the constructor
    setCompressor(true);
  }

  final double COMPRESSOR_PAUSE_TIME = 10.0;

  public void setCompressor(boolean b) {
    // b = false;
    if (b) {
      compressor.start();
    } else {
      compressor.stop();
      m_Timer.start();
    }
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run

    // if the timer expires, turn on the compressor.
    if (m_Timer.hasPeriodPassed(COMPRESSOR_PAUSE_TIME)) {
      setCompressor(true);
    }
  }
}
