package org.mayheminc.robot2020.subsystems;

import org.mayheminc.robot2020.Constants;

import com.ctre.phoenix.motorcontrol.*;
import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import org.mayheminc.util.MayhemTalonSRX;
// import org.mayheminc.util.PidTuner;
import org.mayheminc.util.PidTunerObject;

public class Hood extends SubsystemBase implements PidTunerObject {
    private final MayhemTalonSRX hoodTalon = new MayhemTalonSRX(Constants.Talon.SHOOTER_HOOD);

    private final static int MIN_POSITION = 0;
    private final static int MAX_POSITION = 90000;
    private final static double POSITION_TOLERANCE = 1000.0;

    public final static double STARTING_POSITION = 0;
    public final static double TARGET_ZONE_POSITION = 5000;
    public final static double INITIATION_LINE_POSITION = 65000;
    public final static double TRENCH_MID_POSITION = 80000;

    private double m_desiredPosition = 0.0;

    /**
     * Creates a new Hood.
     */
    public Hood() {
        configureTalon();
    }

    public void init() {
        configureTalon();
    }

    private void configureTalon() {
        hoodTalon.config_kP(0, 1.0, 0);
        hoodTalon.config_kI(0, 0.0, 0);
        hoodTalon.config_kD(0, 0.0, 0);
        hoodTalon.config_kF(0, 0.0, 0);

        hoodTalon.changeControlMode(ControlMode.Position);
        hoodTalon.setNeutralMode(NeutralMode.Coast);
        hoodTalon.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);

        hoodTalon.configNominalOutputVoltage(+0.0f, -0.0f);
        hoodTalon.configPeakOutputVoltage(+12.0, -12.0);
        hoodTalon.setInverted(true);
        hoodTalon.setSensorPhase(true);

        hoodTalon.configForwardSoftLimitThreshold(MAX_POSITION);
        hoodTalon.configForwardSoftLimitEnable(true);
        hoodTalon.configReverseSoftLimitThreshold(MIN_POSITION);
        hoodTalon.configReverseSoftLimitEnable(true);
    }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run
        UpdateDashboard();
    }

    private void UpdateDashboard() {
        SmartDashboard.putNumber("Shooter Hood Pos", hoodTalon.getPosition());
        SmartDashboard.putNumber("Shooter Hood Setpoint", m_desiredPosition);
    }

    public void zero() {
        hoodTalon.setPosition(0);
        m_desiredPosition = 0.0;
        hoodTalon.set(ControlMode.PercentOutput, 0.0);
    }

    public void setPosition(double pos) {
        m_desiredPosition = pos;
        hoodTalon.set(ControlMode.Position, m_desiredPosition);
    }

    public boolean isAtPosition() {
        return (Math.abs(getPosition() - m_desiredPosition) < POSITION_TOLERANCE);
    }

    public double getPosition() {
        return hoodTalon.getPosition();
    }

    public void setVBus(double d) {
        hoodTalon.set(ControlMode.PercentOutput, d);
    }

    ////////////////////////////////////////////////////
    // PidTunerObject
    @Override
    public double getP() {
        return hoodTalon.getP();
    }

    @Override
    public double getI() {
        return hoodTalon.getI();
    }

    @Override
    public double getD() {
        return hoodTalon.getD();
    }

    @Override
    public double getF() {
        return hoodTalon.getF();

    }

    @Override
    public void setP(double d) {
        hoodTalon.config_kP(0, d, 0);
    }

    @Override
    public void setI(double d) {
        hoodTalon.config_kI(0, d, 0);
    }

    @Override
    public void setD(double d) {
        hoodTalon.config_kD(0, d, 0);
    }

    @Override
    public void setF(double d) {
        hoodTalon.config_kF(0, d, 0);
    }

}