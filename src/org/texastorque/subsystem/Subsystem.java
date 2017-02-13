package org.texastorque.subsystem;

import org.texastorque.interfaces.TorqueSubsystem;
import org.texastorque.io.Input;

public abstract class Subsystem implements TorqueSubsystem {

	protected Input i;
	
	public void setInput(Input i) {
		this.i = i;
	}
	
}
