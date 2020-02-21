/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.mayheminc.robot2020.subsystems;

import org.mayheminc.robot2020.RobotContainer;

// import org.mayheminc.robot2019.Robot;

// import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
// import sun.net.www.content.text.plain;

/**
 * Add your docs here.
 */

public class Targeting extends SubsystemBase {

  // Logitech C920 Field of View Information:
  // Diagonal FOV = 78.0
  // Horizontal FOV = 70.42
  // Vertical FOV = 43.3
  private final static double FOV_HORIZ_CAMERA_IN_DEGREES = 78.0;

  // Define the "target location" to be halfway from left to right
  private final double CENTER_OF_TARGET_X = 0.5;

  // Calculate ticks per degree.
  // encoder ticks * turret pulley teeth / drive pulley teeth / 360 degrees
  private final double TICKS_PER_DEGREE = (4096.0 * 225.0 / 18.0 / 360.0); // was 6300 / 45

  private static final double SPEED_EQ_M = -4.115;
  private static final double SPEED_EQ_B = 2.244;

  // After computing a desired azimuth, add a "fudge" offset to correct
  // empirically measured error. Offset should be in azimuth "ticks."
  private static final double AZIMUTH_CORRECTION_OFFSET = 0.0; // was -2.0 at CMP

  private double m_desiredAzimuth;
  private double[] m_target_array;
  private int m_priorFrameCount;
  private double m_priorFrameTime;
  private double[] ARRAY_OF_NEG_ONE = { -1.0 };

  private double m_bestY = 0.0;
  private double m_bestX = 0.0;
  private double m_tilt = 0.0;
  private double m_area = 0.0;

  public enum TargetPosition {
    LEFT_MOST, CENTER_MOST, RIGHT_MOST, CENTER_OF_RIGHT_CARGO_SHIP, CENTER_OF_LEFT_CARGO_SHIP
  };

  public enum TargetHeight {
    CARGO, HATCH
  };

  @Override
  public void periodic() {
    update();
  }

  // TODO: make an updateSmartDashboard() method in Targeting for optimization
  public void update() {
    // perform periodic update functions for the targeting capability
    int latestFrameCount = (int) SmartDashboard.getNumber("frameCount", -1.0 /* default to -1 */);
    if (latestFrameCount < 0) {
      // an invalid number for latestFrameCount, display warning light
      SmartDashboard.putBoolean("visionOK", false);
      SmartDashboard.putString("visionOkDebug", "No Data");
    } else if (latestFrameCount == m_priorFrameCount) {
      // have not received a new frame. If more than 1 second has elapsed since
      // prior new frame, display a warning light on the SmartDashboard
      if (Timer.getFPGATimestamp() > m_priorFrameTime + 1.0) {
        SmartDashboard.putBoolean("visionOK", false);
        SmartDashboard.putString("visionOkDebug", "Stale Data");
      } else {
        // else, have an old frame, but it's not too old yet, so do nothing
      }
    } else {
      // have received a new frame, save the time and update m_priorFrameCount
      m_priorFrameTime = Timer.getFPGATimestamp();
      m_priorFrameCount = latestFrameCount;
      SmartDashboard.putBoolean("visionOK", true);
      SmartDashboard.putString("visionOkDebug", "Good Data");
    }

    // Update all of the targeting information, as follows:
    // 1 - Determine if we have any valid data in the array.
    // If not, set the "error" to zero, so that the robot thinks
    // it is on target.
    // 2 - Use the target to compute the needed information

    // get the latest output from the targeting camera
    m_target_array = SmartDashboard.getNumberArray("target", ARRAY_OF_NEG_ONE);

    if (m_target_array == null || m_target_array.length == 0) {
      // this means the key is found, but is empty
      m_bestX = 0.0;
      m_bestY = 0.0;
      m_tilt = 0.0;
      m_area = 0.0;
      m_desiredAzimuth = RobotContainer.shooter.getAzimuthForCapturedImage();
    } else if (m_target_array[0] < 0.0) {
      // this means the array has no valid data. Set m_xError = 0.0
      m_bestX = 0.0;
      m_bestY = 0.0;
      m_tilt = 0.0;
      m_area = 0.0;
      m_desiredAzimuth = RobotContainer.shooter.getAzimuthForCapturedImage();
    } else {
      // We have a valid data array.
      // There are three different situations:
      // a - We want the left-most target
      // b - We want the "centered" target
      // c - We want the right-most target

      // Handle each of them separately;
      // we need the results in "bestXError" and "bestY"
      m_bestX = m_target_array[0]; // get the x-value
      m_bestY = m_target_array[1]; // get the y-value
      m_tilt = m_target_array[2];
      m_area = m_target_array[3];

      m_desiredAzimuth = findDesiredAzimuth(m_bestX, m_bestY, m_tilt, m_area);
    }

    // at this point in the code, the "selected" target should be in the "best"
    SmartDashboard.putNumber("m_bestX", m_bestX);
    SmartDashboard.putNumber("m_bestY", m_bestY);
    SmartDashboard.putNumber("m_tilt", m_tilt);
    SmartDashboard.putNumber("m_area", m_area);
  }

  public double getDesiredAzimuth() {
    return m_desiredAzimuth + AZIMUTH_CORRECTION_OFFSET;
  }

  public double getRecommendedSpeed() {
    // Returns a speed based upon the Y value
    double speed;

    if (m_bestY < 0.1) {
      // can't see the target, set speed to something real slow
      speed = 0.2;
    } else {

      // use the equation to determine the speed from m_bestY
      speed = (SPEED_EQ_M * m_bestY) + SPEED_EQ_B;

      // enforce min speed of 0.30 and max speed of 0.90
      if (speed < 0.30) {
        speed = 0.30;
      } else if (speed > 0.90) {
        speed = 0.90;
      }
    }

    return speed;
  }

  /**
   * Return the desired turrent encoder ticks in the turret for the center of the
   * target.
   * 
   * @param X
   * @param Y
   * @param tilt
   * @param area
   * @return
   */
  public double findDesiredAzimuth(double X, double Y, double tilt, double area) {
    // Calulate angle error based on an X,Y
    double angleError;
    double ticksError;
    double xError;
    double desiredAzimuth;

    // compute the "x error" based upon the center for shooting
    xError = X - CENTER_OF_TARGET_X;
    // Find the angle error
    angleError = FOV_HORIZ_CAMERA_IN_DEGREES * xError;
    // convert the angle error into ticks
    ticksError = angleError * TICKS_PER_DEGREE;

    // Convert angleError into a desired azimuth, using the azimuth history
    desiredAzimuth = ticksError + RobotContainer.shooter.getAzimuthForCapturedImage();
    // Update SmartDashboard
    SmartDashboard.putNumber("Vision Angle Error", angleError);
    SmartDashboard.putNumber("Vision Desired Azimuth", desiredAzimuth);
    return desiredAzimuth;
  }

}
