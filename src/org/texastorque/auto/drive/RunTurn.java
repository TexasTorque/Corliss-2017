package org.texastorque.auto.drive;

import org.texastorque.auto.AutoManager;
import org.texastorque.auto.AutonomousCommand;
import org.texastorque.feedback.Feedback;
import org.texastorque.subsystem.DriveBase.DriveType;

public class RunTurn extends AutonomousCommand {

	private double theta;
	private double precision;
	
	private final double TCONSTANT = .25;
	private final double DPRECISION = .0625;
	public RunTurn(double theta, double precision) {
		this.theta = theta;
		this.precision = precision;
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
		AutoManager.pause(theta*TCONSTANT);
	}
	
	@Override
	public void reset() {
		theta = 0;
	}
	
}
