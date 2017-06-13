package org.texastorque.auto.drive;

import org.texastorque.auto.AutonomousSequence;
import org.texastorque.auto.gear.PlaceGearSequence;
import org.texastorque.auto.util.Pause;

import edu.wpi.first.wpilibj.DriverStation;

public class AirShipCenter extends AutonomousSequence {

	boolean dropGear;
	
	public AirShipCenter(boolean dropGear) {
		this.dropGear = dropGear;
		init();
	}
	
	@Override
	public void init() {
		switch(DriverStation.getInstance().getAlliance()) {
			case Red:
				commandList.add(new RunDrive(-78.5, .0125, 1.25));
				break;
			case Blue:
				commandList.add(new RunDrive(-77, .0125, 1.25));
				break;
		}
		commandList.addAll(new PlaceGearSequence().getCommands());
	}
	
}
