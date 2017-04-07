package org.texastorque.auto.drive;

import org.texastorque.auto.AutoManager;
import org.texastorque.auto.AutonomousCommand;
import org.texastorque.feedback.Feedback;
import org.texastorque.subsystem.DriveBase.DriveType;

public class RunDrive extends AutonomousCommand {
	
	private double distance;
	private double precision;
	
	private final double DCONSTANT = .028;
	private final double DPRECISION = .125;
	
	public RunDrive(double distance, double precision) {
		this.distance = distance;
		this.precision = precision;
	}
	
	public RunDrive(double distance) {
		this.distance = distance;
		precision = DPRECISION;
	}
	
	public void run() {
		Feedback.getInstance().resetDB_gyro();
		Feedback.getInstance().resetDB_encoders();
		output.upShift(false);
		input.setDB_driveSetpoint(distance, precision);
		driveBase.setType(DriveType.AUTODRIVE);
		AutoManager.pause(distance*DCONSTANT);
	}
	
	public void reset() {
		distance = 0;
	}

}
