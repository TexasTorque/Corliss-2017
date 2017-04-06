package org.texastorque.auto.shooter;

import org.texastorque.auto.AutonomousCommand;

public class RunShooter extends AutonomousCommand {

	private double setpoint;
	
	public enum Setpoints {
		LAYUP(3200), LONGSHOT(3200);
		
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
