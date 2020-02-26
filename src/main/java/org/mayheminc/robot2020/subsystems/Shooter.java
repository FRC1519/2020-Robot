package org.mayheminc.robot2020.subsystems;

import org.mayheminc.robot2020.Constants;
// import org.mayheminc.robot2020.RobotContainer;

import com.ctre.phoenix.motorcontrol.*;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import org.mayheminc.util.History;
import org.mayheminc.util.MayhemTalonSRX;
// import org.mayheminc.util.PidTuner;
import org.mayheminc.util.PidTunerObject;

public class Shooter extends SubsystemBase implements PidTunerObject {
    private final MayhemTalonSRX shooterWheelLeft = new MayhemTalonSRX(Constants.Talon.SHOOTER_WHEEL_LEFT);
    private final MayhemTalonSRX shooterWheelRight = new MayhemTalonSRX(Constants.Talon.SHOOTER_WHEEL_RIGHT);
    private final MayhemTalonSRX turretTalon = new MayhemTalonSRX(Constants.Talon.SHOOTER_TURRET);
    private final MayhemTalonSRX hoodTalon = new MayhemTalonSRX(Constants.Talon.SHOOTER_HOOD);
    private final VictorSPX feederTalon = new VictorSPX(Constants.Talon.SHOOTER_FEEDER);

    // private final double MAX_SPEED_RPM = 5760.0;
    private final double TALON_TICKS_PER_REV = 2048.0;
    private final double TURRET_MIN_POS = -5500.0;
    private final double TURRET_MAX_POS = +5500.0;
    private final double SECONDS_PER_MINUTE = 60.0;
    private final double HUNDRED_MS_PER_SECOND = 10.0;

    public final static double HOOD_TARGET_ZONE_POSITION = 5000;
    public final static double HOOD_INITIATION_LINE_POSITION = 65000;
    public final static double HOOD_TRENCH_MID_POSITION = 80000;

    double m_targetSpeedRPM;
    History headingHistory = new History();

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
    public Shooter() {
        configureWheelFalcons();
        configureTurretTalon();
        configureHoodTalon();
        configureFeederTalon();
    }

    public void init() {
        configureWheelFalcons();
        configureTurretTalon();
        configureHoodTalon();
        configureFeederTalon();

        setShooterWheelSpeedVBus(0.0);
    }

    private void configureFeederTalon() {
        // feederTalon.changeControlMode(ControlMode.PercentOutput);
        feederTalon.setNeutralMode(NeutralMode.Brake);

        feederTalon.configNominalOutputForward(+0.0f);
        feederTalon.configNominalOutputReverse(0.0);
        feederTalon.configPeakOutputForward(+6.0);
        feederTalon.configPeakOutputReverse(-6.0);
    }

    private void configureHoodTalon() {
        hoodTalon.config_kP(0, 1.0, 0);
        hoodTalon.config_kI(0, 0.0, 0);
        hoodTalon.config_kD(0, 0.0, 0);
        hoodTalon.config_kF(0, 0.0, 0);

        hoodTalon.changeControlMode(ControlMode.Position);
        feederTalon.setNeutralMode(NeutralMode.Coast);
        hoodTalon.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);

        hoodTalon.configNominalOutputVoltage(+0.0f, -0.0f);
        hoodTalon.configPeakOutputVoltage(+12.0, -12.0);
        hoodTalon.setInverted(true);
        hoodTalon.setSensorPhase(true);

        hoodTalon.configForwardSoftLimitThreshold(100000);
        hoodTalon.configForwardSoftLimitEnable(true);
        hoodTalon.configReverseSoftLimitThreshold(0);
        hoodTalon.configReverseSoftLimitEnable(true);
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
        turretTalon.configPeakOutputVoltage(+4.0, -4.0);

        turretTalon.configForwardSoftLimitThreshold(6000);
        turretTalon.configForwardSoftLimitEnable(true);
        turretTalon.configReverseSoftLimitThreshold(-6000);
        turretTalon.configReverseSoftLimitEnable(true);

        this.setTurretVBus(0.0);
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
        updateHistory();
    }

    // KBS: tuned below at practice field on 21 Feb 2020 via successive refinement.
    private static final double CAMERA_LAG = 0.08; // .05 was best so far in 2020; used .150 in 2019

    private void updateHistory() {
        double now = Timer.getFPGATimestamp();
        headingHistory.add(now, getTurretPosition());
    }

    public double getAzimuthForCapturedImage() {
        double now = Timer.getFPGATimestamp();
        double indexTime = now - CAMERA_LAG;
        return headingHistory.getAzForTime(indexTime);
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

        SmartDashboard.putNumber("Shooter turret pos", turretTalon.getPosition());
        SmartDashboard.putNumber("Shooter turret vbus", turretTalon.getMotorOutputVoltage());
        SmartDashboard.putNumber("Shooter turret velocity", turretTalon.getSelectedSensorVelocity(0));

        SmartDashboard.putNumber("Shooter hood pos", hoodTalon.getPosition());
        SmartDashboard.putNumber("Shooter feeder speed", feederTalon.getMotorOutputVoltage());

        // SmartDashboard.putNumber("Shooter Debug", debugShooter++);
    }

    public void zero() {
        turretTalon.setPosition(0);
        hoodTalon.setPosition(0);
    }

    /**
     * Set the absolute turret position.
     */
    public void setTurretPositionAbs(double pos) {
        if (pos < TURRET_MIN_POS) {
            pos = TURRET_MIN_POS;
        }
        if (pos > TURRET_MAX_POS) {
            pos = TURRET_MAX_POS;
        }
        // turretTalon.set(ControlMode.Position, pos);
        turretTalon.set(ControlMode.MotionMagic, pos);
    }

    /**
     * Set the relative turret position
     * 
     * @param pos number of encoder ticks to adjust.
     */
    public void setTurretPositionRel(double pos) {
        setTurretPositionAbs(getTurretPosition() + pos);
    }

    public void setTurretVBus(double power) {
        turretTalon.set(ControlMode.PercentOutput, power);
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

    public void setHoodVBus(double d) {
        hoodTalon.set(ControlMode.PercentOutput, d);
    }

    public void setFeederSpeed(double pos) {
        feederTalon.set(ControlMode.PercentOutput, pos);
    }

    /**
     * Set shooter to rpm speed.
     * 
     * @param rpm
     */
    public void setShooterWheelSpeed(double rpm) {
        m_targetSpeedRPM = rpm;
        double ticks = convertRpmToTicksPer100ms(rpm);
        shooterWheelLeft.set(ControlMode.Velocity, ticks);
        shooterWheelRight.set(ControlMode.Velocity, ticks);
    }

    public void setShooterWheelSpeedVBus(double pos) {
        shooterWheelLeft.set(ControlMode.PercentOutput, pos);
        shooterWheelRight.set(ControlMode.PercentOutput, pos);
    }

    public double getShooterWheelSpeed() {
        return convertTicksPer100msToRPM(shooterWheelLeft.getSelectedSensorVelocity(0));
    }

    public double getShooterWheelTargetSpeed() {
        return m_targetSpeedRPM;
    }

    public double getShooterWheelSpeedVBus() {
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