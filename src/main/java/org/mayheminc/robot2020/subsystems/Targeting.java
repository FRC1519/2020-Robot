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
  // NOTE: 76.5 horizontal FOV determined empirically by Ken on 2/22/2020
  private final static double FOV_HORIZ_CAMERA_IN_DEGREES = 76.5;

  // Define the "target location" to be halfway from left to right
  private final double CENTER_OF_TARGET_X = 0.475;

  private final double BEST_Y_CLOSE_THRESHOLD = 0.1;
  private final double CLOSE_WHEEL_SPEED = 3000.0;
  private final double CLOSE_HOOD_ANGLE = 30000.0;

  // Calculate ticks per degree.
  // encoder ticks * turret pulley teeth / drive pulley teeth / 360 degrees
  private final double TICKS_PER_DEGREE = (4096.0 * 225.0 / 18.0 / 360.0); // was 6300 / 45

  // After computing a desired azimuth, add a "fudge" offset to correct
  // empirically measured error. Offset should be in azimuth "ticks."
  private static final double AZIMUTH_CORRECTION_OFFSET = 0.0; // was -2.0 at CMP

  private double m_desiredAzimuth;
  private double m_desiredHood;
  private double m_desiredWheelSpeed;
  private double[] m_target_array;
  private int m_priorFrameCount;
  private double m_priorFrameTime;
  private final double[] ARRAY_OF_NEG_ONE = { -1.0 };

  private double m_bestY = 0.0;
  private double m_bestX = 0.0;
  private double m_tilt = 0.0;
  private double m_area = 0.0;

  @Override
  public void periodic() {
    update();
  }

  // TODO: make an updateSmartDashboard() method in Targeting for optimization
  public void update() {
    // get the latest output from the targeting camera
    m_target_array = SmartDashboard.getNumberArray("target", ARRAY_OF_NEG_ONE);

    // Update all of the targeting information, as follows:
    // 1 - Determine if we have any valid data in the array.
    // If not, set the "error" to zero, so that the robot thinks
    // it is on target.
    // 2 - Use the target to compute the needed information

    if (m_target_array == null || m_target_array.length == 0) {
      // This means that the key was set, but to an empty array
      SmartDashboard.putBoolean("visionOK", false);
      SmartDashboard.putString("visionOkDebug", "Bad Data");
      m_bestX = 0.0;
      m_bestY = 0.0;
      m_tilt = 0.0;
      m_area = 0.0;
      // m_desiredAzimuth = RobotContainer.turret.getAzimuthForCapturedImage();
    } else if (m_target_array[0] < 0.0) {
      // This means the array was not retrieved (and the default value was returned)
      // Display warning light
      SmartDashboard.putBoolean("visionOK", false);
      SmartDashboard.putString("visionOkDebug", "No Data");
      m_bestX = 0.0;
      m_bestY = 0.0;
      m_tilt = 0.0;
      m_area = 0.0;
      // m_desiredAzimuth = RobotContainer.turret.getAzimuthForCapturedImage();
    } else {
      // We have a valid data array.
      final int latestFrameCount = (int) m_target_array[0];
      
      // perform periodic update functions for the targeting capability
      if (latestFrameCount == m_priorFrameCount) {
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
      
      m_bestX = m_target_array[1]; // get the x-value
      m_bestY = m_target_array[2]; // get the y-value
      m_tilt = m_target_array[3];
      m_area = m_target_array[4];

      m_desiredAzimuth = findDesiredAzimuth(m_bestX, m_bestY, m_tilt, m_area);
      m_desiredHood = getHoodFromY();
      m_desiredWheelSpeed = getWheelSpeedFromY();
    }

    // at this point in the code, the "selected" target should be in the "best"
    SmartDashboard.putNumber("m_bestX", m_bestX);
    SmartDashboard.putNumber("m_bestY", m_bestY);
    SmartDashboard.putNumber("m_tilt", m_tilt);
    SmartDashboard.putNumber("m_area", m_area);

    SmartDashboard.putNumber("Range per Y", this.getRangeFromY());
    SmartDashboard.putNumber("Range per Area", this.getRangeFromArea());

    SmartDashboard.putNumber("Wheel Speed From Y", this.getWheelSpeedFromY());
    SmartDashboard.putNumber("Hood Angle From Y", this.getHoodFromY());
  }

  public double getDesiredAzimuth() {
    return m_desiredAzimuth + AZIMUTH_CORRECTION_OFFSET;
  }

  public double getDesiredHood() {
    return m_desiredHood;
  }

  public double getDesiredWheelSpeed() {
    return m_desiredWheelSpeed;
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
    desiredAzimuth = ticksError + RobotContainer.turret.getAzimuthForCapturedImage();
    // Update SmartDashboard
    SmartDashboard.putNumber("Vision Angle Error", angleError);
    SmartDashboard.putNumber("Vision Desired Azimuth", desiredAzimuth);
    return desiredAzimuth;
  }

  /**
   * use m_bestY to get the desired hood setting for the target
   * 
   * @return
   */
  private double getHoodFromY() {
    // below is the "curve fit" for the "long shot"

    if (m_bestY < BEST_Y_CLOSE_THRESHOLD) {
      // too close for the lob shot, switch to the bullet shot
      return CLOSE_HOOD_ANGLE;
    } else {
      return 105874 + -407324 * m_bestY + 881911 * m_bestY * m_bestY + -506286 * m_bestY * m_bestY * m_bestY;
    }
  }

  /**
   * use m_bestY to get the desired wheel speed for the target
   * 
   * @return
   */
  private double getWheelSpeedFromY() {
    double computedWheelSpeed = 2618 + -1939 * m_bestY + 3583 * m_bestY * m_bestY;

    if (m_bestY < BEST_Y_CLOSE_THRESHOLD) {
      // too close for the lob shot, switch to the bullet shot
      computedWheelSpeed = CLOSE_WHEEL_SPEED;
    }

    if (computedWheelSpeed < ShooterWheel.IDLE_SPEED) {
      computedWheelSpeed = ShooterWheel.IDLE_SPEED;
    } else if (computedWheelSpeed > ShooterWheel.MAX_SPEED_RPM) {
      computedWheelSpeed = ShooterWheel.MAX_SPEED_RPM;
    }
    return computedWheelSpeed;
  }

  /**
   * use m_bestY to get the range in feet to the target.
   * 
   * @return
   */
  private double getRangeFromY() {
    return 8.11 + -9.17 * m_bestY + 33.9 * m_bestY * m_bestY;
  }

  /**
   * Use the area to calculate the range in feet.
   * 
   * @return
   */
  private double getRangeFromArea() {
    return 0.912 * Math.pow(m_area, -0.695);
  }

}
