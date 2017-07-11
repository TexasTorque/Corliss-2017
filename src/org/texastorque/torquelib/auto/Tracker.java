package org.texastorque.torquelib.auto;

import org.texastorque.io.HumanInput;

public abstract class Tracker {

	protected Class HIClass;
	
	public Tracker() {
		HIClass = HumanInput.class;
	}
	
}
