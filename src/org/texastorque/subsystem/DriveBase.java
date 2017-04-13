package org.texastorque.subsystem;

import org.texastorque.auto.AutoManager;
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
public class DriveBase extends Subsystem {

	private static DriveBase instance;

	private double leftSpeed = 0d;
	private double rightSpeed = 0d;

	private double setpoint = 0;
	private double previousSetpoint = 0;
	private double prevTime;
	private double precision;

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
		TELEOP, TELEOPGEARPLACE, AUTODRIVE, AUTOTURN, AUTOOVERRIDE, AUTOVISIONTURN, AUTOIRDRIVE;
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

	@Override
	public void disabledInit() {
		leftSpeed = 0;
		rightSpeed = 0;
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
		switch (type) {
		case AUTOIRDRIVE:
			double error = f.getDB_distance() - 8;
			if (error >= 6 && error - previousError < 0) {
				leftSpeed = leftRIMP.calculate(-error, f.getDB_leftRate());
				rightSpeed = rightRIMP.calculate(-error, f.getDB_rightRate());
			} else {
				leftSpeed = 0;
				rightSpeed = 0;
			}
			previousError = error;
			break;
		case AUTOVISIONTURN:
			error = -f.getPX_HorizontalDegreeOff();
			System.out.println("db_angle: " + f.getDB_angle());
			leftSpeed = leftRIMP.calculate(-error, f.getDB_angleRate());
			rightSpeed = -leftSpeed;
			System.out.println("leftSpeed: " + leftSpeed);
			previousError = error;
			break;
		case AUTODRIVE:
			setpoint = i.getDB_setpoint();
			if (setpoint != previousSetpoint) {
				previousSetpoint = setpoint;
				precision = i.getDB_precision();
				tmp.generateTrapezoid(setpoint, 0d, 0d);
				prevTime = Timer.getFPGATimestamp();
			}
			if (TorqueMathUtil.near(setpoint, f.getDB_leftDistance(), precision)
					&& TorqueMathUtil.near(setpoint, f.getDB_rightDistance(), precision))
				AutoManager.interruptThread();
			double dt = Timer.getFPGATimestamp() - prevTime;
			prevTime = Timer.getFPGATimestamp();
			tmp.calculateNextSituation(dt);

			targetPosition = tmp.getCurrentPosition();
			targetVelocity = tmp.getCurrentVelocity();
			targetAcceleration = tmp.getCurrentAcceleration();

			leftSpeed = leftPV.calculate(tmp, f.getDB_leftDistance(), f.getDB_leftRate());
			rightSpeed = rightPV.calculate(tmp, f.getDB_rightDistance(), f.getDB_rightRate());
			upShift = false;
			break;
		case AUTOTURN:
			turnSetpoint = i.getDB_turnSetpoint();
			if (turnSetpoint != turnPreviousSetpoint) {
				turnPreviousSetpoint = turnSetpoint;
				precision = i.getDB_precision();
				turnProfile.generateTrapezoid(turnSetpoint, 0.0, 0.0);
				prevTime = Timer.getFPGATimestamp();
			}
			if (TorqueMathUtil.near(turnSetpoint, f.getDB_angle(), precision))
				AutoManager.interruptThread();
			dt = Timer.getFPGATimestamp() - prevTime;
			prevTime = Timer.getFPGATimestamp();
			turnProfile.calculateNextSituation(dt);

			targetAngle = turnProfile.getCurrentPosition();
			targetAngularVelocity = turnProfile.getCurrentVelocity();

			leftSpeed = turnPV.calculate(turnProfile, f.getDB_angle(), f.getDB_angleRate());
			rightSpeed = -leftSpeed;
			upShift = false;
			break;
			default:
				leftSpeed = 0;
				rightSpeed = 0;
				break;
		}
		run();
	}

	@Override

	public void teleopContinuous() {
		switch (type) {
		case TELEOPGEARPLACE:
			break;
		case TELEOP:
			leftSpeed = i.getDB_leftSpeed();
			rightSpeed = i.getDB_rightSpeed();
			if (f.getDB_leftRate() < -20 && f.getDB_rightRate() < -20 && i.flipCheck()) {
				leftSpeed = rightSpeed = 0.0;
			}
			upShift = i.getUpShift();
			break;
		default:
			type = DriveType.TELEOP;
			break;
		}
		run();
	}

	public void disabledContinuous() {
		output();
	}
	
	private void run() {
		if (kiddieMode) {
			leftSpeed = TorqueMathUtil.constrain(leftSpeed, .45);
			rightSpeed = TorqueMathUtil.constrain(rightSpeed, .45);
		} else {
			leftSpeed = TorqueMathUtil.constrain(leftSpeed, 1.0);
			rightSpeed = TorqueMathUtil.constrain(rightSpeed, 1.0);
		}
		output();
	}

	private void output() {
		o.upShift(upShift);
		o.setDriveBaseSpeed(leftSpeed, rightSpeed);
	}

	public void setType(DriveType type) {
		this.type = type;
	}

	@Override
	public void smartDashboard() {
		SmartDashboard.putNumber("DB_LEFTSPEED", leftSpeed);
		SmartDashboard.putNumber("DB_RIGHTSPEED", rightSpeed);
		SmartDashboard.putBoolean("DB_UPSHIFTED", upShift);

		SmartDashboard.putString("DBA_TYPE", type.toString());
		SmartDashboard.putNumber("DBA_TARGETPOSITION", targetPosition);
		SmartDashboard.putNumber("DBA_TARGETVELOCITY", targetVelocity);
		SmartDashboard.putNumber("DBA_TARGETACCELERATION", targetAcceleration);
		SmartDashboard.putNumber("DBA_TARGETANGLE", targetAngle);
		SmartDashboard.putNumber("DBA_TARGETANGULARVELOCITY", targetAngularVelocity);
	}

	public static DriveBase getInstance() {
		return instance == null ? instance = new DriveBase() : instance;
	}

}
