package org.texastorque.auto.drive;

import org.texastorque.auto.AutonomousSequence;
import org.texastorque.auto.gear.PlaceGearSequence;

public class AirShipCenter extends AutonomousSequence {

	boolean dropGear;
	
	public AirShipCenter(boolean dropGear) {
		this.dropGear = dropGear;
	}
	
	@Override
	public void init() {
		commandList.add(new DistanceDrive(-76, .125));
		commandList.addAll(new PlaceGearSequence().getCommands());
	}
	
}
