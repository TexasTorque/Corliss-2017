package org.texastorque.torquelib.auto;

import java.util.HashMap;

public class ButtonTracker {

	private String fieldName;
	private HashMap<Double, Boolean> data;

	public ButtonTracker(String fieldName) {
		this.fieldName = fieldName;
		data = new HashMap<>();
	}

}
