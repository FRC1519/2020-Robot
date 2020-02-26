package org.mayheminc.robot2020.subsystems;

import org.mayheminc.robot2020.Constants;

import com.ctre.phoenix.motorcontrol.*;
import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import org.mayheminc.util.History;
import org.mayheminc.util.MayhemTalonSRX;
import org.mayheminc.util.PidTunerObject;

public class Turret extends SubsystemBase implements PidTunerObject {
    private final MayhemTalonSRX turretTalon = new MayhemTalonSRX(Constants.Talon.SHOOTER_TURRET);

    private final int MIN_POSITION = -12500;
    private final int MAX_POSITION = +26000;

    double m_targetSpeedRPM;
    History headingHistory = new History();

    /**
     * Creates a new Turret.
     */
    public Turret() {
        configureTurretTalon();
    }

    public void init() {
        configureTurretTalon();
    }

    void configureTurretTalon() {
        turretTalon.config_kP(0, 1.0, 0);
        turretTalon.config_kI(0, 0.0, 0);
        turretTalon.config_kD(0, 0.0, 0);
        turretTalon.config_kF(0, 0.0, 0);
        turretTalon.changeControlMode(ControlMode.Position);
        turretTalon.setNeutralMode(NeutralMode.Coast);
        turretTalon.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);

        turretTalon.configMotionCruiseVelocity(800); // measured velocity of ~100K
        // at 80%; set cruise to that
        turretTalon.configMotionAcceleration(3200); // acceleration of 4x velocity
        // allows cruise in 1/4 second

        turretTalon.configNominalOutputVoltage(+0.0f, -0.0f);
        turretTalon.configPeakOutputVoltage(+6.0, -6.0);

        turretTalon.configForwardSoftLimitThreshold(MAX_POSITION);
        turretTalon.configForwardSoftLimitEnable(true);
        turretTalon.configReverseSoftLimitThreshold(MIN_POSITION);
        turretTalon.configReverseSoftLimitEnable(true);

        this.setVBus(0.0);
    }

   

    @Override
    public void periodic() {
        // This method will be called once per scheduler run
        UpdateDashboard();
        updateHistory();
    }

    // KBS: tuned below at practice field on 21 Feb 2020 via successive refinement.
    private static final double CAMERA_LAG = 0.08; // .05 was best so far in 2020; used .150 in 2019

    private void updateHistory() {
        double now = Timer.getFPGATimestamp();
        headingHistory.add(now, getPosition());
    }

    public double getAzimuthForCapturedImage() {
        double now = Timer.getFPGATimestamp();
        double indexTime = now - CAMERA_LAG;
        return headingHistory.getAzForTime(indexTime);
    }

    private void UpdateDashboard() {
        SmartDashboard.putNumber("Shooter turret pos", turretTalon.getPosition());
        SmartDashboard.putNumber("Shooter turret vbus", turretTalon.getMotorOutputVoltage());
        SmartDashboard.putNumber("Shooter turret velocity", turretTalon.getSelectedSensorVelocity(0));
    }

    public void zero() {
        turretTalon.setPosition(0);
    }

    /**
     * Set the absolute turret position.
     */
    public void setPositionAbs(double pos) {
        if (pos < MIN_POSITION) {
            pos = MIN_POSITION;
        }
        if (pos > MAX_POSITION) {
            pos = MAX_POSITION;
        }
        turretTalon.set(ControlMode.MotionMagic, pos);
    }

    /**
     * Set the relative turret position
     * 
     * @param pos number of encoder ticks to adjust.
     */
    public void setPositionRel(double pos) {
        setPositionAbs(getPosition() + pos);
    }

    public void setVBus(double power) {
        turretTalon.set(ControlMode.PercentOutput, power);
    }

    /**
     * Get the current position of the turret.
     * 
     * @return
     */
    public double getPosition() {
        return turretTalon.getPosition();
    }


    ////////////////////////////////////////////////////
    // PidTunerObject
    @Override
    public double getP() {
        return turretTalon.getP();
    }

    @Override
    public double getI() {
        return turretTalon.getI();
    }

    @Override
    public double getD() {
        return turretTalon.getD();
    }

    @Override
    public double getF() {
        return turretTalon.getF();

    }

    @Override
    public void setP(double d) {
        turretTalon.config_kP(0, d, 0);
    }

    @Override
    public void setI(double d) {
        turretTalon.config_kI(0, d, 0);
    }

    @Override
    public void setD(double d) {
        turretTalon.config_kD(0, d, 0);
    }

    @Override
    public void setF(double d) {
        turretTalon.config_kF(0, d, 0);
    }

}