package org.texastorque.auto.drive;

import org.texastorque.auto.AutoManager;
import org.texastorque.auto.AutonomousCommand;

public class DistanceDrive extends AutonomousCommand {
	
	private double distance;
	private double precision;
	
	private final double DCONSTANT = .75;
	private final double DPRECISION = .125;
	
	public DistanceDrive(double distance, double precision) {
		this.distance = distance;
		this.precision = precision;
	}
	
	public DistanceDrive(double distance) {
		this.distance = distance;
		precision = DPRECISION;
	}
	
	public void run() {
		input.setDB_driveSetpoint(distance, precision);
		AutoManager.pause(distance*DCONSTANT);
	}
	
	public void reset() {
		distance = 0;
	}

}
