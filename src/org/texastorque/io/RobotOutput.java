package org.texastorque.io;

import org.texastorque.constants.Ports;
import org.texastorque.torquelib.component.TorqueMotor;

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
	
	private TorqueMotor FW_leftSole;
	private TorqueMotor FW_rightSole;

	private TorqueMotor IN_lowerSole;
	private TorqueMotor IN_upperSole;
	
	private TorqueMotor CN_rightSole;
	private TorqueMotor CN_leftSole;
	
	private TorqueMotor TW_rightSole;
	private TorqueMotor TW_leftSole;
	
	private TorqueMotor CL_left;
	private TorqueMotor CL_right;
	
	private DoubleSolenoid BN_left;
	private DoubleSolenoid BN_right;

	private Solenoid GH_sole;
	private Solenoid GR_right;
	private Solenoid GR_left;
	
	private boolean flipDriveTrain = false;
	private boolean flipShooter = false;
	private boolean flipIntake = false;
	private boolean flipConveyor = false;
	private boolean flipTwinsters = false;
	private boolean flipClimber = false;
	
	public RobotOutput() {
		DB_leftFore = new TorqueMotor(new VictorSP(Ports.DB_LEFTFORE), flipDriveTrain);
		DB_leftRear = new TorqueMotor(new VictorSP(Ports.DB_LEFTREAR), flipDriveTrain);
		DB_leftRear = new TorqueMotor(new VictorSP(Ports.DB_RIGHTFORE), !flipDriveTrain);
		DB_leftRear = new TorqueMotor(new VictorSP(Ports.DB_RIGHTREAR), !flipDriveTrain);
		
		FW_leftSole = new TorqueMotor(new VictorSP(Ports.FW_LEFT), flipShooter);
		FW_rightSole = new TorqueMotor(new VictorSP(Ports.FW_RIGHT), flipShooter);
		
		IN_lowerSole = new TorqueMotor(new VictorSP(Ports.IN_LOWER), flipIntake);
		IN_upperSole = new TorqueMotor(new VictorSP(Ports.IN_UPPER), flipIntake);
		
		CN_leftSole = new TorqueMotor(new VictorSP(Ports.CN_LEFT), flipConveyor);
		CN_rightSole = new TorqueMotor(new VictorSP(Ports.CN_RIGHT), flipConveyor);
		
		TW_leftSole = new TorqueMotor(new VictorSP(Ports.TW_LEFT), flipTwinsters);
		TW_rightSole = new TorqueMotor(new VictorSP(Ports.TW_RIGHT), flipTwinsters);
		
		CL_left = new TorqueMotor(new VictorSP(Ports.CL_LEFT), flipClimber);
		CL_right = new TorqueMotor(new VictorSP(Ports.CL_RIGHT), flipClimber);
		
		BN_left = new DoubleSolenoid(Ports.BN_LEFT_A, Ports.BN_LEFT_B);
		BN_right = new DoubleSolenoid(Ports.BN_RIGHT_A, Ports.BN_RIGHT_B);

		GR_left=new Solenoid(Ports.GR_LEFT);
		GR_right=new Solenoid(Ports.GR_RIGHT);
		
		GH_sole=new Solenoid(Ports.GH_SOLE);
	}
	
	/**
	 * Set the motor speeds of the Robot's drivetrain.  Both input numbers should range from
	 * 1 => -1.
	 * @param leftSpeed - The speed the leftside motors should be set to.
	 * @param rightSpeed  - The speed the rightside motors should be set to.
	 */
	public void setDriveBaseSpeed(double leftSpeed, double rightSpeed) {
		DB_leftFore.set(leftSpeed);
		DB_leftRear.set(leftSpeed);
		DB_rightFore.set(rightSpeed);
		DB_rightRear.set(rightSpeed);
	}
	
	/**
	 * Set the motor speeds of the Robot's flywheel.  Both input numbers should range from
	 * 1 => -1.
	 * @param leftSpeed - The speed the leftside motor should be set to.
	 * @param rightSpeed  - The speed the rightside motor should be set to.
	 */
	public void setFlyWheelSpeed(double leftSpeed, double rightSpeed) {
		FW_leftSole.set(leftSpeed);
		FW_rightSole.set(rightSpeed);
	}

	/**
	 * Set the motor speeds of the Robot's intake.  Both input numbers should range from
	 * 1 => -1.
	 * @param upperSpeed - The speed the leftside motor should be set to.
	 * @param lowerSpeed  - The speed the rightside motor should be set to.
	 */
	public void setIntakeSpeed(double upperSpeed, double lowerSpeed) {
		IN_lowerSole.set(lowerSpeed);
		IN_upperSole.set(upperSpeed);
	}
	
	/**
	 * Set the motor speeds of the Robot's conveyor.  Both input numbers should range from
	 * 1 => -1.
	 * @param leftSpeed - The speed the leftside motor should be set to.
	 * @param rightSpeed  - The speed the rightside motor should be set to.
	 */
	public void setConveyorSpeed(double leftSpeed, double rightSpeed) {
		CN_rightSole.set(rightSpeed);
		CN_leftSole.set(leftSpeed);
	}
	
	/**
	 * Set the motor speeds of the Robot's twinsters.  Both input numbers should range from
	 * 1 => -1.
	 * @param leftSpeed - The speed the leftside motor should be set to.
	 * @param rightSpeed  - The speed the rightside motor should be set to.
	 */
	public void setTwinstersSpeed(double leftSpeed, double rightSpeed) {
		TW_rightSole.set(rightSpeed);
		TW_leftSole.set(leftSpeed);
	}
	
	/**
	 * Set the motor speeds of the Robot's cimber.  The input numbers should range from
	 * 1 => -1.
	 * @param leftSpeed - The speed the leftside motor should be set to.
	 */
	public void setTwinstersSpeed(double speed) {
		CL_left.set(speed);
		CL_right.set(speed);
	}
	
	public void openGearRamp(boolean open){
		GR_right.set(open);
		GR_left.set(open);
	}
	
	/**
	 * Set the state of the bin pneumatics.
	 * @param output - set the pneumatic in or out.
	 */
	public void setBinExtension(boolean extended) {
		BN_left.set(extended ? Value.kForward : Value.kReverse);
		BN_right.set(extended ? Value.kForward : Value.kReverse);
	}
	
	public void extendGearHolder(boolean extended){
		GH_sole.set(extended);
	}
	
	public static RobotOutput getInstance() {
		return instance == null ? instance = new RobotOutput() : instance;
	}

}
