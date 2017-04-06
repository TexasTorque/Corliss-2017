package org.texastorque.auto.twinsters;

import org.texastorque.auto.AutonomousCommand;

public class RunTwinster extends AutonomousCommand {

	private double speed;
	
	public RunTwinster(double speed) {
		this.speed = speed;
	}
	
	@Override
	public void run() {
		output.setTwinstersSpeed(speed, speed);
	}
	
	@Override
	public void reset() {
	}
	
}
