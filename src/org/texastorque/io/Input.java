package org.texastorque.io;

public class Input {

	private static Input instance;

	protected double DB_leftSpeed;
	protected double DB_rightSpeed;
	protected boolean DB_shiftSole = true;

	protected boolean doRumble;

	// AUTO MODES
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
	protected boolean GC_down;
	
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

	public void setFW_leftSetpoint(double speed) {
		FW_leftSetpoint = speed;
	}

	public double getFW_rightSetpoint() {
		return FW_rightSetpoint;
	}

	public void setFW_rightSetpoint(double speed) {
		FW_rightSetpoint = speed;
	}

	public double getFW_gateSpeed() {
		return FW_gateSpeed;
	}

	public void setFW_gateSpeed(double speed) {
		FW_gateSpeed = speed;
	}

	public double getIN_speed() {
		return IN_speed;
	}

	public double getTW_rightSpeed() {
		return TW_rightSpeed;
	}

	public void setTW_rightSpeed(double speed) {
		TW_rightSpeed = speed;
	}

	public double getTW_leftSpeed() {
		return TW_leftSpeed;
	}

	public void setTW_leftSpeed(double speed) {
		TW_leftSpeed = speed;
	}

	public double getTW_feederSpeed() {
		return TW_feederSpeed;
	}

	public void setTW_feederSpeed(double speed) {
		TW_feederSpeed = speed;
	}

	public double getCL_speed() {
		return CL_speed;
	}

	public boolean getGR_open() {
		return GR_open;
	}

	public boolean getGH_extended() {
		return GH_extended;
	}
	
	public boolean getGC_down() {
		return GC_down;
	}

	public boolean getUpShift() {
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