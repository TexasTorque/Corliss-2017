package org.texastorque.torquelib.auto;

import java.io.File;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.util.HashMap;

public class StickTracker {

	private HashMap<Double, Double> driverLX;
	private HashMap<Double, Double> operatorLX;
	private HashMap<Double, Double> driverLY;
	private HashMap<Double, Double> operatorLY;

	private HashMap<Double, Double> driverRX;
	private HashMap<Double, Double> operatorRX;
	private HashMap<Double, Double> driverRY;
	private HashMap<Double, Double> operatorRY;

	private HashMap<Double, Boolean> driverClickLeft;
	private HashMap<Double, Boolean> operatorClickLeft;
	private HashMap<Double, Boolean> driverClickRight;
	private HashMap<Double, Boolean> operatorClickRight;

	public void update(double driverLX, double operatorLX, double driverLY,
			double operatorLY, double driverRX, double operatorRX,
			double driverRY, double operatorRY, boolean driverClickLeft,
			boolean operatorClickLeft, boolean driverClickRight,
			boolean operatorClickRight, double timeStamp) {
		
		this.driverLX.put(timeStamp, driverLX);
		this.driverLY.put(timeStamp, driverLY);
		this.driverRX.put(timeStamp, driverRX);
		this.driverRY.put(timeStamp, driverRY);
		this.driverClickLeft.put(timeStamp, driverClickLeft);
		this.driverClickRight.put(timeStamp, driverClickRight);

		this.operatorLX.put(timeStamp, operatorLX);
		this.operatorLY.put(timeStamp, operatorLY);
		this.operatorRX.put(timeStamp, operatorRX);
		this.operatorRY.put(timeStamp, operatorRY);
		this.operatorClickLeft.put(timeStamp, operatorClickLeft);
		this.operatorClickRight.put(timeStamp, operatorClickRight);

	}
	
	public void print() {
		try {
			String fileName = getClass().getCanonicalName();
			PrintWriter pw = new PrintWriter(new File(fileName));
			
			pw.println(driverLX);
			pw.println(driverLY);
			pw.println(driverRX);
			pw.println(driverRY);
			pw.println(driverClickLeft);
			pw.println(driverClickRight);

			pw.println(operatorLX);
			pw.println(operatorLY);
			pw.println(operatorRX);
			pw.println(operatorRY);
			pw.println(operatorClickLeft);
			pw.println(operatorClickRight);
		} catch (Exception e) {
			System.out.println("Error Trying to Cache Stick Tracker...");
		}
	}

}
