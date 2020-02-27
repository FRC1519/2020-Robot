package org.mayheminc.robot2020.subsystems;

import org.mayheminc.robot2020.Constants;
// import org.mayheminc.robot2020.RobotContainer;

import com.ctre.phoenix.motorcontrol.*;
import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import org.mayheminc.util.MayhemTalonSRX;
import org.mayheminc.util.PidTunerObject;

public class ShooterWheel extends SubsystemBase implements PidTunerObject {
    private final MayhemTalonSRX shooterWheelLeft = new MayhemTalonSRX(Constants.Talon.SHOOTER_WHEEL_LEFT);
    private final MayhemTalonSRX shooterWheelRight = new MayhemTalonSRX(Constants.Talon.SHOOTER_WHEEL_RIGHT);

    // private final double MAX_SPEED_RPM = 5760.0;
    private final double TALON_TICKS_PER_REV = 2048.0;
    private final double SECONDS_PER_MINUTE = 60.0;
    private final double HUNDRED_MS_PER_SECOND = 10.0;

    public static final double SHOOTER_WHEEL_INITIATION_LINE_SPEED = 3000.0;
    public static final double SHOOTER_WHEEL_TRENCH_FRONT_SPEED = 3400.0;

    double m_targetSpeedRPM;

    // Note:  for ease of thinking, 1 RPM =~ 3.4 native units for the shooter
    double convertRpmToTicksPer100ms(double rpm) {
        return rpm / SECONDS_PER_MINUTE * TALON_TICKS_PER_REV / HUNDRED_MS_PER_SECOND;
    }

    // Note:  3.413 native units =~ 1.0 RPM for the shooter
    double convertTicksPer100msToRPM(double ticks) {
        return ticks * HUNDRED_MS_PER_SECOND / TALON_TICKS_PER_REV * SECONDS_PER_MINUTE;
    }

    /**
     * Creates a new Shooter.
     */
    public ShooterWheel() {
        configureWheelFalcons();
    }

    public void init() {
        configureWheelFalcons();
        setSpeedVBus(0.0);
    }
    // configure a pair of shooter wheel falcons
    private void configureWheelFalcons() {
        // most of the configuration is shared for the two Falcons
        configureOneWheelFalcon(shooterWheelLeft);
        configureOneWheelFalcon(shooterWheelRight);

        // with the exception of one rotating the opposite direction
        shooterWheelLeft.setInverted(false);
        shooterWheelRight.setInverted(true);
    }
    private void configureOneWheelFalcon(MayhemTalonSRX shooterWheelFalcon) {
        shooterWheelFalcon.setFeedbackDevice(FeedbackDevice.IntegratedSensor);
        shooterWheelFalcon.setNeutralMode(NeutralMode.Coast);
        shooterWheelFalcon.configNominalOutputVoltage(+0.0f, -0.0f);
        shooterWheelFalcon.configPeakOutputVoltage(+12.0, 0.0);
        shooterWheelFalcon.configNeutralDeadband(0.001);  // Config neutral deadband to be the smallest possible

        // For PIDF computations, 1023 is interpreted as "full" motor output
        // Velocity is in native units of TicksPer100ms.
        // 1000rpm =~ 3413 native units.
        // P of "3.0" means that full power applied with error of 341 native units = 100rpm
        //   (above also means that 50% power boost applied with error of 50rpm)
        shooterWheelFalcon.config_kP(0, 0.1, 0);  // previously used 3.0
        shooterWheelFalcon.config_kI(0, 0.0, 0);
        shooterWheelFalcon.config_kD(0, 0.0, 0);  // CTRE recommends starting at 10x P-gain
        shooterWheelFalcon.config_kF(0, 0.046, 0);    // 1023.0 / convertRpmToTicksPer100ms(5760), 0);
        shooterWheelFalcon.configAllowableClosedloopError(0, 0, 0);   // no "neutral" zone around target
    }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run
        UpdateDashboard();
    }


    private void UpdateDashboard() {
        SmartDashboard.putNumber("Shooter Wheel pos", shooterWheelLeft.getSelectedSensorPosition(0));
        SmartDashboard.putNumber("Shooter Wheel speed", shooterWheelLeft.getSelectedSensorVelocity(0));
        SmartDashboard.putNumber("Shooter Wheel RPM",
                convertTicksPer100msToRPM(shooterWheelLeft.getSelectedSensorVelocity(0)));

        SmartDashboard.putNumber("Shooter Wheel target RPM", m_targetSpeedRPM);
        SmartDashboard.putNumber("Shooter Wheel Error",
                m_targetSpeedRPM - convertTicksPer100msToRPM(shooterWheelLeft.getSelectedSensorVelocity(0)));
        SmartDashboard.putNumber("Shooter Wheel Voltage", shooterWheelLeft.getMotorOutputVoltage());
        SmartDashboard.putNumber("Shooter Wheel Bus Voltage", shooterWheelLeft.getBusVoltage());
        SmartDashboard.putNumber("Shooter Wheel Current", shooterWheelLeft.getSupplyCurrent());

        SmartDashboard.putNumber("Shooter Wheel R-pos", shooterWheelRight.getSelectedSensorPosition(0));
        SmartDashboard.putNumber("Shooter Wheel R-speed", shooterWheelRight.getSelectedSensorVelocity(0));
        SmartDashboard.putNumber("Shooter Wheel R-RPM",
                convertTicksPer100msToRPM(shooterWheelRight.getSelectedSensorVelocity(0)));

        SmartDashboard.putNumber("Shooter Wheel R-target RPM", m_targetSpeedRPM);
        SmartDashboard.putNumber("Shooter Wheel R-Error",
                m_targetSpeedRPM - convertTicksPer100msToRPM(shooterWheelRight.getSelectedSensorVelocity(0)));
        SmartDashboard.putNumber("Shooter Wheel R-Voltage", shooterWheelRight.getMotorOutputVoltage());
        SmartDashboard.putNumber("Shooter Wheel R-Bus Voltage", shooterWheelRight.getBusVoltage());
        SmartDashboard.putNumber("Shooter Wheel R-Current", shooterWheelRight.getSupplyCurrent());
    }

    public void zero() {
    }

    /**
     * Set shooter to rpm speed.
     * 
     * @param rpm
     */
    public void setSpeed(double rpm) {
        m_targetSpeedRPM = rpm;
        double ticks = convertRpmToTicksPer100ms(rpm);
        shooterWheelLeft.set(ControlMode.Velocity, ticks);
        shooterWheelRight.set(ControlMode.Velocity, ticks);
    }

    public void setSpeedVBus(double pos) {
        shooterWheelLeft.set(ControlMode.PercentOutput, pos);
        shooterWheelRight.set(ControlMode.PercentOutput, pos);
    }

    public double getSpeed() {
        return convertTicksPer100msToRPM(shooterWheelLeft.getSelectedSensorVelocity(0));
    }

    public double getTargetSpeed() {
        return m_targetSpeedRPM;
    }

    public double getSpeedVBus() {
        return shooterWheelLeft.getMotorOutputVoltage();
    }

    ////////////////////////////////////////////////////
    // PidTunerObject
    @Override
    public double getP() {
        return shooterWheelLeft.getP();
    }

    @Override
    public double getI() {
        return shooterWheelLeft.getI();
    }

    @Override
    public double getD() {
        return shooterWheelLeft.getD();
    }

    @Override
    public double getF() {
        return shooterWheelLeft.getF();

    }

    @Override
    public void setP(double d) {
        shooterWheelLeft.config_kP(0, d, 0);
        shooterWheelRight.config_kP(0, d, 0);
    }

    @Override
    public void setI(double d) {
        shooterWheelLeft.config_kI(0, d, 0);
        shooterWheelRight.config_kI(0, d, 0);
    }

    @Override
    public void setD(double d) {
        shooterWheelLeft.config_kD(0, d, 0);
        shooterWheelRight.config_kD(0, d, 0);
    }

    @Override
    public void setF(double d) {
        shooterWheelLeft.config_kF(0, d, 0);
        shooterWheelRight.config_kF(0, d, 0);
    }

}