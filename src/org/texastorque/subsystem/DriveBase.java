package org.texastorque.subsystem;

import org.texastorque.constants.Constants;
import org.texastorque.feedback.Feedback;
import org.texastorque.io.RobotOutput;
import org.texastorque.torquelib.controlLoop.TorquePV;
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
		TELEOP, AUTODRIVE, AUTOTURN, AUTOOVERRIDE;
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
		case AUTODRIVE:
			setpoint = i.getDB_setpoint();
			if (setpoint != previousSetpoint) {
				previousSetpoint = setpoint;
				Feedback.getInstance().resetDB_encoders();
				tmp.generateTrapezoid(setpoint, 0d, 0d);
				prevTime = Timer.getFPGATimestamp();
			}

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
				Feedback.getInstance().resetDB_gyro();
				turnProfile.generateTrapezoid(turnSetpoint, 0.0, 0.0);
				prevTime = Timer.getFPGATimestamp();
			}
			dt = Timer.getFPGATimestamp() - prevTime;
			prevTime = Timer.getFPGATimestamp();
			turnProfile.calculateNextSituation(dt);

			targetAngle = turnProfile.getCurrentPosition();
			targetAngularVelocity = turnProfile.getCurrentVelocity();

			leftSpeed = turnPV.calculate(turnProfile, Feedback.getInstance().getDB_angle(), Feedback.getInstance().getDB_angleRate());
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
		if(kiddieMode) {
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
		SmartDashboard.putNumber("DB_TARGETPOSITION",targetPosition);
		SmartDashboard.putNumber("DB_TARGETVELOCITY",targetVelocity);
		SmartDashboard.putNumber("DB_TARGETACCELERATION",targetAcceleration);

		SmartDashboard.putNumber("DB_TARGETANGLE",targetAngle);
		SmartDashboard.putNumber("DB_TARGETANGULARVELOCITY",targetAngularVelocity);
	}

	public static DriveBase getInstance() {
		return instance == null ? instance = new DriveBase() : instance;
	}

}
