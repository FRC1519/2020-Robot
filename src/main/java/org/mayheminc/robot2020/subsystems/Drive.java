package org.mayheminc.robot2020.subsystems;

import com.kauailabs.navx.frc.*;

import org.mayheminc.robot2020.Constants;
import org.mayheminc.util.History;

import edu.wpi.first.wpilibj.*;
import com.ctre.phoenix.motorcontrol.*;
import com.ctre.phoenix.motorcontrol.ControlMode;

//import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

//import org.mayheminc.robot2020.Robot;
//import org.mayheminc.robot2019.RobotMap;
import org.mayheminc.util.MayhemTalonSRX;
import org.mayheminc.util.Utils;

public class Drive extends SubsystemBase {

	History headingHistory = new History();

	// Brake modes
	public static final boolean BRAKE_MODE = true;
	public static final boolean COAST_MODE = false;

	// PID loop variables
	private PIDController m_HeadingPid;
	private PIDHeadingError m_HeadingError;
	private PIDHeadingCorrection m_HeadingCorrection;
	private boolean m_HeadingPidPreventWindup = false;
	private static final int LOOPS_GYRO_DELAY = 10;

	// Talons
	private final MayhemTalonSRX leftFrontTalon = new MayhemTalonSRX(Constants.Talon.DRIVE_LEFT_A);
	private final MayhemTalonSRX leftRearTalon = new MayhemTalonSRX(Constants.Talon.DRIVE_LEFT_B);
	private final MayhemTalonSRX rightFrontTalon = new MayhemTalonSRX(Constants.Talon.DRIVE_RIGHT_A);
	private final MayhemTalonSRX rightRearTalon = new MayhemTalonSRX(Constants.Talon.DRIVE_RIGHT_B);

	// Sensors
	private AHRS Navx;

	// Driving mode
	private boolean m_speedRacerDriveMode = true; // set by default

	// NavX parameters
	private double m_desiredHeading = 0.0;
	private boolean m_useHeadingCorrection = true;
	private static final double HEADING_PID_P_FOR_HIGH_GEAR = 0.030;
	private static final double HEADING_PID_P_FOR_LOW_GEAR = HEADING_PID_P_FOR_HIGH_GEAR / 2.0;
	private static final double kToleranceDegreesPIDControl = 0.2;

	// Drive parameters
	public static final double DISTANCE_PER_PULSE = 3.14 * 8.0 * 36 / 42 / (250 * 4); // pi * diameter * (gear ratio) /
																						// (counts per rev)
	private boolean m_closedLoopMode = false;
	private double m_maxWheelSpeed = 1.0; // set to 1.0 as default for "open loop" percentVBus drive
	private double m_voltageRampRate = 48.0;

	private double m_initialWheelDistance = 0.0;
	private int m_iterationsSinceRotationCommanded = 0;
	private int m_iterationsSinceMovementCommanded = 0;

	private boolean autoAlign = false;

	// Targeting
	// private double TARGET_ALIGNED = -0.4;

	/***********************************
	 * INITIALIZATION
	 **********************************************************/

	public Drive() {
		try {
			/* Communicate w/navX MXP via the MXP SPI Bus. */
			/* Alternatively: I2C.Port.kMXP, SerialPort.Port.kMXP or SerialPort.Port.kUSB */
			/*
			 * See http://navx-mxp.kauailabs.com/guidance/selecting-an-interface/ for
			 * details.
			 */
			Navx = new AHRS(SPI.Port.kMXP);
			Navx.reset();
		} catch (RuntimeException ex) {
			DriverStation.reportError("Error instantiating navX MXP:  " + ex.getMessage(), true);
			System.out.println("Error loading navx.");
		}

		// create a PID Controller that reads the heading error and outputs the heading
		// correction.
		m_HeadingError = new PIDHeadingError();
		m_HeadingError.m_Error = 0.0;
		m_HeadingCorrection = new PIDHeadingCorrection();
		m_HeadingPid = new PIDController(HEADING_PID_P_FOR_HIGH_GEAR, 0.000, 0.04, m_HeadingError, m_HeadingCorrection,
				0.020 /* period in seconds */);
		m_HeadingPid.setInputRange(-180.0f, 180.0f);
		m_HeadingPid.setContinuous(true); // treats the input range as "continous" with wrap-around
		m_HeadingPid.setOutputRange(-.50, .50); // set the maximum power to correct twist
		m_HeadingPid.setAbsoluteTolerance(kToleranceDegreesPIDControl);

		// confirm all four drive talons are in coast mode
		leftFrontTalon.setNeutralMode(NeutralMode.Coast);
		leftRearTalon.setNeutralMode(NeutralMode.Coast);
		rightFrontTalon.setNeutralMode(NeutralMode.Coast);
		rightRearTalon.setNeutralMode(NeutralMode.Coast);

		// set rear talons to follow their respective front talons
		leftRearTalon.changeControlMode(ControlMode.Follower);
		leftRearTalon.set(leftFrontTalon.getDeviceID());

		rightRearTalon.changeControlMode(ControlMode.Follower);
		rightRearTalon.set(rightFrontTalon.getDeviceID());

		// the left motors move the robot forwards with positive power
		// but the right motors are backwards.
		leftFrontTalon.setInverted(false);
		leftRearTalon.setInverted(false);
		rightFrontTalon.setInverted(true);
		rightRearTalon.setInverted(true);

		// sensor phase is reversed, since there are 3 reduction stages in the gearbox
		leftFrontTalon.setSensorPhase(true);
		leftRearTalon.setSensorPhase(true);
		rightFrontTalon.setSensorPhase(true);
		rightRearTalon.setSensorPhase(true);
	}

	public void init() {
		// reset the NavX
		zeroHeadingGyro(0.0);

		// talon closed loop config
		configureCanTalon(leftFrontTalon);
		configureCanTalon(rightFrontTalon);
	}

	public void zeroHeadingGyro(double headingOffset) {
		Navx.zeroYaw();
		setHeadingOffset(headingOffset);

		DriverStation.reportError("heading immediately after zero = " + getHeading() + "\n", false);

		m_desiredHeading = 0.0;

		SmartDashboard.putString("Trace", "Zero Heading Gyro");

		// restart the PID controller loop
		resetAndEnableHeadingPID();
	}

	public void initDefaultCommand() {
		// setDefaultCommand(new SpeedRacerDrive());
	}

	private void configureCanTalon(MayhemTalonSRX talon) {
		double wheelP = 1.5;
		double wheelI = 0.0;
		double wheelD = 0.0;
		double wheelF = 1.0;

		talon.setFeedbackDevice(FeedbackDevice.IntegratedSensor);

		// talon.reverseSensor(false);
		talon.configNominalOutputVoltage(+0.0f, -0.0f);
		talon.configPeakOutputVoltage(+12.0, -12.0);

		if (m_closedLoopMode) {
			talon.changeControlMode(ControlMode.Velocity);
			m_maxWheelSpeed = 270;
		} else {
			talon.changeControlMode(ControlMode.PercentOutput);
			m_maxWheelSpeed = 1.0;
		}

		talon.setPID(wheelP, wheelI, wheelD, wheelF, 0, m_voltageRampRate, 0);

		// talon.enableControl();

		DriverStation.reportError("setWheelPIDF: " + wheelP + " " + wheelI + " " + wheelD + " " + wheelF + "\n", false);
	}

	/**
	 * Set the "Brake Mode" behavior on the drive talons when "in neutral"
	 * 
	 * @param brakeMode - true for "brake in neutral" and false for "coast in
	 *                  neutral"
	 */
	public void setBrakeMode(boolean brakeMode) {

		NeutralMode mode = (brakeMode) ? NeutralMode.Brake : NeutralMode.Coast;

		leftFrontTalon.setNeutralMode(mode);
		leftRearTalon.setNeutralMode(mode);
		rightFrontTalon.setNeutralMode(mode);
		rightRearTalon.setNeutralMode(mode);
	}

	// *********************** CLOSED-LOOP MODE ********************************

	public void toggleClosedLoopMode() {
		if (!m_closedLoopMode) {
			setClosedLoopMode();
		} else {
			setOpenLoopMode();
		}
	}

	public void setClosedLoopMode() {
		m_closedLoopMode = true;
		// reconfigure the "master" drive talons now that we're in closed loop mode
		configureCanTalon(leftFrontTalon);
		configureCanTalon(rightFrontTalon);
	}

	public void setOpenLoopMode() {
		m_closedLoopMode = false;
		// reconfigure the "master" drive talons now that we're in open loop mode
		configureCanTalon(leftFrontTalon);
		configureCanTalon(rightFrontTalon);
	}

	// ********************* ENCODER-GETTERS ************************************

	public int getRightEncoder() {
		return rightFrontTalon.getSelectedSensorPosition(0);
	}

	public int getLeftEncoder() {
		return leftFrontTalon.getSelectedSensorPosition(0);
	}

	// speed is in inches per second
	public double getRightSpeed() {
		return rightFrontTalon.getSelectedSensorVelocity(0);
	}

	public double getLeftSpeed() {
		return leftFrontTalon.getSelectedSensorVelocity(0);
	}

	// *************************** GYRO *******************************************

	public double calculateHeadingError(double Target) {
		double currentHeading = getHeading();
		double error = Target - currentHeading;
		error = error % 360.0;
		if (error > 180.0) {
			error -= 360.0;
		}
		return error;
	}

	public boolean getHeadingCorrectionMode() {
		return m_useHeadingCorrection;
	}

	public void setHeadingCorrectionMode(boolean useHeadingCorrection) {
		m_useHeadingCorrection = useHeadingCorrection;
	}

	private void resetAndEnableHeadingPID() {
		// if (Robot.shifter.getGear() == Shifter.HIGH_GEAR) {
		// m_HeadingPid.setP(HEADING_PID_P_FOR_HIGH_GEAR);
		// } else
		{
			// low gear
			m_HeadingPid.setP(HEADING_PID_P_FOR_LOW_GEAR);
		}
		m_HeadingPid.reset();
		m_HeadingPid.enable();
	}

	static private final double STATIONARY = 0.1;
	static private double m_prevLeftDistance = 0.0;
	static private double m_prevRightDistance = 0.0;

	public boolean isStationary() {
		double leftDistance = getLeftEncoder();
		double rightDistance = getRightEncoder();

		double leftDelta = Math.abs(leftDistance - m_prevLeftDistance);
		double rightDelta = Math.abs(rightDistance - m_prevRightDistance);

		m_prevLeftDistance = leftDistance;
		m_prevRightDistance = rightDistance;

		return leftDelta < STATIONARY && rightDelta < STATIONARY;
	}

	private int LoopCounter = 0;

	public void displayGyroInfo() {
		SmartDashboard.putNumber("Robot Heading", Utils.twoDecimalPlaces(getHeading()));
		SmartDashboard.putNumber("Robot Roll", Utils.twoDecimalPlaces(this.getRoll()));
		SmartDashboard.putNumber("Robot Pitch", Utils.twoDecimalPlaces(this.getPitch()));
		SmartDashboard.putNumber("Loop Counter", LoopCounter++);
	}

	private double m_headingOffset = 0.0;

	public void setHeadingOffset(double arg_offset) {
		m_headingOffset = arg_offset;
	}

	public double getHeading() {
		return Navx.getYaw() + m_headingOffset;
	}

	// the Navx is installed sidways with reference to the front of the robot.
	public double getRoll() {
		return Navx.getPitch();
	}

	// the Navx is installed sidways with reference to the front of the robot.
	public double getPitch() {
		return Navx.getRoll();
	}

	public double getDesiredHeading() {
		return m_desiredHeading;
	}

	// ****************** SETTING POWER TO MOTORS ********************

	public void resetNavXDisplacement() {
		Navx.resetDisplacement();
	}

	public void stop() {
		setMotorPower(0, 0);
	}

	private void setMotorPower(double leftPower, double rightPower) {
		if (rightPower > 1.0) {
			rightPower = 1.0;
		}
		if (rightPower < -1.0) {
			rightPower = -1.0;
		}

		if (leftPower > 1.0) {
			leftPower = 1.0;
		}
		if (leftPower < -1.0) {
			leftPower = -1.0;
		}

		if (m_closedLoopMode) {
			rightFrontTalon.set(ControlMode.Velocity, rightPower * m_maxWheelSpeed);
			leftFrontTalon.set(ControlMode.Velocity, leftPower * m_maxWheelSpeed);
		} else {
			rightFrontTalon.set(ControlMode.PercentOutput, rightPower);
			leftFrontTalon.set(ControlMode.PercentOutput, leftPower);
		}
	}

	PowerDistributionPanel pdp = new PowerDistributionPanel();

	/**
	 * updateSdbPdp Update the Smart Dashboard with the Power Distribution Panel
	 * currents.
	 */
	public void updateSdbPdp() {
		double lf;
		double rf;
		double lb;
		double rb;
		double fudgeFactor = 0.0;

		lf = pdp.getCurrent(Constants.PDP.DRIVE_LEFT_A) - fudgeFactor;
		rf = pdp.getCurrent(Constants.PDP.DRIVE_LEFT_B) - fudgeFactor;
		lb = pdp.getCurrent(Constants.PDP.DRIVE_RIGHT_A) - fudgeFactor;
		rb = pdp.getCurrent(Constants.PDP.DRIVE_RIGHT_B) - fudgeFactor;

		SmartDashboard.putNumber("Left Front I", lf);
		SmartDashboard.putNumber("Right Front I", rf);
		SmartDashboard.putNumber("Left Back I", lb);
		SmartDashboard.putNumber("Right Back I", rb);
	}

	/*
	 * This method allows one to drive in "Tank Drive Mode". Tank drive mode uses
	 * the left side of the joystick to control the left side of the robot, whereas
	 * the right side of the joystick controls the right side of the robot.
	 */
	public void tankDrive(double leftSideThrottle, double rightSideThrottle) {
		if (leftSideThrottle >= 0.0) {
			leftSideThrottle = (leftSideThrottle * leftSideThrottle); // squaring inputs increases fine control
		} else {
			leftSideThrottle = -(leftSideThrottle * leftSideThrottle); // preserves the sign while squaring negative
																		// values
		}

		if (rightSideThrottle >= 0.0) {
			rightSideThrottle = (rightSideThrottle * rightSideThrottle);
		} else {
			rightSideThrottle = -(rightSideThrottle * rightSideThrottle);
		}

		setMotorPower(leftSideThrottle, rightSideThrottle);
	}

	public void setAutoAlignTrue() {
		autoAlign = true;
		// reset the PID controller loop for steering now that we are auto-aligning
		resetAndEnableHeadingPID();
		// Robot.lights.set(LedPatternFactory.autoAlignTrying);
	}

	public void setAutoAlignFalse() {
		autoAlign = false;
		resetAndEnableHeadingPID();
		// Robot.lights.set(LedPatternFactory.autoAlignGotIt);
	}

	public void speedRacerDrive(double throttle, double rawSteeringX, boolean quickTurn) {
		double leftPower, rightPower;
		double rotation = 0;
		double adjustedSteeringX = rawSteeringX * throttle;
		final double QUICK_TURN_GAIN = 0.55; // 2019: .75. 2020: .75 was too fast.
		final double STD_TURN_GAIN = 1.0; // 2019: 1.5. 2020: 1.5 was too fast// the driver wants the non-quick turn
											// turning a little more responsive.

		int throttleSign;
		if (throttle >= 0.0) {
			throttleSign = 1;
		} else {
			throttleSign = -1;
		}
		if (autoAlign) {
			// DriverStation.reportWarning("Auto align was called in drive base", false);

			// Use the targeting code to get the desired heading
			// m_desiredHeading = Robot.targeting.desiredHeading();

			// Use the heading PID to provide the rotation input
			rotation = maintainHeading();

		} else {
			// not using camera targeting right now

			// check for if steering input is essentially zero
			if (false /*
						 * turn off heading correction for now (-0.01 < rawSteeringX) && (rawSteeringX <
						 * 0.01)
						 */) {
				// no turn being commanded, drive straight or stay still
				m_iterationsSinceRotationCommanded++;
				if ((-0.01 < throttle) && (throttle < 0.01)) {
					// no motion commanded, stay still
					m_iterationsSinceMovementCommanded++;
					rotation = 0.0;
					m_desiredHeading = getHeading(); // whenever motionless, set desired heading to current heading
					// reset the PID controller loop now that we have a new desired heading
					resetAndEnableHeadingPID();
				} else {
					// driving straight
					if ((m_iterationsSinceRotationCommanded == LOOPS_GYRO_DELAY)
							|| (m_iterationsSinceMovementCommanded >= LOOPS_GYRO_DELAY)) {
						// exactly LOOPS_GYRO_DELAY iterations with no commanded turn,
						// or haven't had movement commanded for longer than LOOPS_GYRO_DELAY,
						// so we want to take steps to preserve our current heading hereafter

						// get current heading as desired heading
						m_desiredHeading = getHeading();

						// reset the PID controller loop now that we have a new desired heading
						resetAndEnableHeadingPID();
						rotation = 0.0;
					} else if (m_iterationsSinceRotationCommanded < LOOPS_GYRO_DELAY) {
						// not long enough since we were last turning,
						// just drive straight without special heading maintenance
						rotation = 0.0;
					} else if (m_iterationsSinceRotationCommanded > LOOPS_GYRO_DELAY) {
						// after more then LOOPS_GYRO_DELAY iterations since commanded turn,
						// maintain the target heading
						rotation = maintainHeading();
					}
					m_iterationsSinceMovementCommanded = 0;
				}
			} else {
				// commanding a turn, reset iterationsSinceRotationCommanded
				m_iterationsSinceRotationCommanded = 0;
				m_iterationsSinceMovementCommanded = 0;

				if (quickTurn) {
					// want a high-rate turn (also allows "spin" behavior)
					rotation = rawSteeringX * throttleSign * QUICK_TURN_GAIN;
				} else {
					// want a standard rate turn (scaled by the throttle)
					rotation = adjustedSteeringX * STD_TURN_GAIN; // set the turn to the throttle-adjusted steering
					// input

				}
			}
		}
		// power to each wheel is a combination of the throttle and rotation
		leftPower = throttle + rotation;
		rightPower = throttle - rotation;
		setMotorPower(leftPower, rightPower);

	}

	public int stage = 0;
	public final double DELAY = 4;
	public double timerStart = Timer.getFPGATimestamp();

	public boolean selfTest() {
		return false;
	}

	/**
	 * The headings are from -180 to 180. The CurrentHeading is the current robot
	 * orientation. The TargetHeading is where we want the robot to face.
	 * 
	 * e.g. Current = 0, Target = 10, error = 10 Current = 180, Target = -170, error
	 * = 10 (we need to turn 10 deg Clockwise to get to -170) Current = -90, Target
	 * = 180, error = -90 (we need to turn 90 deg Counter-Clockwise to get to 180)
	 * Current = 10, target = -10, error = -20 (we need to turn Counterclockwise -20
	 * deg)
	 * 
	 * @param CurrentHeading
	 * @param TargetHeading
	 * @return
	 */
	private double maintainHeading() {
		double headingCorrection = 0.0;

		// below line is essential to let the Heading PID know the current heading error
		m_HeadingError.m_Error = m_desiredHeading - getHeading();

		if (m_useHeadingCorrection) {
			headingCorrection = -m_HeadingCorrection.HeadingCorrection;
		} else {
			headingCorrection = 0.0;
		}

		// check for major heading changes and take action to prevent
		// integral windup if there is a major heading error
		if (Math.abs(m_HeadingError.m_Error) > 10.0) {
			if (!m_HeadingPidPreventWindup) {
				m_HeadingPid.setI(0.0);
				resetAndEnableHeadingPID();
				m_HeadingPidPreventWindup = true;
			}
		} else {
			m_HeadingPidPreventWindup = false;
			m_HeadingPid.setI(0.001);
		}

		return headingCorrection;
	}

	public void rotate(double RotateX) {
		m_desiredHeading += RotateX;
		if (m_desiredHeading > 180) {
			m_desiredHeading -= 360;
		}
		if (m_desiredHeading < -180) {
			m_desiredHeading += 360;
		}
		m_iterationsSinceRotationCommanded = LOOPS_GYRO_DELAY + 1; // hack so speedracerdrive maintains heading
		m_iterationsSinceMovementCommanded = 0;
	}

	public void rotateToHeading(double desiredHeading) {
		m_desiredHeading = desiredHeading;
	}

	// **********************************************DISPLAY****************************************************

	@Override
	public void periodic() {
		updateSmartDashboard();
	}

	public void updateSmartDashboard() {
		displayGyroInfo();

		// ***** KBS: Uncommenting below, as it takes a LONG time to get PDP values
		// updateSdbPdp();

		SmartDashboard.putNumber("Left Front Encoder Counts", leftFrontTalon.getSelectedSensorPosition(0));
		SmartDashboard.putNumber("Right Front Encoder Counts", rightFrontTalon.getSelectedSensorPosition(0));
		SmartDashboard.putNumber("Left Rear Encoder Counts", leftRearTalon.getSelectedSensorPosition(0));
		SmartDashboard.putNumber("Right Rear Encoder Counts", rightRearTalon.getSelectedSensorPosition(0));

		// Note: getSpeed() returns ticks per 0.1 seconds
		SmartDashboard.putNumber("Left Encoder Speed", leftFrontTalon.getSelectedSensorVelocity(0));
		SmartDashboard.putNumber("Right Encoder Speed", rightFrontTalon.getSelectedSensorVelocity(0));

		// To convert ticks per 0.1 seconds into feet per second
		// a - multiply be 10 (tenths of second per second)
		// b - divide by 12 (1 foot per 12 inches)
		// c - multiply by distance (in inches) per pulse
		SmartDashboard.putNumber("Left Speed (fps)",
				leftFrontTalon.getSelectedSensorVelocity(0) * 10 / 12 * DISTANCE_PER_PULSE);
		SmartDashboard.putNumber("Right Speed (fps)",
				rightFrontTalon.getSelectedSensorVelocity(0) * 10 / 12 * DISTANCE_PER_PULSE);

		SmartDashboard.putNumber("Left Talon Output Voltage", leftFrontTalon.getOutputVoltage());
		SmartDashboard.putNumber("Right Talon Output Voltage", rightFrontTalon.getOutputVoltage());

		SmartDashboard.putBoolean("Closed Loop Mode", m_closedLoopMode);
		SmartDashboard.putBoolean("Speed Racer Drive Mode", m_speedRacerDriveMode);

		SmartDashboard.putBoolean("Heading Correction Mode", m_useHeadingCorrection);
		SmartDashboard.putNumber("Heading Desired", m_desiredHeading);
		SmartDashboard.putNumber("Heading Error", m_HeadingError.m_Error);
		SmartDashboard.putNumber("Heading Correction", -m_HeadingCorrection.HeadingCorrection);

		// SmartDashboard.putNumber("Joystick Drive Throttle",
		// Robot.oi.driveThrottle());
		// SmartDashboard.putNumber("Joystick SteeringX", Robot.oi.steeringX());

		// determine currentSpeed and display it
		// double currentSpeed = getLeftSpeed() > getRightSpeed() ? getLeftSpeed() :
		// getRightSpeed();

		// display current speed to driver
		// SmartDashboard.putNumber("Current Speed", currentSpeed);
	}

	private static final double CAMERA_LAG = 0.150; // was .200; changing to .150 at CMP

	public void updateHistory() {
		double now = Timer.getFPGATimestamp();
		headingHistory.add(now, getHeading());
	}

	public double getHeadingForCapturedImage() {
		double now = Timer.getFPGATimestamp();
		double indexTime = now - CAMERA_LAG;
		return headingHistory.getAzForTime(indexTime);
	}

	/**
	 * Start a distance travel
	 */
	public void saveInitialWheelDistance() {
		m_initialWheelDistance = (getLeftEncoder() + getRightEncoder()) / 2;
	}

	/**
	 * Calculate the distance travelled. Return the second shortest distance. If a
	 * wheel is floating, it will have a larger value - ignore it. If a wheel is
	 * stuck, it will have a small value
	 * 
	 * @return
	 */
	public double getWheelDistance() {
		double dist = (getLeftEncoder() + getRightEncoder()) / 2;
		return dist - m_initialWheelDistance;
	}

	// NOTE the difference between rotateToHeading(...) and goToHeading(...)
	public void setDesiredHeading(double desiredHeading) {
		m_desiredHeading = desiredHeading;
		m_iterationsSinceRotationCommanded = LOOPS_GYRO_DELAY + 1;
		m_iterationsSinceMovementCommanded = 0;

		// reset the heading control loop for the new heading
		resetAndEnableHeadingPID();
	}

}
