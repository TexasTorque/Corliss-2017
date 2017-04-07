package org.texastorque.auto.drive;

import org.texastorque.auto.AutonomousSequence;
import org.texastorque.auto.gear.PlaceGearSequence;

public class AirShipCenter extends AutonomousSequence {

	boolean dropGear;
	
	public AirShipCenter(boolean dropGear) {
		this.dropGear = dropGear;
		init();
	}
	
	@Override
	public void init() {
		commandList.add(new RunDrive(-76, .125));
		commandList.addAll(new PlaceGearSequence().getCommands());
	}
	
}
