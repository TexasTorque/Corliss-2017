package org.texastorque.io;

import org.texastorque.torquelib.base.TorqueClass;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Input implements TorqueClass {

	private static Input instance;
	
	private double DB_leftSpeed;
	private double DB_rightSpeed;
	
	public Input() {
		DB_leftSpeed = 0.0;
		DB_rightSpeed = 0.0;
	}

	public void setDB_leftSpeed(double leftSpeed) {
		DB_leftSpeed = leftSpeed;
	}
	
	public double getDB_leftSpeed() {
		return DB_leftSpeed;
	}

	public void setDB_rightSpeed(double rightSpeed) {
		DB_rightSpeed = rightSpeed;
	}
	
	public double getDB_rightSpeed() {
		return DB_rightSpeed;
	}
	
	@Override
	public void smartDashboard() {
		SmartDashboard.putNumber("DB_LeftSpeed", DB_leftSpeed);
		SmartDashboard.putNumber("DB_RightSpeed", DB_rightSpeed);
	}
	
	public static Input getInstance() {
		return instance == null ? instance = new Input() : instance;
	}

}
