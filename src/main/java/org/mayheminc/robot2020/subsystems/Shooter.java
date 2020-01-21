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
    }

    void configureTurretTalon() {
        turretTalon.config_kP(0, 1.0, 0);
        turretTalon.config_kI(0, 1.0, 0);
        turretTalon.config_kD(0, 1.0, 0);
        turretTalon.config_kF(0, 1.0, 0);
        turretTalon.changeControlMode(ControlMode.Position);
        turretTalon.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);
    }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run
    }

    public void setTurretPosition(double pos) {
        turretTalon.set(ControlMode.Position, pos);
    }

}