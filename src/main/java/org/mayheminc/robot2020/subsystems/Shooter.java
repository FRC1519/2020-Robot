package org.mayheminc.robot2020.subsystems;

import com.kauailabs.navx.frc.*;

import org.mayheminc.robot2020.Constants;
import org.mayheminc.util.History;

import edu.wpi.first.wpilibj.*;
import com.ctre.phoenix.motorcontrol.*;
import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import org.mayheminc.util.MayhemTalonSRX;
import org.mayheminc.util.Utils;

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
        shooterWheelTalon.config_kP(0, 1.0, 0);
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
        SmartDashboard.putNumber("shooter speed", shooterWheelTalon.get());
        SmartDashboard.putNumber("turet pos", turretTalon.get());
        SmartDashboard.putNumber("hood pos", hoodTalon.get());
        SmartDashboard.putNumber("feeder speed", feederTalon.get());
    }

    public void setTurretPosition(double pos) {
        turretTalon.set(ControlMode.Position, pos);
    }

    public double getTurretPosition() {
        return turretTalon.get();
    }

    public void setHoodPosition(double pos) {
        hoodTalon.set(ControlMode.Position, pos);
    }

    public void setFeederSpeed(double pos) {
        feederTalon.set(ControlMode.PercentOutput, pos);
    }

    public void setShooterWheelSpeed(double pos) {
        shooterWheelTalon.set(ControlMode.Velocity, pos);
    }

    public double getShooterWheelSpeed() {
        return shooterWheelTalon.get();
    }
}