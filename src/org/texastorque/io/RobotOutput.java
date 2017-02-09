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
	
	private boolean flipDriveTrain = false;
	private boolean flipShooter = false;
	
	public RobotOutput() {
		DB_leftFore = new TorqueMotor(new VictorSP(0), flipDriveTrain);
		DB_leftRear = new TorqueMotor(new VictorSP(0), flipDriveTrain);
		DB_leftRear = new TorqueMotor(new VictorSP(0), !flipDriveTrain);
		DB_leftRear = new TorqueMotor(new VictorSP(0), !flipDriveTrain);
		
		FW_leftSole = new TorqueMotor(new VictorSP(0), flipShooter);
		FW_rightSole = new TorqueMotor(new VictorSP(0), flipShooter);
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
	public void setDriveFlyWheelSpeed(double leftSpeed, double rightSpeed) {
		FW_leftSole.set(leftSpeed);
		FW_rightSole.set(rightSpeed);
	}
	
	public static RobotOutput getInstance() {
		return instance == null ? instance = new RobotOutput() : instance;
	}

}
