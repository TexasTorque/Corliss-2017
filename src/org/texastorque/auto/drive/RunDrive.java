package org.texastorque.auto.drive;

import org.texastorque.auto.AutoManager;
import org.texastorque.auto.AutonomousCommand;
import org.texastorque.feedback.Feedback;
import org.texastorque.subsystem.DriveBase.DriveType;

public class RunDrive extends AutonomousCommand {
	
	private double distance;
	private double precision;
	
	private final double DCONSTANT = .056;
	private final double DPRECISION = .125;
	private double dTime = -999;
	
	public RunDrive(double distance, double precision, double time) {
		this.distance = distance;
		this.precision = precision;
		dTime = time;
	}
	
	public RunDrive(double distance, double precision) {
		this.distance = distance;
		this.precision = precision;
	}
	
	public RunDrive(double distance) {
		this.distance = distance;
		precision = DPRECISION;
	}
	
	public void run() {
		output.upShift(false);
		Feedback.getInstance().resetDB_gyro();
		Feedback.getInstance().resetDB_encoders();
		output.upShift(false);
		input.setDB_driveSetpoint(distance, precision);
		driveBase.setType(DriveType.AUTODRIVE);
		if(dTime != -999)
			AutoManager.pause(dTime);
		else
			AutoManager.pause(distance*DCONSTANT);
	}
	
	public void reset() {
		distance = 0;
	}

}
