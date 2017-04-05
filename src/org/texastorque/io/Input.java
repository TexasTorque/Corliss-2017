package org.texastorque.io;

import org.texastorque.feedback.Feedback;

public class Input {

	private static Input instance;

	protected volatile double DB_setpoint;
	protected volatile double DB_turnSetpoint;
	protected double DB_leftSpeed;
	protected double DB_rightSpeed;
	protected boolean DB_shiftSole = true;

	protected double FW_leftSetpoint;
	protected double FW_rightSetpoint;
	protected double FW_setpointShift;
	protected double FW_gateSpeed;
	protected boolean FW_hood;

	protected double IN_lowerSpeed;
	protected double IN_upperSpeed;

	protected double TW_leftSpeed;
	protected double TW_rightSpeed;

	protected double CL_speed;

	protected boolean GR_open;
	protected boolean GH_extended;
	protected boolean GC_down;
	protected boolean GC_override;

	protected boolean flipCheck;
	protected boolean doRumble;

	public Input() {
		DB_leftSpeed = 0.0;
		DB_rightSpeed = 0.0;
	}
	
//	drivebase
	public double getDB_leftSpeed() {
		return DB_leftSpeed;
	}

	public double getDB_rightSpeed() {
		return DB_rightSpeed;
	}
	
	public double getDB_setpoint() {
		return DB_setpoint;
	}

	public double getDB_turnSetpoint() {
		return DB_turnSetpoint;
	}
	
	public boolean getUpShift() {
		return DB_shiftSole;
	}

//  flywheel
	public double getFW_leftSetpoint() {
		return FW_leftSetpoint;
	}

	public double getFW_rightSetpoint() {
		return FW_rightSetpoint;
	}

	public double getFW_gateSpeed() {
		return FW_gateSpeed;
	}

	public boolean getFW_hood() {
		return FW_hood;
	}

//	intake
	public double getIN_lowerSpeed() {
		return IN_lowerSpeed;
	}
	
	public double getIN_upperSpeed() {
		return IN_upperSpeed;
	}

//	twinsters
	public double getTW_rightSpeed() {
		return TW_rightSpeed;
	}

	public double getTW_leftSpeed() {
		return TW_leftSpeed;
	}

//	climber
	public double getCL_speed() {
		return CL_speed;
	}

//	gear
	public boolean getGR_open() {
		return GR_open;
	}

	public boolean getGH_extended() {
		return GH_extended;
	}

	public double getGC_setpoint() {
		return GC_down ? 90 : -10;
	}

	public boolean getGC_down() {
		return GC_down;
	}

//	drivebase
	public void setDB_driveSetpoint(double setpoint) {
		DB_setpoint = setpoint;
	}
	
	public void setDB_turnSetpoint(double setpoint) {
		DB_turnSetpoint = setpoint + Feedback.getInstance().getDB_angle();
	}

//	flywheel
	public void setFW_leftSetpoint(double speed) {
		FW_leftSetpoint = speed;
	}
	
	public void setFW_rightSetpoint(double speed) {
		FW_rightSetpoint = speed;
	}

	public void setFW_gateSpeed(double speed) {
		FW_gateSpeed = speed;
	}

//	intake
	public void setIN_lowerSpeed(double speed) {
		IN_lowerSpeed = speed;
	}
	
	public void setIN_upperSpeed(double speed) {
		IN_upperSpeed = speed;
	}
	
//	twinsters
	public void setTW_rightSpeed(double speed) {
		TW_rightSpeed = speed;
	}

	public void setTW_leftSpeed(double speed) {
		TW_leftSpeed = speed;
	}

//	gear
	public void setGC_down(boolean down) {
		GC_down = down;
	}

//	misc
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