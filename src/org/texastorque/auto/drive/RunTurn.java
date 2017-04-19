package org.texastorque.auto.drive;

import org.texastorque.auto.AutoManager;
import org.texastorque.auto.AutonomousCommand;
import org.texastorque.feedback.Feedback;
import org.texastorque.subsystem.DriveBase.DriveType;

public class RunTurn extends AutonomousCommand {

	private double theta;
	private double precision;
	
	private final double TCONSTANT = .05;
	private final double DPRECISION = .125;
	private double tTime = -999;
	
	public RunTurn(double theta, double precision, double tTime) {
		this.theta = theta;
		this.precision = precision;
		this.tTime = tTime;
	}
	public RunTurn(double theta) {
		this.theta = theta;
		precision = DPRECISION;
	}
	
	@Override
	public void run() {
		Feedback.getInstance().resetDB_gyro();
		Feedback.getInstance().resetDB_encoders();
		input.setDB_turnSetpoint(theta, precision);
		driveBase.setType(DriveType.AUTOTURN);
		if(tTime != -999)
			AutoManager.pause(tTime);
		else
			AutoManager.pause(theta*TCONSTANT);
	}
	
	@Override
	public void reset() {
		theta = 0;
	}
	
}
