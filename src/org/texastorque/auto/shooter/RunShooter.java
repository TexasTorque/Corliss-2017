package org.texastorque.auto.shooter;

import org.texastorque.auto.AutonomousCommand;
import org.texastorque.constants.Constants;

public class RunShooter extends AutonomousCommand {

	private double setpoint;
	
	public enum Setpoints {
		LAYUP(Constants.FW_LAYUPSHOT.getDouble()), LONGSHOT(Constants.FW_LONGSHOT.getDouble()+25), IDLE(0);
		
		Setpoints(double setpoint) {
			this.setpoint = setpoint;
		}
		
		private double setpoint;
		
		public double getSetpoint() {
			return setpoint;
		}
	}
	
	public RunShooter(double setpoint) {
		this.setpoint = setpoint;
	}
	
	public RunShooter(Setpoints setpoint) {
		this.setpoint = setpoint.getSetpoint();
	}
	
	@Override
	public void run() {
		input.setFW_leftSetpoint(setpoint);
		input.setFW_rightSetpoint(setpoint);
	}
	
	@Override
	public void reset() {
	}
	
}
