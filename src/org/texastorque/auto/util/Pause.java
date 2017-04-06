package org.texastorque.auto.util;

import org.texastorque.auto.AutoManager;
import org.texastorque.auto.AutonomousCommand;

public class Pause extends AutonomousCommand {

	private double waitTime;

	public Pause(double waitTime) {
		this.waitTime = waitTime;
	}

	@Override
	public void run() {
		AutoManager.pause(waitTime);
	}

	@Override
	public void reset() {
	}

}
