package org.texastorque.torquelib.auto;

import java.util.HashMap;

public class StickTracker extends Tracker{

	private String fieldName;
	private HashMap<Double, Double> data;
	
	public StickTracker(String fieldName) {
		this.fieldName= fieldName;
		data = new HashMap<>();
	}
	
	
}
