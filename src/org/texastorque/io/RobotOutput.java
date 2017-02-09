package org.texastorque.io;

import org.texastorque.torquelib.component.TorqueMotor;

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
	
	private boolean flipDriveTrain = false;
	private boolean flipShooter = false;
	private boolean flipIntake = false;
	private boolean flipConveyor = false;
	
	public RobotOutput() {
		DB_leftFore = new TorqueMotor(new VictorSP(0), flipDriveTrain);
		DB_leftRear = new TorqueMotor(new VictorSP(0), flipDriveTrain);
		DB_leftRear = new TorqueMotor(new VictorSP(0), !flipDriveTrain);
		DB_leftRear = new TorqueMotor(new VictorSP(0), !flipDriveTrain);
		
		FW_leftSole = new TorqueMotor(new VictorSP(0), flipShooter);
		FW_rightSole = new TorqueMotor(new VictorSP(0), flipShooter);
		
		IN_lowerSole = new TorqueMotor(new VictorSP(0), flipIntake);
		IN_upperSole = new TorqueMotor(new VictorSP(0), flipIntake);
		
		CN_rightSole = new TorqueMotor(new VictorSP(0), flipConveyor);
		CN_leftSole = new TorqueMotor(new VictorSP(0), flipConveyor);
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
		IN_lowerSole.set(rightSpeed);
		IN_upperSole.set(leftSpeed);
	}
	
	public static RobotOutput getInstance() {
		return instance == null ? instance = new RobotOutput() : instance;
	}

}
