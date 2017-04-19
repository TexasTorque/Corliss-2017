package org.texastorque.io;

import org.texastorque.constants.Constants;
import org.texastorque.constants.Ports;
import org.texastorque.torquelib.component.TorqueMotor;
import org.texastorque.torquelib.util.TorqueMathUtil;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.Relay;
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
	private Solenoid FW_hood;

	private Relay FW_light;
	
	private TorqueMotor IN_upper;
	private TorqueMotor IN_lower;
	
	private TorqueMotor TW_rightSole;
	private TorqueMotor TW_leftSole;

	private TorqueMotor CL_left;
	private TorqueMotor CL_right;

	private TorqueMotor GC_angleMotor;
	private TorqueMotor GC_intakeMotor;
	
	private Solenoid GH_sole;
	private Solenoid GR_sole;
	
	private boolean flipDriveTrain = false;
	private boolean flipShooter = false;
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
		FW_hood = new Solenoid(Ports.FW_HOOD);

		FW_light = new Relay(Ports.FW_LIGHT, Relay.Direction.kBoth);
		
		IN_upper = new TorqueMotor(new VictorSP(Ports.IN_LOWER), flipIntake);
		IN_lower = new TorqueMotor(new VictorSP(Ports.IN_UPPER), flipIntake);

		TW_leftSole = new TorqueMotor(new VictorSP(Ports.TW_LEFT), flipTwinsters);
		TW_rightSole = new TorqueMotor(new VictorSP(Ports.TW_RIGHT), !flipTwinsters);

		CL_left = new TorqueMotor(new VictorSP(Ports.CL_LEFT), flipClimber);
		CL_right = new TorqueMotor(new VictorSP(Ports.CL_RIGHT), flipClimber);

		GR_sole = new Solenoid(Ports.GR_SOLE);
		GH_sole = new Solenoid(Ports.GH_SOLE);
		
		GC_angleMotor = new TorqueMotor(new VictorSP(Ports.GC_ANGLE), false);
		GC_intakeMotor = new TorqueMotor(new VictorSP(Ports.GC_INTAKE), true);
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
	public void setFlyWheelSpeed(double leftSpeed, double rightSpeed) {
		FW_leftSole.set(leftSpeed);
		FW_rightSole.set(rightSpeed);
	}

	public void setGateSpeed(double leftSpeed, double rightSpeed) {
		FW_gateLeft.set(leftSpeed);
		FW_gateRight.set(rightSpeed);
	}
	
	public void setGateSpeed(double gateSpeed) {
		FW_gateLeft.set(gateSpeed);
		FW_gateRight.set(gateSpeed);
	}

	public void setHoodSpeed(boolean hood) {
		FW_hood.set(hood);
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
	public void setIntakeSpeed(double upperSpeed, double lowerSpeed) {
		upperSpeed = TorqueMathUtil.constrain(upperSpeed, Constants.IN_LIMIT.getDouble());
		IN_upper.set(upperSpeed);
		IN_lower.set(lowerSpeed);
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
	public void setTwinstersSpeed(double leftSpeed, double rightSpeed) {
		leftSpeed = TorqueMathUtil.constrain(leftSpeed, Constants.IN_LIMIT.getDouble());
		rightSpeed = TorqueMathUtil.constrain(rightSpeed, Constants.IN_LIMIT.getDouble());
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
		GR_sole.set(open);
	}

	public void extendGearHolder(boolean extended) {
		GH_sole.set(extended);
	}

	public void upShift(boolean upShift) {
		DB_shiftSole.set(upShift ? Value.kForward : Value.kReverse);
	}
	
	public void setLight(boolean light) {
		if(!light) {
			FW_light.set(Relay.Value.kOff);
		} else {
			FW_light.set(Relay.Value.kOn);
		}
	}
	
	public void setGearCollectorSpeed(double angleSpeed, double intakeSpeed) {
		GC_angleMotor.set(TorqueMathUtil.constrain(angleSpeed, 1));
		if(!Input.getInstance().GC_override)
			GC_intakeMotor.set(intakeSpeed);
	}
	
	public void setGearCollectorSpeed(double intakeSpeed) {
		if(!Input.getInstance().GC_override)
			GC_intakeMotor.set(intakeSpeed);
	}
	
	public static RobotOutput getInstance() {
		return instance == null ? instance = new RobotOutput() : instance;
	}

}
