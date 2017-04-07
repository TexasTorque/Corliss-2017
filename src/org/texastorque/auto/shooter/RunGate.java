package org.texastorque.auto.shooter;

import org.texastorque.auto.AutonomousCommand;

public class RunGate extends AutonomousCommand {

	private double gateSpeed;
	
	public RunGate(double gateSpeed) {
		this.gateSpeed = gateSpeed;
	}
	
	@Override
	public void run() {
		output.setGateSpeed(gateSpeed);
	}
	
	@Override
	public void reset() {
	}
}
