package org.mayheminc.robot2020.subsystems;

import org.mayheminc.robot2020.Constants;
import org.mayheminc.robot2020.RobotContainer;

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

    public static final boolean WAIT_FOR_DONE = true;
    private final int MIN_POSITION = -12500; // approx 90 degrees
    private final int MAX_POSITION = +26000; // approx 180 degrees
    private final int DESTINATION_TOLERANCE = 200;

    // if "at destination" want the "I" to get us as close as possible
    private final int INTEGRAL_ZONE = 100;

    // TICKS_PER_DEGREE computed by 4096 ticks per rotation of shaft / 1 rotation of
    // shaft per 18t * 225t / 1 rotation of turret

    public final static double TICKS_PER_DEGREE = 4096.0 / 18.0 * 225.0 / 360.0;
    // above works out to 142.2 ticks per degree
    // a full circle is 51,200 ticks

    double m_targetSpeedRPM = 0.0;
    double m_desiredPosition = 0.0;
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
        // PID tuning notes:
        // during competition season, used P=1.0; everything else zero
        // on 3 December tuned with Caleb, Amy, Coach Streeter with P=0.7, D=7.0
        // Note: had "overshoot" issues when using I. (Tried 0.001 to 0.01)
        turretTalon.config_kP(0, 0.7, 0);
        turretTalon.config_kI(0, 0.01, 0);
        turretTalon.config_kD(0, 7.0, 0);
        turretTalon.config_kF(0, 0.0, 0);

        // experimented by adding "integral zone" for when turret is close to intended
        // target
        turretTalon.config_IntegralZone(0, INTEGRAL_ZONE);
        turretTalon.changeControlMode(ControlMode.Position);
        turretTalon.setNeutralMode(NeutralMode.Coast);
        turretTalon.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);

        turretTalon.configMotionCruiseVelocity(2500); // max velocity at 100% is about 3200, cruise at 80%
        turretTalon.configMotionAcceleration(5000); // acceleration of 2x velocity, allows cruise in 1/2 second

        turretTalon.configNominalOutputVoltage(+0.0f, -0.0f);
        turretTalon.configPeakOutputVoltage(+12.0, -12.0);

        turretTalon.configForwardSoftLimitThreshold(MAX_POSITION);
        turretTalon.configForwardSoftLimitEnable(true);
        turretTalon.configReverseSoftLimitThreshold(MIN_POSITION);
        turretTalon.configReverseSoftLimitEnable(true);

        
        turretTalon.setInverted(false);
        turretTalon.setSensorPhase(false);
        
        this.setVBus(0.0);
    }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run
        UpdateDashboard();
        updateHistory();
    }

    // KBS: tuned below at practice field on 21 Feb 2020 via successive refinement
    // to get 0.08 w/Logitech C920 camera.

    // KBS: tuned below at practice field on 24 Sept 2020 w/Caleb and Amy to be 0.17
    // w/1CGN camera
    private static final double CAMERA_LAG = 0.17; // .05 was best so far in 2020; used .150 in 2019

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
        SmartDashboard.putNumber("Shooter turret pos desired", m_desiredPosition);
        SmartDashboard.putNumber("Shooter turret vbus", turretTalon.getMotorOutputVoltage());
        SmartDashboard.putNumber("Shooter turret velocity", turretTalon.getSelectedSensorVelocity(0));
    }

    public void zero() {
        m_desiredPosition = 0.0;
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
        m_desiredPosition = pos;
        turretTalon.set(ControlMode.MotionMagic, pos);
    }

    /**
     * Set the relative turret position
     * 
     * @param pos number of encoder ticks to adjust.
     */
    public void setPositionRel(double pos) {
        // Below line sets position relative to most recent "desiredPosition" but may
        // have BAD side-effect if turret had just been in VBus mode (i.e. may not have
        // been near prior desiredPosition) Would need to decide a safer way to know
        // whether to have new relative position be relative to the current
        // desiredPosition or the current actual position.

        // m_desiredPosition = m_desiredPosition + pos;
        m_desiredPosition = getPosition() + pos;
        setPositionAbs(m_desiredPosition);
    }

    public void setVBus(double power) {
        turretTalon.set(ControlMode.PercentOutput, power);
    }

    /**
     * Get the current position of the turret (in encoder counts).
     * 
     * @return
     */
    public double getPosition() {
        return turretTalon.getPosition();
    }

    /**
     * Get the desired position of the turret.
     * 
     * @return
     */
    public double getDesiredPosition() {
        return m_desiredPosition;
    }

    /**
     * Gets the turret's absolute position, in degrees, relative to the robot's
     * heading.
     * 
     * @return
     */
    public double getGlobalTurretPosition() {
        return RobotContainer.drive.getHeading() + (this.getPosition() / TICKS_PER_DEGREE);
    }

    /**
     * Return true if close enough to desired position
     * 
     * @return
     */
    public boolean isAtDesiredPosition() {
        return (Math.abs(getPosition() - getDesiredPosition()) < DESTINATION_TOLERANCE);
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