package org.texastorque.auto.gear;

import org.texastorque.auto.AutoManager;
import org.texastorque.auto.AutonomousCommand;

public class ToggleGearHolder extends AutonomousCommand {

	boolean open;
	
	public ToggleGearHolder(boolean open) {
		this.open = open;
	}
	
	@Override
	public void run() {
		output.extendGearHolder(open);
		AutoManager.pause(.0125);
	}
	
	@Override
	public void reset() {
	}
	
}
