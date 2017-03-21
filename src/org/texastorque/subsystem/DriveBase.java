package org.texastorque.subsystem;

import org.texastorque.auto.Auto;
import org.texastorque.constants.Constants;
import org.texastorque.feedback.Feedback;
import org.texastorque.io.RobotOutput;
import org.texastorque.torquelib.controlLoop.TorquePV;
import org.texastorque.torquelib.controlLoop.TorqueRIMP;
import org.texastorque.torquelib.controlLoop.TorqueTMP;
import org.texastorque.torquelib.util.TorqueMathUtil;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

// instanced, include pertinent methods (remove this header before first commit)
public class DriveBase extends Subsystem implements Runnable {
	
	private static final double SPEED_LIMIT_KIDDIE_MODE = 0.3;
	private static final double SPEED_LIMIT_DEFAULT = 1.0;

	private static volatile DriveBase instance;
	
	private final RobotOutput robotOut = RobotOutput.getInstance();
	private final Feedback feedback = Feedback.getInstance();
	private final Auto autonomous = Auto.getInstance();

	private double leftSpeed = 0d;
	private double rightSpeed = 0d;

	private double setpoint = 0;
	private double previousSetpoint = 0;
	private double prevTime;

	private TorqueTMP tmp;
	private TorquePV leftPV;
	private TorquePV rightPV;
	private TorqueRIMP leftRIMP;
	private TorqueRIMP rightRIMP;
	private double previousError;

	private double targetPosition;
	private double targetVelocity;
	private double targetAcceleration;

	private double turnPreviousSetpoint = 0;
	private double turnSetpoint;

	private TorqueTMP turnProfile;
	private TorquePV turnPV;

	private double targetAngle;
	private double targetAngularVelocity;

	private boolean upShift = false;
	private boolean kiddieMode = false;

	public enum DriveType {
		TELEOP, TELEOPGEARPLACE, AUTODRIVE, AUTOTURN, AUTOOVERRIDE, AUTOIRDRIVE;
	}

	private DriveType type = DriveType.TELEOP;

	public DriveBase() {
		init();
	}

	@Override
	public void autoInit() {
		init();
	}

	@Override
	public void teleopInit() {
		type = DriveType.TELEOP;
		init();
	}

	private void init() {
		tmp = new TorqueTMP(Constants.DB_MVELOCITY.getDouble(), Constants.DB_MACCELERATION.getDouble());
		turnProfile = new TorqueTMP(Constants.DB_MAVELOCITY.getDouble(), Constants.DB_MAACCELERATION.getDouble());
		leftPV = new TorquePV();
		rightPV = new TorquePV();
		turnPV = new TorquePV();

		leftPV.setGains(Constants.DB_LEFT_PV_P.getDouble(), Constants.DB_LEFT_PV_V.getDouble(),
				Constants.DB_LEFT_PV_ffV.getDouble(), Constants.DB_LEFT_PV_ffA.getDouble());
		leftPV.setTunedVoltage(Constants.TUNED_VOLTAGE.getDouble());

		rightPV.setGains(Constants.DB_RIGHT_PV_P.getDouble(), Constants.DB_RIGHT_PV_V.getDouble(),
				Constants.DB_RIGHT_PV_ffV.getDouble(), Constants.DB_RIGHT_PV_ffA.getDouble());
		rightPV.setTunedVoltage(Constants.TUNED_VOLTAGE.getDouble());

		turnPV.setGains(Constants.DB_TURN_PV_P.getDouble(), Constants.DB_TURN_PV_V.getDouble(),
				Constants.DB_TURN_PV_ffV.getDouble(), Constants.DB_TURN_PV_ffA.getDouble());
		turnPV.setTunedVoltage(Constants.TUNED_VOLTAGE.getDouble());

		rightRIMP = new TorqueRIMP(Constants.DB_MVELOCITY.getDouble(), Constants.DB_MACCELERATION.getDouble(), 0);
		leftRIMP = new TorqueRIMP(Constants.DB_MVELOCITY.getDouble(), Constants.DB_MACCELERATION.getDouble(), 0);

		rightRIMP.setGains(Constants.DB_RIMP_P.getDouble(), Constants.DB_RIMP_V.getDouble(),
				Constants.DB_RIMP_ffV.getDouble(), Constants.DB_RIMP_ffA.getDouble());
		leftRIMP.setGains(Constants.DB_RIMP_P.getDouble(), Constants.DB_RIMP_V.getDouble(),
				Constants.DB_RIMP_ffV.getDouble(), Constants.DB_RIMP_ffA.getDouble());

		prevTime = Timer.getFPGATimestamp();
	}

	@Override
	public void autoContinuous() {
		run();
	}

	@Override
	public void teleopContinuous() {
		run();
	}

	@Override
	public void run() {
		switch (type) {
		case TELEOPGEARPLACE:
			break;
			
		case AUTOIRDRIVE:
			runAutoIRDrive();
			break;
			
		case AUTODRIVE:
			runAutoDrive();
			break;
			
		case AUTOTURN:
			runAutoTurn();
			break;
			
		case TELEOP:
			runTeleop();
			break;
			
		default:
			break;
		}

		output();
	}
	
	private void runAutoIRDrive() {
		double error = Feedback.getInstance().getDB_distance() - 8;
		if (error >= 6 && error - previousError < 0) {
			leftSpeed = leftRIMP.calculate(-error, feedback.getDB_leftRate());
			rightSpeed = rightRIMP.calculate(-error, feedback.getDB_rightRate());
		} else {
			leftSpeed = 0;
			rightSpeed = 0;
		}
		previousError = error;
	}
	
	private void runAutoDrive() {
		setpoint = in.getDB_setpoint();
		if (setpoint != previousSetpoint) {
			previousSetpoint = setpoint;
			tmp.generateTrapezoid(setpoint, 0d, 0d);
			prevTime = Timer.getFPGATimestamp();
		}
		
		if (TorqueMathUtil.near(setpoint, feedback.getDB_leftDistance(), .5)
				&& TorqueMathUtil.near(setpoint, feedback.getDB_rightDistance(), .5)) {
			autonomous.setActionDone();
		}
		
		double dt = Timer.getFPGATimestamp() - prevTime;
		prevTime = Timer.getFPGATimestamp();
		tmp.calculateNextSituation(dt);

		targetPosition = tmp.getCurrentPosition();
		targetVelocity = tmp.getCurrentVelocity();
		targetAcceleration = tmp.getCurrentAcceleration();

		leftSpeed = leftPV.calculate(tmp, feedback.getDB_leftDistance(), feedback.getDB_leftRate());
		rightSpeed = rightPV.calculate(tmp, feedback.getDB_rightDistance(), feedback.getDB_rightRate());
		
		upShift = false;
	}
	
	private void runAutoTurn() {
		turnSetpoint = in.getDB_turnSetpoint();
		if (turnSetpoint != turnPreviousSetpoint) {
			turnPreviousSetpoint = turnSetpoint;
			turnProfile.generateTrapezoid(turnSetpoint, 0.0, 0.0);
			prevTime = Timer.getFPGATimestamp();
		}
		
		if (TorqueMathUtil.near(turnSetpoint, feedback.getDB_angle(), .5)) {
			autonomous.setActionDone();
		}
		
		double dt = Timer.getFPGATimestamp() - prevTime;
		prevTime = Timer.getFPGATimestamp();
		turnProfile.calculateNextSituation(dt);

		targetAngle = turnProfile.getCurrentPosition();
		targetAngularVelocity = turnProfile.getCurrentVelocity();

		leftSpeed = turnPV.calculate(turnProfile, feedback.getDB_angle(), feedback.getDB_angleRate());
		rightSpeed = -leftSpeed;
		
		upShift = false;
	}
	
	private void runTeleop() {
		leftSpeed = in.getDB_leftSpeed();
		rightSpeed = in.getDB_rightSpeed();
		
		if (feedback.getDB_leftRate() < -20 && feedback.getDB_rightRate() < -20 && in.flipCheck()) {
			leftSpeed = rightSpeed = 0.0;
		}
		
		upShift = in.getUpShift();
	}

	private void output() {
		if (kiddieMode) {
			leftSpeed = TorqueMathUtil.constrain(leftSpeed, SPEED_LIMIT_KIDDIE_MODE);
			rightSpeed = TorqueMathUtil.constrain(rightSpeed, SPEED_LIMIT_KIDDIE_MODE);
		} else {
			leftSpeed = TorqueMathUtil.constrain(leftSpeed, SPEED_LIMIT_DEFAULT);
			rightSpeed = TorqueMathUtil.constrain(rightSpeed, SPEED_LIMIT_DEFAULT);
		}
		
		robotOut.upShift(upShift);
		robotOut.setDriveBaseSpeed(leftSpeed, rightSpeed);
	}

	public void setType(DriveType type) {
		this.type = type;
	}

	@Override
	public void smartDashboard() {
		SmartDashboard.putNumber("DB_LEFTSPEED", leftSpeed);
		SmartDashboard.putNumber("DB_RIGHTSPEED", rightSpeed);
		SmartDashboard.putBoolean("UPSHIFTED", upShift);
		SmartDashboard.putString("DB_TYPE", type.toString());
		SmartDashboard.putNumber("DB_TARGETPOSITION", targetPosition);
		SmartDashboard.putNumber("DB_TARGETVELOCITY", targetVelocity);
		SmartDashboard.putNumber("DB_TARGETACCELERATION", targetAcceleration);

		SmartDashboard.putNumber("DB_TARGETANGLE", targetAngle);
		SmartDashboard.putNumber("DB_TARGETANGULARVELOCITY", targetAngularVelocity);
	}

	public static synchronized DriveBase getInstance() {
		return instance == null ? instance = new DriveBase() : instance;
	}
}
