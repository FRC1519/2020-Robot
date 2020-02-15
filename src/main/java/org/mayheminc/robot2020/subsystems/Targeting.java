/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.mayheminc.robot2020.subsystems;

import org.mayheminc.robot2020.RobotContainer;

// import org.mayheminc.robot2019.Robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
// import sun.net.www.content.text.plain;

/**
 * Add your docs here.
 */

public class Targeting extends SubsystemBase {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  // COMPETITION ROBOT NEEDS THE ONE BELOW!
  // private double Y_WHEN_TARGET_AT_WALL = 0.65; // Worked fine and 0.70

  // PRACTICE ROBOT NEEDS THE ONE BELOW!
  // private double m_YWhenTargetAtWall= 0.75; // Worked fine and 0.70

  // Y when hatch panel is at wall when the arm is low
  private static final double Y_AT_WALL_SAFETY_MARGIN = 0.05;

  private static final double Y_WHEN_HATCH_LOW_AT_WALL = 0.75 + Y_AT_WALL_SAFETY_MARGIN;
  // Y when hatch panel is at wall when the arm is high
  private static final double Y_WHEN_HATCH_MID_AT_WALL = 0.50 + Y_AT_WALL_SAFETY_MARGIN;
  // Y when hatch panel is at wall when the arm is high
  private static final double Y_WHEN_HATCH_HIGH_AT_WALL = 0.60 + Y_AT_WALL_SAFETY_MARGIN;

  private static final double SPEED_EQ_M = -4.115;
  private static final double SPEED_EQ_B = 2.244;

  // Below values are for centered arm
  // private static final double CENTER_EQ_M = -0.1925;
  // private static final double CENTER_EQ_B = 0.5719;

  // Below values are for arm off 2 inches to left on the practice robot
  // private static final double CENTER_EQ_M_HP = -0.2964;
  // private static final double CENTER_EQ_B_HP = 0.5871;
  // private static final double CENTER_EQ_M_CARGO = -0.6102;
  // private static final double CENTER_EQ_B_CARGO = 0.6334;

  // Below values are for competition robot based upon PineTree data
  private static final double CENTER_EQ_M_HP = -0.2762;
  private static final double CENTER_EQ_B_HP = 0.5563;

  // below values are guesses for competition robot; same as on practice robot
  private static final double CENTER_EQ_M_CARGO = -0.6102;
  private static final double CENTER_EQ_B_CARGO = 0.6334;

  // After computing a desired heading, add a "fudge" offset to correct
  // empirically measured error. Found to be approx -1 degree (to shift aim 1" to
  // the left) during NECMP Thursday AM practice field session, for competition
  // robot.

  // heading correction offset had been 0.0 for PineTree
  // changed to -1.0 for first 6 matches of NECMP
  // changed to 0.0 at lunch time on Friday
  private static final double HEADING_CORRECTION_OFFSET = 0.0; // was -2.0 at CMP

  private double m_desiredAzimuth;
  private double[] m_target_array;
  private int m_priorFrameCount;
  private double m_priorFrameTime;
  private double[] ARRAY_OF_NEG_ONE = { -1.0 };
  private final static double FOV_CAMERA_IN_DEGREES = 78.0;
  private double m_bestY = 0.0;
  private double m_bestX = 0.0;
  private double tilt = 0.0;
  private double area = 0.0;

  public enum TargetPosition {
    LEFT_MOST, CENTER_MOST, RIGHT_MOST, CENTER_OF_RIGHT_CARGO_SHIP, CENTER_OF_LEFT_CARGO_SHIP
  };

  public enum TargetHeight {
    CARGO, HATCH
  };

  private TargetPosition m_mode = TargetPosition.CENTER_MOST;
  // Mode for target height
  private TargetHeight m_TargetHeightMode = TargetHeight.HATCH;

  @Override
  public void periodic() {
    update();
  }

  // TODO: make an updateSmartDashboard() method in Targeting for optimization
  // TODO: clean up the content in Targeting.update() -- just too long!
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

    double[] centerMostTargetArray;
    // Update all of the targeting information, as follows:
    // 1 - Determine if we have any valid data in the array.
    // If not, set the "error" to zero, so that the robot thinks
    // it is on target.
    // 2 - Look through the valid data in the array to find the
    // target closest to the "trueCenter"
    // 3 - Use the selected target to compute the needed information

    // get the latest output from the targeting camera
    m_target_array = SmartDashboard.getNumberArray("target", ARRAY_OF_NEG_ONE);

    if (m_target_array == null || m_target_array.length == 0) {
      // this means the key is found, but is empty
      m_bestX = 0.0;
      m_bestY = 0.0;
      tilt = 0.0;
      area = 0.0;
      m_desiredAzimuth = RobotContainer.shooter.getAzimuthForCapturedImage();
    } else if (m_target_array[0] < 0.0) {
      // this means the array has no valid data. Set m_xError = 0.0
      m_bestX = 0.0;
      m_bestY = 0.0;
      tilt = 0.0;
      area = 0.0;
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
      tilt = m_target_array[2];
      area = m_target_array[3];

      m_desiredAzimuth = findDesiredAzimuth(m_bestX, m_bestY, tilt, area);
    }

    // at this point in the code, the "selected" target should be in the "best"
    SmartDashboard.putNumber("m_bestX", m_bestX);
    SmartDashboard.putNumber("m_bestY", m_bestY);
    SmartDashboard.putNumber("tilt", tilt);
    SmartDashboard.putNumber("area", area);
  }

  public double getDesiredAzimuth() {
    return m_desiredAzimuth + HEADING_CORRECTION_OFFSET;
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

  // public boolean atWall(Autonomous.RocketHeight desiredHeight) {
  // // we are at the wall when the target is lower in the field of view (bigger
  // Y)
  // // than the "at the wall" threshold
  // switch (desiredHeight) {
  // case HIGH:
  // return (m_bestY >= Y_WHEN_HATCH_HIGH_AT_WALL);
  // case MID:
  // return (m_bestY >= Y_WHEN_HATCH_MID_AT_WALL);
  // case LOW:
  // return (m_bestY >= Y_WHEN_HATCH_LOW_AT_WALL);
  // default:
  // return (m_bestY >= Y_WHEN_HATCH_LOW_AT_WALL);
  // }
  // }

  public void setMode(TargetPosition modeToSet) {
    // Set the mode e.g. LEFT_MOST, CENTER_MOST, RIGHT_MOST,
    // CENTER_OF_RIGHT_CARGO_SHIP, CENTER_OF_LEFT_CARGO_SHIP
    m_mode = modeToSet;
  }

  private final double CenterOfTarget_X = 0.5;
  private final double TICK_PER_DEGREE = (6300.0 / 45.0);

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
    double AngleError;
    double ticksError;
    // double TrueCenter;
    double XError;
    double desiredAzimuth;

    // compute the "x error" based upon the trueCenter
    XError = X - CenterOfTarget_X;
    // Find the angle error
    AngleError = FOV_CAMERA_IN_DEGREES * XError;
    ticksError = AngleError * TICK_PER_DEGREE;

    // Convert angleError into a desired heading, using the heading history
    desiredAzimuth = ticksError + RobotContainer.shooter.getAzimuthForCapturedImage();
    // Update SmartDashboard
    SmartDashboard.putNumber("True Angle Error", AngleError);
    SmartDashboard.putNumber("Vision Desired Azimuth", desiredAzimuth);
    return desiredAzimuth;
  }

  public void setTargetHeight(TargetHeight target) {
    m_TargetHeightMode = target;
  }

}
