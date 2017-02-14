package org.texastorque.io;

public class Input {

	private static Input instance;
	
	protected double DB_leftSpeed;
	protected double DB_rightSpeed;
	
	protected double FW_leftSpeed;
	protected double FW_rightSpeed;
	protected double FW_gateSpeed;
	
	protected double IN_speed;
	protected double TW_feederSpeed;
	
	protected double TW_leftSpeed;
	protected double TW_rightSpeed;
	
	protected double CL_speed;
	
	protected boolean GR_open;
	protected boolean GH_extended;
	
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
	
	public void setFW_leftSpeed(double leftSpeed) {
		FW_leftSpeed = leftSpeed;
	}
	
	public double getFW_leftSpeed() {
		return FW_leftSpeed;
	}
	
	public void setFW_rightSpeed(double rightSpeed) {
		FW_rightSpeed = rightSpeed;
	}
	
	public double getFW_rightSpeed() {
		return FW_rightSpeed;
	}

	public void setFW_gateSpeed(double gateSpeed) {
		FW_rightSpeed = gateSpeed;
	}
	
	public double getFW_gateSpeed() {
		return FW_gateSpeed;
	}
	
	public void setIN_speed(double speed) {
		IN_speed = speed;
	}
	
	public double getIN_speed() {
		return IN_speed;
	}
	
	public void setTW_rightSpeed(double rightSpeed) {
		TW_rightSpeed = rightSpeed;
	}
	
	public double getTW_rightSpeed() {
		return TW_rightSpeed;
	}
	
	public void setTW_leftSpeed(double leftSpeed) {
		TW_leftSpeed = leftSpeed;
	}
	
	public double getTW_leftSpeed() {
		return TW_leftSpeed;
	}

	public void setTW_feederSpeed(double feederSpeed) {
		TW_feederSpeed = feederSpeed;
	}
	
	public double getTW_feederSpeed() {
		return TW_feederSpeed;
	}
	
	public void setCL_speed(double speed) {
		CL_speed = speed;
	}
	
	public double getCL_speed() {
		return CL_speed;
	}
	
	public boolean getGR_open() {
		return GR_open;
	}
	
	public void setGR_open(boolean open) {
		GR_open = open;
	}
	
	public boolean getGH_extended() {
		return GH_extended;
	}
	
	public void setGH_extended(boolean extended) {
		GH_extended = extended;
	}
	
	public static Input getInstance() {
		return instance == null ? instance = new Input() : instance;
	}

}
