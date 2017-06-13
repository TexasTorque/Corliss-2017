package org.texastorque.io;

import org.texastorque.feedback.Feedback;

public class Input {

	private static Input instance;

	protected double DB_leftSpeed;
	protected double DB_rightSpeed;
	protected boolean DB_shiftSole = true;
	protected boolean DB_runningVision = false;
	protected boolean VI_rpmsGood = false;
	
	protected volatile double DB_setpoint;
	protected volatile double DB_turnSetpoint;
	protected volatile double DB_precision;

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
	protected boolean GC_reset;
	protected boolean hasGC_reset = false;
	protected boolean G_press = false;
	protected boolean GC_outake;
	protected boolean GC_intake;
	protected double GC_outakeSpeed;

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
	
	public double getDB_precision() {
		return DB_precision;
	}
	
	public boolean getUpShift() {
		return DB_shiftSole;
	}

//  flywheel
	public double getFW_leftSetpoint() {
		return FW_leftSetpoint-175;
	}

	public double getFW_rightSetpoint() {
		return FW_rightSetpoint-175;
	}

	public double getFW_gateSpeed() {
		return FW_gateSpeed;
	}

	public boolean getFW_hood() {
		return FW_hood;
	}
	
	public boolean getG_press() {
		return G_press;
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
		return GC_down ? 110 : 2;
	}
	
	public double getGC_outakeSetpoint() {
		return GC_outake ? 60 : 0;
	}

	public boolean getGC_down() {
		return GC_down;
	}
	
	public boolean getGC_outake() {
		return GC_outake;
	}
	
	public double getGC_outakeSpeed() {
		return GC_outakeSpeed;
	}
	
	public boolean getGC_intake() {
		return GC_intake;
	}
	
	public boolean getGC_reset() {
		return GC_reset;
	}
	
//	drivebase
	public void setDB_driveSetpoint(double setpoint, double precision) {
		DB_setpoint = setpoint;
		DB_precision = precision;
	}
	
	public void setDB_turnSetpoint(double setpoint, double precision) {
		DB_turnSetpoint = setpoint + Feedback.getInstance().getDB_angle();
		DB_precision = precision;
	}
	
	public void setUpShift(boolean shift) {
		DB_shiftSole = shift;
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
	
	public void setGC_outake(boolean out) {
		GC_outake = out;
	}
	
	public void setGC_outakeSpeed(double speed) {
		GC_outakeSpeed = speed;
	}

//	misc
	public void setRumble(boolean rumble) {
		doRumble = rumble;
	}
	
	public void setVI_rpmsGood(boolean good) {
		VI_rpmsGood = good;
	}
	
	public boolean getVI_rpmsGood() {
		return VI_rpmsGood;
	}

	public boolean flipCheck() {
		return flipCheck;
	}

	public static Input getInstance() {
		return instance == null ? instance = new Input() : instance;
	}

}