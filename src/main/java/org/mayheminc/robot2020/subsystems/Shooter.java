package org.mayheminc.robot2020.subsystems;

import org.mayheminc.robot2020.Constants;

import com.ctre.phoenix.motorcontrol.*;
import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import org.mayheminc.util.MayhemTalonSRX;

public class Shooter extends SubsystemBase {
    private final MayhemTalonSRX shooterWheelTalon = new MayhemTalonSRX(Constants.Talon.SHOOTER_WHEEL);
    private final MayhemTalonSRX turretTalon = new MayhemTalonSRX(Constants.Talon.SHOOTER_TURRET);
    private final MayhemTalonSRX hoodTalon = new MayhemTalonSRX(Constants.Talon.SHOOTER_HOOD);
    private final MayhemTalonSRX feederTalon = new MayhemTalonSRX(Constants.Talon.SHOOTER_FEEDER);

    /**
     * Creates a new Shooter.
     */
    public Shooter() {
        configureTurretTalon();
        configureWheelTalon();
        configureHoodTalon();
        configureFeederTalon();
    }

    private void configureFeederTalon() {
        feederTalon.changeControlMode(ControlMode.PercentOutput);
    }

    private void configureHoodTalon() {
        hoodTalon.config_kP(0, 1.0, 0);
        hoodTalon.config_kI(0, 0.0, 0);
        hoodTalon.config_kD(0, 0.0, 0);
        hoodTalon.config_kF(0, 0.0, 0);
        hoodTalon.changeControlMode(ControlMode.Position);
        hoodTalon.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);
    }

    private void configureWheelTalon() {
        shooterWheelTalon.config_kP(0, 60.0, 0);
        shooterWheelTalon.config_kI(0, 0.0, 0);
        shooterWheelTalon.config_kD(0, 0.0, 0);
        shooterWheelTalon.config_kF(0, 0.0, 0);
        shooterWheelTalon.changeControlMode(ControlMode.Velocity);
        shooterWheelTalon.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);
    }

    void configureTurretTalon() {
        turretTalon.config_kP(0, 1.0, 0);
        turretTalon.config_kI(0, 0.0, 0);
        turretTalon.config_kD(0, 0.0, 0);
        turretTalon.config_kF(0, 0.0, 0);
        turretTalon.changeControlMode(ControlMode.Position);
        turretTalon.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);
    }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run
        UpdateDashboard();
    }

    private void UpdateDashboard() {
        SmartDashboard.putNumber("Shooter Wheel speed", shooterWheelTalon.getSpeed());
        SmartDashboard.putNumber("Shooter turet pos", turretTalon.getPosition());
        SmartDashboard.putNumber("Shooter hood pos", hoodTalon.getPosition());
        SmartDashboard.putNumber("Shooter feeder speed", feederTalon.getPosition());
    }

    public void zeroTurretPosition(int pos) {
        turretTalon.setPosition(pos);
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

    public void setShooterWheelSpeed(double pos) {
        shooterWheelTalon.set(ControlMode.Velocity, pos);
    }   
    public void setShooterWheelSpeedVBus(double pos) {
        shooterWheelTalon.set(ControlMode.PercentOutput, pos);
    }

    public double getShooterWheelSpeed() {
        return shooterWheelTalon.getSpeed();
    }   
    
     public double getShooterWheelSpeedVBus() {
        return shooterWheelTalon.getOutputVoltage();
    }

}