package org.texastorque.torquelib.auto;

import java.io.File;
import java.io.PrintWriter;
import java.util.HashMap;

public class ButtonTracker {

	private HashMap<Double, Boolean> driverData;
	private HashMap<Double, Boolean> operatorData;

	public void update(boolean driverData, boolean operatorData, double timestamp) {
		this.driverData.put(timestamp, driverData);
		this.operatorData.put(timestamp, operatorData);
	}
	
	public void print() {
		try {
			String fileName = getClass().getCanonicalName();
			PrintWriter pw = new PrintWriter(new File(fileName));
			
			pw.println(driverData);
			pw.println(operatorData);
			
		} catch (Exception e) {
			System.out.println("Error Trying to Cache Button Tracker...");
		}
	}
	
}
