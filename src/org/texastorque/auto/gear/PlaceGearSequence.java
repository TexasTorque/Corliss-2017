package org.texastorque.auto.gear;

import org.texastorque.auto.AutonomousSequence;
import org.texastorque.auto.drive.RunDrive;

public class PlaceGearSequence extends AutonomousSequence {

	public PlaceGearSequence() {
		init();
	}
	
	@Override
	public void init() {
		System.out.println("INITIALIZED");
		commandList.addLast(new ToggleGearHolder(true));
		commandList.addLast(new RunDrive(12));
		commandList.addLast(new ToggleGearHolder(false));
	}
	
}
