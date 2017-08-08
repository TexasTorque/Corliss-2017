package org.texastorque.auto.gear;

import org.texastorque.auto.AutonomousSequence;
import org.texastorque.auto.drive.RunDrive;
import org.texastorque.auto.util.Pause;

public class PlaceGearSequence extends AutonomousSequence {

	public PlaceGearSequence() {
		init();
	}
	
	@Override
	public void init() {
		commandList.addLast(new ToggleGearHolder(true));
		commandList.addLast(new Pause(1.25));
		commandList.addLast(new RunDrive(40, .125, 1));
		commandList.addLast(new ToggleGearHolder(false));
	}
	
}
