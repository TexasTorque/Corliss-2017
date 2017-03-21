package org.texastorque.subsystem;

import org.texastorque.interfaces.TorqueSubsystem;
import org.texastorque.io.Input;

public abstract class Subsystem implements TorqueSubsystem {

	protected Input in;
	
	public void setInput(Input in) {
		this.in = in;
	}
}
