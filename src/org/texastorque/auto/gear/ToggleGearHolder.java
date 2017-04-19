package org.texastorque.auto.gear;

import org.texastorque.auto.AutoManager;
import org.texastorque.auto.AutonomousCommand;

public class ToggleGearHolder extends AutonomousCommand {

	boolean open;
	boolean pneumatic = false;
	
	public ToggleGearHolder(boolean open) {
		this.open = open;
	}
	
	@Override
	public void run() {
		if(pneumatic) {
			output.extendGearHolder(open);
			AutoManager.pause(.0125);
		} else {
			input.setGC_outake(open);
			if(open) {
				AutoManager.pause(.25);
				input.setGC_outakeSpeed(-1);
				AutoManager.pause(.25);
			} else {
				AutoManager.pause(.25);
			}
		}
	}
	
	@Override
	public void reset() {
	}
	
}
