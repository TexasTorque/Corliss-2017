package org.texastorque.auto.util;

import org.texastorque.auto.AutoManager;
import org.texastorque.auto.AutonomousCommand;

public class Pause extends AutonomousCommand {

	private double timewait;
	
	public Pause(double timewait) {
		this.timewait = timewait;
	}
	
	@Override
	public void run() {
		AutoManager.pause(timewait);
	}
	
	@Override
	public void reset() {
	}
	
}
