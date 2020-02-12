package org.mayheminc.robot2020.subsystems;

import org.mayheminc.robot2020.Constants;
import org.mayheminc.robot2020.RobotContainer;

import com.ctre.phoenix.motorcontrol.*;
import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import org.mayheminc.util.MayhemTalonSRX;
import org.mayheminc.util.PidTuner;
import org.mayheminc.util.PidTunerObject;

public class Shooter extends SubsystemBase implements PidTunerObject {
    private final MayhemTalonSRX shooterWheelTalon = new MayhemTalonSRX(Constants.Talon.SHOOTER_WHEEL);
    private final MayhemTalonSRX turretTalon = new MayhemTalonSRX(Constants.Talon.SHOOTER_TURRET);
    private final MayhemTalonSRX hoodTalon = new MayhemTalonSRX(Constants.Talon.SHOOTER_HOOD);
    private final MayhemTalonSRX feederTalon = new MayhemTalonSRX(Constants.Talon.SHOOTER_FEEDER);

    private final double MAX_SPEED_RPM = 5760.0;
    private final double TALON_TICKS_PER_REV = 2048.0;

    double m_targetSpeedRPM;

    double convertRpmToTicksPer100ms(double rpm) {
        return rpm / 60 * 2048 / 10;
    }

    double convertTicksPer100msToRPM(double ticks) {
        return ticks * 10 / 2048 * 60;
    }

    /**
     * Creates a new Shooter.
     */
    public Shooter() {
        configureTurretTalon();
        configureWheelTalon();
        configureHoodTalon();
        configureFeederTalon();
    }

    public void init() {
        configureTurretTalon();
        configureWheelTalon();
        configureHoodTalon();
        configureFeederTalon();

        setShooterWheelSpeedVBus(0.0);
    }

    private void configureFeederTalon() {
        feederTalon.changeControlMode(ControlMode.PercentOutput);

        feederTalon.configNominalOutputVoltage(+0.0f, -0.0f);
        feederTalon.configPeakOutputVoltage(+6.0, -6.0);
    }

    private void configureHoodTalon() {
        hoodTalon.config_kP(0, 1.0, 0);
        hoodTalon.config_kI(0, 0.0, 0);
        hoodTalon.config_kD(0, 0.0, 0);
        hoodTalon.config_kF(0, 0.0, 0);

        hoodTalon.changeControlMode(ControlMode.Position);
        hoodTalon.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);

        hoodTalon.configNominalOutputVoltage(+0.0f, -0.0f);
        hoodTalon.configPeakOutputVoltage(+6.0, -6.0);
    }

    private void configureWheelTalon() {
        shooterWheelTalon.config_kP(0, 0.0, 0);
        shooterWheelTalon.config_kI(0, 0.0, 0);
        shooterWheelTalon.config_kD(0, 0.0, 0);
        shooterWheelTalon.config_kF(0, 1023.0 / convertRpmToTicksPer100ms(5760), 0);

        shooterWheelTalon.setFeedbackDevice(FeedbackDevice.IntegratedSensor);

        shooterWheelTalon.configNominalOutputVoltage(+0.0f, -0.0f);
        shooterWheelTalon.configPeakOutputVoltage(+12.0, 0.0);
    }

    void configureTurretTalon() {
        turretTalon.config_kP(0, 1.0, 0);
        turretTalon.config_kI(0, 0.0, 0);
        turretTalon.config_kD(0, 0.0, 0);
        turretTalon.config_kF(0, 0.0, 0);
        turretTalon.changeControlMode(ControlMode.Position);
        turretTalon.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);

        turretTalon.configNominalOutputVoltage(+0.0f, -0.0f);
        turretTalon.configPeakOutputVoltage(+6.0, -6.0);
    }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run

        UpdateDashboard();
    }

    int debugShooter;

    private void UpdateDashboard() {
        SmartDashboard.putNumber("Shooter Wheel pos", shooterWheelTalon.getSelectedSensorPosition(0));
        SmartDashboard.putNumber("Shooter Wheel speed", shooterWheelTalon.getSelectedSensorVelocity(0));
        SmartDashboard.putNumber("Shooter Wheel RPM",
                convertTicksPer100msToRPM(shooterWheelTalon.getSelectedSensorVelocity(0)));

        SmartDashboard.putNumber("Shooter Wheel target RPM", m_targetSpeedRPM);
        SmartDashboard.putNumber("Shooter Wheel Error",
                m_targetSpeedRPM - convertTicksPer100msToRPM(shooterWheelTalon.getSelectedSensorVelocity(0)));

        SmartDashboard.putNumber("Shooter turet pos", turretTalon.getPosition());
        SmartDashboard.putNumber("Shooter hood pos", hoodTalon.getPosition());
        SmartDashboard.putNumber("Shooter feeder speed", feederTalon.getPosition());

        SmartDashboard.putNumber("Shooter Debug", debugShooter++);
    }

    public void zero() {
        turretTalon.setPosition(0);
        hoodTalon.setPosition(0);
    }

    /**
     * Set the absolute turret position.
     */
    public void setTurretPositionAbs(double pos) {
        turretTalon.set(ControlMode.Position, pos);
    }

    /**
     * Set the relative turret position
     * 
     * @param pos number of encoder ticks to adjust.
     */
    public void setTurretPositionRel(double pos) {
        turretTalon.set(ControlMode.Position, getTurretPosition() + pos);
    }

    /**
     * Get the current position of the turret.
     * 
     * @return
     */
    public double getTurretPosition() {
        return turretTalon.getPosition();
    }

    public void setHoodPosition(double pos) {
        hoodTalon.set(ControlMode.Position, pos);
    }

    public double getHoodPosition() {
        return hoodTalon.getPosition();
    }

    public void setFeederSpeed(double pos) {
        feederTalon.set(ControlMode.PercentOutput, pos);
    }

    public void setShooterWheelSpeed(double rpm) {
        double ticks = convertRpmToTicksPer100ms(rpm);
        m_targetSpeedRPM = rpm;
        shooterWheelTalon.set(ControlMode.Velocity, ticks);
    }

    public void setShooterWheelSpeedVBus(double pos) {
        shooterWheelTalon.set(ControlMode.PercentOutput, pos);
    }

    public double getShooterWheelSpeed() {
        return convertTicksPer100msToRPM(shooterWheelTalon.getSelectedSensorVelocity(0));
    }

    public double getShooterWheelTargetSpeed() {
        return m_targetSpeedRPM;
    }

    public double getShooterWheelSpeedVBus() {
        return shooterWheelTalon.getOutputVoltage();
    }

    ////////////////////////////////////////////////////
    // PidTunerObject
    @Override
    public double getP() {
        return shooterWheelTalon.getP();
    }

    @Override
    public double getI() {
        return shooterWheelTalon.getI();
    }

    @Override
    public double getD() {
        return shooterWheelTalon.getD();
    }

    @Override
    public double getF() {
        return shooterWheelTalon.getF();

    }

    @Override
    public void setP(double d) {
        shooterWheelTalon.config_kP(0, d, 0);
    }

    @Override
    public void setI(double d) {
        shooterWheelTalon.config_kI(0, d, 0);
    }

    @Override
    public void setD(double d) {
        shooterWheelTalon.config_kD(0, d, 0);

    }

    @Override
    public void setF(double d) {
        shooterWheelTalon.config_kF(0, d, 0);
    }

}