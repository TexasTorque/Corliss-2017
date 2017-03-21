package org.texastorque.io;

import org.texastorque.constants.Constants;
import org.texastorque.constants.Ports;
import org.texastorque.torquelib.component.TorqueMotor;
import static org.texastorque.torquelib.util.TorqueMathUtil.constrain;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.VictorSP;

public class RobotOutput {

	/* Singleton */
	
	private static volatile RobotOutput instance;
	
	
	/* Constants */
	
	private static final double LIMIT_SPEED = Constants.IN_LIMIT.getDouble();

	
	/* DriveBase */
	
	private TorqueMotor DB_leftFore;
	private TorqueMotor DB_leftRear;
	private TorqueMotor DB_rightFore;
	private TorqueMotor DB_rightRear;
	private DoubleSolenoid DB_shiftSole;


	/* Flywheel */
	
	private TorqueMotor FW_leftSole;
	private TorqueMotor FW_rightSole;
	private TorqueMotor FW_gateLeft;
	private TorqueMotor FW_gateRight;
	private Solenoid FW_hood;
	private Relay FW_light;
	
	
	/* Intake */
	
	private TorqueMotor IN_sole;

	
	/* Twinsters */
	
	private TorqueMotor TW_feederSole;
	private TorqueMotor TW_rightSole;
	private TorqueMotor TW_leftSole;

	
	/* Climber */
	
	private TorqueMotor CL_left;
	private TorqueMotor CL_right;

	
	/* Gear Mechanism */
	
	private DoubleSolenoid GC_deploy;
	private TorqueMotor GC_belt;
	private Solenoid GH_sole;
	private Solenoid GR_sole;
	
	
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
		FW_gateLeft = new TorqueMotor(new VictorSP(Ports.FW_GATEL), !flipShooter);
		FW_gateRight = new TorqueMotor(new VictorSP(Ports.FW_GATER), flipShooter);
		FW_hood = new Solenoid(Ports.FW_HOOD);

		FW_light = new Relay(Ports.FW_LIGHT, Relay.Direction.kBoth);
		
		IN_sole = new TorqueMotor(new VictorSP(Ports.IN_LOWER), flipIntake);

		TW_leftSole = new TorqueMotor(new VictorSP(Ports.TW_LEFT), flipTwinsters);
		TW_rightSole = new TorqueMotor(new VictorSP(Ports.TW_RIGHT), !flipTwinsters);
		TW_feederSole = new TorqueMotor(new VictorSP(Ports.TW_FEEDER), flipFeeder);

		CL_left = new TorqueMotor(new VictorSP(Ports.CL_LEFT), flipClimber);
		CL_right = new TorqueMotor(new VictorSP(Ports.CL_RIGHT), flipClimber);

		GR_sole = new Solenoid(Ports.GR_SOLE);
		GH_sole = new Solenoid(Ports.GH_SOLE);
		
		GC_deploy = new DoubleSolenoid(Ports.GC_DEPLOY_A, Ports.GC_DEPLOY_B);
		GC_belt = new TorqueMotor(new VictorSP(Ports.GC_BELT), false);
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
	public void setIntakeSpeed(double speed) {
		speed = constrain(speed, LIMIT_SPEED);
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
		leftSpeed = constrain(leftSpeed, LIMIT_SPEED);
		rightSpeed = constrain(rightSpeed, LIMIT_SPEED);
		feederSpeed = constrain(feederSpeed, LIMIT_SPEED);
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
	
	public void deployGearCollector(boolean deployed) {
		GC_deploy.set(deployed ? Value.kForward : Value.kReverse);
		if(deployed)
			GC_belt.set(1);
		else
			GC_belt.set(0);
	}

	public static synchronized RobotOutput getInstance() {
		return instance == null ? instance = new RobotOutput() : instance;
	}
}
