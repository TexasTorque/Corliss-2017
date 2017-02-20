package org.texastorque.io;

import org.texastorque.constants.Constants;
import org.texastorque.constants.Ports;
import org.texastorque.torquelib.component.TorqueMotor;
import org.texastorque.torquelib.util.TorqueMathUtil;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.VictorSP;

public class RobotOutput {

	private static RobotOutput instance;

	private TorqueMotor DB_leftFore;
	private TorqueMotor DB_leftRear;
	private TorqueMotor DB_rightFore;
	private TorqueMotor DB_rightRear;
	private DoubleSolenoid DB_shiftSole;
	
	private TorqueMotor FW_leftSole;
	private TorqueMotor FW_rightSole;
	private TorqueMotor FW_gateLeft;
	private TorqueMotor FW_gateRight;

	private TorqueMotor IN_sole;

	private TorqueMotor TW_feederSole;
	private TorqueMotor TW_rightSole;
	private TorqueMotor TW_leftSole;

	private TorqueMotor CL_left;
	private TorqueMotor CL_right;

	private DoubleSolenoid BN_sole;

	private Solenoid GH_sole;
	private Solenoid GR_right;
	private Solenoid GR_left;

	private boolean flipDriveTrain = false;
	private boolean flipShooter = true;
	private boolean flipIntake = true;
	private boolean flipFeeder = true;
	private boolean flipTwinsters = false;
	private boolean flipClimber = true;

	public RobotOutput() {
		DB_leftFore = new TorqueMotor(new VictorSP(Ports.DB_LEFTFORE), flipDriveTrain);
		DB_leftRear = new TorqueMotor(new VictorSP(Ports.DB_LEFTREAR), flipDriveTrain);
		DB_rightFore = new TorqueMotor(new VictorSP(Ports.DB_RIGHTFORE), !flipDriveTrain);
		DB_rightRear = new TorqueMotor(new VictorSP(Ports.DB_RIGHTREAR), !flipDriveTrain);
		DB_shiftSole = new DoubleSolenoid(Ports.DB_SHIFT_A, Ports.DB_SHIFT_B);

		FW_leftSole = new TorqueMotor(new VictorSP(Ports.FW_LEFT), flipShooter);
		FW_rightSole = new TorqueMotor(new VictorSP(Ports.FW_RIGHT), !flipShooter);
		FW_gateLeft = new TorqueMotor(new VictorSP(Ports.FW_GATEL), flipShooter);
		FW_gateRight = new TorqueMotor(new VictorSP(Ports.FW_GATER), !flipShooter);

		IN_sole = new TorqueMotor(new VictorSP(Ports.IN_LOWER), flipIntake);

		TW_leftSole = new TorqueMotor(new VictorSP(Ports.TW_LEFT), flipTwinsters);
		TW_rightSole = new TorqueMotor(new VictorSP(Ports.TW_RIGHT), !flipTwinsters);
		TW_feederSole = new TorqueMotor(new VictorSP(Ports.TW_FEEDER), flipFeeder);

		CL_left = new TorqueMotor(new VictorSP(Ports.CL_LEFT), flipClimber);
		CL_right = new TorqueMotor(new VictorSP(Ports.CL_RIGHT), flipClimber);

		BN_sole = new DoubleSolenoid(Ports.BN_B, Ports.BN_A);

		GR_left = new Solenoid(Ports.GR_LEFT);
		GR_right = new Solenoid(Ports.GR_RIGHT);

		GH_sole = new Solenoid(Ports.GH_SOLE);
	}

	/**
	 * Set the motor speeds of the Robot's drivetrain. Both input numbers should
	 * range from 1 => -1.
	 * 
	 * @param leftSpeed
	 *            - The speed the leftside motors should be set to.
	 * @param rightSpeed
	 *            - The speed the rightside motors should be set to.
	 */
	public void setDriveBaseSpeed(double leftSpeed, double rightSpeed) {
		DB_leftFore.set(leftSpeed);
		DB_leftRear.set(leftSpeed);
		DB_rightFore.set(rightSpeed);
		DB_rightRear.set(rightSpeed);
	}

	/**
	 * Set the motor speeds of the Robot's flywheel & gate. Both input numbers
	 * should range from 1 => -1.
	 * 
	 * @param leftSpeed
	 *            - The speed the leftside motor should be set to.
	 * @param rightSpeed
	 *            - The speed the rightside motor should be set to.
	 * @param gateSpeed
	 *            - The speed the gate motor should be set to.
	 */
	public void setFlyWheelSpeed(double leftSpeed, double rightSpeed, double gateSpeed) {
		FW_leftSole.set(leftSpeed);
		FW_rightSole.set(rightSpeed);
		FW_gateLeft.set(gateSpeed);
		FW_gateRight.set(gateSpeed);
	}

	/**
	 * Set the motor speeds of the Robot's intake. Both input numbers should
	 * range from 1 => -1.
	 * 
	 * @param upperSpeed
	 *            - The speed the leftside motor should be set to.
	 * @param lowerSpeed
	 *            - The speed the rightside motor should be set to.
	 */
	public void setIntakeSpeed(double speed) {
		speed = TorqueMathUtil.constrain(speed, Constants.IN_LIMIT.getDouble());
		IN_sole.set(speed);
	}

	/**
	 * Set the motor speeds of the Robot's twinsters. Both input numbers should
	 * range from 1 => -1.
	 * 
	 * @param leftSpeed
	 *            - The speed the leftside motor should be set to.
	 * @param rightSpeed
	 *            - The speed the rightside motor should be set to.
	 */
	public void setTwinstersSpeed(double leftSpeed, double rightSpeed, double feederSpeed) {
		leftSpeed = TorqueMathUtil.constrain(leftSpeed, Constants.IN_LIMIT.getDouble());
		rightSpeed = TorqueMathUtil.constrain(rightSpeed, Constants.IN_LIMIT.getDouble());
		feederSpeed = TorqueMathUtil.constrain(feederSpeed, Constants.IN_LIMIT.getDouble());
		
		TW_feederSole.set(feederSpeed);
		TW_rightSole.set(rightSpeed);
		TW_leftSole.set(leftSpeed);
	}

	/**
	 * Set the motor speeds of the Robot's cimber. The input numbers should
	 * range from 1 => -1.
	 * 
	 * @param leftSpeed
	 *            - The speed the leftside motor should be set to.
	 */
	public void setClimberSpeed(double speed) {
		CL_left.set(speed);
		CL_right.set(speed);
	}

	public void openGearRamp(boolean open) {
		GR_right.set(open);
		GR_left.set(open);
	}

	/**
	 * Set the state of the bin pneumatics.
	 * 
	 * @param output
	 *            - set the pneumatic in or out.
	 */
	public void setBinExtension(boolean extended) {
		BN_sole.set(extended ? Value.kReverse : Value.kForward);
	}

	public void extendGearHolder(boolean extended) {
		GH_sole.set(extended);
	}
	
	public void upShift(boolean upShift){
		DB_shiftSole.set(upShift ? Value.kForward:Value.kReverse);
	}
	
	public static RobotOutput getInstance() {
		return instance == null ? instance = new RobotOutput() : instance;
	}

}
