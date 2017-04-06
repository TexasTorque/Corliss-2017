package org.texastorque.auto.gear;

import org.texastorque.auto.AutoManager;
import org.texastorque.auto.AutonomousCommand;

public class PlaceGear extends AutonomousCommand {

	@Override
	public void run() {
		output.extendGearHolder(true);
		AutoManager.pause(.0125);
	}
	
	@Override
	public void reset() {
	}
	
}
