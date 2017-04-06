package org.texastorque.auto.gear;

import org.texastorque.auto.AutonomousSequence;
import org.texastorque.auto.drive.DistanceDrive;

public class PlaceGearSequence extends AutonomousSequence {

	@Override
	public void init() {
		commandList.addLast(new ToggleGearHolder(true));
		commandList.addLast(new DistanceDrive(-12,.125));
		commandList.addLast(new ToggleGearHolder(false));
	}
	
}
