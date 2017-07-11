package org.texastorque.torquelib.auto;

import java.util.HashMap;

public class ButtonTracker extends Tracker {

	private String fieldName;
	private HashMap<Double, ButtonState> data;

	public ButtonTracker(String fieldName) {
		this.fieldName = fieldName;
		data = new HashMap<>();
	}

}
