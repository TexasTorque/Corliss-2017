package org.texastorque.auto.drive;

import org.texastorque.auto.AutoManager;
import org.texastorque.auto.AutonomousCommand;

public class TurnAngle extends AutonomousCommand {

	private double theta;
	private double precision;
	
	private final double TCONSTANT = .25;
	private final double DPRECISION = .0625;
	public TurnAngle(double theta, double precision) {
		this.theta = theta;
		this.precision = precision;
	}
	public TurnAngle(double theta) {
		this.theta = theta;
		precision = DPRECISION;
	}
	
	@Override
	public void run() {
		input.setDB_driveSetpoint(theta, precision);
		AutoManager.pause(theta*TCONSTANT);
	}
	
	@Override
	public void reset() {
		theta = 0;
	}
	
}
