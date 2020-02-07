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
import org.mayheminc.util.MayhemTalonSRX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Intake extends SubsystemBase {

  private final int PIVOT_CLOSE_ENOUGH = 20;
  private final MayhemTalonSRX rollerTalon = new MayhemTalonSRX(Constants.Talon.INTAKE_ROLLERS);
  private final MayhemTalonSRX extenderTalon = new MayhemTalonSRX(Constants.Talon.INTAKE_EXTENDER);
  private final int PIVOT_ZERO_POSITION = 900;
  public final double PIVOT_UP = 900.0;
  public final double PIVOT_DOWN = 0.0;

  enum PivotMode {
    MANUAL_MODE, PID_MODE,
  };

  PivotMode mode = PivotMode.MANUAL_MODE;
  boolean isMoving;
  double m_targetPosition;

  /**
   * Creates a new Intake.
   */
  public Intake() {
    rollerTalon.setNeutralMode(NeutralMode.Coast);
    rollerTalon.configNominalOutputVoltage(+0.0f, -0.0f);
    rollerTalon.configPeakOutputVoltage(+12.0, -12.0);

    configMotor(extenderTalon);
  }

  void configMotor(MayhemTalonSRX motor) {
    // initial calcs for computing kP...
    // If we want 50% power when at the full extreme,
    // Full extreme is 900 ticks
    // kP = (0.5 * 1023) / 900 = 0.568
    motor.config_kP(0, 0.5, 0); // based upon Ken's initial calcs, above

    // typical value of about 1/100 of kP for starting tuning
    motor.config_kI(0, 0.0, 0);

    // typical value of about 10x to 100x of kP for starting tuning
    motor.config_kD(0, 150.0, 0);
    // motor.config_kD(0, 0.575, 0);

    // practically always set kF to 0 for position control
    // for things like gravity compensation, use the "arbitrary feed forward" that
    // can be specified with the "4-parameter" TalonSRX.set() method
    motor.config_kF(0, 0.0, 0);

    motor.setNeutralMode(NeutralMode.Coast);
    motor.setInverted(false);
    motor.setSensorPhase(true);
    motor.configNominalOutputVoltage(+0.0f, -0.0f);
    motor.configPeakOutputVoltage(+12.0, -12.0);
    motor.configClosedloopRamp(0.05); // limit neutral to full to 0.05 seconds
    motor.configMotionCruiseVelocity(30); // estimate 30 ticks max
    motor.configMotionAcceleration(60); // acceleration of 2x velocity allows cruise to be attained in 1/2
                                        // second
    motor.setFeedbackDevice(FeedbackDevice.QuadEncoder);
  }

  public void zero() {
    extenderTalon.setEncPosition(PIVOT_ZERO_POSITION);
  }

  /**
   * Negative for intake. Positive for spit.
   * 
   * @param power
   */
  public void setRollers(double power) {
    rollerTalon.set(ControlMode.PercentOutput, power);

  }

  public void setExtender(Double b) {
    m_targetPosition = b;
    extenderTalon.set(ControlMode.Position, b);
    mode = PivotMode.PID_MODE;
    isMoving = true;
  }

  public boolean isExtenderAtPosition() {
    return !isMoving;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run

    if (mode == PivotMode.PID_MODE) {

      // if the pivot is close enough, turn off the motor
      if (Math.abs(extenderTalon.getPosition() - m_targetPosition) < PIVOT_CLOSE_ENOUGH) {
        isMoving = false;
        setExtenderVBus(0);
      }
    } else {
    }

    updateSmartDashBoard();
  }

  public void updateSmartDashBoard() {
    SmartDashboard.putNumber("Intake Position", extenderTalon.getPosition());
    SmartDashboard.putNumber("Intake Target", m_targetPosition);

    SmartDashboard.putBoolean("Intake Is Moving", isMoving);
    SmartDashboard.putBoolean("Intake PID Mode", (mode == PivotMode.PID_MODE));
    SmartDashboard.putNumber("Intake Rollers", rollerTalon.get());
  }

  public void setExtenderVBus(double VBus) {
    extenderTalon.set(ControlMode.PercentOutput, VBus);
    mode = PivotMode.MANUAL_MODE;
  }
}
