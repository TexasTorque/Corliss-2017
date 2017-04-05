package org.texastorque.auto.drive;

import org.texastorque.auto.AutonomousCommand;
import org.texastorque.io.RobotOutput;

public class DistanceDrive extends AutonomousCommand {
	
	private double distance;
	private double precision;
	
	public DistanceDrive(double distance, double precision) {
		this.distance = distance;
		this.precision = precision;
	}
	
	public void run() {
		input.setDB_driveSetpoint(distance, precision);
	}
	
	public void reset() {
		distance = 0;
	}

}
