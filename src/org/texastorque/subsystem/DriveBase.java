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
public class DriveBase extends Subsystem {

	private static DriveBase instance;

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

	private void run() {
		switch (type) {
		case TELEOPGEARPLACE:
			break;
		case AUTOIRDRIVE:
			double error = Feedback.getInstance().getDB_distance() - 8;
			if (error >= 6 && error - previousError < 0) {
				leftSpeed = leftRIMP.calculate(-error, Feedback.getInstance().getDB_leftRate());
				rightSpeed = rightRIMP.calculate(-error, Feedback.getInstance().getDB_rightRate());
			} else {
				leftSpeed = 0;
				rightSpeed = 0;
			}
			previousError = error;
			output();
			break;
		case AUTODRIVE:
			setpoint = i.getDB_setpoint();
			if (setpoint != previousSetpoint) {
				previousSetpoint = setpoint;
				tmp.generateTrapezoid(setpoint, 0d, 0d);
				prevTime = Timer.getFPGATimestamp();
			}
			if (TorqueMathUtil.near(setpoint, Feedback.getInstance().getDB_leftDistance(), .5)
					&& TorqueMathUtil.near(setpoint, Feedback.getInstance().getDB_rightDistance(), .5))
				Auto.getInstance().setActionDone();
			double dt = Timer.getFPGATimestamp() - prevTime;
			prevTime = Timer.getFPGATimestamp();
			tmp.calculateNextSituation(dt);

			targetPosition = tmp.getCurrentPosition();
			targetVelocity = tmp.getCurrentVelocity();
			targetAcceleration = tmp.getCurrentAcceleration();

			leftSpeed = leftPV.calculate(tmp, Feedback.getInstance().getDB_leftDistance(),
					Feedback.getInstance().getDB_leftRate());
			rightSpeed = rightPV.calculate(tmp, Feedback.getInstance().getDB_rightDistance(),
					Feedback.getInstance().getDB_rightRate());
			upShift = false;
			output();
			break;
		case AUTOTURN:
			turnSetpoint = i.getDB_turnSetpoint();
			if (turnSetpoint != turnPreviousSetpoint) {
				turnPreviousSetpoint = turnSetpoint;
				turnProfile.generateTrapezoid(turnSetpoint, 0.0, 0.0);
				prevTime = Timer.getFPGATimestamp();
			}
			if (TorqueMathUtil.near(turnSetpoint, Feedback.getInstance().getDB_angle(), .5))
				Auto.getInstance().setActionDone();
			dt = Timer.getFPGATimestamp() - prevTime;
			prevTime = Timer.getFPGATimestamp();
			turnProfile.calculateNextSituation(dt);

			targetAngle = turnProfile.getCurrentPosition();
			targetAngularVelocity = turnProfile.getCurrentVelocity();

			leftSpeed = turnPV.calculate(turnProfile, Feedback.getInstance().getDB_angle(),
					Feedback.getInstance().getDB_angleRate());
			rightSpeed = -leftSpeed;
			upShift = false;
			output();
			break;
		case TELEOP:
			leftSpeed = i.getDB_leftSpeed();
			rightSpeed = i.getDB_rightSpeed();
			if (Feedback.getInstance().getDB_leftRate() < -20 && Feedback.getInstance().getDB_rightRate() < -20
					&& i.flipCheck()) {
				leftSpeed = rightSpeed = 0.0;
			}
			upShift = i.getUpShift();
			output();
			break;
		}
	}

	private void output() {
		if (kiddieMode) {
			leftSpeed = TorqueMathUtil.constrain(leftSpeed, .3);
			rightSpeed = TorqueMathUtil.constrain(rightSpeed, .3);
		} else {
			leftSpeed = TorqueMathUtil.constrain(leftSpeed, 1.0);
			rightSpeed = TorqueMathUtil.constrain(rightSpeed, 1.0);
		}
		RobotOutput.getInstance().upShift(upShift);
		RobotOutput.getInstance().setDriveBaseSpeed(leftSpeed, rightSpeed);
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

	public static DriveBase getInstance() {
		return instance == null ? instance = new DriveBase() : instance;
	}

}
