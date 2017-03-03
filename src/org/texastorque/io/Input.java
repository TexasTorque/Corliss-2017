package org.texastorque.io;

public class Input {

	private static Input instance;
	
	protected double DB_leftSpeed;
	protected double DB_rightSpeed;
	protected boolean DB_shiftSole = true;
	
	protected boolean doRumble;
	
//	AUTO MODES
	protected volatile double DB_setpoint;
	protected volatile double DB_turnSetpoint;
	
	protected double FW_leftSetpoint;
	protected double FW_rightSetpoint;
	protected double FW_setpointShift;
	protected double FW_gateSpeed;
	protected boolean FW_hood;
	
	protected double IN_speed;
	protected double TW_feederSpeed;
	
	protected double TW_leftSpeed;
	protected double TW_rightSpeed;
	
	protected double CL_speed;
	
	protected boolean GR_open;
	protected boolean GH_extended;
	
	protected boolean flipCheck;
	
	public Input() {
		DB_leftSpeed = 0.0;
		DB_rightSpeed = 0.0;
	}

	public double getDB_leftSpeed() {
		return DB_leftSpeed;
	}

	public double getDB_rightSpeed() {
		return DB_rightSpeed;
	}
	
	public double getFW_leftSetpoint() {
		return FW_leftSetpoint;
	}
	
	public double getFW_rightSetpoint() {
		return FW_rightSetpoint;
	}
	
	public double getFW_gateSpeed() {
		return FW_gateSpeed;
	}
	
	public double getIN_speed() {
		return IN_speed;
	}
	
	
	public double getTW_rightSpeed() {
		return TW_rightSpeed;
	}
	
	public double getTW_leftSpeed() {
		return TW_leftSpeed;
	}

	public double getTW_feederSpeed() {
		return TW_feederSpeed;
	}
	
	public double getCL_speed() {
		return CL_speed;
	}
	
	public boolean getGR_open(){
		return GR_open;
	}
	
	public boolean getGH_extended(){
		return GH_extended;
	}
	
	public boolean getUpShift(){
		return DB_shiftSole;
	}

	public boolean getFW_hood() {
		return FW_hood;
	}
	
	public void setDB_driveSetpoint(double setpoint) {
		DB_setpoint = setpoint;
	}
	
	public double getDB_setpoint() {
		return DB_setpoint;
	}

	public void setDB_turnSetpoint(double setpoint) {
		DB_turnSetpoint = setpoint;
	}
	
	public double getDB_turnSetpoint() {
		return DB_turnSetpoint;
	}
	
	public void setRumble(boolean rumble) {
		doRumble = rumble;
	}
	
	public boolean flipCheck() {
		return flipCheck;
	}
	
	public static Input getInstance() {
		return instance == null ? instance = new Input() : instance;
	}

}
