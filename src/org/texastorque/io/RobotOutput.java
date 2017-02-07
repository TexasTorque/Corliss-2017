package org.texastorque.io;

import org.texastorque.torquelib.component.TorqueMotor;

import edu.wpi.first.wpilibj.VictorSP;

public class RobotOutput {
	
	private static RobotOutput instance;
	
	private double DB_leftSpeed;
	private double DB_rightSpeed;
	
	private TorqueMotor DB_leftFore;
	private TorqueMotor DB_leftRear;
	private TorqueMotor DB_rightFore;
	private TorqueMotor DB_rightRear;
	
	private boolean flipDriveTrain = false;
	
	public RobotOutput() {
		DB_leftFore = new TorqueMotor(new VictorSP(0), flipDriveTrain);
		DB_leftRear = new TorqueMotor(new VictorSP(0), flipDriveTrain);

		DB_leftRear = new TorqueMotor(new VictorSP(0), !flipDriveTrain);
		DB_leftRear = new TorqueMotor(new VictorSP(0), !flipDriveTrain);
	}
	
	/**
	 * Set the motor speeds of the Robot's drivetrain.  Both input numbers should range from
	 * 1 => -1.
	 * @param leftSpeed - The speed the leftside motors should be set to.
	 * @param rightSpeed  - The speed the rightside motors should be set to.
	 */
	public void setDriveBaseSpeed(double leftSpeed, double rightSpeed) {
		
	}
	
	public static RobotOutput getInstance() {
		return instance == null ? instance = new RobotOutput() : instance;
	}

}
